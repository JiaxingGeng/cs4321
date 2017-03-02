package cs4321.project2.deparser;

import net.sf.jsqlparser.statement.select.*;
import cs4321.project2.*;
import java.io.IOException;


public class SelectDeParser implements SelectItemVisitor {
	
	private Catalog catalog;
	private StringBuffer sb;
	
	public SelectDeParser() throws IOException{
		Catalog c = Catalog.getInstance("");
		catalog = c;	
	}
	
	public StringBuffer getBuffer(){
		return sb;
	}
	
	public void visit(AllColumns allColumns){
		sb = new StringBuffer("");
	}
	
	public void visit(AllTableColumns allTableColumns){
		
	}
	
	public void visit(SelectExpressionItem selectExpressionItem){
		ExpressionDeParser expressionVisitor = new ExpressionDeParser();
		expressionVisitor.visit(selectExpressionItem);
		sb = expressionVisitor.getBuffer();	
	}
}
