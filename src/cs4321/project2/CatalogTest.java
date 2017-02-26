package cs4321.project2;

import static org.junit.Assert.*;
import java.io.IOException;
import java.util.LinkedList;

import org.junit.Test;

public class CatalogTest {

	/**
	 * Since there is only one Catalog object that can be created, we need to
	 * run the code multiple times to check different cases
	 */
	@Test
	public void testGetInstance() {		
		// test for null directory
		//		try {Catalog catalog = Catalog.getInstance(null);  fail();}
		//		catch (IOException e) {}
		// test for wrong directory
		//		try {Catalog catalog = Catalog.getInstance("abc");  fail();}
		//		catch (IOException e) {}		
		try {Catalog.getInstance("testCatalog");}
		catch (IOException e) {fail();}
	}

	/**
	 * Tests for getTables()
	 */
	@Test
	public void testGetTables(){
		try {Catalog catalog = Catalog.getInstance("testCatalog");
		LinkedList<String> tables = catalog.getTables();
		assertEquals("Sailors",tables.get(0));
		assertEquals("Reserves",tables.get(1));
		assertEquals("Table",tables.get(2));
		assertEquals(3,tables.size());}
		catch (IOException e) {e.printStackTrace();fail();}

		// test whether the catalog will be changed for a different dir
		try {Catalog catalog =Catalog.getInstance("abc");
		LinkedList<String> tables = catalog.getTables();
		assertEquals("Sailors",tables.get(0));
		assertEquals("Reserves",tables.get(1));
		assertEquals("Table",tables.get(2));
		assertEquals(3,tables.size());}
		catch (IOException e) {e.printStackTrace();fail();}

	}

	/**
	 * Tests for getAttributes()
	 */
	@Test
	public void testGetAttributes(){
		try {Catalog catalog =Catalog.getInstance("test/testCatalog");
		assertEquals("Sailors: A B C",catalog.tableToString("Sailors"));
		assertEquals("Reserves: G H",catalog.tableToString("Reserves"));
		assertEquals("Table: A B C",catalog.tableToString("Table"));}
		catch (IOException e) {e.printStackTrace();fail();}
	}
}
