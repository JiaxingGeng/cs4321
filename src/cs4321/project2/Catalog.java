package cs4321.project2;

import java.util.HashMap;
import java.util.LinkedList;

import java.util.Arrays;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.File;
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
	private static LinkedList<String> tables;
	private static Catalog instance;
	private static String dir;
	private static String tempDir;
	private static int[][] config;
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
	public static synchronized Catalog getInstance(String dir,String tempDir) 
			throws IOException{
		if (instance == null){
			Catalog.dir = dir;
			Catalog.tempDir = tempDir;
			instance = new Catalog();
			BufferedReader bf = new BufferedReader
					(new FileReader(dir+File.separator+"db"+File.separator+"schema.txt"));
			catalogHash = new HashMap<>();
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
			
			config = new int[2][];
			extractConfig();
			
		}
		return instance;
	}
	public static synchronized Catalog getInstance(String dir) 
			throws IOException{
		if (instance == null){
			Catalog.dir = dir;
			Catalog.tempDir = null;
			instance = new Catalog();
			BufferedReader bf = new BufferedReader
					(new FileReader(dir+File.separator+"db"+File.separator+"schema.txt"));
			catalogHash = new HashMap<>();
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
	public static synchronized Catalog getInstance(){
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
	
	/**
	 * Get the input directory 
	 * @return String represents input directory
	 */
	public String getInputDir(){
		return dir;
	}
	
	/**
	 * Get the Temporary directory 
	 * @return String represents input directory
	 */
	public String getTempDir(){
		return tempDir;
	}	
	

	private static void extractConfig() throws IOException{
		BufferedReader bf = new BufferedReader
				(new FileReader(dir+File.separator+"plan_builder_config.txt"));
		int[] joinConfig = new int[2];
		int[] sortConfig = new int[2];
		
		String currentLine = bf.readLine();
		String[] s1= currentLine.split("\\s+");
		joinConfig[0] = Integer.parseInt(s1[0]);
		if (s1.length == 2)
			joinConfig[1] = Integer.parseInt(s1[1]);
		
		currentLine = bf.readLine();
		String[] s2= currentLine.split("\\s+");
		sortConfig[0] = Integer.parseInt(s2[0]);
		if (s2.length == 2)
			sortConfig[1] = Integer.parseInt(s2[1]);
		bf.close();
		
		config[0] = joinConfig;
		config[1] = sortConfig; 
		
	}
	
	public int[][] getConfig(){
		return config;
	}
	
}
