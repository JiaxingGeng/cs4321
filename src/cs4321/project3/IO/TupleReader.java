package cs4321.project3.IO;

import java.io.IOException;

/**
 * Tuple Reader Interface
 * @author Jiaxing Geng (jg755), Yangyi Hao (yh326) 
 */

public interface TupleReader {	
		
	public String readLine() throws IOException;

	public void reset() throws IOException;
	
	public void close() throws IOException;	
}
