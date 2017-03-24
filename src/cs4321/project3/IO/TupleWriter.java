package cs4321.project3.IO;

import java.io.IOException;

import cs4321.project2.operator.*;

/**
 * Tuple Writer Interface
 * @author Jiaxing Geng (jg755), Yangyi Hao (yh326)
 *
 */
public interface TupleWriter {
	public void write(Tuple t) throws IOException;
	
	public void close() throws IOException;
}
