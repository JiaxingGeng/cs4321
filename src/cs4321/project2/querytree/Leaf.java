package cs4321.project2.querytree;

import net.sf.jsqlparser.expression.Expression;

/**
 * Nodes that contains expression for single column and will be converted to
 * selection operator when building the query plan.
 * @author Jiaxing Geng (jg755), Yangyi Hao (yh326)
 *
 */
public class Leaf extends Node{

	private String tableName;
	
	public Leaf(String tableName,Expression expression){
		super.expression = expression;
		this.tableName = tableName;			
	}
		
	/**
	 * Get the table name with its alias
	 * @return the String as "tableName.tableAlias"
	 */
	public String getTableName (){
		return tableName;
	}
	
}
