package cs4321.project3.operator.logical;

import java.util.List;
import cs4321.project3.operator.*;
/**
 * Logical Sort Operator that is used to build logical query plan
 * 
 * @author Jiaxing Geng (jg755), Yangyi Hao (yh326)
 */
public class SortLogicalOperator extends LogicalOperator {
	
	private List<?> orderByElements;
	private List<?> selectItems;
	private LogicalOperator op;
	
	public SortLogicalOperator
	(List<?> items,List<?> orders, LogicalOperator op){
		orderByElements = orders;
		selectItems = items;
		this.op = op;
	}
	/**
	 * Get elements from ORDER BY
	 * @return a list of order by elements
	 */
	public List<?> getOrderByELements(){
		return orderByElements;
	}
	/**
	 * Get elements from select items
	 * @return a list of select items
	 */
	public List<?> getSelectItems(){
		return selectItems;
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
