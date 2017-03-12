package cs4321.project2.operator;

import static org.junit.Assert.*;

import org.junit.Test;

public class TupleTest {

	@Test
	public void testGetColumns() {
		String[] attrs = {"attr1", "attr2", "attr3"};
		Tuple myTuple = new Tuple(attrs);
		assertEquals(myTuple.getColumns(), 3);
		String[] attrs1 = {};
		Tuple myTuple1 = new Tuple(attrs1);
		assertEquals(myTuple1.getColumns(), 0);
	}
	
	@Test
	public void testTuple(){
		String[] attrs = {"a", "b", "c"};
		Tuple leftTuple = new Tuple(attrs);
		String[] attrs2 = {"d", "e"};
		Tuple rightTuple = new Tuple(attrs2);
		Tuple joinTuple = leftTuple.joins(rightTuple);
		joinTuple.print();
	}

	@Test 
	public void testEquals(){
		String[] attrs1 = {"a", "b", "c"};
		Tuple tuple1 = new Tuple(attrs1);
		String[] attrs2 = {"a", "b", "d"};
		Tuple tuple2 = new Tuple(attrs2);
		assertEquals(false,tuple1.equals(tuple2,null));
	}
}
