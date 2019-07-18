package dao;

import java.io.Serializable;

public class WorkPackageDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private long id;
	private String nomeWP;
	private String titolo;
	private String descrizione;

	private ProgettoDTO progetto;

	@Override
	public String toString() {
		return "WorkPackageDTO [id=" + id + ", nomeWP=" + nomeWP + ", titolo=" + titolo + ", descrizione=" + descrizione
				+ ", progetto=" + progetto + "]";
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNomeWP() {
		return nomeWP;
	}

	public void setNomeWP(String nomeWP) {
		this.nomeWP = nomeWP;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public ProgettoDTO getProgetto() {
		return progetto;
	}

	public void setProgetto(ProgettoDTO progetto) {
		this.progetto = progetto;
	}

	public WorkPackageDTO(long id, String nomeWP, String titolo, String descrizione, ProgettoDTO progetto) {
		this.id = id;
		this.nomeWP = nomeWP;
		this.titolo = titolo;
		this.descrizione = descrizione;
		this.progetto = progetto;
	}

	public WorkPackageDTO() {
		// TODO Auto-generated constructor stub
	}

}