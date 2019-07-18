package it.unibo.tw.dao;

import java.io.Serializable;
import java.util.Set;

public class RicettaDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int id;
	private String nomeRicetta;
	private int tempoPreparazione;
	private int livelloDifficolta;
	private int calorie;
	private Set<IngredienteDTO> ingredienti;

	public RicettaDTO() {
		// TODO Auto-generated constructor stub
	}

	public RicettaDTO(int id, String nomeRicetta, int tempoPreparazione, int livelloDifficolta, int calorie,
			Set<IngredienteDTO> ingredienti) {
		this.id = id;
		this.nomeRicetta = nomeRicetta;
		this.tempoPreparazione = tempoPreparazione;
		this.livelloDifficolta = livelloDifficolta;
		this.calorie = calorie;
		this.ingredienti = ingredienti;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNomeRicetta() {
		return nomeRicetta;
	}

	public void setNomeRicetta(String nomeRicetta) {
		this.nomeRicetta = nomeRicetta;
	}

	public int getTempoPreparazione() {
		return tempoPreparazione;
	}

	public void setTempoPreparazione(int tempoPreparazione) {
		this.tempoPreparazione = tempoPreparazione;
	}

	public int getLivelloDifficolta() {
		return livelloDifficolta;
	}

	public void setLivelloDifficolta(int livelloDifficolta) {
		this.livelloDifficolta = livelloDifficolta;
	}

	public int getCalorie() {
		return calorie;
	}

	public void setCalorie(int calorie) {
		this.calorie = calorie;
	}

	public Set<IngredienteDTO> getIngredienti() {
		return ingredienti;
	}

	public void setIngredienti(Set<IngredienteDTO> ingredienti) {
		this.ingredienti = ingredienti;
	}

	@Override
	public String toString() {
		return "RicettaDTO [id=" + id + ", nomeRicetta=" + nomeRicetta + ", tempoPreparazione=" + tempoPreparazione
				+ ", livelloDifficolta=" + livelloDifficolta + ", calorie=" + calorie + ", ingredienti=" + ingredienti
				+ "]";
	}

}
