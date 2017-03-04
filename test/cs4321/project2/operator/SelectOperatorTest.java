/**
 * 
 */
package cs4321.project2.operator;

import static org.junit.Assert.*;

import java.io.FileReader;
import java.util.HashMap;

import org.junit.Test;

import cs4321.project2.Catalog;
import cs4321.project2.deparser.SelectDeParser;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.parser.CCJSqlParser;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.*;
import java.io.IOException;

/**
 * @author ronha_000
 *
 */
public class SelectOperatorTest {

	/**
	 * Test method for {@link cs4321.project2.operator.SelectOperator#getNextTuple()}.
	 */
	@Test
	public void testGetNextTuple() {
		System.out.println("----------------------");
		System.out.println("| testGetNextTuple() |");
		System.out.println("----------------------");
		try {
			String inputdir = "testFolder";
			CCJSqlParser parser = new CCJSqlParser(new FileReader(inputdir + "/queries_custom0.sql"));
			Statement statement;
			Catalog.getInstance(inputdir);
			while ((statement = parser.Statement()) != null) {
				Select select = (Select) statement;
				PlainSelect plainSelect = (PlainSelect) select.getSelectBody();
				FromItem fromItem = plainSelect.getFromItem();
				ScanOperator sOp = new ScanOperator(fromItem,inputdir);
				Expression expression = plainSelect.getWhere();
				System.out.print("----- New Query: ");
				System.out.println(expression.toString() + " -----");
				SelectOperator seOp = new SelectOperator(sOp, expression);
				Tuple nextTuple = seOp.getNextTuple();
				while (nextTuple != null) {
					nextTuple.print(); // Should print all the qualified tuples for the current query
					nextTuple = seOp.getNextTuple();
				}
			}			
		}
		catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
			fail();
		}
	}

	/**
	 * Test method for {@link cs4321.project2.operator.SelectOperator#reset()}.
     */	 
	@Test
	public void testReset() {
		System.out.println("---------------");
		System.out.println("| testReset() |");
		System.out.println("---------------");
		try {
			String inputdir = "testFolder";
			CCJSqlParser parser = new CCJSqlParser(new FileReader(inputdir + "/queries_custom0.sql"));
			Statement statement;
			Catalog.getInstance(inputdir);
			while ((statement = parser.Statement()) != null) {
				Select select = (Select) statement;
				PlainSelect plainSelect = (PlainSelect) select.getSelectBody();
				FromItem fromItem = plainSelect.getFromItem();
				ScanOperator sOp = new ScanOperator(fromItem,inputdir);
				Expression expression = plainSelect.getWhere();
				System.out.print("----- New Query: ");
				System.out.println(expression.toString() + " -----");
				SelectOperator seOp = new SelectOperator(sOp, expression);
				Tuple nextTuple = seOp.getNextTuple();
				if (nextTuple != null) {
					nextTuple.print();
					seOp.reset();
					seOp.getNextTuple().print(); // Should print the first qualified tuple twice, if there is any qualified tuples.
				}
			}			
		}
		catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
			fail();
		}
	}

	/**
	 * Test method for {@link cs4321.project2.operator.SelectOperator#dump()}.
	 */
	@Test
	public void testDump() {
		System.out.println("--------------");
		System.out.println("| testDump() |");
		System.out.println("--------------");
		try {
			String inputdir = "testFolder";
			CCJSqlParser parser = new CCJSqlParser(new FileReader(inputdir + "/queries_custom0.sql"));
			Statement statement;
			Catalog catalog = Catalog.getInstance(inputdir);
			while ((statement = parser.Statement()) != null) {
				Select select = (Select) statement;
				PlainSelect plainSelect = (PlainSelect) select.getSelectBody();
				FromItem fromItem = plainSelect.getFromItem();
				SelectDeParser selectVisitor = new SelectDeParser();
			    fromItem.accept(selectVisitor);
			    String tableName = selectVisitor.getResult();
			    catalog.setColumnsHash(ColToIndexHash(tableName));
				ScanOperator sOp = new ScanOperator(fromItem,inputdir);
				Expression expression = plainSelect.getWhere();
				System.out.print("----- New Query: ");
				System.out.println(expression.toString() + " -----");
				SelectOperator seOp = new SelectOperator(sOp, expression);
				seOp.dump(); // should print the results for this query
				catalog.clearColumnsHash();
			}			
		}
		catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
			fail();
		}
	}
	
	private HashMap<String,Integer> ColToIndexHash(String tableTuple) 
			throws IOException{
		String[] tp = tableTuple.split(",");
		Catalog catalog = Catalog.getInstance(null);
		String[] attributes = catalog.getAttributes(tp[0]);
		HashMap<String,Integer> res = new HashMap<>();
		for (int i=0;i<attributes.length;i++) 
			res.put(tp[1]+"."+attributes[i],i);
		return res;	
	}

}
