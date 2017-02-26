package cs4321.project2.operator;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import net.sf.jsqlparser.schema.*;

/**
 * Scan the table from the file in the database
 * 
 * @author Jiaxing Geng (jg755), Yangyi Hao (yh326)
 */


public class ScanOperator extends Operator{
	
	private BufferedReader bf;
	private String dataPath;
	
	public ScanOperator(Table t,String dir) throws IOException{
		dataPath = dir + "/db/data" + t.getName() +".txt";
		bf = new BufferedReader(new FileReader(dataPath));
	}
	
	public Tuple getNextTuple() throws IOException{
		String currentLine = bf.readLine();
		if (currentLine == null) {
			bf.close();
			return null;
		}
		else return new Tuple(currentLine.split(","));
	}
	
	public void reset() throws IOException{
		if (bf == null)  // might need to change
			bf = new BufferedReader(new FileReader(dataPath));
		else {
			bf.close();
			bf = new BufferedReader(new FileReader(dataPath));
		}		
	}
}
