package cs4321.project3.operator.logical;

import cs4321.project3.operator.*;
import net.sf.jsqlparser.statement.select.Distinct;
/**
 * Logical Duplicate Elimination Operator that is 
 * used to build logical query plan
 * 
 * @author Jiaxing Geng (jg755), Yangyi Hao (yh326)
 */
public class DuplicateEliminationLogicalOperator extends LogicalOperator {

	private Distinct distinct;
	private LogicalOperator op;
	
	public DuplicateEliminationLogicalOperator
	(Distinct distinct, LogicalOperator op){
		this.distinct = distinct;
		this.op = op;
	}
	/**
	 * Get distinct type
	 * @return distinct type
	 */
	public Distinct getDistinct(){
		return distinct;
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
