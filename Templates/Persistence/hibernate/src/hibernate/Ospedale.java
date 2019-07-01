package hibernate;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Ospedale implements Serializable {

    private static final long serialVersionUID = 1L;

    int ospedaleID;
    int codiceOspedale;
    String nome;
    String citta;
    String indirizzo;
    Set<TipoAccertamento> tipiAccertamento = new HashSet<TipoAccertamento>();
    

    @Override
    public String toString() {
        return "Ospedale [CodiceOspedale=" + codiceOspedale + ", citta=" + citta + ", indirizzo=" + indirizzo
                + ", nome=" + nome + ", ospedaleID=" + ospedaleID + ", tipiAccertamento=" + tipiAccertamento + "]";
    }

    public int getOspedaleID() {
        return ospedaleID;
    }

    public void setOspedaleID(int ospedaleID) {
        this.ospedaleID = ospedaleID;
    }

    public int getCodiceOspedale() {
        return codiceOspedale;
    }

    public void setCodiceOspedale(int codiceOspedale) {
        this.codiceOspedale = codiceOspedale;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCitta() {
        return citta;
    }

    public void setCitta(String citta) {
        this.citta = citta;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public Set<TipoAccertamento> getTipiAccertamento() {
        return tipiAccertamento;
    }

    public void setTipiAccertamento(Set<TipoAccertamento> tipiAccertamento) {
        this.tipiAccertamento = tipiAccertamento;
    }

    public Ospedale(int ospedaleID, int codiceOspedale, String nome, String citta, String indirizzo) {
        this.ospedaleID = ospedaleID;
        this.codiceOspedale = codiceOspedale;
        this.nome = nome;
        this.citta = citta;
        this.indirizzo = indirizzo;
    }

    public Ospedale() {
    }
    
}