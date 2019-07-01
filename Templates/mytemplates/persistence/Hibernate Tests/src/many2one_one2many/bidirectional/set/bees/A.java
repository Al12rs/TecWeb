package many2one_one2many.bidirectional.set.bees;

import java.util.Set;

public class A {
	private int id;
	private String value;
	private Set<B> bees;
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
	public Set<B> getBees() {
		return bees;
	}
	public void setBees(Set<B> bees) {
		this.bees = bees;
	}
	@Override
	public String toString() {
		return "A [id=" + id + ", value=" + value + ", bees=" + bees + "]";
	}
}
