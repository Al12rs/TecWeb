package it.unibo.tw.hibernate;

import java.io.Serializable;

public class TipoAccertamentoOspedaleMapping implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int idTipoAccertamento;
	private int idOspedale;

	public TipoAccertamentoOspedaleMapping() {
		// TODO Auto-generated constructor stub
	}

	public int getIdTipoAccertamento() {
		return idTipoAccertamento;
	}

	public void setIdTipoAccertamento(int idTipoAccertamento) {
		this.idTipoAccertamento = idTipoAccertamento;
	}

	public int getIdOspedale() {
		return idOspedale;
	}

	public void setIdOspedale(int idOspedale) {
		this.idOspedale = idOspedale;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idOspedale;
		result = prime * result + idTipoAccertamento;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TipoAccertamentoOspedaleMapping other = (TipoAccertamentoOspedaleMapping) obj;
		if (idOspedale != other.idOspedale)
			return false;
		if (idTipoAccertamento != other.idTipoAccertamento)
			return false;
		return true;
	}

}
