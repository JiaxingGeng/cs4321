package cs4321.project3.operator.logical;

import cs4321.project3.operator.*;
import net.sf.jsqlparser.expression.Expression;

/**
 * Logical Join Operator that is used to build logical query plan
 * 
 * @author Jiaxing Geng (jg755), Yangyi Hao (yh326)
 */
public class JoinLogicalOperator extends LogicalOperator {

	private Expression expression;
	private LogicalOperator left;
	private LogicalOperator right;
	
	public JoinLogicalOperator
	(Expression expression,LogicalOperator op1,LogicalOperator op2){
		this.expression = expression;
		this.left = op1;
		this.right = op2;
	}
	
	/**
	 * Get the stored expression
	 * @return expression stored
	 */
	public Expression getExpression(){
		return expression;
	}
	
	/**
	 * Get the left child operator
	 * @return left operator
	 */
	public LogicalOperator getLeftChild(){
		return left;
	}
	
	/**
	 * Get the right child operator
	 * @return right operator
	 */
	public LogicalOperator getRightChild(){
		return right;
	}
	
	@Override
	public void accept(LogicalOperatorVisitor visitor) {
		visitor.visit(this);
	}

}
