package cs4321.project2.operator;

import net.sf.jsqlparser.expression.Expression;

import java.io.IOException;
import java.util.HashMap;
import cs4321.project2.deparser.*;
import cs4321.project2.*;

/**
 * 
 * @author Jiaxing Geng (jg755), Yangyi Hao (yh326)
 */

public class JoinOperator extends Operator {
	
	private Operator leftOp;
	private Operator rightOp;
	private Expression expression;
	private Catalog catalog;
	private Tuple leftTuple;
	
	
	public JoinOperator(Operator op1, Operator op2,Expression expression)
			throws IOException{
		leftOp = op1;
		rightOp= op2;
		this.expression = expression;	
		catalog = Catalog.getInstance(null);
		leftTuple = leftOp.getNextTuple();
	}
	
	public Tuple getNextTuple() throws IOException{
		if (leftTuple==null) return null;
		Tuple t2 = rightOp.getNextTuple();
		if (t2==null) {
			rightOp.reset();
			t2 = rightOp.getNextTuple();
		}
		
		
		
		
		return null;
	}
	

	public void reset() throws IOException{
		
	}
	
//	private HashMap<String,Integer> JoinHashMap
//	(String table1, String table2){
//		HashMap<String,Integer> joinHashMap = new HashMap<>();
//		HashMap<String,Integer> attr1 = catalog.getAttributes(table1);
//		HashMap<String,Integer> attr2 = catalog.getAttributes(table2);
//
//		return null;
//	}
}
