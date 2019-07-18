package dao;

import java.io.Serializable;

public class ADTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private int a1;
	private int a2;
	private int a3;
	private int id_b;

	public ADTO() {
		// TODO Auto-generated constructor stub
	}

	public ADTO(int a1, int a2, int a3, int id_b) {
		this.a1 = a1;
		this.a2 = a2;
		this.a3 = a3;
		this.id_b = id_b;
	}

	public int getA1() {
		return a1;
	}

	public void setA1(int a1) {
		this.a1 = a1;
	}

	public int getA2() {
		return a2;
	}

	public void setA2(int a2) {
		this.a2 = a2;
	}

	public int getA3() {
		return a3;
	}

	public void setA3(int a3) {
		this.a3 = a3;
	}

	public int getId_b() {
		return id_b;
	}

	public void setId_b(int id_b) {
		this.id_b = id_b;
	}

	@Override
	public String toString() {
		return "ADTO [a1=" + a1 + ", a2=" + a2 + ", a3=" + a3 + ", id_b=" + id_b + "]";
	}

}