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
	
	public SelectDeParser(){};
	
	public SelectDeParser(ExpressionDeParser expressionVisitor){	
		res = null;
		this.expressionVisitor = expressionVisitor;
	}
	
	public String getResult(){
		return res;
	}
	
	public void visit(AllColumns allColumns){
		res = allColumns.toString();
	}
	
	public void visit(AllTableColumns allTableColumns){
		
	}
	
	public void visit(SelectExpressionItem selectExpressionItem){
		Expression expression = selectExpressionItem.getExpression();
		expression.accept(expressionVisitor);
		res = expressionVisitor.getResult();
	}
	
	public void visit(Table tableName) {
		res = tableName.getName()+"."+tableName.getAlias();
	}
	
	public void visit(SubSelect subSelect) {
		
	}
	
	public void visit(SubJoin subjoin){
		
	}
	
}
