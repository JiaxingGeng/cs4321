package cs4321.project2;

import net.sf.jsqlparser.statement.select.*;
import cs4321.project2.deparser.SelectDeParser;
import cs4321.project2.operator.*;
import cs4321.project2.querytree.QueryTree;
import cs4321.project2.querytree.QueryTreeVisitor;

import java.util.Arrays;
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
	
	@SuppressWarnings({ "unchecked" })
	public PlanGenerator(PlainSelect plainSelect,String inputdir) 
			throws IOException{
		catalog = Catalog.getInstance(null);
		FromItem fromItem = plainSelect.getFromItem();
		System.out.println("fromItem is: " + fromItem.toString());
		List<?> selectItems = plainSelect.getSelectItems();
		if (selectItems != null) {
			System.out.println("selectItems is: ");
			for (SelectItem selectItem : (List<SelectItem>) selectItems) {
				System.out.print(selectItem.toString() + " ");
			}
			System.out.println("");
		}
		List<?> fromJoins = plainSelect.getJoins();
		if (fromJoins != null) {
			System.out.println("fromJoins is: ");
			for (Join join : (List<Join>) fromJoins)
				System.out.print(join.toString() + " ");
			System.out.println("");
		}
		List<?> orderByElements = plainSelect.getOrderByElements();
		if (orderByElements != null) {
			System.out.println("orderByElements is: ");
			for (OrderByElement orderByElement : (List<OrderByElement>) orderByElements)
				System.out.print(orderByElement.toString() + " ");
			System.out.println("");
		}
		Expression expression = plainSelect.getWhere();
		if (expression != null) {
			System.out.println("expression is: " + expression.toString());
		}
		Distinct distinct = plainSelect.getDistinct();
		if (distinct != null) {
			System.out.println("distinct is: " + distinct.toString());
		}
		
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
