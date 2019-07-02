
package it.unibo.tw.dao;

import java.io.Serializable;
import java.util.List;

public class IngredienteDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private int id;
    private String nomeIngrediente;
    private Float quantita;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeIngrediente() {
        return nomeIngrediente;
    }

    public void setNomeIngrediente(String nomeIngrediente) {
        this.nomeIngrediente = nomeIngrediente;
    }

    public Float getQuantita() {
        return quantita;
    }

    public void setQuantita(Float quantita) {
        this.quantita = quantita;
    }

    public IngredienteDTO(int id, String nomeIngrediente, Float quantita) {
        this.id = id;
        this.nomeIngrediente = nomeIngrediente;
        this.quantita = quantita;
    }

}
