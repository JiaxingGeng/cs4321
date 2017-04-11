package cs4321.project3.utils;

import cs4321.project3.IO.*;
import java.io.IOException;
import java.util.List;
import java.util.LinkedList;
import java.util.Collections;
import java.util.Comparator;
import cs4321.project2.operator.*;

/**
 * Sort the tuples in ascending order in a given file and output the 
 * sorted table
 * @author Jiaxing Geng (jg755), Yangyi Hao (yh326)
 *
 */
public class SortFile {
	private boolean binaryFile;
	private String dataPath;
	
	public SortFile(String dataPath, boolean binaryFile){
		this.binaryFile = binaryFile;
		this.dataPath = dataPath;
	}
	
	/**
	 * Sort the given table and out put sorted table with _sorted suffix
	 * @param binaryOutputFile
	 * 		output file is binary or not
	 */
	public void sort(boolean binaryOutputFile) throws IOException{
		TupleReader reader;
		if (binaryFile)  reader = new BinaryReader(dataPath);
		else reader = new HumanReadableReader(dataPath);
		List<Tuple> tuples = new LinkedList<>();
		String currentLine = reader.readLine();
		while (currentLine !=null){
			tuples.add(new Tuple(currentLine.split(",")));
			currentLine = reader.readLine();
		}
		
		Collections.sort(tuples, new TupleComparator());
		
		TupleWriter writer;
		if (binaryOutputFile) writer = new BinaryWriter(dataPath+"_sorted");
		else writer = new HumanReadableWriter(dataPath+"_sorted_hm");
		for (Tuple t:tuples)	writer.write(t);
		writer.close();
		
	}
	
	private class TupleComparator implements Comparator<Tuple>{
		public int compare(Tuple t1,Tuple t2){
			String[] s1 = t1.getAttributes();
			String[] s2 = t2.getAttributes();
			for (int i=0;i<s1.length;i++){
				int a = Integer.parseInt(s1[i]);
				int b = Integer.parseInt(s2[i]);
				if (a<b) return -1;
				if (a>b) return 1;			
			}		
			return 0;
		}	
	}
	
}
