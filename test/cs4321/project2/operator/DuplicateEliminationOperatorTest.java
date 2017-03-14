package cs4321.project2.operator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import cs4321.project2.Catalog;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.*;

public class DuplicateEliminationOperatorTest {

	@Test
	public void testGetNextTuple0() {
		System.out.println("-----------------------");
		System.out.println("| testGetNextTuple0() |");
		System.out.println("-----------------------");
		Table t = new Table(null,"DuplicateTestTable0");
		t.setAlias("DTT0");
		try{ Catalog.getInstance("testFolder");
		ScanOperator so = new ScanOperator(t);
		System.out.println("Original results:");
		so.dump();
		so.reset();
		List<OrderByElement> orderByElements = new ArrayList<OrderByElement> ();
		OrderByElement orderByElement0 = new OrderByElement();
		orderByElement0.setExpression(new Column(t, "A"));
		orderByElements.add(orderByElement0);
		OrderByElement orderByElement1 = new OrderByElement();
		orderByElement1.setExpression(new Column(t, "B"));
		orderByElements.add(orderByElement1);
		SortOperator sto = new SortOperator(so, orderByElements);
		System.out.println("Order results:");
		sto.dump();
		sto.reset();
		SelectExpressionItem selectExpressionItem = new SelectExpressionItem();
		selectExpressionItem.setExpression(new Column(t, "A"));
		List<SelectItem> selectItems = new ArrayList<SelectItem> ();
		selectItems.add(selectExpressionItem);
		DuplicateEliminationOperator deo = new DuplicateEliminationOperator(sto, selectItems);
		System.out.println("Distinct results:");
		deo.dump();
		} catch (IOException e) {}
	}
	
	@Test
	public void testGetNextTuple1() {
		System.out.println("-----------------------");
		System.out.println("| testGetNextTuple1() |");
		System.out.println("-----------------------");
		Table t = new Table(null,"DuplicateTestTable0");
		t.setAlias("DTT0");
		try{ Catalog.getInstance("testFolder");
		ScanOperator so = new ScanOperator(t);
		System.out.println("Original results:");
		so.dump();
		so.reset();
		List<OrderByElement> orderByElements = new ArrayList<OrderByElement> ();
		OrderByElement orderByElement0 = new OrderByElement();
		orderByElement0.setExpression(new Column(t, "A"));
		orderByElements.add(orderByElement0);
		OrderByElement orderByElement1 = new OrderByElement();
		orderByElement1.setExpression(new Column(t, "B"));
		orderByElements.add(orderByElement1);
		SortOperator sto = new SortOperator(so, orderByElements);
		System.out.println("Order results:");
		sto.dump();
		sto.reset();
		SelectExpressionItem selectExpressionItem0 = new SelectExpressionItem();
		selectExpressionItem0.setExpression(new Column(t, "A"));
		SelectExpressionItem selectExpressionItem1 = new SelectExpressionItem();
		selectExpressionItem1.setExpression(new Column(t, "B"));
		List<SelectItem> selectItems = new ArrayList<SelectItem> ();
		selectItems.add(selectExpressionItem0);
		selectItems.add(selectExpressionItem1);
		DuplicateEliminationOperator deo = new DuplicateEliminationOperator(sto, selectItems);
		System.out.println("Distinct results:");
		deo.dump();
		} catch (IOException e) {}
	}
	
	@Test
	public void testGetNextTuple2() {
		System.out.println("-----------------------");
		System.out.println("| testGetNextTuple2() |");
		System.out.println("-----------------------");
		Table t = new Table(null,"DuplicateTestTable1");
		t.setAlias("DTT1");
		try{ Catalog.getInstance("testFolder");
		ScanOperator so = new ScanOperator(t);
		System.out.println("Original results:");
		so.dump();
		so.reset();
		List<OrderByElement> orderByElements = new ArrayList<OrderByElement> ();
		OrderByElement orderByElement0 = new OrderByElement();
		orderByElement0.setExpression(new Column(t, "A"));
		orderByElements.add(orderByElement0);
		OrderByElement orderByElement1 = new OrderByElement();
		orderByElement1.setExpression(new Column(t, "B"));
		orderByElements.add(orderByElement1);
		SortOperator sto = new SortOperator(so, orderByElements);
		System.out.println("Order results:");
		sto.dump();
		sto.reset();
		SelectExpressionItem selectExpressionItem0 = new SelectExpressionItem();
		selectExpressionItem0.setExpression(new Column(t, "A"));
		SelectExpressionItem selectExpressionItem1 = new SelectExpressionItem();
		selectExpressionItem1.setExpression(new Column(t, "B"));
		List<SelectItem> selectItems = new ArrayList<SelectItem> ();
		selectItems.add(selectExpressionItem0);
		selectItems.add(selectExpressionItem1);
		DuplicateEliminationOperator deo = new DuplicateEliminationOperator(sto, selectItems);
		System.out.println("Distinct results:");
		deo.dump();
		} catch (IOException e) {}
	}
	
