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

	public String getElement(int pos){
		if (attributes != null && pos<attributes.length-1) 
			return attributes[pos];
		else return null;
	}
	
	public void print(){
		for (String s: attributes) System.out.print(s + ", ");
		System.out.println("");
	}

}
