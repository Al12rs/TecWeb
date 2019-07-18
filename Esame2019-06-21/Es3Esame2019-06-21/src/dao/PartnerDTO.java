package dao;

import java.io.Serializable;
import java.util.Set;

public class PartnerDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private int idPartner;
	private String siglaPartner;
	private String nome;
	private Set<WorkPackageDTO> packages;

	public PartnerDTO() {
		// TODO Auto-generated constructor stub
	}

	public int getIdPartner() {
		return idPartner;
	}

	public void setIdPartner(int idPartner) {
		this.idPartner = idPartner;
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

	@Override
	public String toString() {
		return "Partner [idPartner=" + idPartner + ", siglaPartner=" + siglaPartner + ", nome=" + nome + ", packages="
				+ packages + "]";
	}

}
