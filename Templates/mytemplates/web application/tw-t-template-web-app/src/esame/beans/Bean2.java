package esame.beans;

import java.util.ArrayList;
import java.util.List;

public class Bean2 {
private int c;
private List<Bean1> list;
public Bean2(){
	this(0, new ArrayList<>());
}
public Bean2(int c, List<Bean1> list) {
	super();
	this.c = c;
	this.list = list;
}
public int getC() {
	return c;
}
public void setC(int c) {
	this.c = c;
}
public List<Bean1> getList() {
	return list;
}
public void setList(List<Bean1> list) {
	this.list = list;
}
}
