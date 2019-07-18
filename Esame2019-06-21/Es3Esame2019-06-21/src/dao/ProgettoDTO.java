package dao;

import java.io.Serializable;
import java.util.Set;

public class ProgettoDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private int idProgetto;
	private String codiceProgetto;
	private String nomeProgetto;
	private int annoInizio;
	private int durata;
	private Set<WorkPackageDTO> packages;

	public ProgettoDTO() {
		// TODO Auto-generated constructor stub
	}

	public int getIdProgetto() {
		return idProgetto;
	}

	public void setIdProgetto(int idProgetto) {
		this.idProgetto = idProgetto;
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

	public Set<WorkPackageDTO> getPackages() {
		return packages;
	}

	public void setPackages(Set<WorkPackageDTO> packages) {
		this.packages = packages;
	}

	@Override
	public String toString() {
		return "Progetto [idProgetto=" + idProgetto + ", codiceProgetto=" + codiceProgetto + ", nomeProgetto="
				+ nomeProgetto + ", annoInizio=" + annoInizio + ", durata=" + durata + ", packages=" + packages + "]";
	}

}
