package cs4321.project2.operator;

import java.io.IOException;
import java.util.List;
import net.sf.jsqlparser.schema.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Comparator;

public class SortOperator extends Operator {
	
	private HashMap<String,Integer> colToIndexHash;
	private LinkedList<String[]> bufferList;
	private Operator child;
	
	public SortOperator(Operator c, List<?> orderByElements) throws IOException{
		child = c;
		super.columns = c.getColumns();
		colToIndexHash = this.getColumnsHash();
		
		LinkedList<Integer> pos = new LinkedList<>();
		for (int i=0;i<orderByElements.size();i++){
			Column column = (Column) orderByElements.get(i);
			String columnName = column.getColumnName(); 
			String tableName = column.getTable().getName();
			pos.add(colToIndexHash.get(tableName+"."+columnName));
		}
		
		Tuple t;
		bufferList = new LinkedList<>();
		while((t=c.getNextTuple())!=null){
			String[] attributes = t.getAttributes();
			for(int i=0;i<pos.size();i++){
				LinkedList<String> intTuple = new LinkedList<>();
				intTuple.add(attributes[pos.get(i)]);
				String[] intArray = intTuple.toArray(new String[intTuple.size()]);
				bufferList.add(intArray);
			}
		}	
		CompareTuple comp = new CompareTuple();	
		Collections.sort(bufferList, comp);	
	}
	
	private class CompareTuple implements Comparator<String[]>{
		public int compare(String[] s1,String[]s2){
			for(int i=0;i<s1.length;i++){
				int num1 = Integer.parseInt(s1[i]);
				int num2 = Integer.parseInt(s2[i]);
				if (num1<num2) return -1;
				if (num1>num2) return 1;
			}	
			return 0;
		}		
	}
	
	@Override
	public Tuple getNextTuple() throws IOException {
		if(bufferList.isEmpty()) return null;
		return new Tuple(bufferList.pop());
	}

	@Override
	public void reset() throws IOException {
		child.reset();
	}

}
