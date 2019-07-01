package esame.dao;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class PasseggeroDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private long id;
	private String codicePasseggero;
	private String nome;
	private String cognome;
	private String sesso;
	private String codicePassaporto;
	private Set<VoloDTO> voli;
	public PasseggeroDTO() {
		voli = new HashSet<>();
	}
	public PasseggeroDTO(long id, String codicePasseggero, String nome, String cognome, String sesso,
			String codicePassaporto) {
		this();
		this.id = id;
		this.codicePasseggero = codicePasseggero;
		this.nome = nome;
		this.cognome = cognome;
		this.sesso = sesso;
		this.codicePassaporto = codicePassaporto;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getCodicePasseggero() {
		return codicePasseggero;
	}
	public void setCodicePasseggero(String codicePasseggero) {
		this.codicePasseggero = codicePasseggero;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getSesso() {
		return sesso;
	}
	public void setSesso(String sesso) {
		this.sesso = sesso;
	}
	public String getCodicePassaporto() {
		return codicePassaporto;
	}
	public void setCodicePassaporto(String codicePassaporto) {
		this.codicePassaporto = codicePassaporto;
	}
	public Set<VoloDTO> getVoli() {
		return voli;
	}
	public void setVoli(Set<VoloDTO> voli) {
		this.voli = voli;
	}
	@Override
	public String toString() {
		return "PasseggeroDTO [id=" + id + ", codicePasseggero=" + codicePasseggero + ", nome=" + nome + ", cognome="
				+ cognome + ", sesso=" + sesso + ", codicePassaporto=" + codicePassaporto + ", voli=" + voli + "]";
	}	
}