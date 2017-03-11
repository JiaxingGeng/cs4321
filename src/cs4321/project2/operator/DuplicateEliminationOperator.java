package cs4321.project2.operator;

import java.io.IOException;

public class DuplicateEliminationOperator extends Operator {
	
	private SortOperator child;
	private Tuple lastTuple;
	
	public DuplicateEliminationOperator(SortOperator sortOperator) 
			throws IOException{
		super.columns = null;
		child = sortOperator;	
		lastTuple = child.getNextTuple();
	}

	@Override
	public Tuple getNextTuple() throws IOException {
		if (lastTuple == null) return null;
		Tuple t = child.getNextTuple();
		//if (t == null) return lastTuple;
		if(lastTuple.equals(t)) return this.getNextTuple();
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
