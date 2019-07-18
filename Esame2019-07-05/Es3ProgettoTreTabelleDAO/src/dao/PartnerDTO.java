package dao;

import java.io.Serializable;
import java.util.Set;

public class PartnerDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private long id;
	private String siglaPartner;
	private String nome;
	private Set<WorkPackageDTO> packages;

	@Override
	public String toString() {
		return "PartnerDTO [id=" + id + ", siglaPartner=" + siglaPartner + ", nome=" + nome + ", packages=" + packages
				+ "]";
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getSiglaPartner() {
		return siglaPartner;
	}

	public void setSiglaPartner(String siglaPartner) {
		this.siglaPartner = siglaPartner;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Set<WorkPackageDTO> getPackages() {
		return packages;
	}

	public void setPackages(Set<WorkPackageDTO> packages) {
		this.packages = packages;
	}

	public PartnerDTO(long id, String siglaPartner, String nome) {
		this.id = id;
		this.siglaPartner = siglaPartner;
		this.nome = nome;
	}

	public PartnerDTO() {
		// TODO Auto-generated constructor stub
	}

}