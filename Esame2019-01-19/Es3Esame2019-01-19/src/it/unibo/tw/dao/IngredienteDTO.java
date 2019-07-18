package it.unibo.tw.dao;

import java.io.Serializable;

public class IngredienteDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int id;
	private String nomeIngrediente;
	private int quantita;

	public IngredienteDTO() {
		// TODO Auto-generated constructor stub
	}

	public IngredienteDTO(int id, String nomeIngrediente, int quantita) {
		this.id = id;
		this.nomeIngrediente = nomeIngrediente;
		this.quantita = quantita;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNomeIngrediente() {
		return nomeIngrediente;
	}

	public void setNomeIngrediente(String nomeIngrediente) {
		this.nomeIngrediente = nomeIngrediente;
	}

	public int getQuantita() {
		return quantita;
	}

	public void setQuantita(int quantita) {
		this.quantita = quantita;
	}

	@Override
	public String toString() {
		return "IngredienteDTO [id=" + id + ", nomeIngrediente=" + nomeIngrediente + ", quantita=" + quantita + "]";
	}

}
