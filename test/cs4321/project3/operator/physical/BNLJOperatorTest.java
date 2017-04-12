package cs4321.project3.operator.physical;

import static org.junit.Assert.*;

import org.junit.Test;
import java.io.*;
import cs4321.project2.Catalog;
import cs4321.project2.operator.Operator;
import cs4321.project2.operator.ScanOperator;
import cs4321.project3.utils.*;
import cs4321.project3.IO.*;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.relational.*;

public class BNLJOperatorTest {

	@Test
	public void test1() throws IOException{
		Catalog.getInstance("input");
		Table t1 = new Table(null,"Sailors");
		Operator op1 = new ScanOperator(t1);
		Table t2 = new Table(null,"Reserves");
		Column c1 = new Column(t1,"A");
		Column c2 = new Column(t2,"G");
		EqualsTo exp = new EqualsTo();
		exp.setLeftExpression(c1);
		exp.setRightExpression(c2);
		Operator op2 = new ScanOperator(t2);
		BNLJOperator op = new BNLJOperator(op1,op2,null,2);
//		int tupleNum = 0;
//		while (op.getNextTuple()!=null) tupleNum ++;
//		System.out.println(tupleNum);
//		op1 = new ScanOperator(t1);
//		tupleNum = 0;
//		while (op1.getNextTuple()!=null) tupleNum ++;
//		System.out.println(tupleNum);
//		op1 = new ScanOperator(t2);
//		tupleNum = 0;
//		while (op1.getNextTuple()!=null) tupleNum ++;
//		System.out.println(tupleNum);
		

//		HumanReadableConvertor.convert
//		("output/query8", "output/query8_readable");
//		
		
	}
	
	@Test
	public void test2() throws IOException {
		System.out.println("----------------------");
		System.out.println("| testBNLJOperator() |");
		System.out.println("----------------------");
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
		BNLJOperator op = new BNLJOperator(sol, sor, andExp, 2);
		//SMJOperator smjo = new SMJOperator(sol, sor, andExp);
		//smjo.dump();
		op.dump();
		//jo.dump();
		} catch (IOException e) {}
	}

}
