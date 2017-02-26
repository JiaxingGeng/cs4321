package cs4321.project2;

/**
 * A simple interpreter for SQL statements. It takes in a database and a file 
 * containing several SQL queries. Then it will process and evaluate each SQL
 * queries on the database.
 * Current version can process SELECT-FROM-WHERE queries.
 * 
 * @author Jiaxing Geng (jg755), Yangyi Hao (yh326)
 */

public class Interpreter {

	public static void main(String[] args) {
		try{
			// check input arguments
			if (args == null || args.length != 2)
				throw new IllegalArgumentException
				("need two arguments: inputdir and outputdir");
			String inputdir = args[0];
			String outputdir = args[1];
			
			// build database catalog
			Catalog catalog = Catalog.getInstance(inputdir);
			
			

		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

	}

}
