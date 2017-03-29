package cs4321.project3.operator;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import cs4321.project2.Catalog;
import cs4321.project2.operator.ScanOperator;
import cs4321.project2.operator.Tuple;
import cs4321.project3.IO.BinaryWriter;
import net.sf.jsqlparser.schema.Table;

public class ScanBinOperatorTest {

	@Test
	public void testGetNextTuple() {
		Table t = new Table(null,"Boats_bin");
		t.setAlias("B");
		try{ Catalog.getInstance("testFolder");
		ScanBinOperator so = new ScanBinOperator(t);
		Tuple t0 = so.getNextTuple();
		t0.print(); // print 12, 143, 196
		Tuple t1 = so.getNextTuple();
		t1.print(); // print 30, 63, 101
		//fail();
		Catalog cat = Catalog.getInstance(null);
		BinaryWriter binWriter = new BinaryWriter(cat.getInputDir() + "/db/data/" + "Boats_bin_echo");
		binWriter.write(t0);
		binWriter.write(t1);
		binWriter.close();
		} catch (IOException e) {}
		
		t = new Table(null,"Reserves_bin");
		try{ Catalog.getInstance("testFolder");
		ScanBinOperator so = new ScanBinOperator(t);
		so.getNextTuple();   
		so.getNextTuple();
		so.getNextTuple();
		so.getNextTuple().print(); // print 145, 88
		} catch (IOException e) {fail();}
	}

	@Test
	public void testReset() {
		fail("Not yet implemented");
	}

	@Test
	public void testScanBinOperator() {
		fail("Not yet implemented");
	}

	@Test
	public void testToString() {
		fail("Not yet implemented");
	}

}
