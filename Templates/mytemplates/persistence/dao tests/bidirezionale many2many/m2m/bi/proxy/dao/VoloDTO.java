package m2m.bi.proxy.dao;

import java.io.Serializable;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

public class VoloDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private long id;
	private String codVolo;
	private String compagniaAerea;
	private String localitaDestinazione;
	private Date dataPartenza;
	private int orarioPartenza;
	private Set<PasseggeroDTO> passeggeri;
	public VoloDTO() {
	this.passeggeri = new HashSet<>();
	}
	public VoloDTO(long id, String codVolo, String compagniaAerea, String localitaDestinazione, Date dataPartenza,
			int orarioPartenza) {
		this.id = id;
		this.codVolo = codVolo;
		this.compagniaAerea = compagniaAerea;
		this.localitaDestinazione = localitaDestinazione;
		this.dataPartenza = dataPartenza;
		this.orarioPartenza = orarioPartenza;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getCodVolo() {
		return codVolo;
	}
	public void setCodVolo(String codVolo) {
		this.codVolo = codVolo;
	}
	public String getCompagniaAerea() {
		return compagniaAerea;
	}
	public void setCompagniaAerea(String compagniaAerea) {
		this.compagniaAerea = compagniaAerea;
	}
	public String getLocalitaDestinazione() {
		return localitaDestinazione;
	}
	public void setLocalitaDestinazione(String localitaDestinazione) {
		this.localitaDestinazione = localitaDestinazione;
	}
	public Date getDataPartenza() {
		return dataPartenza;
	}
	public void setDataPartenza(Date dataPartenza) {
		this.dataPartenza = dataPartenza;
	}
	public int getOrarioPartenza() {
		return orarioPartenza;
	}
	public void setOrarioPartenza(int orarioPartenza) {
		this.orarioPartenza = orarioPartenza;
	}
	public Set<PasseggeroDTO> getPasseggeri() {
		return passeggeri;
	}
	public void setPasseggeri(Set<PasseggeroDTO> passeggeri) {
		this.passeggeri = passeggeri;
	}
	@Override
	public String toString() {
		return "Db2VoloProxy [id=" + id + ", codVolo=" + codVolo + ", compagniaAerea=" + compagniaAerea
				+ ", localitaDestinazione=" + localitaDestinazione + ", dataPartenza=" + dataPartenza
				+ ", orarioPartenza=" + orarioPartenza + ", passeggeri=" + passeggeri + "]";
	}	
}