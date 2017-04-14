package cs4321.project3.operator.logical;

import cs4321.project3.operator.*;
import net.sf.jsqlparser.expression.Expression;
/**
 * Logical Select Operator that is used to build logical query plan
 * 
 * @author Jiaxing Geng (jg755), Yangyi Hao (yh326)
 */
public class SelectLogicalOperator extends LogicalOperator {
	
	private Expression expression;
	private LogicalOperator op;
	
	public SelectLogicalOperator(Expression expression, LogicalOperator op){
		this.expression = expression;
		this.op = op;
	}
	/**
	 * Get the expression of selection
	 * @return select expression
	 */
	public Expression getExpression(){
		return expression;
	}
	/**
	 * Get the child operator
	 * @return child operator
	 */
	public LogicalOperator getChild(){
		return op;
	}
	
	@Override
	public void accept(LogicalOperatorVisitor visitor) {
		visitor.visit(this);
	}

}
