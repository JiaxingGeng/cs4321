package cs4321.project2.operator;

import net.sf.jsqlparser.expression.Expression;

import java.io.IOException;
import java.util.HashMap;
import cs4321.project2.deparser.*;

/**
 * A join operator takes in two operators, and join them if the tuples qualifies the expression
 * @author Jiaxing Geng (jg755), Yangyi Hao (yh326)
 */
public class JoinOperator extends Operator {

	private Operator leftOp;
	private Operator rightOp;
	private Expression expression;
	private Tuple leftTuple;
	HashMap<String, Integer> columnsHash;

	/**
	 * Constructor for a JoinOperator
	 * @param op1, the left operator
	 * @param op2, the right operator
	 * @param expression, the expression underwhich the two tuples from each operators will be joined
	 * @throws IOException
	 */
	public JoinOperator(Operator op1, Operator op2, Expression expression)
			throws IOException{
		leftOp = op1;
		rightOp= op2;
		this.expression = expression;	
		String[] columns1 = op1.getColumns();
		String[] columns2 = op2.getColumns();		
		super.columns = new String[columns1.length + columns2.length];
		   System.arraycopy(columns1, 0, super.columns, 0, columns1.length);
		   System.arraycopy(columns2, 0, 
				   super.columns, columns1.length, columns2.length);
		columnsHash = this.getColumnsHash();
		leftTuple = leftOp.getNextTuple();
	}

	/**
	 * Return the next join tuple
	 */
	public Tuple getNextTuple() throws IOException{
		while (true){
			if (leftTuple==null) return null;
			Tuple rightTuple = rightOp.getNextTuple();
			if (rightTuple==null) {
				rightOp.reset();
				rightTuple = rightOp.getNextTuple();
				leftTuple = leftOp.getNextTuple();
				if (leftTuple == null) return null;
			}
			Tuple joinTuple = leftTuple.joins(rightTuple);
			if (expression == null){
				return joinTuple;
			} else {
				ExpressionDeParser ev = new ExpressionDeParser(joinTuple, columnsHash);
				expression.accept(ev);
				if (Boolean.parseBoolean(ev.getResult())) {
					return joinTuple;
				} else {
					continue;
				}
			}
		}	
	}

	/**
	 * Reset, so that in the next call, getNextTuple will return the first tuple
	 */
	public void reset() throws IOException{
		leftOp.reset();
		rightOp.reset();
		leftTuple = leftOp.getNextTuple();
	}
	
	/**
	 * Return the left operator
	 * @return
	 */
	public Operator getLeftChild(){
		return leftOp;
	}
	
	/**
	 * Return the right operator
	 * @return
	 */
	public Operator getRightChild(){
		return rightOp;
	}
	
	public String toString(){
		String exp;
		if(expression==null) exp = "null";
		else exp = expression.toString();
		return "JoinOperator: " + exp + " ";
	}
}
