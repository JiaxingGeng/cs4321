package cs4321.project3.operator.physical;

import java.io.IOException;
import java.util.HashMap;
import java.util.ArrayList;

import cs4321.project2.deparser.ExpressionDeParser;
import cs4321.project2.operator.*;
import net.sf.jsqlparser.expression.Expression;
/**
 * Physical Operator for Block Nested Loop Join
 * @author Jiaxing Geng (jg755), Yangyi Hao (yh326)
 *
 */
public class BNLJOperator extends Operator{
	
	private Operator leftOp;
	private Operator rightOp;
	private Expression expression;
	HashMap<String, Integer> columnsHash;
	
	private static final int PAZE_SIZE = 4096;
	private int tuplesPerScan;
	private ArrayList<Tuple> outerLoopTuples;
	private int outerLoopPos;
	private Tuple rightTuple;
	private Tuple tempTuple;
	private boolean lastBlock;

	/**
	 * Constructor for a JoinOperator
	 * @param op1, the left operator
	 * @param op2, the right operator
	 * @param expression, the expression under which the two tuples from each operators will be joined
	 * @param bufferSize number of pages used for outer loop in each block
	 * @throws IOException
	 */
	public BNLJOperator(Operator op1, Operator op2, Expression expression, int bufferSize)
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
	    tuplesPerScan = bufferSize*((PAZE_SIZE - 8)/(4*columns1.length));
		columnsHash = this.getColumnsHash();
		rightTuple = rightOp.getNextTuple();
		if (rightTuple!=null) outerLoopTuples = readBlocks();
	}

	/**
	 * Return the next join tuple. For each tuple in the inner loop,
	 * we scan the elements in the block that is currently loaded in
	 * memory. When the pointer reaches the end of the inner loop,
	 * it means that all the join operations is finished for current
	 * block. Then we load the next block. The join reaches the end when
	 * it is the last block and the pointer of the inner loop also reaches
	 * the end.
	 */
	public Tuple getNextTuple() throws IOException{
		while (true){
			if (outerLoopTuples == null) return null;
			if (outerLoopPos >= outerLoopTuples.size()){	
				rightTuple = rightOp.getNextTuple();
				if (rightTuple==null) {
					if (lastBlock) return null;
					// rest inner operator and read the next block
					rightOp.reset();
					outerLoopTuples = readBlocks();
					continue;
				} else {
					outerLoopPos = 0;  // scan current block for next inner tuple
				}
			}

			Tuple leftTuple = outerLoopTuples.get(outerLoopPos);
			outerLoopPos ++;
			Tuple joinTuple = leftTuple.joins(rightTuple);
			if (expression == null){
				return joinTuple;
			} else {
				ExpressionDeParser ev = new ExpressionDeParser(joinTuple, columnsHash);
				expression.accept(ev);
				if (Boolean.parseBoolean(ev.getResult())) return joinTuple;
				else continue;	
			}
		}
	}

	/**
	 * Reset, so that in the next call, getNextTuple will return the first tuple
	 */
	public void reset() throws IOException{
		leftOp.reset();
		rightOp.reset();
		rightTuple = rightOp.getNextTuple();
		if (rightTuple!=null) outerLoopTuples = readBlocks();
		else outerLoopTuples = null;
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
		return "BNLJOperator: " + exp + " ";
	}
	/**
	 * Read tuples in one block and stored them in an array list
	 * @return a list that stores the tuples of outer loop block
	 * @throws IOException
	 */
	private ArrayList<Tuple> readBlocks() throws IOException{
		int remainingTuples = tuplesPerScan;
		ArrayList<Tuple> tupleList = new ArrayList<>();
		if (tempTuple!=null) {
			remainingTuples --;
			tupleList.add(tempTuple);
		}
		Tuple leftTuple = leftOp.getNextTuple();
		while (remainingTuples>0 && leftTuple!= null){
			tupleList.add(leftTuple);
			remainingTuples --;
			leftTuple = leftOp.getNextTuple();
		}
		if (leftTuple!=null) tempTuple = leftTuple;
		if (remainingTuples>0) lastBlock = true;
		return tupleList;
	}
	
}
