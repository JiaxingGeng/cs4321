package cs4321.project3.operator;

import java.io.IOException;
import java.util.List;

import cs4321.project2.Catalog;
import cs4321.project2.querytree.QueryTree;
import cs4321.project2.querytree.QueryTreeVisitor;
import cs4321.project3.operator.logical.*;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.statement.select.*;

public class LogicalPlanBuilder {
	
	LogicalOperator op;
	Catalog catalog;
	
	public LogicalPlanBuilder(PlainSelect plainSelect, String inputdir) 
			throws IOException{
		catalog = Catalog.getInstance();
		FromItem fromItem = plainSelect.getFromItem();
		List<?> selectItems = plainSelect.getSelectItems();
		List<?> fromJoins = plainSelect.getJoins();
		List<?> orderByElements = plainSelect.getOrderByElements();
		Expression expression = plainSelect.getWhere();
		Distinct distinct = plainSelect.getDistinct();
				
		if (fromJoins == null){
			op = new ScanLogicalOperator(fromItem);
			if (expression != null){
				op = new SelectLogicalOperator(expression, op);
			}
		} else {
			QueryTree queryTree = new QueryTree(fromItem, fromJoins);
			QueryTreeVisitor visitor = new QueryTreeVisitor(queryTree);
			if (expression !=null){
				expression.accept(visitor);
				queryTree = visitor.getQueryTree();
			}
			op = queryTree.getLogicalPlan();
		}		
		if (distinct != null){
			List<?> onSelectItems = distinct.getOnSelectItems();
			if (onSelectItems == null) 
				distinct.setOnSelectItems(selectItems);
			LogicalOperator sOp = new SortLogicalOperator(selectItems,null,op);
			op = new DuplicateEliminationLogicalOperator(distinct,sOp);
		}
		if (orderByElements != null){
			op = new SortLogicalOperator(selectItems,orderByElements,op);
		}	
		op = new ProjectLogicalOperator(selectItems,op);
	}
	
	public LogicalOperator getLogicalPlan(){
		return op;		
	}

}
