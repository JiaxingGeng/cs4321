package cs4321.project2.operator;

public class Tuple {
	
	private String[] attributes;
	private int num;
	
	public Tuple(String[] attrs){
		attributes = attrs;
		num = attrs.length;
	}
	
	public int getColumns(){
		return num;
	}
	
	public String[] getAttributes() {
		return attributes;
	}
	
	public void print(){
		for (String s: attributes) System.out.print(s + ", ");
		System.out.println("");
	}

}
