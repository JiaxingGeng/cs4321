package cs4321.project3.IO;

import cs4321.project2.operator.Tuple;
import java.io.PrintWriter;
import java.io.IOException;

public class HumanReadableWriter implements TupleWriter {
	
	private PrintWriter writer;
	
	public HumanReadableWriter(String dataPath) throws IOException{
		writer = new PrintWriter(dataPath, "UTF-8");
	}

	@Override
	public void write(Tuple t) throws IOException{
		int num = t.getColumns();
		String[] attributes = t.getAttributes();
		for (int i =0;i<num-1;i++)
			writer.print(attributes[i]+",");
		writer.print(attributes[num-1]);
		writer.println();
	}

	@Override
	public void close() throws IOException{
		writer.close();
	}

}
