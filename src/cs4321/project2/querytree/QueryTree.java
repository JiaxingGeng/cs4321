package cs4321.project2.querytree;

import java.util.Stack;
import java.util.List;
import cs4321.project2.deparser.SelectDeParser;
import cs4321.project2.operator.*;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.statement.select.FromItem;
import net.sf.jsqlparser.statement.select.Join;
import net.sf.jsqlparser.expression.operators.conditional.*;
import java.io.IOException;

/**
 * Query Tree that represents the tree structure of tables with its corresponding
 * expression. It only supports expressions connected by AND. Query Tree can also 
 * be converted to query plan, which includes operators for actual computation.
 * 
 * @author Jiaxing Geng (jg755), Yangyi Hao (yh326)
 */

public class QueryTree {

	private static Node queryTree;

	public QueryTree(){}

	/**
	 * Create QueryTree object with all expressions set to null.
	 * @param fromItem the first item from PlainSelect
	 * @param fromJoins a list of items after the first item
	 */
	public QueryTree(FromItem fromItem, List<?> fromJoins){
		Stack<FromItem> fromItems = new Stack<>();
		fromItems.push(fromItem);
		for (int i=0;i<fromJoins.size();i++){
			Join joinItem = (Join) fromJoins.get(i);
			fromItems.push(joinItem.getRightItem());		
		}
		queryTree = buildTree(fromItems);
	}

	/**
	 * Get current query tree in the memory
	 * @return the top node of the query tree
	 */
	public Node getQueryTree(){
		return queryTree;
	}

	/**
	 * Clear the query tree in the memory. Need to be call when start with
	 * a new query.
	 */
	public void clearQueryTree(){
		queryTree = null;
	}

	/**
	 * Push an expression into the query tree. Assume that all expression are 
	 * connected by AND
	 * @param expression must be relational expression
	 * @param column1 name of the column. If it is not a column, set to null
	 * @param column2 name of the column. If it is not a column, set to null
	 */
	public void setExpression
	(Expression expression, String column1, String column2){
		Node expressionNode;
		if (column2 == null) expressionNode = findLeaf(queryTree,column1);
		else if (column1 == null) expressionNode = findLeaf(queryTree,column2);
		else expressionNode = findJoinNode(queryTree,column1,column2);
		Expression expBefore = expressionNode.getExpression();
		if (expBefore == null) expressionNode.setExpression(expression);
		else {
			Expression newExp = new AndExpression(expBefore,expression);
			expressionNode.setExpression(newExp);
		}
	}

	/**
	 * Convert query tree structure to operator tree(query plan)
	 * @return head join node of the operator tree
	 * @throws IOException
	 */
	public Operator getQueryPlan() throws IOException{
		return buildQueryPlan(queryTree);
	}

	/**
	 * print query tree
	 * @param n top node
	 */
	public void print(Node n){
		if (n instanceof Leaf) {
			Leaf f = (Leaf) n;
			String exp;
			if(n.getExpression()==null) exp = "null";
			else exp = n.getExpression().toString();
			System.out.println("Leaf("+ f.getTableName()+"):"+exp);
		}
		else{
			JoinNode joinNode = (JoinNode) n;
			String exp;
			if(n.getExpression()==null) exp = "null";
			else exp = n.getExpression().toString();
			System.out.print("JoinNode:"+ exp +" ---> ");			
			this.print(joinNode.getRight());
			this.print(joinNode.getLeft());
		}		
	}

	/**
	 * print query plan
	 * @param topOp top operator
	 * @throws IOException
	 */
	public void printQueryPlan(Operator topOp) throws IOException{
		if (topOp instanceof JoinOperator){
			System.out.print(topOp.toString() + "--->");
			this.printQueryPlan(((JoinOperator) topOp).getRightChild());
			this.printQueryPlan(((JoinOperator) topOp).getLeftChild());
		} else if (topOp instanceof SelectOperator){
			System.out.print(topOp.toString() + "--->");
			this.printQueryPlan(((SelectOperator) topOp).getChild());
		} else {
			System.out.println(topOp.toString());
		}		
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
			Expression joinExpression = joinNode.getExpression();
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
