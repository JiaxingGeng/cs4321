package cs4321.project2.operator;

import static org.junit.Assert.*;

import org.junit.Test;

public class TupleTest {

	@Test
	public void testGetColumns() {
		String[] attrs = {"attr1", "attr2", "attr3"};
		Tuple myTuple = new Tuple(attrs);
		assertEquals(myTuple.getColumns(), 3);
	}
	
	@Test
	public void testGetColumnsEmpty() {
		String[] attrs = {};
		Tuple myTuple = new Tuple(attrs);
		assertEquals(myTuple.getColumns(), 0);
	}

}
