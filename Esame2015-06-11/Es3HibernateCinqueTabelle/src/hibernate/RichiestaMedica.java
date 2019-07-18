package hibernate;

import java.io.Serializable;
import java.sql.Date;
import java.util.Set;

public class RichiestaMedica implements Serializable {

	private static final long serialVersionUID = 1L;
	private int idRichiestaMedica;
	private String codicePaziente;
	private Date data;
	private String nomeMedico;

	private Paziente paziente;
	private Set<Accertamento> accertamenti;

	public RichiestaMedica(int idRichiestaMedica, String codicePaziente, Date data, String nomeMedico,
			Paziente paziente) {
		this.idRichiestaMedica = idRichiestaMedica;
		this.codicePaziente = codicePaziente;
		this.data = data;
		this.nomeMedico = nomeMedico;
		this.paziente = paziente;
	}

	public int getIdRichiestaMedica() {
		return idRichiestaMedica;
	}

	public void setIdRichiestaMedica(int idRichiestaMedica) {
		this.idRichiestaMedica = idRichiestaMedica;
	}

	public String getCodicePaziente() {
		return codicePaziente;
	}

	public void setCodicePaziente(String codicePaziente) {
		this.codicePaziente = codicePaziente;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getNomeMedico() {
		return nomeMedico;
	}

	public void setNomeMedico(String nomeMedico) {
		this.nomeMedico = nomeMedico;
	}

	public Paziente getPaziente() {
		return paziente;
	}

	public void setPaziente(Paziente paziente) {
		this.paziente = paziente;
	}

	public Set<Accertamento> getAccertamenti() {
		return accertamenti;
	}

	public void setAccertamenti(Set<Accertamento> accertamenti) {
		this.accertamenti = accertamenti;
	}

	@Override
	public String toString() {
		return "RichiestaMedica [idRichiestaMedica=" + idRichiestaMedica + ", codicePaziente=" + codicePaziente
				+ ", data=" + data + ", nomeMedico=" + nomeMedico + ", paziente=" + paziente + ", accertamenti="
				+ accertamenti + "]";
	}

}
