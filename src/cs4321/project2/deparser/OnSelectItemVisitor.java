package cs4321.project2.deparser;

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.select.AllColumns;
import net.sf.jsqlparser.statement.select.AllTableColumns;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.statement.select.SelectItemVisitor;
import java.util.List;
import java.util.LinkedList;
import java.util.HashMap;

public class OnSelectItemVisitor implements SelectItemVisitor {
	
	private HashMap<String,Integer> colToIndexHash;
	private List<Integer> posList;
	
	public OnSelectItemVisitor(HashMap<String,Integer> colToIndexHash){
		this.colToIndexHash = colToIndexHash;
		posList = (List<Integer>) new LinkedList<Integer>();
	}
	
	public List<Integer> getResult(){
		return posList;
	}

	@Override
	public void visit(AllColumns arg0) {
		posList = null;
	}

	@Override
	public void visit(AllTableColumns arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(SelectExpressionItem arg0) {
		Expression expression = arg0.getExpression();
		Column column = (Column) expression;
		String columnName = column.getColumnName(); 
		String tableName = column.getTable().getName();
		String alias = column.getTable().getAlias();
		if (alias != null) tableName = alias;
//		System.out.println(tableName+"."+columnName);
		int pos = colToIndexHash.get(tableName+"."+columnName);
//		System.out.println(pos);
		posList.add(pos);
	}

}
