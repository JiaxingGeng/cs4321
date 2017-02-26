package cs4321.project2;

import java.io.FileReader;

import net.sf.jsqlparser.parser.CCJSqlParser;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.schema.*;

import cs4321.project2.operator.*;

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
						
			Catalog catalog = Catalog.getInstance(inputdir); // build catalog
			
			// parse the data
			CCJSqlParser parser = 
					new CCJSqlParser(new FileReader(inputdir+"/queries.sql"));
			Statement statement;
			while ((statement = parser.Statement()) != null) {
				Select select = (Select) statement;
				PlainSelect plainSelect = (PlainSelect) select.getSelectBody();
				Table table = (Table) plainSelect.getFromItem();
				ScanOperator sOp = new ScanOperator(table,inputdir);
				sOp.dump();
			}			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}

	}

}
