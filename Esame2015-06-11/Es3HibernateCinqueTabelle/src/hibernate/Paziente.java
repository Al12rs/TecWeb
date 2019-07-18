package hibernate;

import java.io.Serializable;
import java.util.Set;

public class Paziente implements Serializable {

	private static final long serialVersionUID = 1L;
	private int idPaziente;
	private String codiceFiscale;
	private String nome;
	private String cognome;
	private String sesso;

	private Set<RichiestaMedica> richieste;

	public Paziente(int idPaziente, String codiceFiscale, String nome, String cognome, String sesso) {
		this.idPaziente = idPaziente;
		this.codiceFiscale = codiceFiscale;
		this.nome = nome;
		this.cognome = cognome;
		this.sesso = sesso;
	}

	public int getIdPaziente() {
		return idPaziente;
	}

	public void setIdPaziente(int idPaziente) {
		this.idPaziente = idPaziente;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
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

	public String getSesso() {
		return sesso;
	}

	public void setSesso(String sesso) {
		this.sesso = sesso;
	}

	public Set<RichiestaMedica> getRichieste() {
		return richieste;
	}

	public void setRichieste(Set<RichiestaMedica> richieste) {
		this.richieste = richieste;
	}

	@Override
	public String toString() {
		return "Paziente [idPaziente=" + idPaziente + ", codiceFiscale=" + codiceFiscale + ", nome=" + nome
				+ ", cognome=" + cognome + ", sesso=" + sesso + ", richieste=" + richieste + "]";
	}

}
