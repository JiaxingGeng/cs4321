package cs4321.project3.operator.logical;

import cs4321.project3.operator.*;

/**
 * Top-level abstract class for an operator in logical query plan
 * 
 *  @author Jiaxing Geng (jg755), Yangyi Hao (yh326) 
 */

public abstract class LogicalOperator {

	/**
	 * Abstract method for accepting visitor
	 * 
	 * @param visitor
	 *            visitor to be accepted
	 */
	public abstract void accept(LogicalOperatorVisitor visitor);
}
