package dao;

import java.io.Serializable;

public class SquadraPallacanestroDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private int idSquadra;
	private String nome;
	private String torneo;
	private String allenatore;

	// non è necessario il set di giocatori per via della direzione indicata dalla
	// freccia nell'uml
	public SquadraPallacanestroDTO() {
		// TODO Auto-generated constructor stub
	}

	public SquadraPallacanestroDTO(int idSquadra, String nome, String torneo, String allenatore) {
		this.idSquadra = idSquadra;
		this.nome = nome;
		this.torneo = torneo;
		this.allenatore = allenatore;
	}

	public SquadraPallacanestroDTO(String nome, String torneo, String allenatore) {
		this.nome = nome;
		this.torneo = torneo;
		this.allenatore = allenatore;
	}

	public int getIdSquadra() {
		return idSquadra;
	}

	public void setIdSquadra(int idSquadra) {
		this.idSquadra = idSquadra;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTorneo() {
		return torneo;
	}

	public void setTorneo(String torneo) {
		this.torneo = torneo;
	}

	public String getAllenatore() {
		return allenatore;
	}

	public void setAllenatore(String allenatore) {
		this.allenatore = allenatore;
	}

	@Override
	public String toString() {
		return "SquadraPallacanestro [idSquadra=" + idSquadra + ", nome=" + nome + ", torneo=" + torneo
				+ ", allenatore=" + allenatore + "]";
	}

}
