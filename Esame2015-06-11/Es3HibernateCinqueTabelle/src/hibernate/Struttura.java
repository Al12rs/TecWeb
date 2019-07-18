package hibernate;

import java.io.Serializable;
import java.util.Set;

public class Struttura implements Serializable {

	private static final long serialVersionUID = 1L;
	private int idStruttura;
	private String codice;
	private String nome;
	private String indirizzo;

	private Set<TipoAccertamento> tipiAccertamento;

	public Struttura(int idStruttura, String codice, String nome, String indirizzo) {
		this.idStruttura = idStruttura;
		this.codice = codice;
		this.nome = nome;
		this.indirizzo = indirizzo;
	}

	public int getIdStruttura() {
		return idStruttura;
	}

	public void setIdStruttura(int idStruttura) {
		this.idStruttura = idStruttura;
	}

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public Set<TipoAccertamento> getTipiAccertamento() {
		return tipiAccertamento;
	}

	public void setTipiAccertamento(Set<TipoAccertamento> tipiAccertamento) {
		this.tipiAccertamento = tipiAccertamento;
	}

	@Override
	public String toString() {
		return "Struttura [idStruttura=" + idStruttura + ", codice=" + codice + ", nome=" + nome + ", indirizzo="
				+ indirizzo + ", tipiAccertamento=" + tipiAccertamento + "]";
	}

}
