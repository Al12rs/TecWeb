package hibernate;

import java.io.Serializable;

public class Accertamento implements Serializable {

	private static final long serialVersionUID = 1L;
	private int idAccertamento;
	private String codice;
	private String esito;

	private RichiestaMedica richiesta;
	private TipoAccertamento tipo;

	public Accertamento(int idAccertamento, String codice, String esito, RichiestaMedica richiesta) {
		this.idAccertamento = idAccertamento;
		this.codice = codice;
		this.esito = esito;
		this.richiesta = richiesta;
	}

	public int getIdAccertamento() {
		return idAccertamento;
	}

	public void setIdAccertamento(int idAccertamento) {
		this.idAccertamento = idAccertamento;
	}

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public String getEsito() {
		return esito;
	}

	public void setEsito(String esito) {
		this.esito = esito;
	}

	public RichiestaMedica getRichiesta() {
		return richiesta;
	}

	public void setRichiesta(RichiestaMedica richiesta) {
		this.richiesta = richiesta;
	}

	public TipoAccertamento getTipo() {
		return tipo;
	}

	public void setTipo(TipoAccertamento tipo) {
		this.tipo = tipo;
	}

	@Override
	public String toString() {
		return "Accertamento [idAccertamento=" + idAccertamento + ", codice=" + codice + ", esito=" + esito
				+ ", richiesta=" + richiesta + ", tipo=" + tipo + "]";
	}

}
