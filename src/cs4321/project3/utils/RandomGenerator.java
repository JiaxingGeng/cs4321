package cs4321.project3.utils;

import cs4321.project2.operator.*;
import java.util.ArrayList;
import java.util.Random;
/**
 * Generate Random tuples of a given number and a given range
 * @author Jiaxing Geng (jg755), Yangyi Hao (yh326)
 *
 */
public class RandomGenerator {
	private int numTuples;
	private int numAttributes;
	private int[] range;
	
	/**
	 * Constructor for random generator
	 * @param numTuples
	 * @param numAttributes
	 * @param range 
	 * 		The range allows the the random output generated for
	 * 		attribute k satisfy  0<=k<=range[k]
	 */
	public RandomGenerator(int numTuples,int numAttributes,int[] range){
		this.numTuples = numTuples;
		this.numAttributes = numAttributes;
		this.range = range;
	}
	
	public ArrayList<Tuple> getRandomTuples(){
		ArrayList<Tuple> res = new ArrayList<>();
		Random generator = new Random();
		for (int i = 0;i<numTuples;i++){
			String[] attributes = new String[numAttributes];
			for(int j=0;j<numAttributes;j++){
				attributes[j] = ""+generator.nextInt(range[j]);
			}
			res.add(new Tuple(attributes));
		}
		return res;
	}
	
}
