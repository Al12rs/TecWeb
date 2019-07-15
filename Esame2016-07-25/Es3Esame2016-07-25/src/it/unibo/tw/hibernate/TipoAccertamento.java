package it.unibo.tw.hibernate;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class TipoAccertamento implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int idTipoAccertamento;
	private int codiceTipoAccertamento;
	private String descrizione;
	private Set<Ospedale> ospedali = new HashSet<Ospedale>(0);
	private Set<Accertamento> accertamenti = new HashSet<Accertamento>(0);

	public TipoAccertamento() {
		// TODO Auto-generated constructor stub
	}

	public int getIdTipoAccertamento() {
		return idTipoAccertamento;
	}

	public void setIdTipoAccertamento(int idTipoAccertamento) {
		this.idTipoAccertamento = idTipoAccertamento;
	}

	public int getCodiceTipoAccertamento() {
		return codiceTipoAccertamento;
	}

	public void setCodiceTipoAccertamento(int codiceTipoAccertamento) {
		this.codiceTipoAccertamento = codiceTipoAccertamento;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public Set<Ospedale> getOspedali() {
		return ospedali;
	}

	public void setOspedali(Set<Ospedale> ospedali) {
		this.ospedali = ospedali;
	}

	public Set<Accertamento> getAccertamenti() {
		return accertamenti;
	}

	public void setAccertamenti(Set<Accertamento> accertamenti) {
		this.accertamenti = accertamenti;
	}

}
