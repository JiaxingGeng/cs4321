package cs4321.project2.operator;

import static org.junit.Assert.*;
import net.sf.jsqlparser.schema.Table;
import java.io.IOException;

import org.junit.Test;

import cs4321.project2.Catalog;


public class ScanOperatorTest {

	/**
	 * test for getNextTuple(). Assume that the table exists in the folder
	 */
	@Test
	public void testGetNextTuple() {
		Table t = new Table(null,"Boats");
		t.setAlias("B");
		try{ Catalog.getInstance("testFolder");
		ScanOperator so = new ScanOperator(t);
		so.getNextTuple().print();   // print 101, 2, 3,
		so.getNextTuple().print();   // raise exception for empty line
		//fail();
		} catch (IOException e) {}
		
		t = new Table(null,"Reserves");
		try{ Catalog.getInstance("testFolder");
		ScanOperator so = new ScanOperator(t);
		so.getNextTuple();   
		so.getNextTuple();
		so.getNextTuple();
		so.getNextTuple().print(); // print 2, 101,
		} catch (IOException e) {fail();}
	}
	
	/**
	 * test for reset()
	 */
	@Test
	public void testReset(){
		Table t = new Table(null,"Reserves");
		try{ Catalog.getInstance("testFolder");
		ScanOperator so = new ScanOperator(t);
		so.getNextTuple();   
		so.getNextTuple();
		so.getNextTuple();
		so.reset(); 
		so.getNextTuple().print();  // print 1,101,
		} catch (IOException e) {fail();}				
	}
	
	/**
	 * test for dump() and reset() after dump
	 */
	@Test
	public void testDump(){
		/*
		 * Output should be this for each dump():
		 * Reserves Table Print:
		 * 1, 101, 
		 * 1, 102, 
		 * 1, 103, 
		 * 2, 101, 
		 * 3, 102, 
		 * 4, 104, 
		 */
		Table t = new Table(null,"Reserves");
		try{ Catalog.getInstance("testFolder");
		ScanOperator so = new ScanOperator(t);
		System.out.println("Reserves Table Print:");
		so.dump();   
		so.reset();
		System.out.println("Reserves Table Print:");
		so.dump();
		} catch (IOException e) 
		{e.printStackTrace();
		System.err.println(e.getMessage());
		fail();}	
	}
}
