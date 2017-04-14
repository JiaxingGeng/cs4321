package cs4321.project3.operator.physical;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import cs4321.project2.Catalog;
import cs4321.project2.deparser.OnSelectItemVisitor;
import cs4321.project2.operator.*;
import cs4321.project3.IO.*;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.select.OrderByElement;
import net.sf.jsqlparser.statement.select.SelectItem;
/**
 * Physical Operator for External Sort
 * @author Jiaxing Geng (jg755), Yangyi Hao (yh326)
 *
 */
public class ExternalSortOperator extends Operator{
	// id is used to avoid reading same file across different
	// sorting operator. Operators between queries also have
	// different id number.
	private static int currentID;
	private int id;
	
	private TupleReader bf;	
	private static final int PAZE_SIZE = 4096;
	private String tempdir = "temp";
	private String dataPath;
	LinkedList<Integer> pos;
	private boolean sortedFiles;
	private Operator op;
	private int bufferSize; 

	public ExternalSortOperator
	(Operator c, List<?> orderByElements, List<?> selectItems,int bufferSize) throws IOException{
		id = currentID;
		currentID ++;
		Catalog cat = Catalog.getInstance();
		tempdir = cat.getTempDir();
		super.columns = c.getColumns();
		pos = getPositionOrder(orderByElements,selectItems);
		op = c;
		this.bufferSize = bufferSize;
		sortedFiles = false;
	}


	/**
	 * Sort the file and store result in temp directory if unsorted. 
	 * Then get the next tuple from this operator. 
	 * 	 * @return Tuple after operation 
	 */
	@Override
	public Tuple getNextTuple() throws IOException {
		if (!sortedFiles){
			int lastPass = ExternalSort(op,bufferSize,pos);
			File sortDir = new File(tempdir+File.separator+id);
			if (!sortDir.exists()) {
				sortDir.mkdirs();
			}
			dataPath = 
					tempdir+File.separator+id+File.separator+"P"+lastPass+File.separator+"0"; 
			bf = new BinaryReader(dataPath);
			sortedFiles = true;
		}
		
		String currentLine = bf.readLine();
		if (currentLine == null) {
			bf.close();	
			return null;
		}
		else {
			Tuple tp = new Tuple(currentLine.split(","));
			return tp;
		}
	}

	/**
	 * Reset the operator
	 */
	@Override
	public void reset() throws IOException {
		if (bf == null)  
			bf = new BinaryReader(dataPath);
		else {
			bf.close();
			bf = new BinaryReader(dataPath);
		}
	}
	
	/**
	 * Comparator to compare tuples
	 */
	private class CompareTuple implements Comparator<Tuple>{
		private LinkedList<Integer> pos;

		public CompareTuple(LinkedList<Integer> pos) {
			this.pos = pos;
		}

		public int compare(Tuple t1,Tuple t2){
			String[] s1 = t1.getAttributes();
			String[] s2 = t2.getAttributes();
			for(int i=0; i<pos.size(); i++){
				int num1 = Integer.parseInt(s1[pos.get(i)]);
				int num2 = Integer.parseInt(s2[pos.get(i)]);
				if (num1<num2) return -1;
				if (num1>num2) return 1;
			}	
			return 0;
		}		
	}
	/**
	 * Comparator to compare readerNode, a list-like data structure
	 * that can be seen in the following. This comparator is used for
	 * priority queue in the merge pass. 
	 */
	private class CompareBlocks implements Comparator<ReaderNode>{
		
		private CompareTuple comp;
		public CompareBlocks(LinkedList<Integer> pos) {
			comp = new CompareTuple(pos);
		}
		
