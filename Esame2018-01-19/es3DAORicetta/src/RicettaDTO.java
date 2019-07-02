package it.unibo.tw.dao;

import java.io.Serializable;
import java.util.List;

public class RicettaDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private int id;
    private String nomeRicetta;
    private Float tempoPreparazione;
    private int livelloDifficolta;
    private Float calorie;
    private List<IngredienteDTO> ingredienti;

    @Override
    public String toString() {
        return "RicettaDTO [calorie=" + calorie + ", id=" + id + ", livelloDifficolta=" + livelloDifficolta
                + ", nomeRicetta=" + nomeRicetta + ", tempoPreparazione=" + tempoPreparazione + "]";
    }

    public RicettaDTO(int id, String nomeRicetta, Float tempoPreparazione, int livelloDifficolta, Float calorie) {
        this.id = id;
        this.nomeRicetta = nomeRicetta;
        this.tempoPreparazione = tempoPreparazione;
        this.livelloDifficolta = livelloDifficolta;
        this.calorie = calorie;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeRicetta() {
        return nomeRicetta;
    }

    public void setNomeRicetta(String nomeRicetta) {
        this.nomeRicetta = nomeRicetta;
    }

    public int getLivelloDifficolta() {
        return livelloDifficolta;
    }

    public void setLivelloDifficolta(int livelloDifficolta) {
        this.livelloDifficolta = livelloDifficolta;
    }

    public Float getCalorie() {
        return calorie;
    }

    public void setCalorie(Float calorie) {
        this.calorie = calorie;
    }

    public List<IngredienteDTO> getIngredienti() {
        return ingredienti;
    }

    public void setIngredienti(List<IngredienteDTO> ingredienti) {
        this.ingredienti = ingredienti;
    }

    public Float getTempoPreparazione() {
        return tempoPreparazione;
    }

    public void setTempoPreparazione(Float tempoPreparazione) {
        this.tempoPreparazione = tempoPreparazione;
    }
   

}
