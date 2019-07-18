package jdbc;

import java.io.Serializable;

public class Acquisto implements Serializable {

	private static final long serialVersionUID = 1L;
	private int idAcquisto;
	private String codiceAcquisto;
	private double importo;
	private String nomeAcquirente;
	private String cognomeAcquirente;

	public Acquisto() {
		// TODO Auto-generated constructor stub
	}

	public Acquisto(int idAcquisto, String codiceAcquisto, double importo, String nomeAcquirente,
			String cognomeAcquirente) {
		this.idAcquisto = idAcquisto;
		this.codiceAcquisto = codiceAcquisto;
		this.importo = importo;
		this.nomeAcquirente = nomeAcquirente;
		this.cognomeAcquirente = cognomeAcquirente;
	}

	public int getIdAcquisto() {
		return idAcquisto;
	}

	public void setIdAcquisto(int idAcquisto) {
		this.idAcquisto = idAcquisto;
	}

	public String getCodiceAcquisto() {
		return codiceAcquisto;
	}

	public void setCodiceAcquisto(String codiceAcquisto) {
		this.codiceAcquisto = codiceAcquisto;
	}

	public double getImporto() {
		return importo;
	}

	public void setImporto(double importo) {
		this.importo = importo;
	}

	public String getNomeAcquirente() {
		return nomeAcquirente;
	}

	public void setNomeAcquirente(String nomeAcquirente) {
		this.nomeAcquirente = nomeAcquirente;
	}

	public String getCognomeAcquirente() {
		return cognomeAcquirente;
	}

	public void setCognomeAcquirente(String cognomeAcquirente) {
		this.cognomeAcquirente = cognomeAcquirente;
	}

	@Override
	public String toString() {
		return "Acquisto [idAcquisto=" + idAcquisto + ", codiceAcquisto=" + codiceAcquisto + ", importo=" + importo
				+ ", nomeAcquirente=" + nomeAcquirente + ", cognomeAcquirente=" + cognomeAcquirente + "]";
	}
	
	

}
