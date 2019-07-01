

import java.io.Serializable;
import java.util.Set;

public class Supermercato implements Serializable{
	private static final long serialVersionUID = 1L;

	private long CodiceSupermercato;
	private String nome;
	private Float ratingGradimento;
	private Set<ProdottiOfferti> prodottiOfferti;

    @Override
    public String toString() {
        return "Supermercato [CodiceSupermercato=" + CodiceSupermercato + ", nome=" + nome + ", prodottiOfferti="
                + prodottiOfferti + ", ratingGradimento=" + ratingGradimento + "]";
    }

    public long getCodiceSupermercato() {
        return CodiceSupermercato;
    }

    public void setCodiceSupermercato(long codiceSupermercato) {
        CodiceSupermercato = codiceSupermercato;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Float getRatingGradimento() {
        return ratingGradimento;
    }

    public void setRatingGradimento(Float ratingGradimento) {
        this.ratingGradimento = ratingGradimento;
    }

    public Set<ProdottiOfferti> getProdottiOfferti() {
        return prodottiOfferti;
    }

    public void setProdottiOfferti(Set<ProdottiOfferti> prodottiOfferti) {
        this.prodottiOfferti = prodottiOfferti;
    }

    public Supermercato(long codiceSupermercato, String nome, Float ratingGradimento,
            Set<ProdottiOfferti> prodottiOfferti) {
        CodiceSupermercato = codiceSupermercato;
        this.nome = nome;
        this.ratingGradimento = ratingGradimento;
        this.prodottiOfferti = prodottiOfferti;
    }
}