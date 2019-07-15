package hibernate;

import java.io.Serializable;

public class Commissario implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int matricola;
	private String nome;
	private String cognome;
	private Concorso concorso;

	public Commissario(int matricola, String nome, String cognome) {
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

	public Concorso getConcorso() {
		return concorso;
	}

	public void setConcorso(Concorso concorso) {
		this.concorso = concorso;
	}

	@Override
	public String toString() {
		return "Commissario [matricola=" + matricola + ", nome=" + nome + ", cognome=" + cognome + ", concorso="
				+ concorso + "]";
	}

}
