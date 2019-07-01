package esame.jdbc;

import java.io.Serializable;

public class ProdottiOfferti implements Serializable{
	private static final long serialVersionUID = 1L;

	private long CodiceProdotto;
	private String descrizione;
	private String marca;
	private Float prezzo;
	private Supermercato supermercato;

	@Override
	public String toString() {
		return "ProdottiOfferti [CodiceProdotto=" + CodiceProdotto + ", descrizione=" + descrizione + ", marca=" + marca
				+ ", prezzo=" + prezzo + ", supermercato=" + supermercato + "]";
	}

	public long getCodiceProdotto() {
		return CodiceProdotto;
	}

	public void setCodiceProdotto(long codiceProdotto) {
		CodiceProdotto = codiceProdotto;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public Float getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(Float prezzo) {
		this.prezzo = prezzo;
	}

	public Supermercato getSupermercato() {
		return supermercato;
	}

	public void setSupermercato(Supermercato supermercato) {
		this.supermercato = supermercato;
	}

	public ProdottiOfferti(long codiceProdotto, String descrizione, String marca, Float prezzo,
			Supermercato supermercato) {
		CodiceProdotto = codiceProdotto;
		this.descrizione = descrizione;
		this.marca = marca;
		this.prezzo = prezzo;
		this.supermercato = supermercato;
	}

	public ProdottiOfferti() {
	}
}