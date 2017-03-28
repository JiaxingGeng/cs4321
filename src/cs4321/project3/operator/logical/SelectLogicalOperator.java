package cs4321.project3.operator.logical;

import cs4321.project3.operator.*;
import net.sf.jsqlparser.expression.Expression;

public class SelectLogicalOperator extends LogicalOperator {
	
	private Expression expression;
	private LogicalOperator op;
	
	public SelectLogicalOperator(Expression expression,LogicalOperator op){
		this.expression = expression;
		this.op = op;
	}
	
	public Expression getExpression(){
		return expression;
	}
	
	public LogicalOperator getChild(){
		return op;
	}
	
	@Override
	public void accept(LogicalOperatorVisitor visitor) {
		visitor.visit(this);
	}

}
