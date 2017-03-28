package cs4321.project3.operator;

import cs4321.project3.operator.logical.*;

public interface LogicalOperatorVisitor {
	
	public void visit(ScanLogicalOperator op);
	
	public void visit(SelectLogicalOperator op);

	public void visit(ProjectLogicalOperator op);

	public void visit(JoinLogicalOperator op);

	public void visit(SortLogicalOperator op);

	public void visit(DuplicateEliminationLogicalOperator op);

}
