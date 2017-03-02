package cs4321.project2.operator;

import cs4321.project2.Catalog;
import java.io.IOException;
import net.sf.jsqlparser.statement.select.*;
import net.sf.jsqlparser.schema.*;
import java.util.LinkedList;

import java.util.Arrays;
import java.util.List;
import cs4321.project2.deparser.SelectDeParser;;


/**
 * Scan the table from the file in the database
 * 
 * @author Jiaxing Geng (jg755), Yangyi Hao (yh326)
 */


public class ProjectOperator extends Operator {
	private Operator child;
	private PlainSelect ps;
	private String[] attributes;
	private Catalog catalog;
	
	public ProjectOperator(Operator c, PlainSelect p) throws IOException{
		child = c;
		ps = p;
		catalog = Catalog.getInstance(null);
		Table table = (Table) p.getFromItem();
		attributes = catalog.getAttributes(table.getName());
	}
	
	public Tuple getNextTuple() throws IOException{
		Tuple t = child.getNextTuple();
		LinkedList<String> stringList = new LinkedList<>();
		int num = ps.getSelectItems().size();
		for (int i=0;i<num;i++){
			SelectItem selectItem = (SelectItem) ps.getSelectItems().get(i);
			SelectDeParser selectDeParser = new SelectDeParser();
			selectItem.accept(selectDeParser);
			String selectColumn = selectDeParser.getBuffer().toString();
			int pos = Arrays.binarySearch(attributes,selectColumn);
			if (pos>=0) stringList.add(t.getElement(pos));
			else throw new IOException("cannot find Column");
		}
		return new Tuple(stringList.toArray(new String[stringList.size()]));		
	}
	
	public void reset() throws IOException{
		child.reset();
	}
	

}
