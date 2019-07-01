package hibernate;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;



public class TipoAccertamento implements Serializable {

    private static final long serialVersionUID = 1L;

    int tipoAccertamentoID;
    int codiceTipoAccertamento;
    String descrizione;
    Set<Accertamento> accertamenti = new HashSet<Accertamento>();
    Set<Ospedale> ospedali = new HashSet<Ospedale>();

    @Override
    public String toString() {
        return "TipoAccertamento [CodiceTipoAccertamento=" + codiceTipoAccertamento + ", accertamenti=" + accertamenti
                + ", descrizione=" + descrizione + ", ospedali=" + ospedali + ", tipoAccertamentoID="
                + tipoAccertamentoID + "]";
    }

    public int getTipoAccertamentoID() {
        return tipoAccertamentoID;
    }

    public void setTipoAccertamentoID(int tipoAccertamentoID) {
        this.tipoAccertamentoID = tipoAccertamentoID;
    }

    public int getCodiceTipoAccertamento() {
        return codiceTipoAccertamento;
    }

    public void setCodiceTipoAccertamento(int codiceTipoAccertamento) {
        this.codiceTipoAccertamento = codiceTipoAccertamento;
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

    public Set<Ospedale> getOspedali() {
        return ospedali;
    }

    public void setOspedali(Set<Ospedale> ospedali) {
        this.ospedali = ospedali;
    }

    public TipoAccertamento(int tipoAccertamentoID, int codiceTipoAccertamento, String descrizione) {
        this.tipoAccertamentoID = tipoAccertamentoID;
        this.codiceTipoAccertamento = codiceTipoAccertamento;
        this.descrizione = descrizione;
    }

    public TipoAccertamento() {
    }
    
}