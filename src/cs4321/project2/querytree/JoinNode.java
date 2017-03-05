package cs4321.project2.querytree;

import net.sf.jsqlparser.expression.Expression;


public class JoinNode extends Node {
	private Node leftNode;
	private Leaf rightLeaf;
	
	public JoinNode(Node leftNode,Leaf rightLeaf,Expression expression){
		this.leftNode = leftNode;
		this.rightLeaf = rightLeaf;
		super.expression = expression;
	}
		
	public Node getLeft(){
		return leftNode;
	}
	
	public Leaf getRight(){
		return rightLeaf;
	}

}
