package o2m.bi.noproxy.dao;

import java.io.Serializable;
import java.sql.Date;

public class MaratonaDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private long id;
	private long codiceMaratona;
	private String titolo;
	private Date data;
	private String tipo;
	private CittaDTO citta;
	public MaratonaDTO(long id, long codiceMaratona, String titolo, Date data, String tipo, CittaDTO citta) {
		super();
		this.id = id;
		this.codiceMaratona = codiceMaratona;
		this.titolo = titolo;
		this.data = data;
		this.tipo = tipo;
		this.citta = citta;
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
		return "MaratonaDTO [id=" + id + ", citta=" + citta.hashCode() + ", hashCode()=" + hashCode()
				+ "]";
	}	
}