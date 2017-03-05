package cs4321.project2.querytree;

import net.sf.jsqlparser.expression.*;
import net.sf.jsqlparser.expression.operators.arithmetic.*;
import net.sf.jsqlparser.expression.operators.conditional.*;
import net.sf.jsqlparser.expression.operators.relational.*;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.select.SubSelect;

public class QueryTreeVisitor implements ExpressionVisitor {
	
	
	private String column;
	private QueryTree queryTree;
		
	public QueryTreeVisitor(){
		column = null;
		queryTree = new QueryTree();
	}
	
	public String getColumn(){
		return column;
	}
		
	@Override
	public void visit(AndExpression arg0) {	
		Expression left = arg0.getLeftExpression();
		Expression right = arg0.getRightExpression();
		QueryTreeVisitor v1 = new QueryTreeVisitor();
		QueryTreeVisitor v2 = new QueryTreeVisitor();
		left.accept(v1);
		right.accept(v2);
	}
	
	@Override
	public void visit(Column arg0) {
		String columnName = arg0.getColumnName(); 
		String tableName = arg0.getTable().getName();
		column = tableName+"."+columnName;
	}
	
	@Override
	public void visit(LongValue arg0) {
	}

	@Override
	public void visit(MinorThan arg0) {
		Expression left = arg0.getLeftExpression();
		Expression right = arg0.getRightExpression();
		QueryTreeVisitor v1 = new QueryTreeVisitor();
		QueryTreeVisitor v2 = new QueryTreeVisitor();
		left.accept(v1);
		right.accept(v2);
		String column1 = v1.getColumn();
		String column2 = v1.getColumn();
		queryTree.setExpression(arg0, column1, column2);
	}

	@Override
	public void visit(MinorThanEquals arg0) {
		Expression left = arg0.getLeftExpression();
		Expression right = arg0.getRightExpression();
		QueryTreeVisitor v1 = new QueryTreeVisitor();
		QueryTreeVisitor v2 = new QueryTreeVisitor();
		left.accept(v1);
		right.accept(v2);
		String column1 = v1.getColumn();
		String column2 = v1.getColumn();
		queryTree.setExpression(arg0, column1, column2);
	}

	@Override
	public void visit(NotEqualsTo arg0) {
		Expression left = arg0.getLeftExpression();
		Expression right = arg0.getRightExpression();
		QueryTreeVisitor v1 = new QueryTreeVisitor();
		QueryTreeVisitor v2 = new QueryTreeVisitor();
		left.accept(v1);
		right.accept(v2);
		String column1 = v1.getColumn();
		String column2 = v1.getColumn();
		queryTree.setExpression(arg0, column1, column2);

	}
	
	@Override
	public void visit(EqualsTo arg0) {
		Expression left = arg0.getLeftExpression();
		Expression right = arg0.getRightExpression();
		QueryTreeVisitor v1 = new QueryTreeVisitor();
		QueryTreeVisitor v2 = new QueryTreeVisitor();
		left.accept(v1);
		right.accept(v2);
		String column1 = v1.getColumn();
		String column2 = v1.getColumn();
		queryTree.setExpression(arg0, column1, column2);
	}
	

	@Override
	public void visit(GreaterThan arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(GreaterThanEquals arg0) {
		// TODO Auto-generated method stub

	}


	@Override
	public void visit(OrExpression arg0) {

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
