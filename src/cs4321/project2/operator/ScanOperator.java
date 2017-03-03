package cs4321.project2.operator;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import net.sf.jsqlparser.statement.select.FromItem;
import cs4321.project2.*;
import cs4321.project2.deparser.*;

/**
 * Scan the table from the file in the database
 * 
 * @author Jiaxing Geng (jg755), Yangyi Hao (yh326)
 */


public class ScanOperator extends Operator{
	
	private BufferedReader bf;
	private String dataPath;
	private int numColumns;
	
	public ScanOperator(FromItem fromItem, String dir) throws IOException{
	    SelectDeParser selectVisitor = new SelectDeParser();
	    fromItem.accept(selectVisitor);
	    String tableName = selectVisitor.getResult();
		dataPath = dir + "/db/data/" + tableName; 
		bf = new BufferedReader(new FileReader(dataPath));
		Catalog cat = Catalog.getInstance(null);
		numColumns = cat.getAttributes(tableName).size();
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
	
}
