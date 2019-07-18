package jdbc;

import java.util.Set;

public class Tavolo {

	private int numeroTavolo;
	private int capienza;
	private Set<PrenotazioneRistorante> prenotazioni;

	public Tavolo() {
		// TODO Auto-generated constructor stub
	}

	public Tavolo(int numeroTavolo, int capienza) {
		this.numeroTavolo = numeroTavolo;
		this.capienza = capienza;
	}

	public int getNumeroTavolo() {
		return numeroTavolo;
	}

	public void setNumeroTavolo(int numeroTavolo) {
		this.numeroTavolo = numeroTavolo;
	}

	public int getCapienza() {
		return capienza;
	}

	public void setCapienza(int capienza) {
		this.capienza = capienza;
	}

	public Set<PrenotazioneRistorante> getPrenotazioni() {
		return prenotazioni;
	}

	public void setPrenotazioni(Set<PrenotazioneRistorante> prenotazioni) {
		this.prenotazioni = prenotazioni;
	}

	@Override
	public String toString() {
		return "Tavolo [numeroTavolo=" + numeroTavolo + ", capienza=" + capienza + ", prenotazioni=" + prenotazioni
				+ "]";
	}

}
