package dao;

import java.io.Serializable;
import java.util.Set;

public class WorkPackageDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private int idPackage;
	private String nomeWP;
	private String titolo;
	private String descrizione;
	private ProgettoDTO progetto;
	private Set<PartnerDTO> partners;

	public WorkPackageDTO() {
		// TODO Auto-generated constructor stub
	}

	public int getIdPackage() {
		return idPackage;
	}

	public void setIdPackage(int idPackage) {
		this.idPackage = idPackage;
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

	public Set<PartnerDTO> getPartners() {
		return partners;
	}

	public void setPartners(Set<PartnerDTO> partners) {
		this.partners = partners;
	}

	@Override
	public String toString() {
		return "WorkPackage [idPackage=" + idPackage + ", nomeWP=" + nomeWP + ", titolo=" + titolo + ", descrizione="
				+ descrizione + ", progetto=" + progetto + ", partners=" + partners + "]";
	}

}
