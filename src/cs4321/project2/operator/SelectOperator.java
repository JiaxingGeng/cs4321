/**
 * 
 */
package cs4321.project2.operator;

import java.io.IOException;
import java.util.HashMap;
import cs4321.project2.Catalog;
import cs4321.project2.deparser.*;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.statement.select.FromItem;


/**
 * @author Jiaxing Geng (jg755), Yangyi Hao (yh326)
 *
 */
public class SelectOperator extends Operator {
	
	private ScanOperator sOp;
	private HashMap<String, Integer> colToIndexHash;
	private Expression exp;
	
	public SelectOperator(ScanOperator sOp, FromItem fromItem, Expression exp) 
			throws IOException {
		SelectDeParser selectVisitor = new SelectDeParser();
	    fromItem.accept(selectVisitor);
	    String tableName = selectVisitor.getResult();
		Catalog cat = Catalog.getInstance(null);
		colToIndexHash = cat.getAttributes(tableName);	
		this.exp = exp;
		this.sOp = sOp;
	}
	
	@Override
	public Tuple getNextTuple() throws IOException {
		Tuple nextTuple = sOp.getNextTuple();
		if (nextTuple == null) return null;
		ExpressionDeParser ev = new ExpressionDeParser(nextTuple, colToIndexHash);
		exp.accept(ev);
		if (Boolean.parseBoolean(ev.getResult())) {
			return nextTuple;
		} else {
			return this.getNextTuple();
		}
		
	}

	@Override
	public void reset() throws IOException {
		sOp.reset();
	}

}
