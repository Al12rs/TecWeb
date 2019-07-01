package many2many.bees;

import java.util.Set;

public class B {
	private int id;
	private String value;
	private Set<A> aas;
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
	public Set<A> getAas() {
		return aas;
	}
	public void setAas(Set<A> aas) {
		this.aas = aas;
	}
	@Override
	public String toString() {
		return "B [id=" + id + ", value=" + value + ", aas=" + System.identityHashCode(aas) + "]";
	}
	
}
