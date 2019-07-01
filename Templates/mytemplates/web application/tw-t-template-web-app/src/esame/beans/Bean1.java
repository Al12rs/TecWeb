package esame.beans;

public class Bean1 {
	private int a;
	private String b;
	public Bean1() {
		this(0,"");
	}	
	public Bean1(int a, String b) {
		super();
		this.a = a;
		this.b = b;
	}
	public int getA() {
		return a;
	}
	public void setA(int a) {
		this.a = a;
	}
	public String getB() {
		return b;
	}
	public void setB(String b) {
		this.b = b;
	}
}
