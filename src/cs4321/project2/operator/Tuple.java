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
		//System.out.println("the number of elements in this tuple: " + num);
		for (String s: attributes) System.out.print(s + ", ");
		System.out.println("");
	}
	
	public Tuple joins(Tuple rightTuple){
		   String[] comb= new String[num + rightTuple.getColumns()];
		   System.arraycopy(this.getAttributes(), 0, comb, 0, num);
		   System.arraycopy(rightTuple.getAttributes(), 0, 
				   comb, num, rightTuple.getColumns());
		   return new Tuple(comb);
	}
	
	public boolean equals(Tuple t){
		if (t == null) return false;
		String[] compAttrs = t.getAttributes();
		for (int i=0;i<t.num;i++){
			if (!compAttrs[i].equals(attributes[i])) return false;
		}
		return true;
	}

}
