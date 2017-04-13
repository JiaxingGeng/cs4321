package cs4321.project3.operator.physical;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cs4321.project2.operator.Operator;
import cs4321.project2.operator.SortOperator;
import cs4321.project2.operator.Tuple;
import cs4321.project3.deparser.SMJExpressionDeParser;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.select.OrderByElement;
import net.sf.jsqlparser.statement.select.SelectItem;

public class SMJOperator extends Operator {
	
	List<OrderByElement> orderByElements1;
	List<OrderByElement> orderByElements2;
	Operator op1;
	Operator op2;
	Operator so1;
	Operator so2;
	private Tuple leftTuple;
	private Tuple rightTuple;
	boolean needToRevert;
	int revertPoint;
	int currRightIndex;

	public SMJOperator(Operator op1, Operator op2, Expression expression) throws IOException {
		this.op1 = op1;
		this.op2 = op2;
		needToRevert = false;
		revertPoint = 0;
		currRightIndex = 0;
		
		// Step 1: figure out how to sort op1 and op2
		String[] columns1 = op1.getColumns();
		String tableAliasOrName1 = columns1[0].split("\\.")[0];
		//System.out.println(tableAliasOrName1);
		//System.out.println(Arrays.toString(columns1));
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
		for (int i = 0; i < orderByElementsInArray.get(0).size(); i++) {
			OrderByElement orderByElement = new OrderByElement();
			orderByElement.setExpression(orderByElementsInArray.get(0).get(i));
			orderByElements1.add(orderByElement);
		}
	    orderByElements2 = new ArrayList<OrderByElement> ();
		for (int i = 0; i < orderByElementsInArray.get(1).size(); i++) {
			OrderByElement orderByElement = new OrderByElement();
			orderByElement.setExpression(orderByElementsInArray.get(1).get(i));
			orderByElements2.add(orderByElement);
		}
		/*
		for (int i = 0; i < orderByElements1.size(); i++) {
			System.out.println(orderByElements1.get(i).toString());
		}
		for (int i = 0; i < orderByElements2.size(); i++) {
			System.out.println(orderByElements2.get(i).toString());
		}
		*/
		so1 = new SortOperator(op1, orderByElements1, new ArrayList<SelectItem> ());
		//so1.dump();
		//System.out.println("*******************************");
		so2 = new SortOperator(op2, orderByElements2, new ArrayList<SelectItem> ());
		//so2.dump();
		//System.out.println("*******************************");
		leftTuple = so1.getNextTuple();
		rightTuple = so2.getNextTuple();
		currRightIndex++;
		//System.out.println(tableAliasOrName2);
		//System.out.println(Arrays.toString(columns2));
		//String exp_string = expression.toString();
		//System.out.println(exp_string);
		
//		leftTuple.print();
//		so1.dump();
//		System.out.println("*******************************");
//		rightTuple.print();
//		so2.dump();
//		System.out.println("*******************************");
//		so1.reset();
//		so1.getNextTuple();
//		so2.reset();
//		so2.getNextTuple();
		
	}

	@Override
	public Tuple getNextTuple() throws IOException {
		/*
		System.out.print("the left tuple is: ");
		if (leftTuple != null) {
			leftTuple.print();
		}
		else System.out.println("null");
		System.out.print("the right tuple is: ");
		if (rightTuple != null) {
			rightTuple.print();
		}
		else System.out.println("null");
		*/
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
				//leftTuple.print();
				return this.getNextTuple();
			}
			else {
				needToRevert = false;
				return null;
			}
		}
		if (compare(leftTuple, rightTuple) == 0) {
			if (!needToRevert) revertPoint = currRightIndex;
			needToRevert = true;
			Tuple tempRightTuple = rightTuple;
			rightTuple = so2.getNextTuple();
			currRightIndex++;
			
			System.out.print("outputting ");
			leftTuple.joins(tempRightTuple).print();
			
			return leftTuple.joins(tempRightTuple);
		}
		else {
			if (needToRevert) {
				needToRevert = false;
				rightTuple = resetRight(revertPoint);
				currRightIndex = revertPoint;
				leftTuple = so1.getNextTuple();
				//leftTuple.print();
				return this.getNextTuple();
			}
			else {
				if (compare(leftTuple, rightTuple) < 0) {
					leftTuple = so1.getNextTuple();
					//leftTuple.print();
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
		/*
		System.out.print("comparing ");
		tuple1.print();
		System.out.print("and ");
		tuple2.print();
		*/
		//tuple1.print();
		//tuple2.print();
		HashMap<String, Integer> hashMap1 = op1.getColumnsHash();
		//System.out.println(hashMap1);
		HashMap<String, Integer> hashMap2 = op2.getColumnsHash();
		//System.out.println(hashMap2);
		for (int i = 0; i < orderByElements1.size(); i++) {
			Column column1 = (Column) orderByElements1.get(i).getExpression();
			Column column2 = (Column) orderByElements2.get(i).getExpression();
			String columnName1 = orderByElements1.get(i).toString();
			String alias1 = column1.getTable().getAlias();
			if (alias1 != null) columnName1 = alias1 + "." + columnName1.split("\\.")[1];
			//System.out.println(columnName1);
			String columnName2 = orderByElements2.get(i).toString();
			String alias2 = column2.getTable().getAlias();
			if (alias2 != null) columnName2 = alias2 + "." + columnName2.split("\\.")[1];
			//System.out.println(columnName2);
			String attr1 = tuple1.getElement(hashMap1.get(columnName1));
			//System.out.println("attr1 is: " + attr1);
			//System.out.println(hashMap2.get(columnName2));
			String attr2 = tuple2.getElement(hashMap2.get(columnName2));
			//String attr2 = tuple2.getElement(1);
			//System.out.println("attr2 is: " + attr2);
			//if (attr1.compareTo(attr2) < 0) {
			if (Integer.parseInt(attr1) < Integer.parseInt(attr2)) {
				//System.out.println("the result is: -1");
				return -1;
			}
			//if (attr1.compareTo(attr2) > 0) {
			if (Integer.parseInt(attr1) > Integer.parseInt(attr2)) {
				//System.out.println("the result is: 1");
				return 1;
			}
		}
		//System.out.println("the result is: 0");
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
