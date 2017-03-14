package cs4321.project2.operator;

/**
 * Tuples represents the rows of a table and Operator class operate on
 * these tuples to produce the final result.
 * @author Jiaxing Geng (jg755), Yangyi Hao (yh326)
 *
 */
import java.util.List;
import java.io.PrintWriter;
import java.io.IOException;

public class Tuple {

	private String[] attributes;
	private int num;

	public Tuple(String[] attrs){
		attributes = attrs;
		num = attrs.length;
	}
	
	/**
	 * Get the number of columns that are in this tuple
	 * @return number of columns
	 */
	public int getColumns(){
		return num;
	}

	/**
	 * Get all the attributes in this tuple
	 * @return a string array with elements representing each entry 
	 * 		   of the tuple.
	 */
	public String[] getAttributes() {
		return attributes;
	}

	/**
	 * Get one element at a certain position
	 * @param pos position of the element
	 * @return the element and null if pos is out of range or tuple has
	 *         no attribute
	 */
	public String getElement(int pos){
		if (attributes != null && pos<attributes.length-1) 
			return attributes[pos];
		else return null;
	}

	/**
	 * Print the tuple on the screen
	 */
	public void print(){
		for (String s: attributes) System.out.print(s + ", ");
		System.out.println("");
	}
	
	/**
	 * Print the tuple to a file
	 * @param printWriter the writer class that opens the output file
	 * @throws IOException
	 */
	public void print(PrintWriter printWriter) throws IOException{
		for (int i =0;i<num-1;i++)
			printWriter.print(attributes[i]+",");
		printWriter.print(attributes[num-1]);
		printWriter.println();
	}

	/**
	 * Combine two tuples for join operation
	 * @param rightTuple the tuple that will be appended on the right
	 * @return the combined tuple
	 */
	public Tuple joins(Tuple rightTuple){
		String[] comb= new String[num + rightTuple.getColumns()];
		System.arraycopy(this.getAttributes(), 0, comb, 0, num);
		System.arraycopy(rightTuple.getAttributes(), 0, 
				comb, num, rightTuple.getColumns());
		return new Tuple(comb);
	}

	/**
	 * Determines whether this tuple equals a given are equal 
	 * at certain positions
	 * @param t the tuple compared to
	 * @param posList null for comparing all elements; otherwise,
	 *        it contains the list of positions to be compared
	 * @return true if two tuples contains the same values at 
	 *         certain positions
	 */
	public boolean equals(Tuple t, List<Integer> posList){
		if (t == null) return false;
		String[] compAttrs = t.getAttributes();
		if (posList == null) {
			for (int i=0;i<t.num;i++){
				if (!compAttrs[i].equals(attributes[i])) return false;
			}
			return true;		
		} else {
			for (int i:posList){
				if (!compAttrs[i].equals(attributes[i])) return false;
			}
			return true;
		}	
	}

}
