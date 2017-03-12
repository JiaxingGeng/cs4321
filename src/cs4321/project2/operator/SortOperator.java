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

public class SortOperator extends Operator {
	
	private HashMap<String,Integer> colToIndexHash;
	private LinkedList<String[]> bufferList;
	private Integer currOutputIndex;
	
	public SortOperator(Operator c, List<?> orderByElements) throws IOException{
		super.columns = c.getColumns();
		colToIndexHash = this.getColumnsHash();
		currOutputIndex = 0;
		OnSelectItemVisitor visitor = new OnSelectItemVisitor(colToIndexHash);
		LinkedList<Integer> pos = new LinkedList<>();		
		if (orderByElements.get(0) instanceof SelectItem){
			for (int i=0;i<orderByElements.size();i++){
				SelectItem selectItem =(SelectItem) orderByElements.get(i);
				selectItem.accept(visitor);
				pos = (LinkedList<Integer>) visitor.getResult();				
			}
		} else {
			for (int i=0;i<orderByElements.size();i++){
				OrderByElement oElement = (OrderByElement) orderByElements.get(i);
				Column column = (Column) oElement.getExpression();
				String columnName = column.getColumnName(); 
				String tableName = column.getTable().getName();
				String alias = column.getTable().getAlias();
				if (alias != null) tableName = alias;
				pos.add(colToIndexHash.get(tableName+"."+columnName));
			}
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
	
	@Override
	public Tuple getNextTuple() throws IOException {
		if (currOutputIndex < bufferList.size()) 
			return new Tuple(bufferList.get(currOutputIndex++));
		else return null;	}

	@Override
	public void reset() throws IOException {
		currOutputIndex = 0;
	}

}
