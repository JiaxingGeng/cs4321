package cs4321.project3.operator;

import java.io.IOException;
import java.util.List;

import cs4321.project2.operator.Operator;
import cs4321.project2.operator.SortOperator;
import cs4321.project2.operator.Tuple;
import cs4321.project3.IO.BinaryWriter;
import net.sf.jsqlparser.schema.Table;

public class ExternalSortOperator {
	
	SortOperator sto;
	String datapath;

	public ExternalSortOperator(String datapath, List<?> orderByElements) throws IOException {
		this.datapath = datapath;
		String[] parts = datapath.split("\\\\");
		String tableName = parts[parts.length - 1];
		Table t = new Table(null, tableName);
		ScanBinOperator sbo = new ScanBinOperator(t);
//		sto = new SortOperator(sbo, orderByElements);
	}
	
	public void dump() throws IOException {
		String outDataPath = datapath + "_ExternalSorted";
		BinaryWriter binWriter = new BinaryWriter(outDataPath);
		Tuple nextTuple;
		while ((nextTuple = sto.getNextTuple()) != null) binWriter.write(nextTuple);
		binWriter.close();
	}

}
