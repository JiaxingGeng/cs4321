package cs4321.project2.operator;

/**
 * Abstract class for all relational operators. The implementation is based on 
 * iterator API.
 * 
 * @author Jiaxing Geng (jg755), Yangyi Hao (yh326)
 */

public abstract class Operator {
	
	/**
	 *  Get the next tuple of the operator’s output
	 * 
	 * @return the next tuple and null if no output is available
	 */
	public abstract Tuple getNextTuple();
	
	/**
	 * Reset its state and start returning its output again from the beginning
	 */	
	public abstract void reset();
	
	/**
	 * Repeatedly get the next tuple until the end and write each tuple to 
	 * a suitable PrintStream
	 */
	public void dump(){
		Tuple t = this.getNextTuple();
		if (t != null){
			t.print();
			this.dump();
		}
	}
}
