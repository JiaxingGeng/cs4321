package cs4321.project3.operator.physical;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cs4321.project2.operator.Operator;
import cs4321.project2.Catalog;
import cs4321.project2.operator.SortOperator;
import cs4321.project2.operator.Tuple;
import cs4321.project3.deparser.SMJExpressionDeParser;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.select.OrderByElement;

/**
 * SMJ Opeartor takes in two subsequent operators, and performs a Sort Merge Join on them. Currently the expressions for this
 * SMJ Operator has to be a series of equality expressions between two columns connected by ANDs.
 * @author Jiaxing Geng (jg755), Yangyi Hao (yh326)
 *
 */
public class SMJOperator extends Operator {
	
	List<OrderByElement> orderByElements1;
	List<OrderByElement> orderByElements1_noSame;
	List<OrderByElement> orderByElements2;
	List<OrderByElement> orderByElements2_noSame;
	Operator op1;
	Operator op2;
	Operator so1;
	Operator so2;
	private Tuple leftTuple;
	private Tuple rightTuple;
	boolean needToRevert;
	int revertPoint;
	int currRightIndex;

	/**
	 * Construct a SMJ Operator. Setup the variables, and sort both of the subsequent operators according to expression.
	 * @param op1
	 * @param op2
	 * @param expression
	 * @param bufferSize
	 * @throws IOException
	 */
	public SMJOperator
	(Operator op1, Operator op2, Expression expression, int bufferSize) throws IOException {
		this.op1 = op1;
		this.op2 = op2;
		needToRevert = false;
		revertPoint = 0;
		currRightIndex = 0;
		
		String[] columns1 = op1.getColumns();
		String tableAliasOrName1 = columns1[0].split("\\.")[0];
		String[] columns2 = op2.getColumns();
		String tableAliasOrName2 = columns2[0].split("\\.")[0];
		super.columns = new String[columns1.length + columns2.length];
		System.arraycopy(columns1, 0, super.columns, 0, columns1.length);
		System.arraycopy(columns2, 0, super.columns, columns1.length, columns2.length);
		String[] tableAliasOrName = {tableAliasOrName1, tableAliasOrName2};
		SMJExpressionDeParser smjExpressionVisitor = new SMJExpressionDeParser(tableAliasOrName);
		expression.accept(smjExpressionVisitor);
		ArrayList<ArrayList<Column>> orderByElementsInArray = smjExpressionVisitor.getResult();
		orderByElements1 = new ArrayList<OrderByElement> ();
		orderByElements1_noSame = new ArrayList<OrderByElement> ();
		Set<String> orderByElements1CheckSet = new HashSet<String> ();
		//System.out.println(1);
		for (int i = 0; i < orderByElementsInArray.get(0).size(); i++) {
			//System.out.println(i);
			Column column = orderByElementsInArray.get(0).get(i);
			//System.out.println(column.toString());
			OrderByElement orderByElement = new OrderByElement();
			orderByElement.setExpression(column);
			orderByElements1.add(orderByElement);
			String columnName = column.toString();
			//System.out.println(columnName);
			if (!orderByElements1CheckSet.contains(columnName)){
				//orderByElement = new OrderByElement();
				orderByElement.setExpression(column);
				orderByElements1_noSame.add(orderByElement);
				orderByElements1CheckSet.add(columnName);
				//System.out.println(column.toString());
			}
		}
		//System.out.println("------------------");
		//System.out.println(1);
		
	    orderByElements2_noSame = new ArrayList<OrderByElement> ();
	    orderByElements2 = new ArrayList<OrderByElement> ();
	    Set<String> orderByElements2CheckSet = new HashSet<String> ();
		for (int i = 0; i < orderByElementsInArray.get(1).size(); i++) {
			Column column = orderByElementsInArray.get(1).get(i);
			OrderByElement orderByElement = new OrderByElement();
			orderByElement.setExpression(column);
			orderByElements2.add(orderByElement);
			String columnName = column.toString();
			if (!orderByElements2CheckSet.contains(columnName)) {
				//orderByElement = new OrderByElement();
				orderByElement.setExpression(column);
				orderByElements2_noSame.add(orderByElement);
				orderByElements2CheckSet.add(columnName);
				//System.out.println(column.toString());
			}
		}
		
		Catalog cat = Catalog.getInstance();
		int[][] config = cat.getConfig();
		if (config[1][0]==0){ 
		so1 = new SortOperator(op1, orderByElements1_noSame, null);
		so2 = new SortOperator(op2, orderByElements2_noSame, null);
		} else {
			so1 = new ExternalSortOperator(op1, orderByElements1_noSame, null, bufferSize);
			so2 = new ExternalSortOperator(op2, orderByElements2_noSame, null, bufferSize);
		}
		
		leftTuple = so1.getNextTuple();
		//if (leftTuple != null) leftTuple.print();
		//while ((leftTuple = so1.getNextTuple()) != null) leftTuple.print();
		rightTuple = so2.getNextTuple();

		//if (rightTuple != null) rightTuple.print();
		//while ((rightTuple = so2.getNextTuple()) != null) rightTuple.print();
		currRightIndex++;
		//System.out.println("constructor done");
	}

