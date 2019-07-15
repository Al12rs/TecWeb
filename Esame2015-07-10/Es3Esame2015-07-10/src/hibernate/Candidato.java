package hibernate;

import java.io.Serializable;
import java.util.Set;

public class Candidato implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int matricola;
	private String nome;
	private String cognome;
	private Set<Concorso> concorsi;

	public Candidato(int matricola, String nome, String cognome) {
		this.matricola = matricola;
		this.nome = nome;
		this.cognome = cognome;
	}

	public int getMatricola() {
		return matricola;
	}

	public void setMatricola(int matricola) {
		this.matricola = matricola;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public Set<Concorso> getConcorsi() {
		return concorsi;
	}

	public void setConcorsi(Set<Concorso> concorsi) {
		this.concorsi = concorsi;
	}

	@Override
	public String toString() {
		return "Candidato [matricola=" + matricola + ", nome=" + nome + ", cognome=" + cognome + "]";
	}
	
	
}
