package cs4321.project3.operator.logical;

import cs4321.project3.operator.*;
import net.sf.jsqlparser.statement.select.FromItem;

public class ScanLogicalOperator extends LogicalOperator {
	
	private FromItem fromItem;
	
	public ScanLogicalOperator(FromItem fromItem){
		this.fromItem = fromItem;
	}

	public FromItem getFromItem(){
		return fromItem;
	}
	
	@Override
	public void accept(LogicalOperatorVisitor visitor) {
		visitor.visit(this);
	}
}
