package it.unibo.tw.hibernate;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Ospedale implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int idOspedale;
	private int codiceOspedale;
	private String nome;
	private String citta;
	private String indirizzo;
	private Set<TipoAccertamento> tipiAccertamento = new HashSet<TipoAccertamento>(0);

	public Ospedale() {
		// TODO Auto-generated constructor stub
	}

	public int getIdOspedale() {
		return idOspedale;
	}

	public void setIdOspedale(int idOspedale) {
		this.idOspedale = idOspedale;
	}

	public int getCodiceOspedale() {
		return codiceOspedale;
	}

	public void setCodiceOspedale(int codiceOspedale) {
		this.codiceOspedale = codiceOspedale;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCitta() {
		return citta;
	}

	public void setCitta(String citta) {
		this.citta = citta;
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

}