		public int compare(ReaderNode r1, ReaderNode r2){
			try{
			Tuple t1 = new Tuple(r1.getVal().split(","));
			Tuple t2 = new Tuple(r2.getVal().split(","));
			return (comp.compare(t1, t2));
			}
			catch (Exception e){e.printStackTrace();}
			return 0;
		}
		
	}
	/**
	 * Give a list of order that will be used for sort. If orderbyElements
	 * exist, it go to the first. The second priority is selectItems. The 
	 * corresponding index position will be added to the order list.
	 * @param orderByElements elements from ORDER BY
	 * @param selectItems elements from SELECT
	 * @return position order
	 */
	private LinkedList<Integer> getPositionOrder
	(List<?> orderByElements, List<?> selectItems){
		HashMap<String,Integer> colToIndexHash = this.getColumnsHash();
		OnSelectItemVisitor visitor = new OnSelectItemVisitor(colToIndexHash);
		LinkedList<Integer> pos = new LinkedList<>();
		LinkedList<Integer> posTemp = new LinkedList<>();  // temporary storage
		for (int j=0;j<columns.length;j++) posTemp.add(j);	
		if (orderByElements!=null){
			for (int i=0;i<orderByElements.size();i++){
				OrderByElement oElement = (OrderByElement) orderByElements.get(i);
				Column column = (Column) oElement.getExpression();
				String columnName = column.getColumnName(); 
				String tableName = column.getTable().getName();
				String alias = column.getTable().getAlias();
				if (alias != null) tableName = alias;
				pos.add(colToIndexHash.get(tableName+"."+columnName));
				posTemp.set(colToIndexHash.get(tableName+"."+columnName),null);
			}			
		}
		if (selectItems!=null){
			for (Object item:selectItems){
				SelectItem selectItem =(SelectItem) item;
				selectItem.accept(visitor);
			}
			LinkedList<Integer> pos2 = (LinkedList<Integer>) visitor.getResult();
			if (pos2!=null){
				for (int j=0;j<pos2.size();j++){
					int index = pos2.get(j);
					if (!pos.contains(index)) {
						pos.add(index);
						posTemp.set(index, null);
					}
				}
			}
		}
		for (int j=0;j<posTemp.size();j++){
			if(posTemp.get(j)!=null) pos.add(posTemp.get(j));
		}
		return pos;
	}
	/**
	 * External Sort algorithm. In Pass 0, we load B blocks in memory, sort
	 * the data and sort it in temp directory. In the later Pass, we load B-1
	 * sorted blocks, sort them and store the result, until there is only 1 file
	 * in the current output directory. When merge B-1 sorted blocks, I use a 
	 * priority queue data structure. This allows the minimum value of current B-1
	 * blocks to be at the top all the time. To sort these blocks, we pull the top
	 * node, get its value and corresponding block. Then we read the next value of
	 * this block and push it back to the queue.
	 * @param op child operator
	 * @param bufferSize buffersize for external sort
	 * @param pos list of orders
	 * @return number of last pass
	 * @throws IOException
	 */
	private int ExternalSort
	(Operator op, int bufferSize,LinkedList<Integer> pos) throws IOException{
		// Pass 0
		int tuplesScanR0 = bufferSize*((PAZE_SIZE - 8)/(4*columns.length));
		int remainingTuples = tuplesScanR0;
		int blockNumber = 0;
		Tuple t = op.getNextTuple();
		LinkedList<Tuple> bufferList = new LinkedList<>();
		File pass0Dir = new File(tempdir+File.separator+id+File.separator+"P0");
		pass0Dir.mkdirs();
		while (t!=null){
			if (remainingTuples==0){
				CompareTuple comp = new CompareTuple(pos);
				Collections.sort(bufferList, comp);	       // sort the blocks
				// write to temporary results to tempdir
				BinaryWriter writer = 
						new BinaryWriter(tempdir+File.separator+id+File.separator+"P0"+File.separator+blockNumber);
				for (Tuple tuple:bufferList){
					writer.write(tuple);
				}
				writer.close();
				// scan new blocks
				blockNumber++;
				remainingTuples = tuplesScanR0;
				bufferList.clear();			
			} else {
				remainingTuples --;
				bufferList.add(t);
				t = op.getNextTuple();
			}	
		}
		if (bufferList.size()>0){
			CompareTuple comp = new CompareTuple(pos);
			Collections.sort(bufferList, comp);	       // sort the blocks
			BinaryWriter writer = 
					new BinaryWriter(tempdir+File.separator+id+File.separator+"P0"+File.separator+blockNumber);
			for (Tuple tuple:bufferList){
				writer.write(tuple);
			}
			writer.close();		
		}
		if (blockNumber ==0) return 0;
		
		// Remaining Pass
		int pass = 1;
		boolean lastPass = false;
		while (!lastPass){
			
			// get readers for all files
			String sortedPassDir = tempdir+File.separator+id+File.separator+"P"+(pass-1);
			File f = new File(sortedPassDir);
			File[] files = f.listFiles();
			LinkedList<ReaderNode> rdList = new LinkedList<>();
			for (int i=0;i<files.length;i++){
				File file = files[i];
				String fileName = file.toString();			
				if (Character.isDigit(fileName.charAt(fileName.length()-1))){
					BinaryReader rd = new BinaryReader(file.toString());
					rdList.add(new ReaderNode(rd));
				}
			}
			ReaderNode[] readers = rdList.toArray(new ReaderNode[rdList.size()]);
			
			// Use Priority Queue to Merge B-1 blocks
			int remainingFileNum = readers.length;
			int filePos = 0;
			int fileNum = 0;
			File passDir = new File(tempdir+File.separator+id+File.separator+"P"+pass);
			passDir.mkdirs();
			while (remainingFileNum>0){
				String newpass = tempdir+File.separator+id+File.separator+"P"+pass+File.separator+fileNum;
				BinaryWriter writer = new BinaryWriter(newpass);
				int numFileToSort;
				if (remainingFileNum >= bufferSize-1) 
					numFileToSort = bufferSize-1;
				else numFileToSort = remainingFileNum;
				
				CompareBlocks comp = new CompareBlocks(pos); 
				PriorityQueue<ReaderNode> queue = new PriorityQueue<>(numFileToSort,comp);
				for (int j=0;j<numFileToSort;j++){
					queue.add(readers[filePos+j]);
				}
				while (!queue.isEmpty()){
					ReaderNode curr = queue.poll();
					Tuple sTuple = new Tuple(curr.getVal().split(","));
					writer.write(sTuple);
					curr = curr.getNext();
					if (curr.getVal() !=null) queue.add(curr);			
				}
				remainingFileNum = remainingFileNum - numFileToSort;
				filePos = filePos + numFileToSort;
				fileNum++;
				writer.close();
			}
			if (readers.length<=bufferSize-1) lastPass = true;	
			pass++;
		}	
		pass--;
		return pass;
		
	}
	
	/**
	 * Wrap BinaryReader class to a list-like class which stores the
	 * current output. This makes the output result easy to be compared
	 * between different reader class.
	 */
	public class ReaderNode{
		BinaryReader reader;
		String val;
		public ReaderNode(BinaryReader reader) throws IOException{
			this.reader = reader;
			val = reader.readLine();
		}
		/**
		 * Get current value stored in the node
		 * @return tuple values in string representation
		 */
		public String getVal(){
			return val;
		}
		/**
		 * Read the next tuple and return the pointer of the node
		 * that stores that tuple 
		 * @return next node
		 * @throws IOException
		 */
		public ReaderNode getNext() throws IOException{
			if (val == null) {
				reader.close();
				return null;
			}
			else return new ReaderNode(reader);
		}
	}

}
