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
	Catalog catalog;
	
	public PlanGenerator(PlainSelect plainSelect,String inputdir) 
			throws IOException{
		catalog = Catalog.getInstance(null);
		FromItem fromItem = plainSelect.getFromItem();
		ScanOperator opScan = new ScanOperator(fromItem);
	    if (plainSelect.getJoins() == null){
			if (plainSelect.getWhere() == null)
				op = new ProjectOperator(opScan,plainSelect.getSelectItems());
			else {
				SelectOperator opSel = 
						new SelectOperator(opScan,plainSelect.getWhere());
				op = new ProjectOperator(opSel,plainSelect.getSelectItems());
			}    	
	    } else {
	    	
	    }
	}
	
	public Operator getQueryPlan(){
		return op;		
	}

}
