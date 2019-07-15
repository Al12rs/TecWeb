package dao;

import java.io.Serializable;
import java.util.Set;

public class GiocatoreDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private int idGiocatore;
	private String codiceFiscale;
	private String cognome;
	private String nome;
	private int eta;
	private Set<SquadraPallacanestroDTO> squadre;

	public GiocatoreDTO() {
		// TODO Auto-generated constructor stub
	}

	public GiocatoreDTO(int idGiocatore, String codiceFiscale, String cognome, String nome, int eta) {
		this.idGiocatore = idGiocatore;
		this.codiceFiscale = codiceFiscale;
		this.cognome = cognome;
		this.nome = nome;
		this.eta = eta;
	}

	public GiocatoreDTO(String codiceFiscale, String cognome, String nome, int eta) {
		this.codiceFiscale = codiceFiscale;
		this.cognome = cognome;
		this.nome = nome;
		this.eta = eta;
	}

	public int getIdGiocatore() {
		return idGiocatore;
	}

	public void setIdGiocatore(int idGiocatore) {
		this.idGiocatore = idGiocatore;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getEta() {
		return eta;
	}

	public void setEta(int eta) {
		this.eta = eta;
	}

	public Set<SquadraPallacanestroDTO> getSquadre() {
		return squadre;
	}

	public void setSquadre(Set<SquadraPallacanestroDTO> squadre) {
		this.squadre = squadre;
	}

	@Override
	public String toString() {
		return "Giocatore [idGiocatore=" + idGiocatore + ", codiceFiscale=" + codiceFiscale + ", cognome=" + cognome
				+ ", nome=" + nome + ", eta=" + eta + ", squadre=" + squadre + "]";
	}

}
