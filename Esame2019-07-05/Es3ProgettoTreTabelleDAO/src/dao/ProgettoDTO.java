package dao;

import java.io.Serializable;

public class ProgettoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private long id;
	private String codiceProgetto;
	private String nomeProgetto;
	private int annoInizio;
	private int durata;

	@Override
	public String toString() {
		return "ProgettoDTO [id=" + id + ", codiceProgetto=" + codiceProgetto + ", nomeProgetto=" + nomeProgetto
				+ ", annoInizio=" + annoInizio + ", durata=" + durata + "]";
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCodiceProgetto() {
		return codiceProgetto;
	}

	public void setCodiceProgetto(String codiceProgetto) {
		this.codiceProgetto = codiceProgetto;
	}

	public String getNomeProgetto() {
		return nomeProgetto;
	}

	public void setNomeProgetto(String nomeProgetto) {
		this.nomeProgetto = nomeProgetto;
	}

	public int getAnnoInizio() {
		return annoInizio;
	}

	public void setAnnoInizio(int annoInizio) {
		this.annoInizio = annoInizio;
	}

	public int getDurata() {
		return durata;
	}

	public void setDurata(int durata) {
		this.durata = durata;
	}

	public ProgettoDTO(long id, String codiceProgetto, String nomeProgetto, int annoInizio, int durata) {
		this.id = id;
		this.codiceProgetto = codiceProgetto;
		this.nomeProgetto = nomeProgetto;
		this.annoInizio = annoInizio;
		this.durata = durata;
	}

	public ProgettoDTO() {
		// TODO Auto-generated constructor stub
	}

}