package it.unibo.tw.hibernate;

import java.io.Serializable;

public class Accertamento implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int idAccertamento;
	private int codiceAccertamento;
	private String nome;
	private String descrizione;
	private TipoAccertamento tipoAccertamento;

	public Accertamento() {

	}

	public int getIdAccertamento() {
		return idAccertamento;
	}

	public void setIdAccertamento(int idAccertamento) {
		this.idAccertamento = idAccertamento;
	}

	public int getCodiceAccertamento() {
		return codiceAccertamento;
	}

	public void setCodiceAccertamento(int codiceAccertamento) {
		this.codiceAccertamento = codiceAccertamento;
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

	public TipoAccertamento getTipoAccertamento() {
		return tipoAccertamento;
	}

	public void setTipoAccertamento(TipoAccertamento tipoAccertamento) {
		this.tipoAccertamento = tipoAccertamento;
	}

}
