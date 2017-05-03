package cs4321.project3.operator;

import cs4321.project3.operator.logical.*;
import cs4321.project3.operator.physical.*;
import cs4321.project2.operator.*;
import cs4321.project2.Catalog;
import net.sf.jsqlparser.statement.select.*;
import net.sf.jsqlparser.expression.Expression;
import java.io.IOException;
import java.util.List;

/**
 * Generate Physical Query Plan from Logical Query Plan
 * 
 * @author Jiaxing Geng (jg755), Yangyi Hao (yh326)
 */
public class PhysicalPlanBuilder implements LogicalOperatorVisitor {
	
	Operator topOp;
	int[][] config;
	
	public PhysicalPlanBuilder(){
		topOp = null;
		Catalog cat = Catalog.getInstance();
		config = cat.getConfig();		
	}
	/**
	 * Get the physical plan
	 * @return top physical operator
	 */
	public Operator getPhysicalPlan(){
		return topOp;
	}

	/**
	 * Construct physical scan operator
	 */
	public void visit(ScanLogicalOperator op){
		FromItem fromItem = op.getFromItem();
		try{topOp = new ScanOperator(fromItem);}
		catch(IOException e){System.out.println(e.getMessage());}
	}

	/**
	 * Construct physical select operator
	 */
	public void visit(SelectLogicalOperator op){
		Expression exp = op.getExpression();
		PhysicalPlanBuilder visitor = new PhysicalPlanBuilder();
		op.getChild().accept(visitor);
		ScanOperator sOp = (ScanOperator) visitor.getPhysicalPlan();
		topOp = new SelectOperator(sOp,exp);
	}

	/**
	 * Construct physical project operator
	 */
	public void visit(ProjectLogicalOperator op){
		List<?> selectItems = op.getSelectItems();
		PhysicalPlanBuilder visitor = new PhysicalPlanBuilder();
		op.getChild().accept(visitor);
		Operator operator = visitor.getPhysicalPlan();
		topOp = new ProjectOperator(operator,selectItems);
		
	}

	/**
	 * Construct physical join operator. The actual operator
	 * depends on config file. It can be TNLJ,BNLJ or SMJ.
	 */
	public void visit(JoinLogicalOperator op){
		//System.out.println("visiting a join logical operator");
		Expression exp = op.getExpression();
		PhysicalPlanBuilder visitorLeft = new PhysicalPlanBuilder();
		PhysicalPlanBuilder visitorRight = new PhysicalPlanBuilder();
		op.getLeftChild().accept(visitorLeft);
		op.getRightChild().accept(visitorRight);
		Operator opLeft = visitorLeft.getPhysicalPlan();
		Operator opRight = visitorRight.getPhysicalPlan();
		try{
			if (config[0][0]==0)
				topOp = new JoinOperator(opLeft,opRight,exp);
			if (config[0][0]==1){
				int bufferSize = config[0][1];
				topOp = new BNLJOperator(opLeft,opRight,exp,bufferSize);
			}
			if (config[0][0]==2){
				int bufferSize = config[1][1];
				//System.out.println("buffer size is" + bufferSize);
				topOp = new SMJOperator(opLeft,opRight,exp,bufferSize);
				//System.out.println("constructed a topOp");
			}
		}catch(IOException e)
			{System.out.println(e.getMessage());}
	}

	/**
	 * Construct physical sort operator. The actual operator
	 * depends on config file. It can be in-memory sort or 
	 * external sort.
	 */
	public void visit(SortLogicalOperator op){
		List<?> orders = op.getOrderByELements();
		List<?> items = op.getSelectItems();
		PhysicalPlanBuilder visitor = new PhysicalPlanBuilder();
		op.getChild().accept(visitor);
		Operator operator = visitor.getPhysicalPlan();
		try{
			if (config[1][0] == 0)
			topOp = new SortOperator(operator,orders,items);
			else {
				int bufferSize = config[1][1];
				topOp = new ExternalSortOperator(operator,orders,items,bufferSize);
			}
		}catch(IOException e){System.out.println(e.getMessage());}
	}

	/**
	 * Construct distinct operator. The actual sort operator
	 * used before distinct depends on config file. 
	 * It can be in-memory sort or external sort.
	 */
	public void visit(DuplicateEliminationLogicalOperator op){
		Distinct distinct = op.getDistinct();
		List<?> items = distinct.getOnSelectItems();
		PhysicalPlanBuilder visitor = new PhysicalPlanBuilder();
		op.getChild().accept(visitor);
		Operator operator = visitor.getPhysicalPlan();
		try{topOp = new DuplicateEliminationOperator(operator,items);}
		catch(IOException e){System.out.println(e.getMessage());}
	}

}
