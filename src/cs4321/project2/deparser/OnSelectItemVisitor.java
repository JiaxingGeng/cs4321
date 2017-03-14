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

/**
 * An OnSelectItemVisitor that tells the positions of columns
 * @author Jiaxing Geng (jg755), Yangyi Hao (yh326)
 *
 */
public class OnSelectItemVisitor implements SelectItemVisitor {
	
	private HashMap<String,Integer> colToIndexHash;
	private List<Integer> posList;
	
	/**
	 * Constructor for the OnSelectItemVisitor
	 * @param colToIndexHash
	 * 		 a hash map that maps each column to its positions
	 */
	public OnSelectItemVisitor(HashMap<String, Integer> colToIndexHash){
		this.colToIndexHash = colToIndexHash;
		posList = (List<Integer>) new LinkedList<Integer>();
	}
	
	/**
	 * Return a list of positions
	 * @return position list
	 */
	public List<Integer> getResult(){
		return posList;
	}

	/**
	 * Return null if it's AllColumns (*)
	 */
	@Override
	public void visit(AllColumns arg0) {
		posList = null;
	}

	@Override
	public void visit(AllTableColumns arg0) {
		// TODO Auto-generated method stub

	}

	/**
	 * Return the position of arg0
	 */
	@Override
	public void visit(SelectExpressionItem arg0) {
		Expression expression = arg0.getExpression();
		Column column = (Column) expression;
		String columnName = column.getColumnName(); 
		String tableName = column.getTable().getName();
		String alias = column.getTable().getAlias();
		if (alias != null) tableName = alias;
		int pos = colToIndexHash.get(tableName+"."+columnName);
		posList.add(pos);
	}

}
