package cs4321.project3.operator.physical;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import cs4321.project2.Catalog;
import cs4321.project2.operator.ScanOperator;
import cs4321.project3.deparser.SMJExpressionDeParser;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;

public class SMJOperatorTest {

	@Test
	public void testGetNextTuple() {
		fail("Not yet implemented");
	}

	@Test
	public void testReset() {
		fail("Not yet implemented");
	}

	@Test
	// SELECT FROM TestTable1 TestTable2 WHERE TestTable1.J = TestTable2.K
	public void testSMJOperator() {
		System.out.println("-----------------------");
		System.out.println("| testSMJOperator() |");
		System.out.println("-----------------------");
		Table tl = new Table(null,"Sailors");
		tl.setAlias("S");
		Table tr = new Table(null, "Reserves");
		tr.setAlias("R");
		Column leftExpCol1 = new Column(tl, "A");
		Column rightExpCol1 = new Column(tr, "G");
		EqualsTo equalsToExp1 = new EqualsTo();
		equalsToExp1.setLeftExpression(leftExpCol1);
		equalsToExp1.setRightExpression(rightExpCol1);
		//equalsToExp1.accept(new SMJExpressionDeParser());
		Column leftExpCol2 = new Column(tl, "B");
		Column rightExpCol2 = new Column(tr, "H");
		EqualsTo equalsToExp2 = new EqualsTo();
		equalsToExp2.setLeftExpression(leftExpCol2);
		equalsToExp2.setRightExpression(rightExpCol2);
		//equalsToExp2.accept(new SMJExpressionDeParser());
		AndExpression andExp = new AndExpression();
		andExp.setLeftExpression(equalsToExp1);
		andExp.setRightExpression(equalsToExp2);
		//andExp.accept(new SMJExpressionDeParser());
		try{ Catalog.getInstance("input");
		ScanOperator sol = new ScanOperator(tl);
		ScanOperator sor = new ScanOperator(tr);
		SMJOperator smjo = new SMJOperator(sol, sor, andExp);
		smjo.dump();
		//jo.dump();
		} catch (IOException e) {}
	}

}
