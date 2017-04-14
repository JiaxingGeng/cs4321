/**
 * 
 */
package cs4321.project3.deparser;

import java.util.ArrayList;

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
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.GreaterThan;
import net.sf.jsqlparser.expression.operators.relational.GreaterThanEquals;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.expression.operators.relational.IsNullExpression;
import net.sf.jsqlparser.expression.operators.relational.ItemsListVisitor;
import net.sf.jsqlparser.expression.operators.relational.LikeExpression;
import net.sf.jsqlparser.expression.operators.relational.Matches;
import net.sf.jsqlparser.expression.operators.relational.MinorThan;
import net.sf.jsqlparser.expression.operators.relational.MinorThanEquals;
import net.sf.jsqlparser.expression.operators.relational.NotEqualsTo;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.select.SubSelect;

/**
 * SMJExpressionDeParser is a special expression deparser specifically designed for the SMJ operator
 * The expression that evaluates MUST (need to double check) be 
 * a series of equality expression connected by AND, and the equality expressions MUST have only columns on 
 * two sides.
 * The result will be a tuple (array of length 2) of arrays of strings (column names).
 * @author Jiaxing Geng (jg755), Yangyi Hao (yh326)
 *
 */
public class SMJExpressionDeParser implements ExpressionVisitor, ItemsListVisitor {

	private ArrayList<ArrayList<Column>> result;
	private String[] tableNames;
	
	/**
	 * 
	 */
	public SMJExpressionDeParser(String[] tableNames) {
		result = new ArrayList<ArrayList<Column>> ();
		this.tableNames = tableNames;
	}
	
	public ArrayList<ArrayList<Column>> getResult() {
		return result;
	}

	/* (non-Javadoc)
	 * @see net.sf.jsqlparser.expression.operators.relational.ItemsListVisitor#visit(net.sf.jsqlparser.expression.operators.relational.ExpressionList)
	 */
	@Override
	public void visit(ExpressionList arg0) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see net.sf.jsqlparser.expression.ExpressionVisitor#visit(net.sf.jsqlparser.expression.NullValue)
	 */
	@Override
	public void visit(NullValue arg0) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see net.sf.jsqlparser.expression.ExpressionVisitor#visit(net.sf.jsqlparser.expression.Function)
	 */
	@Override
	public void visit(Function arg0) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see net.sf.jsqlparser.expression.ExpressionVisitor#visit(net.sf.jsqlparser.expression.InverseExpression)
	 */
	@Override
	public void visit(InverseExpression arg0) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see net.sf.jsqlparser.expression.ExpressionVisitor#visit(net.sf.jsqlparser.expression.JdbcParameter)
	 */
	@Override
	public void visit(JdbcParameter arg0) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see net.sf.jsqlparser.expression.ExpressionVisitor#visit(net.sf.jsqlparser.expression.DoubleValue)
	 */
	@Override
	public void visit(DoubleValue arg0) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see net.sf.jsqlparser.expression.ExpressionVisitor#visit(net.sf.jsqlparser.expression.LongValue)
	 */
	@Override
	public void visit(LongValue arg0) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see net.sf.jsqlparser.expression.ExpressionVisitor#visit(net.sf.jsqlparser.expression.DateValue)
	 */
	@Override
	public void visit(DateValue arg0) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see net.sf.jsqlparser.expression.ExpressionVisitor#visit(net.sf.jsqlparser.expression.TimeValue)
	 */
	@Override
	public void visit(TimeValue arg0) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see net.sf.jsqlparser.expression.ExpressionVisitor#visit(net.sf.jsqlparser.expression.TimestampValue)
	 */
	@Override
	public void visit(TimestampValue arg0) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see net.sf.jsqlparser.expression.ExpressionVisitor#visit(net.sf.jsqlparser.expression.Parenthesis)
	 */
	@Override
	public void visit(Parenthesis arg0) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see net.sf.jsqlparser.expression.ExpressionVisitor#visit(net.sf.jsqlparser.expression.StringValue)
	 */
	@Override
	public void visit(StringValue arg0) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see net.sf.jsqlparser.expression.ExpressionVisitor#visit(net.sf.jsqlparser.expression.operators.arithmetic.Addition)
	 */
	@Override
	public void visit(Addition arg0) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see net.sf.jsqlparser.expression.ExpressionVisitor#visit(net.sf.jsqlparser.expression.operators.arithmetic.Division)
	 */
	@Override
	public void visit(Division arg0) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see net.sf.jsqlparser.expression.ExpressionVisitor#visit(net.sf.jsqlparser.expression.operators.arithmetic.Multiplication)
	 */
	@Override
	public void visit(Multiplication arg0) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see net.sf.jsqlparser.expression.ExpressionVisitor#visit(net.sf.jsqlparser.expression.operators.arithmetic.Subtraction)
	 */
	@Override
	public void visit(Subtraction arg0) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see net.sf.jsqlparser.expression.ExpressionVisitor#visit(net.sf.jsqlparser.expression.operators.conditional.AndExpression)
	 */
	@Override
	public void visit(AndExpression arg0) {
		Expression left = arg0.getLeftExpression();
		Expression right = arg0.getRightExpression();
		SMJExpressionDeParser leftVisitor = new SMJExpressionDeParser(tableNames);
		SMJExpressionDeParser rightVisitor = new SMJExpressionDeParser(tableNames);
		left.accept(leftVisitor);
		right.accept(rightVisitor);
		ArrayList<ArrayList<Column>> leftResult = leftVisitor.getResult();
		ArrayList<ArrayList<Column>> rightResult = rightVisitor.getResult();
		//System.out.println("rightResult.size() is: " + rightResult.size());
		//System.out.println("rightResult.get(0).size() is: " + rightResult.get(0).size());
		for (int i = 0; i < rightResult.get(0).size(); i++) {
			leftResult.get(0).add(rightResult.get(0).get(i));
		}
		for (int i = 0; i < rightResult.get(1).size(); i++) {
			leftResult.get(1).add(rightResult.get(1).get(i));
		}
		result = leftResult;

	}

