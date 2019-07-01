package one2many.set.string;

import java.util.Set;

public class A {
	private int id;
	private String value;
	private Set<String> strings;
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
	public Set<String> getStrings() {
		return strings;
	}
	public void setStrings(Set<String> strings) {
		this.strings = strings;
	}
	@Override
	public String toString() {
		return "A [id=" + id + ", value=" + value + ", strings=" + strings + "]";
	}
}
