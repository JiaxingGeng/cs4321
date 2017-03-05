package cs4321.project2;

import java.util.HashMap;
import java.util.LinkedList;

import java.util.Arrays;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * The catalog can keep track of tables and what the schema 
 * of different tables is, and so on.
 * This class uses thread safe singleton pattern.
 * 
 * @author Jiaxing Geng (jg755), Yangyi Hao (yh326)
 */
public class Catalog {
	
	private static HashMap<String,String[]> catalogHash; 
	private static HashMap<String,Integer> columnsHash;
	private static LinkedList<String> tables;
	private static Catalog instance;
	private static String dir;
	
	private Catalog() {}
	
	/**
	 * Get the catalog object and invoke the constructor if object doesn't 
	 * exist. 
	 * Precondition: assume that the format is valid in schema.txt file
	 * @param dir
	 *            the directory that contains schema's
	 *            it can be put into any string object if the catalog 
	 *            is already initialized
	 * @return catalog object
	 */
	public static synchronized Catalog getInstance(String dir) 
			throws IOException{
		if (instance == null){
			Catalog.dir = dir;
			instance = new Catalog();
			BufferedReader bf = new BufferedReader
					(new FileReader(dir+"/db/schema.txt"));
			catalogHash = new HashMap<>();
			columnsHash = new HashMap<>();
			tables = new LinkedList<>();
			String currentLine;
			while ((currentLine = bf.readLine()) != null ){
				String[] entries = currentLine.split("\\s+");
				if (entries.length>=2){
					tables.add(entries[0]);
					catalogHash.put
					(entries[0], Arrays.copyOfRange(entries, 1, entries.length));
				}
			}
			bf.close();
		}
		return instance;
	}
	
	/**
	 * Get all the tables in schema.txt
	 * @return list that stores all the tables
	 */
	public LinkedList<String> getTables(){
		return tables;
	}
		
	/**
	 * Get the attributes of a table
	 * @param table
	 *             name of the table
	 * @return string array that contains all the attributes
	 */
	public String[] getAttributes(String table){
		return catalogHash.get(table);
	}
	
	public void setColumnsHash(HashMap<String,Integer> columnsHash){
		Catalog.columnsHash = columnsHash;
	}

	public HashMap<String,Integer> getColumnsHash(){
		return columnsHash;
	}
	
	public void addColumnsHash(String s, int col){
		columnsHash.put(s,col);
	}

	public void clearColumnsHash(){
		columnsHash.clear();
	}
	
	public String getInputDir(){
		return dir;
	}
	
}
