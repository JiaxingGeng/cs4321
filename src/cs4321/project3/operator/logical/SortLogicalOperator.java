package cs4321.project3.operator.logical;

import java.util.List;
import cs4321.project3.operator.*;

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
	
	public List<?> getOrderByELements(){
		return orderByElements;
	}
	
	public List<?> getSelectItems(){
		return selectItems;
	}
	
	public LogicalOperator getChild(){
		return op;
	}

	@Override
	public void accept(LogicalOperatorVisitor visitor) {
		visitor.visit(this);
	}

}
