package cs4321.project2.operator;

import java.io.IOException;
import org.junit.Test;

import cs4321.project2.Catalog;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.GreaterThan;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;

public class SelectOperatorTest {

	@Test
	public void testGetNextTuple0() {
		System.out.println("-----------------------");
		System.out.println("| testGetNextTuple0() |");
		System.out.println("-----------------------");
		Table t = new Table(null,"SelectTestTable0");
		t.setAlias("ST0");
		try{ Catalog.getInstance("testFolder");
		ScanOperator so = new ScanOperator(t);
		System.out.println("Original results");
		so.dump();
		so.reset();
		EqualsTo equalsTo = new EqualsTo();
		equalsTo.setLeftExpression(new Column(t, "A"));
		equalsTo.setRightExpression(new Column(t, "B"));
		SelectOperator slo = new SelectOperator(so, equalsTo);
		System.out.println("Selected results:");
		slo.dump();
		} catch (IOException e) {}
	}
	
	@Test
	public void testGetNextTuple1() {
		System.out.println("-----------------------");
		System.out.println("| testGetNextTuple1() |");
		System.out.println("-----------------------");
		Table t = new Table(null,"SelectTestTable0");
		t.setAlias("ST0");
		try{ Catalog.getInstance("testFolder");
		ScanOperator so = new ScanOperator(t);
		System.out.println("Original results");
		so.dump();
		so.reset();
		EqualsTo equalsTo = new EqualsTo();
		equalsTo.setLeftExpression(new Column(t, "A"));
		equalsTo.setRightExpression(new Column(t, "B"));
		GreaterThan greaterThan = new GreaterThan();
		greaterThan.setLeftExpression(new Column(t, "C"));
		greaterThan.setRightExpression(new Column(t, "A"));
		AndExpression andExpression = new AndExpression();
		andExpression.setLeftExpression(equalsTo);
		andExpression.setRightExpression(greaterThan);
		SelectOperator slo = new SelectOperator(so, andExpression);
		System.out.println("Selected results:");
		slo.dump();
		} catch (IOException e) {}
	}

	@Test
	public void testReset() {
		System.out.println("---------------");
		System.out.println("| testReset() |");
		System.out.println("---------------");
		Table t = new Table(null,"SelectTestTable0");
		t.setAlias("ST0");
		try{ Catalog.getInstance("testFolder");
		ScanOperator so = new ScanOperator(t);
		EqualsTo equalsTo = new EqualsTo();
		equalsTo.setLeftExpression(new Column(t, "A"));
		equalsTo.setRightExpression(new Column(t, "B"));
		GreaterThan greaterThan = new GreaterThan();
		greaterThan.setLeftExpression(new Column(t, "C"));
		greaterThan.setRightExpression(new Column(t, "A"));
		AndExpression andExpression = new AndExpression();
		andExpression.setLeftExpression(equalsTo);
		andExpression.setRightExpression(greaterThan);
		SelectOperator slo = new SelectOperator(so, andExpression);
		System.out.println("Selected results:");
		slo.dump();
		slo.reset();
		System.out.println("Reset");
		slo.dump();
		} catch (IOException e) {}
	}

}
