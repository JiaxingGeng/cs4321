package cs4321.project2.querytree;

import net.sf.jsqlparser.schema.*;
import net.sf.jsqlparser.statement.select.*;
import net.sf.jsqlparser.expression.operators.relational.*;
import net.sf.jsqlparser.expression.*;

import static org.junit.Assert.fail;

import java.util.LinkedList;

import org.junit.Test;

import cs4321.project2.Catalog;

public class QueryTreeTest {


	/**
	 * Test Constructor and initialize query tree
	 */
	@Test
	public void testQueryTree() {
		System.out.println("-------------------");
		System.out.println("| testQueryTree() |");
		System.out.println("-------------------");
		// set up fromItem and fromJoins
		Table t1 = new Table(null,"Boat");
		Table t2 = new Table(null,"Sailor");
		Table t3 = new Table(null,"Reserve");
		Table t4 = new Table(null,"Date");
		Join j2 = new Join();
		j2.setRightItem(t2);
		Join j3 = new Join();
		j3.setRightItem(t3);
		Join j4 = new Join();
		j4.setRightItem(t4);
		FromItem fromItem = (FromItem) t1;
		LinkedList<Join> fromJoins = new LinkedList<>();
		fromJoins.add(j2);
		fromJoins.add(j3);
		fromJoins.add(j4);

		QueryTree querytree = new QueryTree(fromItem, fromJoins);
		querytree.print(querytree.getQueryTree());				
	}

	/**
	 * Test setExpression
	 */
	@Test
	public void testSetExpression(){
		System.out.println("-----------------------");
		System.out.println("| testSetExpression() |");
		System.out.println("-----------------------");
		// set up query tree
		Table t1 = new Table(null,"Boat");
		Table t2 = new Table(null,"Sailor");
		Table t3 = new Table(null,"Reserve");
		Table t4 = new Table(null,"Date");
		Join j2 = new Join();
		j2.setRightItem(t2);
		Join j3 = new Join();
		j3.setRightItem(t3);
		Join j4 = new Join();
		j4.setRightItem(t4);
		FromItem fromItem = (FromItem) t1;
		LinkedList<Join> fromJoins = new LinkedList<>();
		fromJoins.add(j2);
		fromJoins.add(j3);
		fromJoins.add(j4);
		QueryTree querytree = new QueryTree(fromItem, fromJoins);

		// test single insertion		
		GreaterThan expression = new GreaterThan();
		expression.setLeftExpression(new Column(t1,"A"));
		expression.setRightExpression(new LongValue("10"));
		System.out.println("Insert Expression:" + expression.toString());
		querytree.setExpression(expression, "Boat.null", null);
		System.out.println("---------------------------");
		querytree.print(querytree.getQueryTree());
		System.out.println("---------------------------");
		expression = new GreaterThan();
		expression.setLeftExpression(new Column(t3,"B"));
		expression.setRightExpression(new LongValue("12"));
		System.out.println("Insert Expression:" + expression.toString());
		querytree.setExpression(expression, "Reserve.null", null);
		System.out.println("---------------------------");
		querytree.print(querytree.getQueryTree());
		System.out.println("---------------------------");
		expression = new GreaterThan();
		expression.setRightExpression(new Column(t1,"C"));
		expression.setLeftExpression(new LongValue("10"));
		System.out.println("Insert Expression:" + expression.toString());
		querytree.setExpression(expression, "Boat.null", null);
		System.out.println("---------------------------");
		querytree.print(querytree.getQueryTree());
		System.out.println("---------------------------");

		// test join insertion
		expression = new GreaterThan();
		expression.setLeftExpression(new Column(t1,"D"));
		expression.setRightExpression(new Column(t3,"E"));
		System.out.println("Insert Expression:" + expression.toString());
		querytree.setExpression(expression, "Boat.null", "Reserve.null");
		System.out.println("---------------------------");
		querytree.print(querytree.getQueryTree());
		System.out.println("---------------------------");
		expression = new GreaterThan();
		expression.setLeftExpression(new Column(t4,"F"));
		expression.setRightExpression(new Column(t3,"G"));
		System.out.println("Insert Expression:" + expression.toString());
		querytree.setExpression(expression, "Reserve.null", "Date.null");
		System.out.println("---------------------------");
		querytree.print(querytree.getQueryTree());
		System.out.println("---------------------------");	
	}

	/**
	 * test getQueryPlan()
	 */
	@Test
	public void testGetQueryPlan(){
		System.out.println("----------------------");
		System.out.println("| testGetQueryPlan() |");
		System.out.println("----------------------");
		// set up query tree
		Table t1 = new Table(null,"Boats");
		Table t2 = new Table(null,"Sailors");
		Table t3 = new Table(null,"Reserves");
		Table t4 = new Table(null,"Date");
		Join j2 = new Join();
		j2.setRightItem(t2);
		Join j3 = new Join();
		j3.setRightItem(t3);
		Join j4 = new Join();
		j4.setRightItem(t4);
		FromItem fromItem = (FromItem) t1;
		LinkedList<Join> fromJoins = new LinkedList<>();
		fromJoins.add(j2);
		fromJoins.add(j3);
		fromJoins.add(j4);
		QueryTree querytree = new QueryTree(fromItem, fromJoins);

		try{
			Catalog.getInstance("testFolder");
			// test single insertion		
			GreaterThan expression = new GreaterThan();
			expression.setLeftExpression(new Column(t1,"A"));
			expression.setRightExpression(new LongValue("10"));
			System.out.println("Insert Expression:" + expression.toString());
			querytree.setExpression(expression, "Boats.null", null);
			System.out.println("---------------------------");
			querytree.printQueryPlan(querytree.getQueryPlan());
			System.out.println("---------------------------");		
			expression = new GreaterThan();
			expression.setLeftExpression(new Column(t3,"B"));
			expression.setRightExpression(new LongValue("12"));
			System.out.println("Insert Expression:" + expression.toString());
			querytree.setExpression(expression, "Reserves.null", null);
			System.out.println("---------------------------");
			querytree.printQueryPlan(querytree.getQueryPlan());
			System.out.println("---------------------------");
			expression = new GreaterThan();
			expression.setRightExpression(new Column(t1,"C"));
			expression.setLeftExpression(new LongValue("10"));
			System.out.println("Insert Expression:" + expression.toString());
			querytree.setExpression(expression, "Boats.null", null);
			System.out.println("---------------------------");
			querytree.printQueryPlan(querytree.getQueryPlan());
			System.out.println("---------------------------");

			// test join insertion
			expression = new GreaterThan();
			expression.setLeftExpression(new Column(t1,"D"));
			expression.setRightExpression(new Column(t3,"E"));
			System.out.println("Insert Expression:" + expression.toString());
			querytree.setExpression(expression, "Boats.null", "Reserves.null");
			System.out.println("---------------------------");
			querytree.printQueryPlan(querytree.getQueryPlan());
			System.out.println("---------------------------");
			expression = new GreaterThan();
			expression.setLeftExpression(new Column(t4,"F"));
			expression.setRightExpression(new Column(t2,"G"));
			System.out.println("Insert Expression:" + expression.toString());
			querytree.setExpression(expression, "Sailors.null", "Date.null");
			System.out.println("---------------------------");
			querytree.printQueryPlan(querytree.getQueryPlan());
			System.out.println("---------------------------");	

		}
		catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
			fail();
		}
	}

}
