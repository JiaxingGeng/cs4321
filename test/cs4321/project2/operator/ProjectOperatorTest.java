package cs4321.project2.operator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import cs4321.project2.Catalog;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.statement.select.SelectItem;

public class ProjectOperatorTest {

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
		slo.reset();
		SelectExpressionItem selectExpressionItem = new SelectExpressionItem();
		selectExpressionItem.setExpression(new Column(t, "A"));
		List<SelectItem> selectItems = new ArrayList<SelectItem> ();
		selectItems.add(selectExpressionItem);
		ProjectOperator po = new ProjectOperator(slo, selectItems);
		System.out.println("Projected results");
		po.dump();
		} catch (IOException e) {}
	}
	
	@Test
	public void testGetNextTuple1() {
		System.out.println("-----------------------");
		System.out.println("| testGetNextTuple1() |");
		System.out.println("-----------------------");
		Table tl = new Table(null,"JoinTestLeftTable0");
		tl.setAlias("JTLT0");
		Table tr = new Table(null, "JoinTestRightTable0");
		tr.setAlias("JTRT0");
		Column leftExpCol = new Column(tl, "A");
		Column rightExpCol = new Column(tr, "A");
		EqualsTo equalsToExp = new EqualsTo();
		equalsToExp.setLeftExpression(leftExpCol);
		equalsToExp.setRightExpression(rightExpCol);
		try{ Catalog.getInstance("testFolder");
		ScanOperator sol = new ScanOperator(tl);
		System.out.println("Left scanned results");
		sol.dump();
		sol.reset();
		ScanOperator sor = new ScanOperator(tr);
		System.out.println("Right scanned results");
		sor.dump();
		sor.reset();
		JoinOperator jo = new JoinOperator(sol, sor, equalsToExp);
		System.out.println("Joined results");
		jo.dump();
		jo.reset();
		SelectExpressionItem selectExpressionItem0 = new SelectExpressionItem();
		selectExpressionItem0.setExpression(new Column(tl, "A"));
		SelectExpressionItem selectExpressionItem1 = new SelectExpressionItem();
		selectExpressionItem1.setExpression(new Column(tr, "A"));
		List<SelectItem> selectItems = new ArrayList<SelectItem> ();
		selectItems.add(selectExpressionItem0);
		selectItems.add(selectExpressionItem1);
		ProjectOperator po = new ProjectOperator(jo, selectItems);
		System.out.println("Projected results");
		po.dump();
		} catch (IOException e) {}
	}

	@Test
	public void testReset() {
		System.out.println("---------------");
		System.out.println("| testReset() |");
		System.out.println("---------------");
		Table tl = new Table(null,"JoinTestLeftTable0");
		tl.setAlias("JTLT0");
		Table tr = new Table(null, "JoinTestRightTable0");
		tr.setAlias("JTRT0");
		Column leftExpCol = new Column(tl, "A");
		Column rightExpCol = new Column(tr, "A");
		EqualsTo equalsToExp = new EqualsTo();
		equalsToExp.setLeftExpression(leftExpCol);
		equalsToExp.setRightExpression(rightExpCol);
		try{ Catalog.getInstance("testFolder");
		ScanOperator sol = new ScanOperator(tl);
		System.out.println("Left scanned results");
		sol.dump();
		sol.reset();
		ScanOperator sor = new ScanOperator(tr);
		System.out.println("Right scanned results");
		sor.dump();
		sor.reset();
		JoinOperator jo = new JoinOperator(sol, sor, equalsToExp);
		System.out.println("Joined results");
		jo.dump();
		jo.reset();
		SelectExpressionItem selectExpressionItem0 = new SelectExpressionItem();
		selectExpressionItem0.setExpression(new Column(tl, "A"));
		SelectExpressionItem selectExpressionItem1 = new SelectExpressionItem();
		selectExpressionItem1.setExpression(new Column(tr, "A"));
		List<SelectItem> selectItems = new ArrayList<SelectItem> ();
		selectItems.add(selectExpressionItem0);
		selectItems.add(selectExpressionItem1);
		ProjectOperator po = new ProjectOperator(jo, selectItems);
		System.out.println("Projected results");
		po.dump();
		po.reset();
		System.out.println("Reset");
		po.dump();
		} catch (IOException e) {}
	}

}
