package o2m.bi.proxy.dao;

import java.io.Serializable;
import java.sql.Date;

public class MaratonaDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private long id;
	private long codiceMaratona;
	private String titolo;
	private Date data;
	private String tipo;
	private long id_citta;
	private CittaDTO citta;
	public MaratonaDTO(long id, long codiceMaratona, String titolo, Date data, String tipo, long id_citta) {
		super();
		this.id = id;
		this.codiceMaratona = codiceMaratona;
		this.titolo = titolo;
		this.data = data;
		this.tipo = tipo;
		this.id_citta = id_citta;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getCodiceMaratona() {
		return codiceMaratona;
	}
	public void setCodiceMaratona(long codiceMaratona) {
		this.codiceMaratona = codiceMaratona;
	}
	public String getTitolo() {
		return titolo;
	}
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public long getId_citta() {
		return id_citta;
	}
	public void setId_citta(long id_citta) {
		this.id_citta = id_citta;
	}
	public CittaDTO getCitta() {
		return citta;
	}
	public void setCitta(CittaDTO citta) {
		this.citta = citta;
	}
	public MaratonaDTO() {
	}
	@Override
	public String toString() {
		return "MaratonaDTO [id=" + id + ", id_citta=" + id_citta + ", citta=" + citta.hashCode() + ", hashCode()=" + hashCode()
				+ "]";
	}	
}