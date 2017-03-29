package cs4321.project3.operator;

import java.io.IOException;

import net.sf.jsqlparser.statement.select.FromItem;
import cs4321.project2.*;
import cs4321.project2.deparser.*;
import cs4321.project2.operator.Operator;
import cs4321.project2.operator.Tuple;
import cs4321.project3.IO.BinaryReader;

/**
 * Scan the table from the file in the database. It will always be the 
 * leaf at a query plan and processed first. 
 * 
 * @author Jiaxing Geng (jg755), Yangyi Hao (yh326)
 */


public class ScanBinOperator extends Operator{
	
	private BinaryReader binReader;
	private String dataPath;
	private int numColumns;
	
	public ScanBinOperator(FromItem fromItem) throws IOException{
		SelectDeParser selectVisitor = new SelectDeParser();
	    fromItem.accept(selectVisitor);
	    String[] tableTuple = selectVisitor.getResult().split("\\.");
	    String tableName = tableTuple[0];
		Catalog cat = Catalog.getInstance(null);
		String[] attributes = cat.getAttributes(tableName);
		numColumns = attributes.length;
		String[] columns = new String[numColumns];
		// storing column names in the operator such that they 
		// can be tracked later
		for (int i=0;i<numColumns;i++){
			if (!tableTuple[1].equals("null"))
				columns[i] = tableTuple[1] + "." + attributes[i];
			else columns[i] = tableTuple[0] + "." + attributes[i];
		}
		super.columns = columns;
		dataPath = cat.getInputDir() + "/db/data/" + tableName; 
		binReader = new BinaryReader(dataPath);
	}
	
	/**
	 * Get the next tuple that is scanned from the file
	 * @return Tuple from scanning 
	 */
	public Tuple getNextTuple() throws IOException{
		String currentLine = binReader.readLine();
		if (currentLine == null) {
			binReader.close();
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
	
	/**
	 * Reset the reader buffer so that it reads the tuples from the start
	 */
	public void reset() throws IOException{
		if (binReader == null)  
			binReader = new BinaryReader(dataPath);
		else {
			binReader.close();
			binReader = new BinaryReader(dataPath);
		}		
	}
	
	/**
	 * Print the operator type and its expression
	 */
	public String toString(){
		return "ScanBinOperator:" + dataPath;
	}	
}
