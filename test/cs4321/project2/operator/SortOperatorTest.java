package cs4321.project2.operator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import cs4321.project2.Catalog;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;

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
		List<Column> orderByElements = new ArrayList<Column> ();
		orderByElements.add(new Column(t, "A"));
		orderByElements.add(new Column(t, "B"));
		//orderByElements.add(new Column(t, "C"));
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
		List<Column> orderByElements = new ArrayList<Column> ();
		orderByElements.add(new Column(t, "A"));
		orderByElements.add(new Column(t, "B"));
		//orderByElements.add(new Column(t, "C"));
		SortOperator sto = new SortOperator(so, orderByElements);
		sto.dump(); // Should print the sorted table
		} catch (IOException e) {}
	}
	
	@Test
	public void testGetNextTuple2() {
		System.out.println("-----------------------");
		System.out.println("| testGetNextTuple2() |");
		System.out.println("-----------------------");
		Table t = new Table(null,"SortTestTable1");
		t.setAlias("STT2");
		try{ Catalog.getInstance("testFolder");
		ScanOperator so = new ScanOperator(t);
		List<Column> orderByElements = new ArrayList<Column> ();
		orderByElements.add(new Column(t, "A"));
		orderByElements.add(new Column(t, "B"));
		//orderByElements.add(new Column(t, "C"));
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
		List<Column> orderByElements = new ArrayList<Column> ();
		orderByElements.add(new Column(t, "A"));
		orderByElements.add(new Column(t, "B"));
		//orderByElements.add(new Column(t, "C"));
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
