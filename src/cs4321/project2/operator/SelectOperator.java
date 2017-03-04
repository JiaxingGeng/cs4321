package cs4321.project2.operator;

import java.io.IOException;
import java.util.HashMap;
import cs4321.project2.Catalog;
import cs4321.project2.deparser.*;
import net.sf.jsqlparser.expression.Expression;

/**
 * @author Jiaxing Geng (jg755), Yangyi Hao (yh326)
 *
 */
public class SelectOperator extends Operator {
	
	private ScanOperator sOp;
	private HashMap<String, Integer> colToIndexHash;
	private Expression exp;
	private Catalog catalog;
	
	public SelectOperator(ScanOperator sOp, Expression exp) 
			throws IOException {
		catalog = Catalog.getInstance(null);
		colToIndexHash = catalog.getColumnsHash();
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