	@Test
	public void testGetNextTuple3() {
		System.out.println("-----------------------");
		System.out.println("| testGetNextTuple3() |");
		System.out.println("-----------------------");
		Table t = new Table(null,"DuplicateTestTable2");
		t.setAlias("DTT2");
		try{ Catalog.getInstance("testFolder");
		ScanOperator so = new ScanOperator(t);
		System.out.println("Original results:");
		so.dump();
		so.reset();
		List<OrderByElement> orderByElements = new ArrayList<OrderByElement> ();
		OrderByElement orderByElement0 = new OrderByElement();
		orderByElement0.setExpression(new Column(t, "A"));
		orderByElements.add(orderByElement0);
		OrderByElement orderByElement1 = new OrderByElement();
		orderByElement1.setExpression(new Column(t, "B"));
		orderByElements.add(orderByElement1);
		SortOperator sto = new SortOperator(so, orderByElements);
		System.out.println("Order results:");
		sto.dump();
		sto.reset();
		SelectExpressionItem selectExpressionItem0 = new SelectExpressionItem();
		selectExpressionItem0.setExpression(new Column(t, "A"));
		SelectExpressionItem selectExpressionItem1 = new SelectExpressionItem();
		selectExpressionItem1.setExpression(new Column(t, "B"));
		List<SelectItem> selectItems = new ArrayList<SelectItem> ();
		selectItems.add(selectExpressionItem0);
		selectItems.add(selectExpressionItem1);
		DuplicateEliminationOperator deo = new DuplicateEliminationOperator(sto, selectItems);
		System.out.println("Distinct results:");
		deo.dump();
		} catch (IOException e) {}
	}

	@Test
	public void testReset() {
		System.out.println("-----------------------");
		System.out.println("| testReset() |");
		System.out.println("-----------------------");
		Table t = new Table(null,"DuplicateTestTable0");
		t.setAlias("DTT0");
		try{ Catalog.getInstance("testFolder");
		ScanOperator so = new ScanOperator(t);
		List<OrderByElement> orderByElements = new ArrayList<OrderByElement> ();
		OrderByElement orderByElement0 = new OrderByElement();
		orderByElement0.setExpression(new Column(t, "A"));
		orderByElements.add(orderByElement0);
		OrderByElement orderByElement1 = new OrderByElement();
		orderByElement1.setExpression(new Column(t, "B"));
		orderByElements.add(orderByElement1);
		SortOperator sto = new SortOperator(so, orderByElements);
		SelectExpressionItem selectExpressionItem0 = new SelectExpressionItem();
		selectExpressionItem0.setExpression(new Column(t, "A"));
		SelectExpressionItem selectExpressionItem1 = new SelectExpressionItem();
		selectExpressionItem1.setExpression(new Column(t, "B"));
		List<SelectItem> selectItems = new ArrayList<SelectItem> ();
		selectItems.add(selectExpressionItem0);
		selectItems.add(selectExpressionItem1);
		DuplicateEliminationOperator deo = new DuplicateEliminationOperator(sto, selectItems);
		deo.getNextTuple().print();
		deo.getNextTuple().print();
		deo.getNextTuple().print();
		deo.getNextTuple().print();
		deo.getNextTuple().print();
		deo.reset();
		System.out.println("Reset");
		deo.getNextTuple().print();
		deo.getNextTuple().print();
		deo.getNextTuple().print();
		deo.reset();
		System.out.println("Reset");
		deo.getNextTuple().print();
		deo.getNextTuple().print();
		deo.getNextTuple().print();
		} catch (IOException e) {}
	}

}
