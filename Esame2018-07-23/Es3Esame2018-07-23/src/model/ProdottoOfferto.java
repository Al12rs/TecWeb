package model;

public class ProdottoOfferto {

	private int codiceProdotto;
	private String descrizione;
	private String marca;
	private float prezzo;
	private int codiceSupermercato;

	public ProdottoOfferto() {
		// TODO Auto-generated constructor stub
	}

	public int getCodiceProdotto() {
		return codiceProdotto;
	}

	public void setCodiceProdotto(int codiceProdotto) {
		this.codiceProdotto = codiceProdotto;
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

	public float getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(float prezzo) {
		this.prezzo = prezzo;
	}

	public int getCodiceSupermercato() {
		return codiceSupermercato;
	}

	public void setCodiceSupermercato(int codiceSupermercato) {
		this.codiceSupermercato = codiceSupermercato;
	}

	@Override
	public String toString() {
		return "ProdottiOfferti [codiceProdotto=" + codiceProdotto + ", descrizione=" + descrizione + ", marca=" + marca
				+ ", prezzo=" + prezzo + ", codiceSupermercato=" + codiceSupermercato + "]";
	}

}
