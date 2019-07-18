package dao;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class BDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private int b1;
	private int b2;
	private int id_c;
	private Set<ADTO> as;

	public BDTO() {
		// TODO Auto-generated constructor stub
	}

	public BDTO(int b1, int b2, int id_c) {
		this.b1 = b1;
		this.b2 = b2;
		this.id_c = id_c;
		this.as = new HashSet<ADTO>();
	}

	public int getB1() {
		return b1;
	}

	public void setB1(int b1) {
		this.b1 = b1;
	}

	public int getB2() {
		return b2;
	}

	public void setB2(int b2) {
		this.b2 = b2;
	}

	public int getId_c() {
		return id_c;
	}

	public void setId_c(int id_c) {
		this.id_c = id_c;
	}

	public Set<ADTO> getAs() {
		return as;
	}

	public void setAs(Set<ADTO> as) {
		this.as = as;
	}

	@Override
	public String toString() {
		return "BDTO [b1=" + b1 + ", b2=" + b2 + ", id_c=" + id_c + ", as=" + as + "]";
	}

}