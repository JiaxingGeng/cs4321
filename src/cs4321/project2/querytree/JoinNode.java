package cs4321.project2.querytree;

import net.sf.jsqlparser.expression.Expression;

/**
 * Nodes that contains expression for two columns and will be converted to
 * join operator when building the query plan. The left child will always be
 * a leaf for our query tree implementation. Expression will be associated 
 * with the table on the child leaf that is higher in the tree.
 * @author Jiaxing Geng (jg755), Yangyi Hao (yh326)
 *
 */
public class JoinNode extends Node {
	private Node leftNode;
	private Leaf rightLeaf;
	
	public JoinNode(Node leftNode,Leaf rightLeaf,Expression expression){
		this.leftNode = leftNode;
		this.rightLeaf = rightLeaf;
		super.expression = expression;
	}
		
	/**
	 * Get the left node
	 * @return node that can be Leaf nor joinNode
	 */
	public Node getLeft(){
		return leftNode;
	}
	
	/**
	 * This leaf's table name determines expression's position.
	 * @return right leaf of the join node
	 */
	public Leaf getRight(){
		return rightLeaf;
	}

}
