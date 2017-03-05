package cs4321.project2.querytree;

import net.sf.jsqlparser.expression.Expression;

public class Leaf extends Node{

	private String tableName;
	
	public Leaf(String tableName,Expression expression){
		super.expression = expression;
		this.tableName = tableName;			
	}
		
	public String getTableName (){
		return tableName;
	}
	
}
