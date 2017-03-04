package cs4321.project2;

import net.sf.jsqlparser.statement.select.*;
import cs4321.project2.deparser.SelectDeParser;
import cs4321.project2.operator.*;
import java.io.IOException;
import java.util.HashMap;

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
		ScanOperator opScan = new ScanOperator(fromItem,inputdir);
		SelectDeParser selectVisitor = new SelectDeParser();
	    fromItem.accept(selectVisitor);
	    String tableName = selectVisitor.getResult();
		catalog.clearColumnsHash();
	    catalog.setColumnsHash(ColToIndexHash(tableName));
		if (plainSelect.getWhere() == null)
			op = new ProjectOperator(opScan,plainSelect.getSelectItems());
		else {
			SelectOperator opSel = 
					new SelectOperator(opScan,plainSelect.getWhere());
			op = new ProjectOperator(opSel,plainSelect.getSelectItems());
		}
	}
	
	public Operator getQueryPlan(){
		return op;		
	}
	
	private HashMap<String,Integer> ColToIndexHash(String tableTuple) 
			throws IOException{
		String[] tp = tableTuple.split(",");
		String[] attributes = catalog.getAttributes(tp[0]);
		HashMap<String,Integer> res = new HashMap<>();
		for (int i=0;i<attributes.length;i++) 
			res.put(tp[1]+"."+attributes[i],i);
		return res;	
	}

}