	/* (non-Javadoc)
	 * @see net.sf.jsqlparser.expression.ExpressionVisitor#visit(net.sf.jsqlparser.expression.operators.conditional.OrExpression)
	 */
	@Override
	public void visit(OrExpression arg0) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see net.sf.jsqlparser.expression.ExpressionVisitor#visit(net.sf.jsqlparser.expression.operators.relational.Between)
	 */
	@Override
	public void visit(Between arg0) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see net.sf.jsqlparser.expression.ExpressionVisitor#visit(net.sf.jsqlparser.expression.operators.relational.EqualsTo)
	 */
	@Override
	public void visit(EqualsTo arg0) {
		Column cl = (Column) arg0.getLeftExpression();
		String tableLeftName = cl.getTable().getName(); // Maybe only need to call toString and auto change to alias
		String tableLeftAlias = cl.getTable().getAlias();
		Column cr = (Column) arg0.getRightExpression();
//		String tableRightName = cl.getTable().getName();
//		String tableRightAlias = cl.getTable().getAlias();
		//System.out.println(cl.toString());
		//System.out.println(cl.getTable().toString());
		//System.out.println(cr.toString());
		//System.out.println(cr.getTable().toString());
		ArrayList<ArrayList<Column>> eqlResult = new ArrayList<ArrayList<Column>> ();
		ArrayList<Column> eqlResultLeft = new ArrayList<Column> ();
		ArrayList<Column> eqlResultRight = new ArrayList<Column> ();
		if (tableLeftName == tableNames[0] || tableLeftAlias == tableNames[0]) {
			eqlResultLeft.add(cl);
			eqlResultRight.add(cr);
		}
		else {
			eqlResultRight.add(cl);
			eqlResultLeft.add(cr);
		}
		//eqlResult.set(0, eqlResultLeft);
		//eqlResult.set(1, eqlResultRight);
		eqlResult.add(eqlResultRight); // add will add from right to left.
		eqlResult.add(eqlResultLeft);
		
		result = eqlResult;

	}

