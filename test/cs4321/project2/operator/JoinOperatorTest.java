package cs4321.project2.operator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import cs4321.project2.Catalog;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.AllColumns;
import net.sf.jsqlparser.statement.select.SelectItem;

public class JoinOperatorTest {

	@Test
	// SELECT FROM JTLT0 JTRT0 WHERE JTLT0.A = JTRT0.A
	public void testGetNextTuple0() { 
		System.out.println("-----------------------");
		System.out.println("| testGetNextTuple0() |");
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
		ScanOperator sor = new ScanOperator(tr);
		JoinOperator jo = new JoinOperator(sol, sor, equalsToExp);
		jo.dump();
		} catch (IOException e) {}
	}
	
	@Test
	// SELECT FROM JTLT1 (distinct, ordered by A, B) JTRT1 (ordered by A, B) WHERE JTLT0.A = JTRT0.B
	public void testGetNextTuple1() { 
		System.out.println("-----------------------");
		System.out.println("| testGetNextTuple1() |");
		System.out.println("-----------------------");
		Table tl = new Table(null,"JoinTestLeftTable1");
		tl.setAlias("JTLT1");
		Table tr = new Table(null, "JoinTestRightTable1");
		tr.setAlias("JTRT1");
		Column leftExpCol = new Column(tl, "A");
		Column rightExpCol = new Column(tr, "B");
		EqualsTo equalsToExp = new EqualsTo();
		equalsToExp.setLeftExpression(leftExpCol);
		equalsToExp.setRightExpression(rightExpCol);
		try{ Catalog.getInstance("testFolder");
		
		ScanOperator sol = new ScanOperator(tl);
		List<Column> orderByElementsLeft = new ArrayList<Column> ();
		orderByElementsLeft.add(new Column(tl, "A"));
		orderByElementsLeft.add(new Column(tl, "B"));
		SortOperator stol = new SortOperator(sol, orderByElementsLeft);
		ArrayList<SelectItem> selectItems =  new ArrayList<SelectItem>();  
		AllColumns allColumns = new AllColumns();
		selectItems.add(allColumns);
		DuplicateEliminationOperator deol = new DuplicateEliminationOperator(stol,selectItems);
		
		ScanOperator sor = new ScanOperator(tr);
		List<Column> orderByElementsRight = new ArrayList<Column> ();
		orderByElementsRight.add(new Column(tr, "A"));
		orderByElementsRight.add(new Column(tr, "B"));
		SortOperator stor = new SortOperator(sor, orderByElementsRight);
		
		JoinOperator jo = new JoinOperator(deol, stor, equalsToExp);
		jo.dump();
		} catch (IOException e) {}
	}
	
	@Test
	public void testReset0() {
		System.out.println("----------------");
		System.out.println("| testReset0() |");
		System.out.println("----------------");
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
		ScanOperator sor = new ScanOperator(tr);
		JoinOperator jo = new JoinOperator(sol, sor, equalsToExp);
		jo.dump();
		jo.reset();
		System.out.println("Reset");
		jo.dump();
		} catch (IOException e) {}
	}

	@Test
	public void testReset1() {
		System.out.println("----------------");
		System.out.println("| testReset1() |");
		System.out.println("----------------");
		Table tl = new Table(null,"JoinTestLeftTable1");
		tl.setAlias("JTLT1");
		Table tr = new Table(null, "JoinTestRightTable1");
		tr.setAlias("JTRT1");
		Column leftExpCol = new Column(tl, "A");
		Column rightExpCol = new Column(tr, "B");
		EqualsTo equalsToExp = new EqualsTo();
		equalsToExp.setLeftExpression(leftExpCol);
		equalsToExp.setRightExpression(rightExpCol);
		try{ Catalog.getInstance("testFolder");
		
		ScanOperator sol = new ScanOperator(tl);
		List<Column> orderByElementsLeft = new ArrayList<Column> ();
		orderByElementsLeft.add(new Column(tl, "A"));
		orderByElementsLeft.add(new Column(tl, "B"));
		SortOperator stol = new SortOperator(sol, orderByElementsLeft);
		ArrayList<SelectItem> selectItems =  new ArrayList<SelectItem>();  
		AllColumns allColumns = new AllColumns();
		selectItems.add(allColumns);
		DuplicateEliminationOperator deol = new DuplicateEliminationOperator(stol,selectItems);
		
		ScanOperator sor = new ScanOperator(tr);
		List<Column> orderByElementsRight = new ArrayList<Column> ();
		orderByElementsRight.add(new Column(tr, "A"));
		orderByElementsRight.add(new Column(tr, "B"));
		SortOperator stor = new SortOperator(sor, orderByElementsRight);
		
		JoinOperator jo = new JoinOperator(deol, stor, equalsToExp);
		jo.dump();
		jo.reset();
		System.out.println("Reset");
		jo.dump();
		jo.reset();
		System.out.println("Reset");
		jo.getNextTuple().print();
		jo.getNextTuple().print();
		jo.getNextTuple().print();
		} catch (IOException e) {}
	}

}
