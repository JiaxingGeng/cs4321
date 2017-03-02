/**
 * 
 */
package cs4321.project2.operator;

import java.util.HashMap;

import net.sf.jsqlparser.expression.AllComparisonExpression;
import net.sf.jsqlparser.expression.AnyComparisonExpression;
import net.sf.jsqlparser.expression.CaseExpression;
import net.sf.jsqlparser.expression.DateValue;
import net.sf.jsqlparser.expression.DoubleValue;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.ExpressionVisitor;
import net.sf.jsqlparser.expression.Function;
import net.sf.jsqlparser.expression.InverseExpression;
import net.sf.jsqlparser.expression.JdbcParameter;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.NullValue;
import net.sf.jsqlparser.expression.Parenthesis;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.TimeValue;
import net.sf.jsqlparser.expression.TimestampValue;
import net.sf.jsqlparser.expression.WhenClause;
import net.sf.jsqlparser.expression.operators.arithmetic.Addition;
import net.sf.jsqlparser.expression.operators.arithmetic.BitwiseAnd;
import net.sf.jsqlparser.expression.operators.arithmetic.BitwiseOr;
import net.sf.jsqlparser.expression.operators.arithmetic.BitwiseXor;
import net.sf.jsqlparser.expression.operators.arithmetic.Concat;
import net.sf.jsqlparser.expression.operators.arithmetic.Division;
import net.sf.jsqlparser.expression.operators.arithmetic.Multiplication;
import net.sf.jsqlparser.expression.operators.arithmetic.Subtraction;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.Between;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.ExistsExpression;
import net.sf.jsqlparser.expression.operators.relational.GreaterThan;
import net.sf.jsqlparser.expression.operators.relational.GreaterThanEquals;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.expression.operators.relational.IsNullExpression;
import net.sf.jsqlparser.expression.operators.relational.LikeExpression;
import net.sf.jsqlparser.expression.operators.relational.Matches;
import net.sf.jsqlparser.expression.operators.relational.MinorThan;
import net.sf.jsqlparser.expression.operators.relational.MinorThanEquals;
import net.sf.jsqlparser.expression.operators.relational.NotEqualsTo;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.select.SubSelect;

/**
 * @author Jiaxing Geng (jg755), Yangyi Hao (yh326)
 *
 */
public class cs4321ExpressionVisitor implements ExpressionVisitor {

	private HashMap<String, Integer> colToIndexHash;
	private Tuple expressionUnderTest;
	private long numericResult;
	private Boolean finalResult;
	
	/**
	 * 
	 */
	public cs4321ExpressionVisitor() {
		// TODO Auto-generated constructor stub
	}
	
	public cs4321ExpressionVisitor(Tuple expressionUnderTest, HashMap<String, Integer> colToIndexHash) {
		this.expressionUnderTest = expressionUnderTest;
		this.colToIndexHash = colToIndexHash;
	}

	/* (non-Javadoc)
	 * @see net.sf.jsqlparser.expression.ExpressionVisitor#visit(net.sf.jsqlparser.expression.LongValue)
	 */
	@Override
	public void visit(LongValue arg0) {
		numericResult = arg0.getValue();
	}

	/* (non-Javadoc)
	 * @see net.sf.jsqlparser.expression.ExpressionVisitor#visit(net.sf.jsqlparser.expression.operators.conditional.AndExpression)
	 */
	@Override
	public void visit(AndExpression arg0) {
		Expression left = arg0.getLeftExpression();
		Expression right = arg0.getRightExpression();
		cs4321ExpressionVisitor ev1 = new cs4321ExpressionVisitor(expressionUnderTest, colToIndexHash); // need to change
		cs4321ExpressionVisitor ev2 = new cs4321ExpressionVisitor(expressionUnderTest, colToIndexHash); // need to change
		left.accept(ev1);
		right.accept(ev2);
		finalResult = ev1.getFinalResult() && ev2.getFinalResult();
	}

	/* (non-Javadoc)
	 * @see net.sf.jsqlparser.expression.ExpressionVisitor#visit(net.sf.jsqlparser.expression.operators.relational.EqualsTo)
	 */
	@Override
	public void visit(EqualsTo arg0) {
		Expression left = arg0.getLeftExpression();
		Expression right = arg0.getRightExpression();
		cs4321ExpressionVisitor ev1 = new cs4321ExpressionVisitor(expressionUnderTest, colToIndexHash); // need to change
		cs4321ExpressionVisitor ev2 = new cs4321ExpressionVisitor(expressionUnderTest, colToIndexHash); // need to change
		left.accept(ev1);
		right.accept(ev2);
		finalResult = ev1.getNumericResult() == ev2.getNumericResult();
	}

