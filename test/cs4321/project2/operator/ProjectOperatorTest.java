package cs4321.project2.operator;

import static org.junit.Assert.*;

import java.io.FileReader;

import org.junit.Test;

import cs4321.project2.*;
import net.sf.jsqlparser.parser.CCJSqlParser;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;

public class ProjectOperatorTest {

	@Test
	public void testGetNextTuple() {
		System.out.println("----------------------");
		System.out.println("| testGetNextTuple() |");
		System.out.println("----------------------");
		try {
			String inputdir = "testFolder";
			CCJSqlParser parser = new CCJSqlParser(new FileReader(inputdir + "/queries_testProject.sql"));
			Statement statement;
			Catalog.getInstance(inputdir);
			while ((statement = parser.Statement()) != null) {
				Select select = (Select) statement;
				PlainSelect plainSelect = (PlainSelect) select.getSelectBody();
				PlanGenerator gen = new PlanGenerator(plainSelect,inputdir);
				Operator op = gen.getQueryPlan();
				System.out.print("----- New Query: ");
				System.out.println(plainSelect.toString() + " -----");
				op.dump();
			}			
		}
		catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
			fail();
		}
	}

}
