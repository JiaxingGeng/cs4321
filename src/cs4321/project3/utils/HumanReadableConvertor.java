package cs4321.project3.utils;

import cs4321.project3.IO.*;
import cs4321.project2.operator.*;
import java.io.*;
/**
 * Convert binary file to human readable file
 * @author Jiaxing Geng (jg755), Yangyi Hao (yh326)
 *
 */
public class HumanReadableConvertor {
	
	public static void convert(String datapath,String output) throws IOException{
		BinaryReader rd = new BinaryReader(datapath);
		HumanReadableWriter wr = new HumanReadableWriter(output);
		String ln = rd.readLine();
		while(ln!=null){
			Tuple t = new Tuple(ln.split(","));
			wr.write(t);
			ln = rd.readLine();
		}
		rd.close();
		wr.close();
	}

}
