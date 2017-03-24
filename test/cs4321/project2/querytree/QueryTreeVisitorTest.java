package cs4321.project2.querytree;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Test;

import cs4321.project2.Catalog;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.operators.relational.MinorThan;
import net.sf.jsqlparser.expression.operators.conditional.*;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.FromItem;
import net.sf.jsqlparser.statement.select.Join;

public class QueryTreeVisitorTest {

	@Test
	public void testQueryTreeVisitor() {
		try{
			Catalog.getInstance("testFolder");
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

			// col < num
			QueryTree querytree = new QueryTree(fromItem, fromJoins);
			MinorThan expression = new MinorThan();
			expression.setLeftExpression(new Column(t1,"A"));
			expression.setRightExpression(new Column(t1,"C"));
			System.out.println("Insert Expression:" + expression.toString());
			QueryTreeVisitor visitor = new QueryTreeVisitor(querytree);
			expression.accept(visitor);
			querytree = visitor.getQueryTree();
			System.out.println("---------------------------");
			querytree.printQueryPlan(querytree.getQueryPlan());
			System.out.println("---------------------------");

			// col1 < col2
			querytree = new QueryTree(fromItem, fromJoins);
			expression = new MinorThan();
			expression.setLeftExpression(new Column(t4,"A"));
			expression.setRightExpression(new Column(t2,"G"));
			System.out.println("Insert Expression:" + expression.toString());
			visitor = new QueryTreeVisitor(querytree);
			expression.accept(visitor);
			querytree = visitor.getQueryTree();
			System.out.println("---------------------------");
			querytree.printQueryPlan(querytree.getQueryPlan());
			System.out.println("---------------------------");

			// AND expression
			querytree = new QueryTree(fromItem, fromJoins);
			expression = new MinorThan();
			expression.setLeftExpression(new Column(t4,"A"));
			expression.setRightExpression(new Column(t2,"G"));
			MinorThan expression2 = new MinorThan();
			expression2.setLeftExpression(new Column(t1,"A"));
			expression2.setRightExpression(new LongValue("10"));
			AndExpression expression3 = new AndExpression();
			expression3.setLeftExpression(expression);
			expression3.setRightExpression(expression2);
			System.out.println("Insert Expression:" + expression3.toString());
			visitor = new QueryTreeVisitor(querytree);
			expression3.accept(visitor);
			querytree = visitor.getQueryTree();
			System.out.println("---------------------------");
			querytree.printQueryPlan(querytree.getQueryPlan());
			System.out.println("---------------------------");

			// AND expression
			querytree = new QueryTree(fromItem, fromJoins);
			expression = new MinorThan();
			expression.setLeftExpression(new Column(t1,"A"));
			expression.setRightExpression(new Column(t1,"C"));
			expression2 = new MinorThan();
			expression2.setLeftExpression(new Column(t1,"A"));
			expression2.setRightExpression(new LongValue("10"));
			expression3 = new AndExpression();
			expression3.setLeftExpression(expression);
			expression3.setRightExpression(expression2);
			System.out.println("Insert Expression:" + expression3.toString());
			visitor = new QueryTreeVisitor(querytree);
			expression3.accept(visitor);
			querytree = visitor.getQueryTree();
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
