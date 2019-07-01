package one2many.list.string;

import java.util.List;

public class A {
	private int id;
	private String value;
	private List<String> strings;
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
	public List<String> getStrings() {
		return strings;
	}
	public void setStrings(List<String> strings) {
		this.strings = strings;
	}
	@Override
	public String toString() {
		return "A [id=" + id + ", value=" + value + ", strings=" + strings + "]";
	}
}
