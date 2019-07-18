package dao;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class CDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private int c1;
	private int c2;
	private int c3;
	private Set<BDTO> bs;

	public CDTO() {
		// TODO Auto-generated constructor stub
	}

	public CDTO(int c1, int c2, int c3) {
		this.c1 = c1;
		this.c2 = c2;
		this.c3 = c3;
		this.bs = new HashSet<BDTO>();
	}

	public int getC1() {
		return c1;
	}

	public void setC1(int c1) {
		this.c1 = c1;
	}

	public int getC2() {
		return c2;
	}

	public void setC2(int c2) {
		this.c2 = c2;
	}

	public int getC3() {
		return c3;
	}

	public void setC3(int c3) {
		this.c3 = c3;
	}

	public Set<BDTO> getBs() {
		return bs;
	}

	public void setBs(Set<BDTO> bs) {
		this.bs = bs;
	}

	@Override
	public String toString() {
		return "CDTO [c1=" + c1 + ", c2=" + c2 + ", c3=" + c3 + ", bs=" + bs + "]";
	}

}