	/**
	 * Return the next tuple that qualifies.
	 */
	@Override
	public Tuple getNextTuple() throws IOException {
		//System.out.println("calling getNextTuple()");
		if (leftTuple == null && rightTuple == null) {
			needToRevert = false;
			return null;
		}
		if (leftTuple == null && rightTuple != null) {
			needToRevert = false;
			return null;
		}
		if (leftTuple != null && rightTuple == null) {
			if (needToRevert) {
				rightTuple = resetRight(revertPoint);
				currRightIndex = revertPoint;
				leftTuple = so1.getNextTuple();
				return this.getNextTuple();
			}
			else {
				needToRevert = false;
				return null;
			}
		}
		//System.out.println("calling compare()");
		if (compare(leftTuple, rightTuple) == 0) {
			if (!needToRevert) revertPoint = currRightIndex;
			needToRevert = true;
			Tuple tempRightTuple = rightTuple;
			rightTuple = so2.getNextTuple();
			currRightIndex++;
			
			return leftTuple.joins(tempRightTuple);
		}
		else {
			if (needToRevert) {
				needToRevert = false;
				rightTuple = resetRight(revertPoint);
				currRightIndex = revertPoint;
				leftTuple = so1.getNextTuple();
				return this.getNextTuple();
			}
			else {
				if (compare(leftTuple, rightTuple) < 0) {
					leftTuple = so1.getNextTuple();
					return this.getNextTuple();
				}
				if (compare(leftTuple, rightTuple) > 0) {
					rightTuple = so2.getNextTuple();
					currRightIndex++;
					return this.getNextTuple();
				}
			}
		}
		return null;
	}
	
	/**
	 * Reset rightTuple to the location specified by index.
	 * @param index
	 * @return
	 * @throws IOException
	 */
	public Tuple resetRight(int index) throws IOException {
		so2.reset();
		Tuple tempTuple = rightTuple;
		for (int i = 0; i < index; i++) {
			tempTuple = so2.getNextTuple();
		}
		return tempTuple;
	}
	
	/**
	 * return -1 if tuple2 > tuple1
	 * return 0 if tuple2 = tuple1
	 * return 1 if tuple2 < tuple1
	 * @param tuple1
	 * @param tuple2
	 * @return
	 */
	public int compare(Tuple tuple1, Tuple tuple2) {
		//System.out.println("comparing ");
		//tuple1.print();
		//System.out.println("and");
		//tuple2.print();
		//System.out.println("the result is");
		HashMap<String, Integer> hashMap1 = op1.getColumnsHash();
		HashMap<String, Integer> hashMap2 = op2.getColumnsHash();
		for (int i = 0; i < orderByElements1.size(); i++) {
		//int i = 0;
		//for (OrderByElement orderByElement : orderByElements1) {
			//System.out.println(index++);
			Column column1 = (Column) orderByElements1.get(i).getExpression();
			//Column column1 = (Column) orderByElement.getExpression();
			//System.out.println(column1.toString());
			Column column2 = (Column) orderByElements2.get(i).getExpression();
			//Column column2 = (Column) orderByElement.getExpression();
			//String columnName1 = orderByElements1.get(i).toString();
			String columnName1 = column1.toString();
			String alias1 = column1.getTable().getAlias();
			//System.out.println(alias1);
			if (alias1 != null) columnName1 = alias1 + "." + columnName1.split("\\.")[1];
			//String columnName2 = orderByElements2.get(i).toString();
			String columnName2 = column2.toString();
			//System.out.println(columnName2);
			String alias2 = column2.getTable().getAlias();
			if (alias2 != null) columnName2 = alias2 + "." + columnName2.split("\\.")[1];
			//System.out.println(columnName1);
			String attr1 = tuple1.getElement(hashMap1.get(columnName1));
			//System.out.println(attr1);
			//System.out.println("printed attr1");
			String attr2 = tuple2.getElement(hashMap2.get(columnName2));
			//System.out.println(attr2);
			if (Integer.parseInt(attr1) < Integer.parseInt(attr2)) {
				//System.out.println(-1);
				return -1;
			}
			if (Integer.parseInt(attr1) > Integer.parseInt(attr2)) {
				//System.out.println(1);
				return 1;
			}
		}
		//System.out.println(0);
		return 0;
	}

	@Override
	public void reset() throws IOException {
		so1.reset();
		so2.reset();
		leftTuple = so1.getNextTuple();
		rightTuple = so2.getNextTuple();

	}

}
