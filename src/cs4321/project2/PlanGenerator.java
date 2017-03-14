package cs4321.project2;

import net.sf.jsqlparser.statement.select.*;
import cs4321.project2.operator.*;
import cs4321.project2.querytree.QueryTree;
import cs4321.project2.querytree.QueryTreeVisitor;

import java.util.List;
import net.sf.jsqlparser.expression.Expression;
import java.io.IOException;

/**
 * Generate Query Plan from PlainSelect
 * 
 * @author Jiaxing Geng (jg755), Yangyi Hao (yh326)
 */
public class PlanGenerator {
	
	Operator op;
	Catalog catalog;
	
	/**
	 * Construct a tree of operators, and store the head node into field op.
	 * @param plainSelect, a line of query
	 * @param inputdir, the directory of database, and schema files.
	 * @throws IOException
	 */
	public PlanGenerator(PlainSelect plainSelect, String inputdir) 
			throws IOException{
		catalog = Catalog.getInstance(null);
		FromItem fromItem = plainSelect.getFromItem();
		List<?> selectItems = plainSelect.getSelectItems();
		List<?> fromJoins = plainSelect.getJoins();
		List<?> orderByElements = plainSelect.getOrderByElements();
		Expression expression = plainSelect.getWhere();
		Distinct distinct = plainSelect.getDistinct();
		
		
		if (fromJoins == null){
			op = new ScanOperator(fromItem);
			if (expression != null){
				op = new SelectOperator((ScanOperator)op, expression);
			}
		} else {
			QueryTree queryTree = new QueryTree(fromItem, fromJoins);
			QueryTreeVisitor visitor = new QueryTreeVisitor(queryTree);
			if (expression !=null){
				expression.accept(visitor);
				queryTree = visitor.getQueryTree();
			}
			op = queryTree.getQueryPlan();
		}		
		if (distinct != null){
			List<?> onSelectItems = distinct.getOnSelectItems();
			if (onSelectItems == null) onSelectItems = selectItems;
			SortOperator sOp = new SortOperator(op, onSelectItems);
			op = new DuplicateEliminationOperator(sOp, onSelectItems);
		}
		if (orderByElements != null){
			op = new SortOperator(op, orderByElements);
		}	
		op = new ProjectOperator(op, selectItems);
	}
	
	public Operator getQueryPlan(){
		return op;		
	}
	

}
