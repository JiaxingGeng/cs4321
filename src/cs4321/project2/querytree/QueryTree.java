package cs4321.project2.querytree;

import java.util.Stack;

import cs4321.project2.deparser.SelectDeParser;
import cs4321.project2.operator.*;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.statement.select.FromItem;
import net.sf.jsqlparser.expression.operators.conditional.*;
import java.io.IOException;

public class QueryTree {

	private static Node queryTree;

	public QueryTree(){}

	public QueryTree(Stack<FromItem> fromItems){
		queryTree = buildTree(fromItems);
	}

	public Node getQueryTree(){
		return queryTree;
	}

	public void clearQueryTree(){
		queryTree = null;
	}	
	public void setExpression
	 (Expression expression, String column1, String column2){
		Node expressionNode;
		if (column2 == null) expressionNode = findLeaf(queryTree,column1);
		if (column1 == null) expressionNode = findLeaf(queryTree,column2);
		else expressionNode = findJoinNode(queryTree,column1,column2);
		Expression expBefore = expressionNode.getExpression();
		if (expBefore == null) expressionNode.setExpression(expression);
		else {
			Expression newExp = new AndExpression(expBefore,expression);
			expressionNode.setExpression(newExp);
		}
	}


	public Operator getQueryPlan() throws IOException{
		return buildQueryPlan(queryTree);
	}


	private Operator buildQueryPlan(Node n) throws IOException{
		if (n instanceof Leaf){
			Leaf leafNode = (Leaf) n;
			Table table = new Table(null,leafNode.getTableName());
			ScanOperator sOp = new ScanOperator(table);
			Expression expression = leafNode.getExpression();
			if (expression == null) return sOp;
			else return new SelectOperator(sOp,expression);		
		} else {
			JoinNode joinNode = (JoinNode) n;
			Leaf rightLeaf = joinNode.getRight();
			Node leftnode = joinNode.getLeft();
			Operator opRight;
			Operator opLeft;

			Table table = new Table(null,rightLeaf.getTableName());
			ScanOperator sOp = new ScanOperator(table);
			Expression expression = rightLeaf.getExpression();
			Expression joinExpression = leftnode.getExpression();
			if (expression == null) opRight=sOp;
			else opRight = new SelectOperator(sOp,expression);	
			opLeft = buildQueryPlan(leftnode);	

			return new JoinOperator(opLeft,opRight,joinExpression);						
		}	
	}

	private JoinNode findJoinNode(Node n, String column1, String column2){
		JoinNode joinNode = (JoinNode) n;
		String tableName = joinNode.getRight().getTableName();
		if( column1.equals(tableName) || column2.equals(tableName))
			return joinNode;
		else if (joinNode.getLeft() instanceof Leaf) return null; 
		else return findJoinNode(joinNode.getLeft(), column1, column2);
	}

	private Leaf findLeaf(Node n,String column){
		JoinNode joinNode = (JoinNode) n;
		String tableName = joinNode.getRight().getTableName();
		if( column.equals(tableName)) return joinNode.getRight();
		else if (joinNode.getLeft() instanceof Leaf) {
			Leaf leaf = (Leaf) joinNode.getLeft();
			if (column.equals(leaf.getTableName())) return leaf;
			else return null;
		}
		else return findLeaf(joinNode.getLeft(), column);
	}

	private Node buildTree(Stack<FromItem> fromItems){
		FromItem fromItem = fromItems.pop();
		SelectDeParser selectVisitor = new SelectDeParser();
		fromItem.accept(selectVisitor);
		String tableName = selectVisitor.getResult();
		Leaf leaf = new Leaf(tableName,null);
		if (fromItems.isEmpty()) return leaf;
		else return new JoinNode(this.buildTree(fromItems),leaf,null);
	}

}
