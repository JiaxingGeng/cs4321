package cs4321.project3.operator.physical;

import static org.junit.Assert.*;

import cs4321.project2.Catalog;
import cs4321.project2.operator.*;
import cs4321.project3.utils.*;
import cs4321.project3.IO.*;
import cs4321.project3.operator.physical.ExternalSortOperator.ReaderNode;
import net.sf.jsqlparser.schema.*;
import net.sf.jsqlparser.statement.select.*;
import java.util.*;
import org.junit.Test;

public class ExternalSortOperatorTest {

	@Test
	public void testReaderNode() {
		try{
			Catalog.getInstance("input");
			Table t1 = new Table(null,"Boats");
			Operator op = new ScanOperator(t1);
			Column c1 = new Column(t1,"E");
			Column c2 = new Column(t1,"F");
			SelectExpressionItem sexp1 = new SelectExpressionItem();
			SelectExpressionItem sexp2 = new SelectExpressionItem();
			sexp1.setExpression(c1);
			sexp2.setExpression(c2);
			List<SelectItem> sItems = new LinkedList<>();
			sItems.add(sexp1);
			sItems.add(sexp2);
			
//			BinaryWriter writer = new BinaryWriter("temp/Boats");
//			int[] range = {200,200,200};
//			RandomGenerator rg = new RandomGenerator(10000,3,range);
//			ArrayList<Tuple> tuples = rg.getRandomTuples();
//			for (Tuple t:tuples) writer.write(t);
//			writer.close();
//			HumanReadableConvertor.convert
//			("temp/Boats", "temp/Boats_readable");	
			
//			HumanReadableConvertor.convert ("output/query14", "output/query14_hm");			
			HumanReadableConvertor.convert ("temp/2/P7/0", "temp/2/P7/0_hm");			
			
			
//			
//			ExternalSortOperator opSort = new ExternalSortOperator(op,null,sItems,3);
//			opSort.getNextTuple().print();
//			opSort.getNextTuple().print();
//			opSort.getNextTuple().print();
//			
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
