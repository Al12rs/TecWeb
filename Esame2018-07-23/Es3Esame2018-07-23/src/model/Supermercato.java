package model;

import java.util.Set;

public class Supermercato {

	private int codiceSuper;
	private String nome;
	private int ratingGradimento;
	private Set<ProdottoOfferto> prodottiOfferti;
	
	public Supermercato() {
		// TODO Auto-generated constructor stub
	}
	
	

	public Supermercato(int codiceSuper, String nome, int ratingGradimento) {
		this.codiceSuper = codiceSuper;
		this.nome = nome;
		this.ratingGradimento = ratingGradimento;
	}



	public int getCodiceSuper() {
		return codiceSuper;
	}

	public void setCodiceSuper(int codiceSuper) {
		this.codiceSuper = codiceSuper;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getRatingGradimento() {
		return ratingGradimento;
	}

	public void setRatingGradimento(int ratingGradimento) {
		this.ratingGradimento = ratingGradimento;
	}

	public Set<ProdottoOfferto> getProdottiOfferti() {
		return prodottiOfferti;
	}

	public void setProdottiOfferti(Set<ProdottoOfferto> prodottiOfferti) {
		this.prodottiOfferti = prodottiOfferti;
	}

	@Override
	public String toString() {
		return "Supermercato [codiceSuper=" + codiceSuper + ", nome=" + nome + ", ratingGradimento=" + ratingGradimento
				+ "]";
	}

	

}
