package cs4321.project3.IO;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

public class BinaryReaderTest {

	@Test
	public void testReadLine() throws IOException {
		BinaryReader binReader = new BinaryReader("./input/db/data/Boats");
		HumanReadableReader humanReader = 
				new HumanReadableReader("./testFolder/db/data/Boats_humanreadable");
		String currentLine = binReader.readLine();
		while (currentLine !=null){
			System.out.println(currentLine);
			String compLine = humanReader.readLine();
			assertEquals(true,currentLine.equals(compLine)); // compare all tuples
			currentLine = binReader.readLine();
		}
		assertEquals(null,humanReader.readLine()); //read last line
		
		binReader = new BinaryReader("./testFolder/db/data/Sailors");
		humanReader = 
				new HumanReadableReader("./testFolder/db/data/Sailors_humanreadable");
		currentLine = binReader.readLine();
		while (currentLine !=null){
			String compLine = humanReader.readLine();
			assertEquals(true,currentLine.equals(compLine)); // compare all tuples
			currentLine = binReader.readLine();
		}
		assertEquals(null,humanReader.readLine()); //read last line
		
		binReader = new BinaryReader("./testFolder/db/data/Reserves");
		humanReader = 
				new HumanReadableReader("./testFolder/db/data/Reserves_humanreadable");
		currentLine = binReader.readLine();
		while (currentLine !=null){
			String compLine = humanReader.readLine();
			assertEquals(true,currentLine.equals(compLine)); // compare all tuples
			currentLine = binReader.readLine();
		}
		assertEquals(null,humanReader.readLine()); //read last line
	}
	
	@Test
	public void testReset()  throws IOException {
		
		BinaryReader binReader = new BinaryReader("./testFolder/db/data/Boats");
		int i = 0;
		// test reset in the first page
		while (i<291){ 
			i++;
			binReader.readLine();
		}
		binReader.reset();
		String currentLine = binReader.readLine();
		assertEquals("12,143,196",currentLine);
		
		i=0;
		while (i<400){   // on the second page
			i++;
			binReader.readLine();
		}
		binReader.reset();
		currentLine = binReader.readLine();
		assertEquals("12,143,196",currentLine);
	}

	
}
