package cs4321.project2.querytree;

import net.sf.jsqlparser.expression.*;

/**
 * Nodes are elementary units for query tree structure. Each node stores an 
 * expression.
 * @author Jiaxing Geng (jg755), Yangyi Hao (yh326)
 */
public class Node {
	
	protected Expression expression;
	
	public Node(){
		expression = null;
	}
	/**
	 * Get the expression of the node
	 * @return expression on node and null if it doesn't exist.
	 */
	public Expression getExpression(){
		return expression;
	}
	
	/**
	 * Set the expression of the node
	 * @param exp expression or null if it doesn't exist.
	 */
	public void setExpression(Expression exp){
		expression = exp;
	}
}
