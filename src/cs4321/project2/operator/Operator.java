package cs4321.project2.operator;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

/**
 * Abstract class for all relational operators. The implementation is based on 
 * iterator API.
 * 
 * @author Jiaxing Geng (jg755), Yangyi Hao (yh326)
 */

public abstract class Operator {
	
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
	 * Repeatedly get the next tuple until the end and write each tuple to 
	 * a suitable PrintStream
	 */
	public void dump() throws IOException{
		Tuple t = this.getNextTuple();
		if (t != null){
			t.print();
			this.dump();
		}
	}
	public void dump(PrintWriter printWriter) throws IOException{
		Tuple t = this.getNextTuple();
		if (t != null){
			t.print(printWriter);
			this.dump(printWriter);
		}
	}
		
	public String[] getColumns(){
		return columns;
	}
	
	public HashMap<String, Integer> getColumnsHash(){
		//System.out.println("columns are: " + Arrays.toString(columns));
		HashMap<String, Integer> hashMap = new HashMap<>();
		for(int i=0;i<columns.length;i++) hashMap.put(columns[i], i);
		return hashMap;
	}
	
}
