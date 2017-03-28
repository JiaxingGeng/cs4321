package cs4321.project3.operator.logical;

import cs4321.project3.operator.*;
import net.sf.jsqlparser.statement.select.Distinct;

public class DuplicateEliminationLogicalOperator extends LogicalOperator {

	private Distinct distinct;
	private LogicalOperator op;
	
	public DuplicateEliminationLogicalOperator
	(Distinct distinct, LogicalOperator op){
		this.distinct = distinct;
		this.op = op;
	}
	
	public Distinct getDistinct(){
		return distinct;
	}
	
	public LogicalOperator getChild(){
		return op;
	}

	@Override
	public void accept(LogicalOperatorVisitor visitor) {
		visitor.visit(this);
	}

}
