package cs4321.project3.operator.physical;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.Writer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import org.junit.Test;

import cs4321.project2.Catalog;
import cs4321.project2.operator.*;
import cs4321.project2.operator.ScanOperator;
import cs4321.project3.deparser.SMJExpressionDeParser;
import cs4321.project3.IO.*;
import cs4321.project3.utils.SortFile;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;

public class SMJOperatorTest {

	@Test
	public void testSMJOperator() {
		System.out.println("-----------------------");
		System.out.println("| testSMJOperator() |");
		System.out.println("-----------------------");
		Table tl = new Table(null,"Sailors");
		tl.setAlias("S");
		Table tr = new Table(null, "Reserves");
		tr.setAlias("R");
		Column leftExpCol1 = new Column(tl, "A");
		Column rightExpCol1 = new Column(tr, "G");
		EqualsTo equalsToExp1 = new EqualsTo();
		equalsToExp1.setLeftExpression(leftExpCol1);
		equalsToExp1.setRightExpression(rightExpCol1);
		Column leftExpCol2 = new Column(tl, "B");
		Column rightExpCol2 = new Column(tr, "H");
		EqualsTo equalsToExp2 = new EqualsTo();
		equalsToExp2.setLeftExpression(leftExpCol2);
		equalsToExp2.setRightExpression(rightExpCol2);
		AndExpression andExp = new AndExpression();
		andExp.setLeftExpression(equalsToExp1);
		andExp.setRightExpression(equalsToExp2);
		try{ Catalog.getInstance("input");
		ScanOperator sol = new ScanOperator(tl);
		ScanOperator sor = new ScanOperator(tr);
		SMJOperator smjo = new SMJOperator(sol, sor, andExp);
		} catch (IOException e) {}
	}
	
	@Test
	public void test(){
		try{
			for (int i=1;i<=2;i++){
				String s = "query"+i;
				SortFile sf = new SortFile("./output_smj/"+s,true);
				sf.sort(false);
				sf.sort(true);
				BinaryReader rd= new BinaryReader("./output_smj/"+s);
				HumanReadableWriter wt= new HumanReadableWriter("./output_smj/"+s+"_hm");
				String line = rd.readLine();
				while (line!=null){
					wt.write(new Tuple(line.split(",")));
					line = rd.readLine();
				}
				rd.close();
				wt.close();
			} 
		}
		catch (Exception e){
			fail();
		}
		
		try{
			for (int i=1;i<=2;i++){
				System.out.println(i);
				String s = "query"+i+"_sorted";
				// check every byte
				FileInputStream fin1 = new FileInputStream("./output_smj/"+s);
				FileInputStream fin2 = new FileInputStream("./expected_smj/"+s);
				FileChannel fc1 = fin1.getChannel();
				FileChannel fc2 = fin2.getChannel();
				ByteBuffer buffer1 = ByteBuffer.allocate( 4096 ); 
				ByteBuffer buffer2 = ByteBuffer.allocate( 4096 );
				HumanReadableReader rd= new HumanReadableReader("./output_smj/"+s+"_hm");
				String current_tuple=null;
				int res = fc1.read(buffer1);
				int block=0;
				while (res!=-1){
					int res2 = fc2.read(buffer2); 
					if (res2 ==-1) fail();  // not having the same page
					for(int j=0;j<4096;j++){
						if (i==1 && j>8 && j%(4*5)==0) {
							current_tuple = rd.readLine();
						}
						if (i==1 && j>8 && j%(4*8)==0) {
							current_tuple = rd.readLine();
						}
						byte b1 = buffer1.get(j);
						byte b2 = buffer2.get(j);
						if(b1!=b2){
						System.out.println("wrong pos block num:"+block);
						System.out.println("wrong pos:"+j);
						System.out.println("somewhere around this tuple:"+current_tuple);
						rd.close();
						}
						assertEquals(b1,b2);
					}
					buffer1 = ByteBuffer.allocate( 4096 ); 
					buffer2 = ByteBuffer.allocate( 4096 );
					res = fc1.read(buffer1);
					block++;
				}		
				fin1.close();
				fin2.close();		
			}
		} catch (Exception e){
			fail();
		}
	}

}
