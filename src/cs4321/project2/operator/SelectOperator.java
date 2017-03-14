package cs4321.project2.operator;

import java.io.IOException;
import java.util.HashMap;
import cs4321.project2.deparser.*;
import net.sf.jsqlparser.expression.Expression;

/**
 * SelectOperator select a certain columns from a tuple according to
 * the input expression. It always have a ScanOperator as its child.
 * @author Jiaxing Geng (jg755), Yangyi Hao (yh326)
 *
 */
public class SelectOperator extends Operator {
	
	private ScanOperator sOp;
	private HashMap<String, Integer> colToIndexHash;
	private Expression exp;
	
	public SelectOperator(ScanOperator sOp, Expression exp) 
			throws IOException {
		this.exp = exp;
		this.sOp = sOp;
		super.columns = sOp.getColumns();
		colToIndexHash = this.getColumnsHash();
	}
	
	/**
	 * Get the next tuple from this operator
	 * @return Tuple after operation 
	 */
	@Override
	public Tuple getNextTuple() throws IOException {
		Tuple nextTuple = sOp.getNextTuple();
		if (nextTuple == null) return null;
		ExpressionDeParser ev = new ExpressionDeParser(nextTuple, colToIndexHash);
		exp.accept(ev);
		if (Boolean.parseBoolean(ev.getResult())) {
			return nextTuple;
		} else {
			return this.getNextTuple();
		}
		
	}
	
	/**
	 * Reset the operator
	 */
	@Override
	public void reset() throws IOException {
		sOp.reset();
	}
	
	/**
	 * Get the child of this select operator
	 * @return the scanOperator below this selection 
	 */
	public ScanOperator getChild(){
		return sOp;
	}
	/**
	 * Print the operator type and its expression
	 */
	public String toString(){
		String exp2;
		if(exp==null) exp2 = "null";
		else exp2 = exp.toString();
		return "SelectOperator: " + exp2 + " ";
	}

}
