package cs4321.project2.operator;

import java.io.IOException;
import net.sf.jsqlparser.statement.select.*;
import java.util.LinkedList;
import java.util.*;
import cs4321.project2.deparser.*;
import cs4321.project2.*;


/**
 * Scan the table from the file in the database
 * 
 * @author Jiaxing Geng (jg755), Yangyi Hao (yh326)
 */


public class ProjectOperator extends Operator {
	private Operator child;
	private List<?> selectItems;
	private Catalog catalog;
	private String table;
	
	public ProjectOperator(Operator c, List<?> selectItems, FromItem fromItem) 
			throws IOException{
		child = c;
		this.selectItems = selectItems;	
		catalog = Catalog.getInstance(null);
		SelectDeParser selectVisitor = new SelectDeParser();
	    fromItem.accept(selectVisitor);
	    table = selectVisitor.getResult();
	}
	
	public Tuple getNextTuple() throws IOException{
		Tuple t = child.getNextTuple();
		if (t == null) return null;
		LinkedList<String> projList = new LinkedList<>();
		int num = selectItems.size();
		for (int i=0;i<num;i++){
			SelectItem selectItem =(SelectItem) selectItems.get(i);
			ExpressionDeParser expressionVisitor = 
					new ExpressionDeParser(t,catalog.getAttributes(table));		
			SelectDeParser selectDeParser = new SelectDeParser(expressionVisitor);
			selectItem.accept(selectDeParser);
			String selectColumn = selectDeParser.getResult().toString();
			// case: select AllColumns 
			if (selectColumn.equals("*")) return t;	
			// Assume the other case is Column
			projList.add(selectColumn);
		}
		return new Tuple(projList.toArray(new String[projList.size()]));		
	}
	
	public void reset() throws IOException{
		child.reset();
	}
	

}
