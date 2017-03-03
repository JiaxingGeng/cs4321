package cs4321.project2.deparser;

import java.util.HashMap;

import cs4321.project2.operator.*;
import net.sf.jsqlparser.expression.*;
import net.sf.jsqlparser.expression.operators.arithmetic.*;
import net.sf.jsqlparser.expression.operators.conditional.*;
import net.sf.jsqlparser.expression.operators.relational.*;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.select.SubSelect;

/**
 * @author Jiaxing Geng (jg755), Yangyi Hao (yh326)
 *
 */
public class ExpressionDeParser implements ExpressionVisitor,ItemsListVisitor{

	private HashMap<String, Integer> colToIndexHash;
	private Tuple tupleUnderTest;
	private String result;
	
	public ExpressionDeParser(){
		result = null;
	}
	
	public ExpressionDeParser(Tuple tupleUnderTest, HashMap<String, Integer> colToIndexHash) {
		this.tupleUnderTest = tupleUnderTest;
		this.colToIndexHash = colToIndexHash;
		result = null;
	}
	
	public String getResult(){
		return result;
	}

	public void visit(LongValue arg0) {
		result = arg0.toString();
	}

	@Override
	public void visit(AndExpression arg0) {
		Expression left = arg0.getLeftExpression();
		Expression right = arg0.getRightExpression();
		ExpressionDeParser ev1 = new ExpressionDeParser(tupleUnderTest, colToIndexHash);
		ExpressionDeParser ev2 = new ExpressionDeParser(tupleUnderTest, colToIndexHash);
		left.accept(ev1);
		right.accept(ev2);
		boolean b1 = Boolean.parseBoolean(ev1.getResult());
		boolean b2 = Boolean.parseBoolean(ev2.getResult());
		result = new Boolean(b1&&b2).toString();
	}

	@Override
	public void visit(EqualsTo arg0) {
		Expression left = arg0.getLeftExpression();
		Expression right = arg0.getRightExpression();
		ExpressionDeParser ev1 = new ExpressionDeParser(tupleUnderTest, colToIndexHash);
		ExpressionDeParser ev2 = new ExpressionDeParser(tupleUnderTest, colToIndexHash);
		left.accept(ev1);
		right.accept(ev2);
		long l1 = Long.parseLong(ev1.getResult());
		long l2 = Long.parseLong(ev2.getResult());
		result = new Boolean(l1==l2).toString();
	}

	@Override
	public void visit(GreaterThan arg0) {
		Expression left = arg0.getLeftExpression();
		Expression right = arg0.getRightExpression();
		ExpressionDeParser ev1 = new ExpressionDeParser(tupleUnderTest, colToIndexHash); // need to change
		ExpressionDeParser ev2 = new ExpressionDeParser(tupleUnderTest, colToIndexHash); // need to change
		left.accept(ev1);
		right.accept(ev2);
		long l1 = Long.parseLong(ev1.getResult());
		long l2 = Long.parseLong(ev2.getResult());
		result = new Boolean(l1>l2).toString();
	}

	@Override
	public void visit(GreaterThanEquals arg0) {
		Expression left = arg0.getLeftExpression();
		Expression right = arg0.getRightExpression();
		ExpressionDeParser ev1 = new ExpressionDeParser(tupleUnderTest, colToIndexHash); // need to change
		ExpressionDeParser ev2 = new ExpressionDeParser(tupleUnderTest, colToIndexHash); // need to change
		left.accept(ev1);
		right.accept(ev2);
		long l1 = Long.parseLong(ev1.getResult());
		long l2 = Long.parseLong(ev2.getResult());
		result = new Boolean(l1>=l2).toString();
	}


	@Override
	public void visit(MinorThan arg0) {
		Expression left = arg0.getLeftExpression();
		Expression right = arg0.getRightExpression();
		ExpressionDeParser ev1 = new ExpressionDeParser(tupleUnderTest, colToIndexHash); // need to change
		ExpressionDeParser ev2 = new ExpressionDeParser(tupleUnderTest, colToIndexHash); // need to change
		left.accept(ev1);
		right.accept(ev2);
		long l1 = Long.parseLong(ev1.getResult());
		long l2 = Long.parseLong(ev2.getResult());
		result = new Boolean(l1<l2).toString();
	}


	@Override
	public void visit(MinorThanEquals arg0) {
		Expression left = arg0.getLeftExpression();
		Expression right = arg0.getRightExpression();
		ExpressionDeParser ev1 = new ExpressionDeParser(tupleUnderTest, colToIndexHash); // need to change
		ExpressionDeParser ev2 = new ExpressionDeParser(tupleUnderTest, colToIndexHash); // need to change
		left.accept(ev1);
		right.accept(ev2);
		long l1 = Long.parseLong(ev1.getResult());
		long l2 = Long.parseLong(ev2.getResult());
		result = new Boolean(l1<=l2).toString();
	}


	@Override
	public void visit(NotEqualsTo arg0) {
		Expression left = arg0.getLeftExpression();
		Expression right = arg0.getRightExpression();
		ExpressionDeParser ev1 = new ExpressionDeParser(tupleUnderTest, colToIndexHash); // need to change
		ExpressionDeParser ev2 = new ExpressionDeParser(tupleUnderTest, colToIndexHash); // need to change
		left.accept(ev1);
		right.accept(ev2);
		long l1 = Long.parseLong(ev1.getResult());
		long l2 = Long.parseLong(ev2.getResult());
		result = new Boolean(l1!=l2).toString();
	}


	@Override
	public void visit(Column arg0) {
		String columnName = arg0.getColumnName(); 
		String tableName = arg0.getTable().getName();
		int pos = colToIndexHash.get(tableName+"."+columnName);
	    result = tupleUnderTest.getAttributes()[pos];
	}
	   
    public void visit(ExpressionList expressionList) {
		// TODO Auto-generated method stub
    }
    
	@Override
	public void visit(NullValue arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Function arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(InverseExpression arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(JdbcParameter arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(DoubleValue arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(DateValue arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(TimeValue arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(TimestampValue arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Parenthesis arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(StringValue arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Addition arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Division arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Multiplication arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Subtraction arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(OrExpression arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Between arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(InExpression arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(IsNullExpression arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(LikeExpression arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(SubSelect arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(CaseExpression arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(WhenClause arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(ExistsExpression arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(AllComparisonExpression arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(AnyComparisonExpression arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Concat arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Matches arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(BitwiseAnd arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(BitwiseOr arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(BitwiseXor arg0) {
		// TODO Auto-generated method stub
		
	}

	

}
