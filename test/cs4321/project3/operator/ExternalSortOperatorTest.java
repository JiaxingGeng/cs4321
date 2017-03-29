package cs4321.project3.operator;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import cs4321.project2.Catalog;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.OrderByElement;

public class ExternalSortOperatorTest {

	@Test
	public void testExternalSortOperator() throws IOException {
		Catalog.getInstance("testFolder");
		Catalog cat = Catalog.getInstance(null);
		String datapath = cat.getInputDir() + "\\db\\data\\" + "Boats_bin";
		Table t = new Table(null,"Boats_bin");
		List<OrderByElement> orderByElements = new ArrayList<OrderByElement> ();
		OrderByElement orderByElement0 = new OrderByElement();
		orderByElement0.setExpression(new Column(t, "A"));
		orderByElements.add(orderByElement0);
		//OrderByElement orderByElement1 = new OrderByElement();
		//orderByElement1.setExpression(new Column(t, "C"));
		//orderByElements.add(orderByElement1);
		ExternalSortOperator eso = new ExternalSortOperator(datapath, orderByElements);
		eso.dump();
	}

}
