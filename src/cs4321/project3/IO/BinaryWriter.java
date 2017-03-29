package cs4321.project3.IO;

import cs4321.project2.operator.Tuple;
import java.io.IOException;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.nio.ByteBuffer;

public class BinaryWriter implements TupleWriter {

	private static final int PAGE_SIZE = 4096;

	private int remainingBytes; // remaining bytes in current page
	private int numTuples; // number of tuples written in the page
	private int numAttributes; // number of attributes
	private int bufferPos; // index of current reader buffer position
	boolean firstBuffer; // whether is the first buffer or not

	private FileOutputStream fout;
	private FileChannel fc;
	private ByteBuffer buffer;

	public BinaryWriter(String dataPath) throws IOException{
		fout = new FileOutputStream(dataPath);
		fc = fout.getChannel();
		remainingBytes = 0;
		firstBuffer = true;
	}

	@Override
	public void write(Tuple t) throws IOException{
		int tupleSize = t.getColumns()*4;
		if (tupleSize > remainingBytes)  {
			if (!firstBuffer){
				outputCurrentBuffer();
				buffer = startNewBuffer(t);		
			} else {
				buffer = startNewBuffer(t);
				firstBuffer = false;
			}
		}
		String[] attrs = t.getAttributes();
		for (String attr:attrs){
			buffer.putInt(bufferPos, Integer.parseInt(attr));
			bufferPos +=4;
		}
		remainingBytes -= tupleSize;
		numTuples ++;
	}

	/**
	 * Fill zeros at the end of the current page when the writer is closed
	 */
	@Override
	public void close() throws IOException{
		if(!firstBuffer) outputCurrentBuffer();	
		else {  // no input: fill page with zeros
			bufferPos = 0;
			while(bufferPos < PAGE_SIZE){
				buffer = ByteBuffer.allocate(PAGE_SIZE);
				buffer.putInt(bufferPos, 0);
				bufferPos +=4;
			}		
		}
		fout.close();
	}

	private ByteBuffer startNewBuffer(Tuple t){
		bufferPos = 8;
		numTuples = 0;
		numAttributes = t.getColumns();
		remainingBytes = PAGE_SIZE - 8;
		return ByteBuffer.allocate(PAGE_SIZE);
	}
	
	private void outputCurrentBuffer() throws IOException{
		buffer.putInt(0, numAttributes);
		buffer.putInt(4, numTuples);
		// fill the remaining space with zeros
		if (remainingBytes != 0){
			while (bufferPos<PAGE_SIZE) {
				buffer.putInt(bufferPos, 0);
				bufferPos +=4;
			}
		}
		fc.write(buffer);
	}
}
