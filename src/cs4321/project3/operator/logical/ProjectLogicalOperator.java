package cs4321.project3.operator.logical;

import java.util.List;
import cs4321.project3.operator.*;
/**
 * Logical Project Operator that is used to build logical query plan
 * 
 * @author Jiaxing Geng (jg755), Yangyi Hao (yh326)
 */
public class ProjectLogicalOperator extends LogicalOperator {
	
	private List<?> selectItems;
	private LogicalOperator op;
	
	public ProjectLogicalOperator(List<?> selectItems,LogicalOperator op){
		this.selectItems = selectItems;
		this.op = op;
	}

	/**
	 * Get select Items
	 * @return list of select items
	 */
	public List<?> getSelectItems(){
		return selectItems;
	}
	
	/**
	 * Get child operator
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
