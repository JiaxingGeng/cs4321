package cs4321.project2.operator;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import net.sf.jsqlparser.statement.select.*;
import cs4321.project2.deparser.*;

public class DuplicateEliminationOperator extends Operator {

	private SortOperator child;
	private Tuple lastTuple;
	private List<Integer> posList;

	public DuplicateEliminationOperator
	(SortOperator sortOperator,List<?> onSelectItems) 
			throws IOException{
		super.columns = sortOperator.getColumns();
		child = sortOperator;	
		lastTuple = child.getNextTuple();
//		System.out.println(columns[0]);
		HashMap<String, Integer> colToIndexHash = this.getColumnsHash();
		OnSelectItemVisitor visitor = new OnSelectItemVisitor(colToIndexHash);
		for (int i=0;i<onSelectItems.size();i++){
			SelectItem selectItem =(SelectItem) onSelectItems.get(i);
			selectItem.accept(visitor);
			posList = visitor.getResult();

		}
	}

	@Override
	public Tuple getNextTuple() throws IOException {
		if (lastTuple == null) return null;
		Tuple t = child.getNextTuple();
		//if (t == null) return lastTuple;
		if(lastTuple.equals(t,posList)) return this.getNextTuple();
		else {
			Tuple temp = lastTuple;
			lastTuple = t;
			return temp;
		}	
	}

	@Override
	public void reset() throws IOException {
		child.reset();
		lastTuple = child.getNextTuple();
	}

}
