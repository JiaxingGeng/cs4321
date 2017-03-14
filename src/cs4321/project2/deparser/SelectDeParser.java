package cs4321.project2.deparser;

import net.sf.jsqlparser.statement.select.*;
import net.sf.jsqlparser.expression.*;
import net.sf.jsqlparser.schema.*;

/**
 * A class to de-parse (that is, tranform from JSqlParser hierarchy 
 * into a string) a Select
 * @author Jiaxing Geng (jg755), Yangyi Hao (yh326)
 *
 */
public class SelectDeParser implements SelectItemVisitor, FromItemVisitor {
	
	private String res;
	private ExpressionDeParser expressionVisitor;
	
	/**
	 * Default Constructor for SelectDeParser
	 */
	public SelectDeParser(){};
	
	/**
	 * A constructor for SelectDeParser
	 * @param expressionVisitor, the expressionVistor is needed to evaluate SelectExpressionItem
	 */
	public SelectDeParser(ExpressionDeParser expressionVisitor){	
		res = null;
		this.expressionVisitor = expressionVisitor;
	}
	
	/**
	 * Return the result res
	 * @return
	 */
	public String getResult(){
		return res;
	}
	
	/**
	 * If it's an AllColumns, simply parse it to a string and return
	 */
	public void visit(AllColumns allColumns){
		res = allColumns.toString();
	}
	
	/**
	 * Not implemented
	 */
	public void visit(AllTableColumns allTableColumns){
		
	}
	
	/**
	 * If it's a SelectExpressionItem, use the ExpressionVisitor to evaluate 
	 * and store the resulting string in res
	 */
	public void visit(SelectExpressionItem selectExpressionItem){
		Expression expression = selectExpressionItem.getExpression();
		expression.accept(expressionVisitor);
		res = expressionVisitor.getResult();
	}
	
	/**
	 * If it's a tableName, parse it to a string with its name and alias.
	 */
	public void visit(Table tableName) {
		res = tableName.getName()+"."+tableName.getAlias();
	}
	
	/**
	 * Not implemented
	 */
	public void visit(SubSelect subSelect) {
		
	}
	
	/**
	 * Not implemented
	 */
	public void visit(SubJoin subjoin){
		
	}
	
}
