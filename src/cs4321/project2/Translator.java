package cs4321.project2;

import java.io.File;
import java.io.IOException;

import cs4321.project2.operator.Tuple;
import cs4321.project3.IO.BinaryReader;
import cs4321.project3.IO.HumanReadableWriter;

public class Translator {

	public static void main(String[] args) throws IOException {
		String sourcePath = "output" + File.separator + "query1";
		String targetPath = sourcePath + "_hm";
		
		translate_hm_to_bin(sourcePath, targetPath);
	}
	
	public void tanslate_bin_to_hm(String sourcePath, String targetPath) {
		BinaryReader binReader = new BinaryReader(sourcePath);
		HumanReadableWriter humanReadableWriter = new HumanReadableWriter(targetPath);
		Tuple tupleRead;
		while ((tupleRead = binReader.readLineReturnTuple()) != null) {
			//tupleRead.print();
			humanReadableWriter.write(tupleRead);
		}
		binReader.close();
		humanReadableWriter.close();
	}
	
	public void translate_hm_to_bin(String sourcePath, String targetPath) {
		BinaryWrite binWriter = new BinaryWriter(targetPath);
		HumanReadableReader hmReadableReader = new HumanReadableReader(SourcePath);
		
		// TODO
	}
}
