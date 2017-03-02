/**
 * 
 */
package cs4321.project2.operator;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import cs4321.project2.Catalog;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.schema.Table;

/**
 * @author Jiaxing Geng (jg755), Yangyi Hao (yh326)
 *
 */
public class SelectOperator extends Operator {
	
	private ScanOperator sOp;
	private LinkedList<Tuple> result;
	private HashMap<String, Integer> colToIndexHash;
	private Expression exp;
	
	public SelectOperator(Table t, String dir, Catalog cat, Expression exp) throws IOException {
		sOp = new ScanOperator(t, dir, cat);
		colToIndexHash = getColToIndexHash(cat.getHashMap().get(t.getName()));
		result = new LinkedList<Tuple> ();
		this.exp = exp;
	}
	
	HashMap<String, Integer> getColToIndexHash(String[] cols) {
		HashMap<String, Integer> myHash = new HashMap<String, Integer> ();
		for (int i = 0; i < cols.length; i++) {
			myHash.put(cols[i], i);
		}
		return myHash;
	}
	
	@Override
	public void dump() throws IOException {
		for (Tuple t : result) {
			t.print();
		}
	}

	/* (non-Javadoc)
	 * @see cs4321.project2.operator.Operator#getNextTuple()
	 */
	@Override
	public Tuple getNextTuple() throws IOException {
		Tuple nextTuple = sOp.getNextTuple();
		if (nextTuple == null) return null;
		cs4321ExpressionVisitor ev = new cs4321ExpressionVisitor(nextTuple, colToIndexHash);
		exp.accept(ev);
		if (ev.getFinalResult()) {
			result.add(nextTuple);
		} 
		return nextTuple;
	}

	/* (non-Javadoc)
	 * @see cs4321.project2.operator.Operator#reset()
	 */
	@Override
	public void reset() throws IOException {
		sOp.reset();
	}

}
