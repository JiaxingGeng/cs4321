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
	
	public ScanOperator(FromItem fromItem) throws IOException{
		SelectDeParser selectVisitor = new SelectDeParser();
	    fromItem.accept(selectVisitor);
	    String[] tableTuple = selectVisitor.getResult().split("\\.");
	    String tableName = tableTuple[0];
		Catalog cat = Catalog.getInstance(null);
		String[] attributes = cat.getAttributes(tableName);
		//System.out.println("attributes are: " + Arrays.toString(attributes));
		numColumns = attributes.length;
		//System.out.println("numColumns: " + numColumns);
		String[] columns = new String[numColumns];
		for (int i=0;i<numColumns;i++){
			if (!tableTuple[1].equals("null"))
				columns[i] = tableTuple[1] + "." + attributes[i];
			else columns[i] = tableTuple[0] + "." + attributes[i];
		}
		super.columns = columns;
		dataPath = cat.getInputDir() + "/db/data/" + tableName; 
		bf = new BufferedReader(new FileReader(dataPath));
	}
	
	public Tuple getNextTuple() throws IOException{
		String currentLine = bf.readLine();
		//System.out.println(currentLine);
		if (currentLine == null) {
			bf.close();
			return null;
		}
		else {
			Tuple tp = new Tuple(currentLine.split(","));
			//System.out.println(tp.getColumns());
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
	
	public String toString(){
		return "ScanOperator:" + dataPath;
	}	
}
