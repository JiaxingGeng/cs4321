package cs4321.project2.operator;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import net.sf.jsqlparser.schema.*;
import cs4321.project2.*;

/**
 * Scan the table from the file in the database
 * 
 * @author Jiaxing Geng (jg755), Yangyi Hao (yh326)
 */


public class ScanOperator extends Operator{
	
	private BufferedReader bf;
	private String dataPath;
	private int numColumns;
	//private Tuple currTuple;
	
	public ScanOperator(Table t, String dir, Catalog cat) throws IOException{
		dataPath = dir + "/db/data/" + t.getName();  // getName or getSchema?
		bf = new BufferedReader(new FileReader(dataPath));
		numColumns = cat.getAttributes(t.getName()).length;
	}
	
	public Tuple getNextTuple() throws IOException{
		String currentLine = bf.readLine();
		if (currentLine == null) {
			bf.close();
			return null;
		}
		else {
			Tuple tp = new Tuple(currentLine.split(","));
			if (tp.getColumns() == numColumns) {
				//currTuple = tp;
				return tp;
			}
			else throw new IOException
			("each row should have same columns in the same database");
		}
	}
	
	public void reset() throws IOException{
		if (bf == null)  // might need to change
			bf = new BufferedReader(new FileReader(dataPath));
		else {
			bf.close();
			bf = new BufferedReader(new FileReader(dataPath));
		}		
	}
	
	/*
	public Tuple getCurrTuple() throws IOException {
		return currTuple;
	}
	*/
}
