package cs4321.project2;

import net.sf.jsqlparser.statement.select.*;
import cs4321.project2.operator.*;
import java.io.IOException;

/**
 * Generate Query Plan from PlainSelect
 * 
 * @author Jiaxing Geng (jg755), Yangyi Hao (yh326)
 */
public class PlanGenerator {
	
	Operator op;
	
	public PlanGenerator(PlainSelect plainSelect,String inputdir) 
			throws IOException{
		FromItem fromItem = plainSelect.getFromItem();
		ScanOperator opScan = new ScanOperator(fromItem,inputdir);
		if (plainSelect.getWhere() == null)
			op = new ProjectOperator(opScan,plainSelect.getSelectItems(),fromItem);
		else {
			SelectOperator opSel = 
					new SelectOperator(opScan,fromItem,plainSelect.getWhere());
			op = new ProjectOperator(opSel,plainSelect.getSelectItems(),fromItem);
		}
	}
	
	public Operator getQueryPlan(){
		return op;
		
	}

}
