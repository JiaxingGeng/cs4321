package cs4321.project2.operator;

import java.io.IOException;
import java.util.List;

import cs4321.project2.deparser.OnSelectItemVisitor;
import net.sf.jsqlparser.schema.*;
import net.sf.jsqlparser.statement.select.OrderByElement;
import net.sf.jsqlparser.statement.select.SelectItem;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Comparator;

/**
 * Sort Operator that sort the elements according to certain orders.
 * It is used for operations ORDER BY and DISTINCT. The sorting order
 * is always ascending. The tuple is first ordered by the attributes
 * specified in ORDER BY and then ordered by the order they appeared 
 * in the tuples. NOTE: our sort operator supports the ordering of
 * columns that doensn't appear in the select item and we put sort 
 * operator after projection operator for query plan generation.
 * @author Jiaxing Geng (jg755), Yangyi Hao (yh326)
 *
 */
public class SortOperator extends Operator {

	private HashMap<String,Integer> colToIndexHash;
	private LinkedList<String[]> bufferList; // list that stores the sorted result
	private Integer currOutputIndex;

	/**
	 * Constructor for Sort Operator
	 * @param c child operator
	 * @param orderByElements 
	 * 			there can be two possibilities: SelctItem list to support
	 * distinct operation and OrderByElements list to support sort operation.
	 * For distinct, the input should be the items in DISTINCT ON(..) or the
	 * items after SELCT DISTINCT.(See PlanGernator for outside implementation)
	 * @throws IOException
	 */
	public SortOperator(Operator c, List<?> orderByElements, List<?> selectItems) throws IOException{
		super.columns = c.getColumns();
		colToIndexHash = this.getColumnsHash();
		currOutputIndex = 0;
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
		for (Object item:selectItems){
			SelectItem selectItem =(SelectItem) item;
			selectItem.accept(visitor);
		}
		LinkedList<Integer> pos2 = (LinkedList<Integer>) visitor.getResult();
		if (pos2!=null){
			for (int j=0;j<pos2.size();j++){
				int index = pos2.get(j);
				if (!pos.contains(index)){
					pos.add(index);
					posTemp.set(index, null);
				}
			}
		}
		for (int j=0;j<posTemp.size();j++){
			if(posTemp.get(j)!=null) pos.add(posTemp.get(j));
		}
		Tuple t;
		bufferList = new LinkedList<>();
		while((t=c.getNextTuple())!=null){			
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

	/**
	 * Get the next tuple from this operator 
	 * 	 * @return Tuple after operation 
	 */
	@Override
	public Tuple getNextTuple() throws IOException {
		if (currOutputIndex < bufferList.size()) 
			return new Tuple(bufferList.get(currOutputIndex++));
		else return null;	}

	/**
	 * Reset the operator
	 */
	@Override
	public void reset() throws IOException {
		currOutputIndex = 0;
	}

}
