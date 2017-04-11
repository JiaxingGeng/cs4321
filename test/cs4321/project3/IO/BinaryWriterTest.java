package cs4321.project3.IO;

import static org.junit.Assert.*;
import java.io.IOException;
import cs4321.project2.operator.*;
import org.junit.Test;
import java.io.FileInputStream;
import java.nio.channels.FileChannel;
import java.nio.ByteBuffer;


public class BinaryWriterTest {

	@Test
	public void testWrite1() throws IOException{
		BinaryWriter binWriter = new BinaryWriter("./testFolder/db/data/Boats_copy");
		String s1 = "1,2,3";
		String s2 = "2,3,4";
		binWriter.write(new Tuple(s1.split(",")));
		binWriter.write(new Tuple(s2.split(",")));
		for(int i=0;i<340;i++){  //write over 2 pages
			binWriter.write(new Tuple(s2.split(",")));
		}	
		binWriter.close();
	}

	/**
	 * Copy the table and check every byte of the output
	 * @throws IOException
	 */
	@Test
	public void testWrite2() throws IOException{

		// copy table
		BinaryReader binReader = new BinaryReader("./testFolder/db/data/Boats");
		BinaryWriter binWriter = new BinaryWriter("./testFolder/db/data/Boats_copy");
		String currentLine = binReader.readLine();
		while (currentLine!=null){
			Tuple t = new Tuple(currentLine.split(","));
			binWriter.write(t);
			currentLine = binReader.readLine();
		}
		binWriter.close();

		// check every byte
		FileInputStream fin1 = new FileInputStream("./testFolder/db/data/Boats");
		FileInputStream fin2 = new FileInputStream("./testFolder/db/data/Boats_copy");
		FileChannel fc1 = fin1.getChannel();
		FileChannel fc2 = fin2.getChannel();
		ByteBuffer buffer1 = ByteBuffer.allocate( 4096 ); 
		ByteBuffer buffer2 = ByteBuffer.allocate( 4096 );
		int res = fc1.read(buffer1);
		while (res!=-1){
			int res2 = fc2.read(buffer2); 
			if (res2 ==-1) fail();  // not having the same page
			for(int i=0;i<4096;i++){
				byte b1 = buffer1.get(i);
				byte b2 = buffer2.get(i);
				assertEquals(b1,b2);
			}
			buffer1 = ByteBuffer.allocate( 4096 ); 
			buffer2 = ByteBuffer.allocate( 4096 );
			res = fc1.read(buffer1);
		}

		fin1.close();
		fin2.close();
	}

	/**
	 * Test final output with expected output in checkpoint
	 * @throws IOException
	 */
	@Test
	public void testOutput() {

		try{
		for (int i=13;i<=15;i++){
			String s = "query"+i;
//			System.out.println(s);
			// check every byte
			FileInputStream fin1 = new FileInputStream("./output/"+s);
			FileInputStream fin2 = new FileInputStream("./expected/"+s);
			FileChannel fc1 = fin1.getChannel();
			FileChannel fc2 = fin2.getChannel();
			ByteBuffer buffer1 = ByteBuffer.allocate( 4096 ); 
			ByteBuffer buffer2 = ByteBuffer.allocate( 4096 );
			int res = fc1.read(buffer1);
			while (res!=-1){
				int res2 = fc2.read(buffer2); 
				if (res2 ==-1) fail();  // not having the same page
				for(int j=0;j<4096;j++){
					byte b1 = buffer1.get(j);
					byte b2 = buffer2.get(j);
					assertEquals(b1,b2);
				}
				buffer1 = ByteBuffer.allocate( 4096 ); 
				buffer2 = ByteBuffer.allocate( 4096 );
				res = fc1.read(buffer1);
			}		
			fin1.close();
			fin2.close();		
		}} catch (Exception e){
			fail();
		}

	}


}
