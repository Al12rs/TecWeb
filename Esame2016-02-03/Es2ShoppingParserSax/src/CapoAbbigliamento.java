
public class CapoAbbigliamento {

	private String fotografia;
	private String descrizione;
	private String prezzo;

	public CapoAbbigliamento(String fotografia, String descrizione, String prezzo) {
		this.fotografia = fotografia;
		this.descrizione = descrizione;
		this.prezzo = prezzo;
	}

	public String getFotografia() {
		return fotografia;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public String getPrezzo() {
		return prezzo;
	}

	@Override
	public String toString() {
		return "CapoAbbigliamento [fotografia=" + fotografia + ", descrizione=" + descrizione + ", prezzo=" + prezzo
				+ "]";
	}

}
