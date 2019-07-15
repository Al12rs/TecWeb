package hibernate;

import java.io.Serializable;
import java.sql.Date;
import java.util.Set;

public class MaterialeFisico implements Serializable {

	private static final long serialVersionUID = 1L;
	private int idMaterialeFisico;
	private String codiceMateriale;
	private String nome;
	private String descrizione;
	private Date dataCreazione;
	private ArchivioFisico archivio;
	private Set<OggettoDigitale> oggetti;

	public MaterialeFisico() {
		// TODO Auto-generated constructor stub
	}

	public MaterialeFisico(int idMaterialeFisico, String codiceMateriale, String nome, String descrizione,
			Date dataCreazione) {
		this.idMaterialeFisico = idMaterialeFisico;
		this.codiceMateriale = codiceMateriale;
		this.nome = nome;
		this.descrizione = descrizione;
		this.dataCreazione = dataCreazione;
	}

	public int getIdMaterialeFisico() {
		return idMaterialeFisico;
	}

	public void setIdMaterialeFisico(int idMaterialeFisico) {
		this.idMaterialeFisico = idMaterialeFisico;
	}

	public String getCodiceMateriale() {
		return codiceMateriale;
	}

	public void setCodiceMateriale(String codiceMateriale) {
		this.codiceMateriale = codiceMateriale;
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

	public ArchivioFisico getArchivio() {
		return archivio;
	}

	public void setArchivio(ArchivioFisico archivio) {
		this.archivio = archivio;
	}

	public Set<OggettoDigitale> getOggetti() {
		return oggetti;
	}

	public void setOggetti(Set<OggettoDigitale> oggetti) {
		this.oggetti = oggetti;
	}

	@Override
	public String toString() {
		return "MaterialeFisico [idMaterialeFisico=" + idMaterialeFisico + ", codiceMateriale=" + codiceMateriale
				+ ", nome=" + nome + ", descrizione=" + descrizione + ", dataCreazione=" + dataCreazione + ", archivio="
				+ archivio + ", oggetti=" + oggetti + "]";
	}

}
