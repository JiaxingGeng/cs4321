package cs4321.project2;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * The catalog can keep track of information such as where a file 
 * for a given table is located, what the schema of different tables is, and so on.
 * This class uses thread safe singleton pattern.
 * 
 * @author Jiaxing Geng (jg755), Yangyi Hao (yh326)
 */
public class Catalog {
	
	private static HashMap<String,String[]> catalogHash; 
	private static LinkedList<String> tables;
	private static Catalog instance;
	
	private Catalog(String dir) throws IOException{
		BufferedReader bf = new BufferedReader(new FileReader(dir+"/schema.txt"));
		catalogHash = new HashMap<>();
		tables = new LinkedList<>();
		String currentLine;
		while ((currentLine = bf.readLine()) != null ){
			String[] entries = currentLine.split("\\s+");
			tables.add(entries[0]);
			catalogHash.put(entries[0],Arrays.copyOfRange(entries, 1, entries.length));
		}
		bf.close();
	}
	
	/**
	 * Get the catalog object and invoke the constructor if object doesn't 
	 * exist. 
	 * @param dir
	 *            the directory that contains schema's
	 * @return catalog object
	 */
	public static synchronized Catalog getInstance(String dir) 
			throws IOException{
		if (instance == null){
			instance = new Catalog(dir);
		}
		return instance;
	}
	
	/**
	 * Get all the tables in schema.txt
	 * @return list that stores all the tables
	 */
	public static LinkedList<String> getTables(){
		return tables;
	}
	
	/**
	 * Get the attributes of a table
	 * @param table
	 *             name of the table
	 * @return string array that contains all the attributes
	 */
	public static String[] getAttributes(String table){
		return catalogHash.get(table);
	}

}
