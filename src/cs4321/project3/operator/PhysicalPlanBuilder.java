package cs4321.project3.operator;

import cs4321.project3.operator.logical.*;
import cs4321.project3.operator.physical.*;
import cs4321.project2.operator.*;
import net.sf.jsqlparser.statement.select.*;
import net.sf.jsqlparser.expression.Expression;
import java.io.IOException;
import java.util.List;

public class PhysicalPlanBuilder implements LogicalOperatorVisitor {
	
	Operator topOp;
	
	public PhysicalPlanBuilder(){
		topOp = null;
	}
	
	public Operator getPhysicalPlan(){
		return topOp;
	}

	public void visit(ScanLogicalOperator op){
		FromItem fromItem = op.getFromItem();
		// putting catch here is not save: need to be fixed after checkpoint
		try{topOp = new ScanOperator(fromItem);}
		catch(IOException e){System.out.println(e.getMessage());}
	}

	public void visit(SelectLogicalOperator op){
		Expression exp = op.getExpression();
		PhysicalPlanBuilder visitor = new PhysicalPlanBuilder();
		op.getChild().accept(visitor);
		// the following cast might need to be changed in the future
		ScanOperator sOp = (ScanOperator) visitor.getPhysicalPlan();
		topOp = new SelectOperator(sOp,exp);
	}

	public void visit(ProjectLogicalOperator op){
		List<?> selectItems = op.getSelectItems();
		PhysicalPlanBuilder visitor = new PhysicalPlanBuilder();
		op.getChild().accept(visitor);
		Operator operator = visitor.getPhysicalPlan();
		topOp = new ProjectOperator(operator,selectItems);
		
	}

	public void visit(JoinLogicalOperator op){
		Expression exp = op.getExpression();
		PhysicalPlanBuilder visitorLeft = new PhysicalPlanBuilder();
		PhysicalPlanBuilder visitorRight = new PhysicalPlanBuilder();
		op.getLeftChild().accept(visitorLeft);
		op.getRightChild().accept(visitorRight);
		Operator opLeft = visitorLeft.getPhysicalPlan();
		Operator opRight = visitorRight.getPhysicalPlan();
		try{topOp = new SMJOperator(opLeft,opRight,exp);}
		catch(IOException e){System.out.println(e.getMessage());}
	}

	public void visit(SortLogicalOperator op){
		List<?> orders = op.getOrderByELements();
		List<?> items = op.getSelectItems();
		PhysicalPlanBuilder visitor = new PhysicalPlanBuilder();
		op.getChild().accept(visitor);
		Operator operator = visitor.getPhysicalPlan();
		try{topOp = new SortOperator(operator,orders,items);}
		catch(IOException e){System.out.println(e.getMessage());}
	}

	public void visit(DuplicateEliminationLogicalOperator op){
		Distinct distinct = op.getDistinct();
		List<?> items = distinct.getOnSelectItems();
		PhysicalPlanBuilder visitor = new PhysicalPlanBuilder();
		op.getChild().accept(visitor);
		// the following cast might need to be changed in the future
		SortOperator operator = (SortOperator) visitor.getPhysicalPlan();
		try{topOp = new DuplicateEliminationOperator(operator,items);}
		catch(IOException e){System.out.println(e.getMessage());}
	}


}
