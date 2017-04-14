package cs4321.project3.IO;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * Use BufferedReader to implement reader of human readable files
 * @author Jiaxing Geng (jg755), Yangyi Hao (yh326) 
 *
 */
public class HumanReadableReader implements TupleReader {
	
	private BufferedReader bf;
	private String dataPath;
	
	/**
	 * Constructor of Human Readable Reader
	 * @param dataPath location to write file
	 * @throws IOException
	 */
	public HumanReadableReader(String dataPath) throws IOException{
		bf = new BufferedReader(new FileReader(dataPath));
		this.dataPath = dataPath; 
	}
	
	/**
	 * Read current line
	 */
	@Override
	public String readLine() throws IOException{
		return  bf.readLine();	
	}

	/**
	 * reset the operator
	 */
	@Override
	public void reset() throws IOException{
		if (bf == null)  
			bf = new BufferedReader(new FileReader(dataPath));
		else {
			bf.close();
			bf = new BufferedReader(new FileReader(dataPath));
		}	
	}

	/**
	 * close the reader buffer
	 */
	@Override
	public void close() throws IOException{
		bf.close();
	}

}
