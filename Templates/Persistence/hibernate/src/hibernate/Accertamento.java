package hibernate;

import java.io.Serializable;

public class Accertamento implements Serializable {

    private static final long serialVersionUID = 1L;
    
    int accertamentoID;
    int codiceAccertamento;
    String nome;
    String descrizione;
    TipoAccertamento tipoAccertamento;

    @Override
    public String toString() {
        return "Accertamento [accertamentoID=" + accertamentoID + ", codiceAccertamento=" + codiceAccertamento
                + ", descrizione=" + descrizione + ", nome=" + nome + ", tipoAccertamento=" + tipoAccertamento + "]";
    }

    public int getAccertamentoID() {
        return accertamentoID;
    }

    public void setAccertamentoID(int accertamentoID) {
        this.accertamentoID = accertamentoID;
    }

    public int getCodiceAccertamento() {
        return codiceAccertamento;
    }

    public void setCodiceAccertamento(int codiceAccertamento) {
        this.codiceAccertamento = codiceAccertamento;
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

    public TipoAccertamento getTipoAccertamento() {
        return tipoAccertamento;
    }

    public void setTipoAccertamento(TipoAccertamento tipoAccertamento) {
        this.tipoAccertamento = tipoAccertamento;
    }

    public Accertamento(int accertamentoID, int codiceAccertamento, String nome, String descrizione,
            TipoAccertamento tipoAccertamento) {
        this.accertamentoID = accertamentoID;
        this.codiceAccertamento = codiceAccertamento;
        this.nome = nome;
        this.descrizione = descrizione;
        this.tipoAccertamento = tipoAccertamento;
    }

    public Accertamento() {
    }
}