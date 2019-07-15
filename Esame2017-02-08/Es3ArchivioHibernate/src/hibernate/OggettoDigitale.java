package hibernate;

import java.io.Serializable;
import java.sql.Date;

public class OggettoDigitale implements Serializable {

	private static final long serialVersionUID = 1L;
	private int idOggettoDigitale;
	private String codiceOggetto;
	private String nome;
	private String formato;
	private Date dataDigitalizzazione;
	private MaterialeFisico materiale;

	public OggettoDigitale() {
		// TODO Auto-generated constructor stub
	}

	public OggettoDigitale(int idOggettoDigitale, String codiceOggetto, String nome, String formato,
			Date dataDigitalizzazione) {
		this.idOggettoDigitale = idOggettoDigitale;
		this.codiceOggetto = codiceOggetto;
		this.nome = nome;
		this.formato = formato;
		this.dataDigitalizzazione = dataDigitalizzazione;
	}

	public int getIdOggettoDigitale() {
		return idOggettoDigitale;
	}

	public void setIdOggettoDigitale(int idOggettoDigitale) {
		this.idOggettoDigitale = idOggettoDigitale;
	}

	public String getCodiceOggetto() {
		return codiceOggetto;
	}

	public void setCodiceOggetto(String codiceOggetto) {
		this.codiceOggetto = codiceOggetto;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getFormato() {
		return formato;
	}

	public void setFormato(String formato) {
		this.formato = formato;
	}

	public Date getDataDigitalizzazione() {
		return dataDigitalizzazione;
	}

	public void setDataDigitalizzazione(Date dataDigitalizzazione) {
		this.dataDigitalizzazione = dataDigitalizzazione;
	}

	public MaterialeFisico getMateriale() {
		return materiale;
	}

	public void setMateriale(MaterialeFisico materiale) {
		this.materiale = materiale;
	}

	@Override
	public String toString() {
		return "OggettoDigitale [idOggettoDigitale=" + idOggettoDigitale + ", codiceOggetto=" + codiceOggetto
				+ ", nome=" + nome + ", formato=" + formato + ", dataDigitalizzazione=" + dataDigitalizzazione
				+ ", materiale=" + materiale + "]";
	}

}
