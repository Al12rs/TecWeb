package esame.dao;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class CittaDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private long id;
	private String nome;
	private String nazione;
	private Set<MaratonaDTO> maratone;
	public CittaDTO(long id, String nome, String nazione) {
		super();
		this.id = id;
		this.nome = nome;
		this.nazione = nazione;
		this.maratone = new HashSet<>();
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getNazione() {
		return nazione;
	}
	public void setNazione(String nazione) {
		this.nazione = nazione;
	}
	public Set<MaratonaDTO> getMaratone() {
		return maratone;
	}
	public void setMaratone(Set<MaratonaDTO> maratone) {
		this.maratone = maratone;
	}
	public CittaDTO() {
	}
	@Override
	public String toString() {
		return "CittaDTO [id=" + id + ", maratone=" + maratone + ", hashCode()=" + hashCode() + "]";
	}
}