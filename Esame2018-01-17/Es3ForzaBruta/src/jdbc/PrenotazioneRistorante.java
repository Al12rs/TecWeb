package jdbc;

import java.sql.Date;

public class PrenotazioneRistorante {

	private String cognome;
	private Date data;
	private int numeroPersone;
	private String cellulare;
	private int numeroTavolo;

	public PrenotazioneRistorante() {
		// TODO Auto-generated constructor stub
	}

	public PrenotazioneRistorante(String cognome, Date data, int numeroPersone, String cellulare, int numeroTavolo) {
		this.cognome = cognome;
		this.data = data;
		this.numeroPersone = numeroPersone;
		this.cellulare = cellulare;
		this.numeroTavolo = numeroTavolo;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public int getNumeroPersone() {
		return numeroPersone;
	}

	public void setNumeroPersone(int numeroPersone) {
		this.numeroPersone = numeroPersone;
	}

	public String getCellulare() {
		return cellulare;
	}

	public void setCellulare(String cellulare) {
		this.cellulare = cellulare;
	}

	public int getNumeroTavolo() {
		return numeroTavolo;
	}

	public void setNumeroTavolo(int numeroTavolo) {
		this.numeroTavolo = numeroTavolo;
	}

	@Override
	public String toString() {
		return "PrenotazioneRistorante [cognome=" + cognome + ", data=" + data + ", numeroPersone=" + numeroPersone
				+ ", cellulare=" + cellulare + ", numeroTavolo=" + numeroTavolo + "]";
	}

}
