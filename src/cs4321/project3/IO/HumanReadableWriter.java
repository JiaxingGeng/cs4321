package cs4321.project3.IO;

import cs4321.project2.operator.Tuple;
import java.io.PrintWriter;
import java.io.IOException;

/**
 * Use BufferedWriter to write outputs that are human readable
 * @author Jiaxing Geng (jg755), Yangyi Hao (yh326) 
 *
 */
public class HumanReadableWriter implements TupleWriter {
	
	private PrintWriter writer;
	/**
	 * Constructor of Human Readable Writer
	 * @param dataPath location to write file
	 * @throws IOException
	 */
	public HumanReadableWriter(String dataPath) throws IOException{
		writer = new PrintWriter(dataPath, "UTF-8");
	}

	/**
	 * Write Tuple t
	 * @param t tuple to be written
	 */
	@Override
	public void write(Tuple t) throws IOException{
		int num = t.getColumns();
		String[] attributes = t.getAttributes();
		for (int i =0;i<num-1;i++)
			writer.print(attributes[i]+",");
		writer.print(attributes[num-1]);
		writer.println();
	}

	/**
	 * close the writer buffer
	 */
	@Override
	public void close() throws IOException{
		writer.close();
	}

}
