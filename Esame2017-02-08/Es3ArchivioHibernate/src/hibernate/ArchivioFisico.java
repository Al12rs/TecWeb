package hibernate;

import java.io.Serializable;
import java.sql.Date;
import java.util.Set;

public class ArchivioFisico implements Serializable {

	private static final long serialVersionUID = 1L;
	private int idArchivioFisico;
	private String codiceArchivio;
	private String nome;
	private String descrizione;
	private Date dataCreazione;
	private Set<MaterialeFisico> materiali;

	public ArchivioFisico() {
		// TODO Auto-generated constructor stub
	}

	public ArchivioFisico(int idArchivioFisico, String codiceArchivio, String nome, String descrizione,
			Date dataCreazione) {
		this.idArchivioFisico = idArchivioFisico;
		this.codiceArchivio = codiceArchivio;
		this.nome = nome;
		this.descrizione = descrizione;
		this.dataCreazione = dataCreazione;
	}

	public int getIdArchivioFisico() {
		return idArchivioFisico;
	}

	public void setIdArchivioFisico(int idArchivioFisico) {
		this.idArchivioFisico = idArchivioFisico;
	}

	public String getCodiceArchivio() {
		return codiceArchivio;
	}

	public void setCodiceArchivio(String codiceArchivio) {
		this.codiceArchivio = codiceArchivio;
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

	public Date getDataCreazione() {
		return dataCreazione;
	}

	public void setDataCreazione(Date dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

	public Set<MaterialeFisico> getMateriali() {
		return materiali;
	}

	public void setMateriali(Set<MaterialeFisico> materiali) {
		this.materiali = materiali;
	}

	@Override
	public String toString() {
		return "ArchivioFisico [idArchivioFisico=" + idArchivioFisico + ", codiceArchivio=" + codiceArchivio + ", nome="
				+ nome + ", descrizione=" + descrizione + ", dataCreazione=" + dataCreazione + ", materiali="
				+ materiali + "]";
	}

}