	/* (non-Javadoc)
	 * @see net.sf.jsqlparser.expression.ExpressionVisitor#visit(net.sf.jsqlparser.expression.operators.relational.GreaterThan)
	 */
	@Override
	public void visit(GreaterThan arg0) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see net.sf.jsqlparser.expression.ExpressionVisitor#visit(net.sf.jsqlparser.expression.operators.relational.GreaterThanEquals)
	 */
	@Override
	public void visit(GreaterThanEquals arg0) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see net.sf.jsqlparser.expression.ExpressionVisitor#visit(net.sf.jsqlparser.expression.operators.relational.InExpression)
	 */
	@Override
	public void visit(InExpression arg0) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see net.sf.jsqlparser.expression.ExpressionVisitor#visit(net.sf.jsqlparser.expression.operators.relational.IsNullExpression)
	 */
	@Override
	public void visit(IsNullExpression arg0) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see net.sf.jsqlparser.expression.ExpressionVisitor#visit(net.sf.jsqlparser.expression.operators.relational.LikeExpression)
	 */
	@Override
	public void visit(LikeExpression arg0) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see net.sf.jsqlparser.expression.ExpressionVisitor#visit(net.sf.jsqlparser.expression.operators.relational.MinorThan)
	 */
	@Override
	public void visit(MinorThan arg0) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see net.sf.jsqlparser.expression.ExpressionVisitor#visit(net.sf.jsqlparser.expression.operators.relational.MinorThanEquals)
	 */
	@Override
	public void visit(MinorThanEquals arg0) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see net.sf.jsqlparser.expression.ExpressionVisitor#visit(net.sf.jsqlparser.expression.operators.relational.NotEqualsTo)
	 */
	@Override
	public void visit(NotEqualsTo arg0) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see net.sf.jsqlparser.expression.ExpressionVisitor#visit(net.sf.jsqlparser.schema.Column)
	 */
	@Override
	public void visit(Column arg0) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see net.sf.jsqlparser.expression.ExpressionVisitor#visit(net.sf.jsqlparser.statement.select.SubSelect)
	 */
	@Override
	public void visit(SubSelect arg0) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see net.sf.jsqlparser.expression.ExpressionVisitor#visit(net.sf.jsqlparser.expression.CaseExpression)
	 */
	@Override
	public void visit(CaseExpression arg0) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see net.sf.jsqlparser.expression.ExpressionVisitor#visit(net.sf.jsqlparser.expression.WhenClause)
	 */
	@Override
	public void visit(WhenClause arg0) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see net.sf.jsqlparser.expression.ExpressionVisitor#visit(net.sf.jsqlparser.expression.operators.relational.ExistsExpression)
	 */
	@Override
	public void visit(ExistsExpression arg0) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see net.sf.jsqlparser.expression.ExpressionVisitor#visit(net.sf.jsqlparser.expression.AllComparisonExpression)
	 */
	@Override
	public void visit(AllComparisonExpression arg0) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see net.sf.jsqlparser.expression.ExpressionVisitor#visit(net.sf.jsqlparser.expression.AnyComparisonExpression)
	 */
	@Override
	public void visit(AnyComparisonExpression arg0) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see net.sf.jsqlparser.expression.ExpressionVisitor#visit(net.sf.jsqlparser.expression.operators.arithmetic.Concat)
	 */
	@Override
	public void visit(Concat arg0) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see net.sf.jsqlparser.expression.ExpressionVisitor#visit(net.sf.jsqlparser.expression.operators.relational.Matches)
	 */
	@Override
	public void visit(Matches arg0) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see net.sf.jsqlparser.expression.ExpressionVisitor#visit(net.sf.jsqlparser.expression.operators.arithmetic.BitwiseAnd)
	 */
	@Override
	public void visit(BitwiseAnd arg0) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see net.sf.jsqlparser.expression.ExpressionVisitor#visit(net.sf.jsqlparser.expression.operators.arithmetic.BitwiseOr)
	 */
	@Override
	public void visit(BitwiseOr arg0) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see net.sf.jsqlparser.expression.ExpressionVisitor#visit(net.sf.jsqlparser.expression.operators.arithmetic.BitwiseXor)
	 */
	@Override
	public void visit(BitwiseXor arg0) {
		// TODO Auto-generated method stub

	}

}
