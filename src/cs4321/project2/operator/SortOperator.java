package cs4321.project2.operator;

import java.io.IOException;
import java.util.List;
import net.sf.jsqlparser.schema.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Comparator;

public class SortOperator extends Operator {
	
	private HashMap<String,Integer> colToIndexHash;
	private LinkedList<String[]> bufferList;
	private Operator child;
	private Integer currOutputIndex;
	
	public SortOperator(Operator c, List<?> orderByElements) throws IOException{
		child = c;
		super.columns = c.getColumns();
		colToIndexHash = this.getColumnsHash();
		currOutputIndex = 0;
		
		LinkedList<Integer> pos = new LinkedList<>();
		for (int i=0;i<orderByElements.size();i++){
			Column column = (Column) orderByElements.get(i);
			//column.setColumnName((String) orderByElements.get(i));
			//column.setTable(null); // might need to change
			String columnName = column.getColumnName(); 
			String tableName = column.getTable().getName();
			String alias = column.getTable().getAlias();
			if (alias != null) tableName = alias;
			//System.out.println("tableName is: " + tableName);
			//System.out.println(colToIndexHash.get(tableName+"."+columnName));
			pos.add(colToIndexHash.get(tableName+"."+columnName));
		}
		//System.out.println("the size of pos is: " + pos.size());
		//System.out.println("printing pos");
		//System.out.println(pos.getLast());
		
		Tuple t;
		bufferList = new LinkedList<>();
		while((t=c.getNextTuple())!=null){
			/*
			String[] attributes = t.getAttributes();
			//System.out.println(Arrays.toString(attributes));
			LinkedList<String> intTuple = new LinkedList<>();
			for(int i=0;i<pos.size();i++){
				
				//System.out.println("pos.get(i) is: " + pos.get(0) + " at i = " + i);
				//System.out.println("attributes[pos.get(i)] is: " + attributes[pos.get(i)]);
				intTuple.add(attributes[pos.get(i)]);
			
			}
			String[] intArray = intTuple.toArray(new String[intTuple.size()]);
			System.out.println("pushed: " + Arrays.toString(intArray));
			bufferList.add(intArray);
			*/
			
			bufferList.add(t.getAttributes());
		}	
		CompareTuple comp = new CompareTuple(pos);	
		Collections.sort(bufferList, comp);	
	}
	
	private class CompareTuple implements Comparator<String[]>{
		private LinkedList<Integer> pos;
		
		public CompareTuple(LinkedList<Integer> pos) {
			this.pos = pos;
		}
		
		public int compare(String[] s1, String[] s2){
			for(int i=0; i<pos.size(); i++){
				int num1 = Integer.parseInt(s1[pos.get(i)]);
				int num2 = Integer.parseInt(s2[pos.get(i)]);
				if (num1<num2) return -1;
				if (num1>num2) return 1;
			}	
			return 0;
		}		
	}
	
	@Override
	public Tuple getNextTuple() throws IOException {
		if (currOutputIndex < bufferList.size()) return new Tuple(bufferList.get(currOutputIndex++));
		else return null;
		//if(bufferList.isEmpty()) return null;
		//System.out.println("pop: " + Arrays.toString(bufferList.peek()));
		//return new Tuple(bufferList.pop());
	}

	@Override
	public void reset() throws IOException {
		currOutputIndex = 0;
		//child.reset();
	}

}
