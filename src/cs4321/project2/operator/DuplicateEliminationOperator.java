package cs4321.project2.operator;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import net.sf.jsqlparser.statement.select.*;
import cs4321.project2.deparser.*;

/**
 * A DuplicateEliminationOperator takes in a SortOperator, and eliminate the
 * duplicate elements specified by onSelectItems
 * @author Jiaxing Geng (jg755), Yangyi Hao (yh326)
 *
 */
public class DuplicateEliminationOperator extends Operator {

	private SortOperator child;
	private Tuple lastTuple;
	private List<Integer> posList;

	/**
	 * Constructor for a DuplicateEliminationOperator
	 * @param sortOperator
	 * @param onSelectItems, the list of columns that this operator wants to distinguish on
	 * @throws IOException
	 */
	public DuplicateEliminationOperator
	(SortOperator sortOperator, List<?> onSelectItems) 
			throws IOException{
		super.columns = sortOperator.getColumns();
		child = sortOperator;	
		lastTuple = child.getNextTuple();
		HashMap<String, Integer> colToIndexHash = this.getColumnsHash();
		OnSelectItemVisitor visitor = new OnSelectItemVisitor(colToIndexHash);
		for (int i=0;i<onSelectItems.size();i++){
			SelectItem selectItem =(SelectItem) onSelectItems.get(i);
			selectItem.accept(visitor);
			posList = visitor.getResult();

		}
	}

	/**
	 * Return the next distinct tuple
	 */
	@Override
	public Tuple getNextTuple() throws IOException {
		if (lastTuple == null) return null;
		Tuple t = child.getNextTuple();
		if(lastTuple.equals(t,posList)) return this.getNextTuple();
		else {
			Tuple temp = lastTuple;
			lastTuple = t;
			return temp;
		}	
	}

	/**
	 * Reset, so that in the next call, getNextTuple will return the very first tuple
	 */
	@Override
	public void reset() throws IOException {
		child.reset();
		lastTuple = child.getNextTuple();
	}

}
