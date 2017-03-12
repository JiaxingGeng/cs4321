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
		ArrayList<SelectItem> selectItems =  new ArrayList<SelectItem>();  
		AllColumns allColumns = new AllColumns();
		selectItems.add(allColumns);
		try{ Catalog.getInstance("testFolder");
		ScanOperator so = new ScanOperator(t);
		List<Column> orderByElements = new ArrayList<Column> ();
		orderByElements.add(new Column(t, "A"));
		orderByElements.add(new Column(t, "B"));
		//orderByElements.add(new Column(t, "C"));
		SortOperator sto = new SortOperator(so, orderByElements);
		DuplicateEliminationOperator deo = new DuplicateEliminationOperator(sto,selectItems);
		deo.dump(); // Should print the sorted distinct table
		} catch (IOException e) {}
	}
	
	@Test
	public void testGetNextTuple1() {
		System.out.println("-----------------------");
		System.out.println("| testGetNextTuple1() |");
		System.out.println("-----------------------");
		Table t = new Table(null,"DuplicateTestTable1");
		t.setAlias("DTT1");
		ArrayList<SelectItem> selectItems =  new ArrayList<SelectItem>();  
		AllColumns allColumns = new AllColumns();
		selectItems.add(allColumns);
		try{ Catalog.getInstance("testFolder");
		ScanOperator so = new ScanOperator(t);
		List<Column> orderByElements = new ArrayList<Column> ();
		orderByElements.add(new Column(t, "A"));
		orderByElements.add(new Column(t, "B"));
		//orderByElements.add(new Column(t, "C"));
		SortOperator sto = new SortOperator(so, orderByElements);
		DuplicateEliminationOperator deo = new DuplicateEliminationOperator(sto,selectItems);
		deo.dump(); // Should print the sorted distinct table
		} catch (IOException e) {}
	}
	
	@Test
	public void testGetNextTuple2() {
		System.out.println("-----------------------");
		System.out.println("| testGetNextTuple2() |");
		System.out.println("-----------------------");
		Table t = new Table(null,"DuplicateTestTable2");
		t.setAlias("DTT2");
		ArrayList<SelectItem> selectItems =  new ArrayList<SelectItem>();  
		AllColumns allColumns = new AllColumns();
		selectItems.add(allColumns);
		try{ Catalog.getInstance("testFolder");
		ScanOperator so = new ScanOperator(t);
		List<Column> orderByElements = new ArrayList<Column> ();
		orderByElements.add(new Column(t, "A"));
		orderByElements.add(new Column(t, "B"));
		//orderByElements.add(new Column(t, "C"));
		SortOperator sto = new SortOperator(so, orderByElements);
		DuplicateEliminationOperator deo = new DuplicateEliminationOperator(sto,selectItems);
		deo.dump(); // Should print the sorted distinct table
		} catch (IOException e) {}
	}

	@Test
	public void testReset() {
		System.out.println("-----------------------");
		System.out.println("| testReset() |");
		System.out.println("-----------------------");
		Table t = new Table(null,"DuplicateTestTable0");
		t.setAlias("DTT0");
		ArrayList<SelectItem> selectItems =  new ArrayList<SelectItem>();  
		AllColumns allColumns = new AllColumns();
		selectItems.add(allColumns);
		try{ Catalog.getInstance("testFolder");
		ScanOperator so = new ScanOperator(t);
		List<Column> orderByElements = new ArrayList<Column> ();
		orderByElements.add(new Column(t, "A"));
		orderByElements.add(new Column(t, "B"));
		//orderByElements.add(new Column(t, "C"));
		SortOperator sto = new SortOperator(so, orderByElements);
		DuplicateEliminationOperator deo = new DuplicateEliminationOperator(sto,selectItems);
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
