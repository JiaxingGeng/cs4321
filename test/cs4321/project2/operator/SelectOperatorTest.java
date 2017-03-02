/**
 * 
 */
package cs4321.project2.operator;

import static org.junit.Assert.*;

import java.io.FileReader;
import java.io.IOException;

import org.junit.Test;

import cs4321.project2.Catalog;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.parser.CCJSqlParser;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;

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
			Catalog catalog = Catalog.getInstance(inputdir);
			while ((statement = parser.Statement()) != null) {
				Select select = (Select) statement;
				PlainSelect plainSelect = (PlainSelect) select.getSelectBody();
				Table table = (Table) plainSelect.getFromItem();
				Expression expression = plainSelect.getWhere();
				System.out.print("----- New Query: ");
				System.out.println(expression.toString() + " -----");
				SelectOperator seOp = new SelectOperator(table, inputdir, catalog, expression);
				seOp.getNextTuple().print(); // should print 1,200,50,
				seOp.getNextTuple().print(); // should print 2,200,200,
				seOp.getNextTuple().print(); // should print 3,100,105
				seOp.getNextTuple().print(); // should print 3,100,50
				seOp.getNextTuple().print(); // should print 5,100,500
				seOp.getNextTuple().print(); // should print 6,300,400
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
			Catalog catalog = Catalog.getInstance(inputdir);
			while ((statement = parser.Statement()) != null) {
				Select select = (Select) statement;
				PlainSelect plainSelect = (PlainSelect) select.getSelectBody();
				Table table = (Table) plainSelect.getFromItem();
				Expression expression = plainSelect.getWhere();
				System.out.print("----- New Query: ");
				System.out.println(expression.toString() + " -----");
				SelectOperator seOp = new SelectOperator(table, inputdir, catalog, expression);
				seOp.getNextTuple().print(); // should print 1,200,50,
				seOp.getNextTuple().print(); // should print 2,200,200,
				seOp.getNextTuple().print(); // should print 3,100,105
				seOp.reset();
				seOp.getNextTuple().print(); // should print 1,200,50
				seOp.getNextTuple().print(); // should print 2,200,200
				seOp.getNextTuple().print(); // should print 3,100,105
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
				Table table = (Table) plainSelect.getFromItem();
				Expression expression = plainSelect.getWhere();
				System.out.print("----- New Query: ");
				System.out.println(expression.toString() + " -----");
				SelectOperator seOp = new SelectOperator(table, inputdir, catalog, expression);
				seOp.getNextTuple();
				seOp.getNextTuple();
				seOp.getNextTuple();
				seOp.getNextTuple();
				seOp.getNextTuple();
				seOp.getNextTuple();
				seOp.dump(); // should print the results for this query
			}			
		}
		catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
			fail();
		}
	}

}
