package one2one;

public class A {
	private int id;
	private String value;
	private B b;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public B getB() {
		return b;
	}
	public void setB(B b) {
		this.b = b;
	}
	@Override
	public String toString() {
		return "A [id=" + id + ", value=" + value + ", b=" + System.identityHashCode(b) + "]";
	}
}
