package beans;

public class Personaggio {

	private String nome;
	private String descrizione;
	private Coordinate posizione;

	public Personaggio() {
		// TODO Auto-generated constructor stub
	}

	public Personaggio(String nome, String descrizione, Coordinate posizione) {
		this.nome = nome;
		this.descrizione = descrizione;
		this.posizione = posizione;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public Coordinate getPosizione() {
		return posizione;
	}

	public void setPosizione(Coordinate posizione) {
		this.posizione = posizione;
	}

}
