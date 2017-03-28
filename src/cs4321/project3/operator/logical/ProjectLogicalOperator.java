package cs4321.project3.operator.logical;

import java.util.List;
import cs4321.project3.operator.*;

public class ProjectLogicalOperator extends LogicalOperator {
	
	private List<?> selectItems;
	private LogicalOperator op;
	
	public ProjectLogicalOperator(List<?> selectItems,LogicalOperator op){
		this.selectItems = selectItems;
		this.op = op;
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
