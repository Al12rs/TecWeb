package many2one_one2many.bidirectional.list.bees;

import java.util.List;

public class A {
	private int id;
	private String value;
	private List<B> bees;
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
	public List<B> getBees() {
		return bees;
	}
	public void setBees(List<B> bees) {
		this.bees = bees;
	}
	@Override
	public String toString() {
		return "A [id=" + id + ", value=" + value + ", bees=" + bees + "]";
	}	
}