	/* (non-Javadoc)
	 * @see net.sf.jsqlparser.expression.ExpressionVisitor#visit(net.sf.jsqlparser.expression.operators.relational.GreaterThan)
	 */
	@Override
	public void visit(GreaterThan arg0) {
		Expression left = arg0.getLeftExpression();
		Expression right = arg0.getRightExpression();
		cs4321ExpressionVisitor ev1 = new cs4321ExpressionVisitor(expressionUnderTest, colToIndexHash); // need to change
		cs4321ExpressionVisitor ev2 = new cs4321ExpressionVisitor(expressionUnderTest, colToIndexHash); // need to change
		left.accept(ev1);
		right.accept(ev2);
		finalResult = ev1.getNumericResult() > ev2.getNumericResult();
	}

	/* (non-Javadoc)
	 * @see net.sf.jsqlparser.expression.ExpressionVisitor#visit(net.sf.jsqlparser.expression.operators.relational.GreaterThanEquals)
	 */
	@Override
	public void visit(GreaterThanEquals arg0) {
		Expression left = arg0.getLeftExpression();
		Expression right = arg0.getRightExpression();
		cs4321ExpressionVisitor ev1 = new cs4321ExpressionVisitor(expressionUnderTest, colToIndexHash); // need to change
		cs4321ExpressionVisitor ev2 = new cs4321ExpressionVisitor(expressionUnderTest, colToIndexHash); // need to change
		left.accept(ev1);
		right.accept(ev2);
		finalResult = ev1.getNumericResult() >= ev2.getNumericResult();
	}

	/* (non-Javadoc)
	 * @see net.sf.jsqlparser.expression.ExpressionVisitor#visit(net.sf.jsqlparser.expression.operators.relational.MinorThan)
	 */
	@Override
	public void visit(MinorThan arg0) {
		Expression left = arg0.getLeftExpression();
		Expression right = arg0.getRightExpression();
		cs4321ExpressionVisitor ev1 = new cs4321ExpressionVisitor(expressionUnderTest, colToIndexHash); // need to change
		cs4321ExpressionVisitor ev2 = new cs4321ExpressionVisitor(expressionUnderTest, colToIndexHash); // need to change
		left.accept(ev1);
		right.accept(ev2);
		finalResult = ev1.getNumericResult() < ev2.getNumericResult();
	}

	/* (non-Javadoc)
	 * @see net.sf.jsqlparser.expression.ExpressionVisitor#visit(net.sf.jsqlparser.expression.operators.relational.MinorThanEquals)
	 */
	@Override
	public void visit(MinorThanEquals arg0) {
		Expression left = arg0.getLeftExpression();
		Expression right = arg0.getRightExpression();
		cs4321ExpressionVisitor ev1 = new cs4321ExpressionVisitor(expressionUnderTest, colToIndexHash); // need to change
		cs4321ExpressionVisitor ev2 = new cs4321ExpressionVisitor(expressionUnderTest, colToIndexHash); // need to change
		left.accept(ev1);
		right.accept(ev2);
		finalResult = ev1.getNumericResult() <= ev2.getNumericResult();
	}

	/* (non-Javadoc)
	 * @see net.sf.jsqlparser.expression.ExpressionVisitor#visit(net.sf.jsqlparser.expression.operators.relational.NotEqualsTo)
	 */
	@Override
	public void visit(NotEqualsTo arg0) {
		Expression left = arg0.getLeftExpression();
		Expression right = arg0.getRightExpression();
		cs4321ExpressionVisitor ev1 = new cs4321ExpressionVisitor(expressionUnderTest, colToIndexHash); // need to change
		cs4321ExpressionVisitor ev2 = new cs4321ExpressionVisitor(expressionUnderTest, colToIndexHash); // need to change
		left.accept(ev1);
		right.accept(ev2);
		finalResult = ev1.getNumericResult() != ev2.getNumericResult();
	}

	/* (non-Javadoc)
	 * @see net.sf.jsqlparser.expression.ExpressionVisitor#visit(net.sf.jsqlparser.schema.Column)
	 */
	@Override
	public void visit(Column arg0) {
		String columnName = arg0.getColumnName();
		String numericResultInString = expressionUnderTest.getAttributes()[colToIndexHash.get(columnName)];
		numericResult = Integer.valueOf(numericResultInString);
	}
	
	public long getNumericResult() {
    	return numericResult;
    	
    }
    
    public boolean getFinalResult() {
    	return finalResult;
    	
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
