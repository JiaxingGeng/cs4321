package cs4321.project2.operator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import cs4321.project2.Catalog;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.OrderByElement;

public class SortOperatorTest {

	@Test
	public void testGetNextTuple0() {
		System.out.println("-----------------------");
		System.out.println("| testGetNextTuple0() |");
		System.out.println("-----------------------");
		Table t = new Table(null,"SortTestTable0");
		t.setAlias("STT0");
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
		sto.dump(); // Should print the original table
		} catch (IOException e) {}
	}
	
	@Test
	public void testGetNextTuple1() {
		System.out.println("-----------------------");
		System.out.println("| testGetNextTuple1() |");
		System.out.println("-----------------------");
		Table t = new Table(null,"SortTestTable1");
		t.setAlias("STT1");
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
		sto.dump(); // Should print the sorted table
		} catch (IOException e) {}
	}
	
	@Test
	public void testGetNextTuple2() {
		System.out.println("-----------------------");
		System.out.println("| testGetNextTuple2() |");
		System.out.println("-----------------------");
		Table t = new Table(null,"SortTestTable2");
		t.setAlias("STT2");
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
		sto.dump(); // Should print the sorted table
		} catch (IOException e) {}
	}
	
	@Test
	public void testGetNextTuple3() {
		System.out.println("-----------------------");
		System.out.println("| testGetNextTuple3() |");
		System.out.println("-----------------------");
		Table t = new Table(null,"SortTestTable2");
		t.setAlias("STT2");
		try{ Catalog.getInstance("testFolder");
		ScanOperator so = new ScanOperator(t);
		List<OrderByElement> orderByElements = new ArrayList<OrderByElement> ();
		OrderByElement orderByElement0 = new OrderByElement();
		orderByElement0.setExpression(new Column(t, "B"));
		orderByElements.add(orderByElement0);
		OrderByElement orderByElement1 = new OrderByElement();
		orderByElement1.setExpression(new Column(t, "A"));
		orderByElements.add(orderByElement1);
		SortOperator sto = new SortOperator(so, orderByElements);
		sto.dump(); // Should print the sorted table
		} catch (IOException e) {}
	}

	@Test
	/**
	 * In this case, what is reset supposed to do?
	 */
	public void testReset() {
		System.out.println("---------------");
		System.out.println("| testReset() |");
		System.out.println("---------------");
		Table t = new Table(null,"SortTestTable1");
		t.setAlias("STT1");
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
		sto.getNextTuple().print();
		sto.getNextTuple().print();
		sto.getNextTuple().print();
		sto.getNextTuple().print();
		sto.getNextTuple().print();
		sto.reset();
		System.out.println("Reset");
		sto.getNextTuple().print();
		sto.getNextTuple().print();
		sto.getNextTuple().print();
		sto.reset();
		System.out.println("Reset");
		sto.getNextTuple().print();
		sto.getNextTuple().print();
		sto.getNextTuple().print();
		} catch (IOException e) {}
	}

}
