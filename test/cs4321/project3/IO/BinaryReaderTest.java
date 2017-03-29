package cs4321.project3.IO;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

public class BinaryReaderTest {

	@Test
	public void testBinaryReader() throws IOException {
		BinaryReader myReader = new BinaryReader("C:\\Users\\ronha_000\\OneDrive\\Spring\\CS 4321\\Samples_p3\\samples\\input\\db\\data\\Boats");
		System.out.println(myReader.readLine());
		System.out.println(myReader.readLine());
		System.out.println(myReader.readLine());
	}

	@Test
	public void testReadLine() {
		fail("Not yet implemented");
	}

	@Test
	public void testReset() {
		fail("Not yet implemented");
	}

	@Test
	public void testClose() {
		fail("Not yet implemented");
	}

}
