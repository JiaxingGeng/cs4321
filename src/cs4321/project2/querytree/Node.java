package cs4321.project2.querytree;

import net.sf.jsqlparser.expression.*;

public class Node {
	
	protected Expression expression;
	
	public Node(){
		expression = null;
	}
	
	public Expression getExpression(){
		return expression;
	}
	public void setExpression(Expression exp){
		expression = exp;
	}
}
