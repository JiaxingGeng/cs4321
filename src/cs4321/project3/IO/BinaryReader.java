package cs4321.project3.IO;

import java.io.IOException;
import java.io.FileInputStream;
import java.nio.channels.FileChannel;
import java.nio.ByteBuffer;

/**
 * Use JavaNIO(ByteBuffer) to read pages from binary file format input
 * @author Jiaxing Geng (jg755), Yangyi Hao (yh326) 
 *
 */
public class BinaryReader implements TupleReader {
	
	private int remainingTuples; // number of remaining tuples to be read
	private int numAttributes; // number of attributes for one tuple
	private int bufferPos; // index of current reader buffer position
	
	private FileInputStream fin;
	private ByteBuffer buffer;
	private FileChannel fc;
	
	public BinaryReader(String dataPath) throws IOException{
		fin = new FileInputStream(dataPath);
		fc = fin.getChannel();
		buffer = ByteBuffer.allocate( 4096 ); 
		remainingTuples = 0;
	}

	@Override
	public String readLine() throws IOException {
		if (remainingTuples == 0) {
			int res = startNewBuffer();
			if (res == -1) return null;
		}
		String tuple = "";
		for (int i=0;i<numAttributes-1;i++){
			int val = buffer.getInt(bufferPos);
			tuple = tuple + val +",";
			bufferPos += 4;
		}
		tuple = tuple +  buffer.getInt(bufferPos);
		bufferPos += 4;
		remainingTuples --;
		return tuple;
	}

	@Override
	public void reset() throws IOException {
		fc.position(0);
		remainingTuples = 0;
	}

	@Override
	public void close() throws IOException {
		fin.close();
	}

	private int startNewBuffer() throws IOException {
		int res = fc.read(buffer);
		/*
		int firstInt = buffer.getInt(0);
		int secondInt = buffer.getInt(4);
		System.out.println("first int:" + firstInt);
		System.out.println("second int: " + secondInt);
		*/
		
		remainingTuples = buffer.getInt(4);
		//System.out.println("remaining tuples: " + remainingTuples);
		numAttributes = buffer.getInt(0);
		//System.out.println("numAttributes: " + numAttributes);
		bufferPos = 8;  // position of first data entry
		
		return res;
	}
	
}
