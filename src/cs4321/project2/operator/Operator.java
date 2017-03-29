package cs4321.project2.operator;

import java.io.IOException;
import java.util.HashMap;

import cs4321.project3.IO.*;

/**
 * Abstract class for all relational operators. The implementation is based on 
 * iterator API.
 * 
 * @author Jiaxing Geng (jg755), Yangyi Hao (yh326)
 */

public abstract class Operator {
	
	// Columns is an array contains all the column names. These are 
	// initialized in ScanOperator. If the table doesn't have alias,
	// TableName.ColumnName will be stored. If it has alias, then
	// AliasName.ColumnName will be stored.
	protected String[] columns;
	
	/**
	 *  Get the next tuple of the operator’s output
	 * 
	 * @return the next tuple and null if no output is available
	 */
	public abstract Tuple getNextTuple() throws IOException;
	
	/**
	 * Reset its state and start returning its output again from the beginning
	 */	
	public abstract void reset() throws IOException;
	
	/**
	 * Repeatedly get the next tuple until the end and print the tuple on 
	 * the screen.
	 */
	public void dump() throws IOException{
		Tuple t = this.getNextTuple();
		if (t != null){
			t.print();
			this.dump();
		}
	}

	/**
	 * Repeatedly get the next tuple until the end and write the tuple to 
	 * a file
	 * @param printWriter the writer has opened an output file 
	 * @throws IOException
	 */
	public void dump(TupleWriter tupleWriter) throws IOException{
		Tuple t = this.getNextTuple();
		while (t != null){
			tupleWriter.write(t);
			t = this.getNextTuple();
		}
	}
	
	/**
	 * 	Get all the column names
	 * @return The column names
	 */
	public String[] getColumns(){
		return columns;
	}
	
	/**
	 * Hash to column names with its corresponding index. This allows 
	 * the operators to quickly search for the position of a given column
	 * @return the HashMap of the columns with its index
	 */
	public HashMap<String, Integer> getColumnsHash(){
		//System.out.println("columns are: " + Arrays.toString(columns));
		HashMap<String, Integer> hashMap = new HashMap<>();
		for(int i=0;i<columns.length;i++) hashMap.put(columns[i], i);
		return hashMap;
	}
	
}
