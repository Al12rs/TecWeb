package hibernate;

import java.io.Serializable;
import java.util.Set;

public class Concorso implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int codiceConcorso;
	private String classeConcorso;
	private String descrizione;
	private Set<Candidato> candidati;
	private Set<Commissario> commissari;

	public Concorso(int codiceConcorso, String classeConcorso, String descrizione) {
		this.codiceConcorso = codiceConcorso;
		this.classeConcorso = classeConcorso;
		this.descrizione = descrizione;
	}

	public int getCodiceConcorso() {
		return codiceConcorso;
	}

	public void setCodiceConcorso(int codiceConcorso) {
		this.codiceConcorso = codiceConcorso;
	}

	public String getClasseConcorso() {
		return classeConcorso;
	}

	public void setClasseConcorso(String classeConcorso) {
		this.classeConcorso = classeConcorso;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public Set<Candidato> getCandidati() {
		return candidati;
	}

	public void setCandidati(Set<Candidato> candidati) {
		this.candidati = candidati;
	}

	public Set<Commissario> getCommissari() {
		return commissari;
	}

	public void setCommissari(Set<Commissario> commissari) {
		this.commissari = commissari;
	}

	@Override
	public String toString() {
		return "Concorso [codiceConcorso=" + codiceConcorso + ", classeConcorso=" + classeConcorso + ", descrizione="
				+ descrizione + "]";
	}

}
