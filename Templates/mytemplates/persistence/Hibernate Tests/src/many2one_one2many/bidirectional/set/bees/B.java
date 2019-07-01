package many2one_one2many.bidirectional.set.bees;

public class B {
	private int id;
	private String value;
	private A a;
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
	public A getA() {
		return a;
	}
	public void setA(A a) {
		this.a = a;
	}
	@Override
	public String toString() {
		return "B [id=" + id + ", value=" + value + ", a=" + System.identityHashCode(a) + "]";
	}
}
