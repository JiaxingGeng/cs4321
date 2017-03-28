package cs4321.project3.operator.logical;

import cs4321.project3.operator.*;
import net.sf.jsqlparser.expression.Expression;

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
	
	public Expression getExpression(){
		return expression;
	}
	
	public LogicalOperator getLeftChild(){
		return left;
	}
	
	public LogicalOperator getRightChild(){
		return right;
	}
	
	@Override
	public void accept(LogicalOperatorVisitor visitor) {
		visitor.visit(this);
	}

}
