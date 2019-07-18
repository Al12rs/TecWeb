package hibernate;

import java.io.Serializable;
import java.util.Set;

public class TipoAccertamento implements Serializable {

	private static final long serialVersionUID = 1L;
	private int idTipoAccertamento;
	private String codice;
	private String descrizione;

	private Set<Accertamento> accertamenti;
	private Set<Struttura> strutture;

	public TipoAccertamento(int idTipoAccertamento, String codice, String descrizione) {
		this.idTipoAccertamento = idTipoAccertamento;
		this.codice = codice;
		this.descrizione = descrizione;
	}

	public int getIdTipoAccertamento() {
		return idTipoAccertamento;
	}

	public void setIdTipoAccertamento(int idTipoAccertamento) {
		this.idTipoAccertamento = idTipoAccertamento;
	}

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public Set<Accertamento> getAccertamenti() {
		return accertamenti;
	}

	public void setAccertamenti(Set<Accertamento> accertamenti) {
		this.accertamenti = accertamenti;
	}

	public Set<Struttura> getStrutture() {
		return strutture;
	}

	public void setStrutture(Set<Struttura> strutture) {
		this.strutture = strutture;
	}

	@Override
	public String toString() {
		return "TipoAccertamento [idTipoAccertamento=" + idTipoAccertamento + ", codice=" + codice + ", descrizione="
				+ descrizione + ", accertamenti=" + accertamenti + ", strutture=" + strutture + "]";
	}

}
