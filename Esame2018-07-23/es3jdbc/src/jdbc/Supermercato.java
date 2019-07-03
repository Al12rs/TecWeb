package jdbc;

import java.util.HashSet;
import java.util.Set;

public class Supermercato {

    private int codiceSuper;
    private String nomeTavolo;
    private int ratingGradimento;
    private Set<Prodotto> prodottiOfferti;

    public Supermercato(int codiceSuper, String nomeTavolo, int ratingGradimento) {
        this.codiceSuper = codiceSuper;
        this.nomeTavolo = nomeTavolo;
        this.ratingGradimento = ratingGradimento;
    }

    public int getCodiceSuper() {
        return codiceSuper;
    }

    public void setCodiceSuper(int codiceSuper) {
        this.codiceSuper = codiceSuper;
    }

    public String getNomeTavolo() {
        return nomeTavolo;
    }

    public void setNomeTavolo(String nomeTavolo) {
        this.nomeTavolo = nomeTavolo;
    }

    public int getRatingGradimento() {
        return ratingGradimento;
    }

    public void setRatingGradimento(int ratingGradimento) {
        this.ratingGradimento = ratingGradimento;
    }

    public Set<Prodotto> getProdottiOfferti() {
        return prodottiOfferti;
    }

    public void setProdottiOfferti(Set<Prodotto> prodottiOfferti) {
        this.prodottiOfferti = prodottiOfferti;
    }

    @Override
    public String toString() {
        return "Supermercato [codiceSuper=" + codiceSuper + ", nomeTavolo=" + nomeTavolo + ", prodottiOfferti="
                + prodottiOfferti + ", ratingGradimento=" + ratingGradimento + "]";
    }

   
}
