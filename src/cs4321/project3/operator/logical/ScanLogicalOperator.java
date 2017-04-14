package cs4321.project3.operator.logical;

import cs4321.project3.operator.*;
import net.sf.jsqlparser.statement.select.FromItem;

/**
 * Logical Scan Operator that is used to build logical query plan
 * 
 * @author Jiaxing Geng (jg755), Yangyi Hao (yh326)
 */
public class ScanLogicalOperator extends LogicalOperator {
	
	private FromItem fromItem;
	
	public ScanLogicalOperator(FromItem fromItem){
		this.fromItem = fromItem;
	}

	/**
	 * Get the table to be scanned
	 * @return fromItem
	 */
	public FromItem getFromItem(){
		return fromItem;
	}
	
	@Override
	public void accept(LogicalOperatorVisitor visitor) {
		visitor.visit(this);
	}
}
