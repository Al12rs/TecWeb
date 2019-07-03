package jdbc;

import java.util.HashSet;
import java.util.Set;

import jdbc.Supermercato;

public class Prodotto {

    private int codiceProdotto;
    private String descrizione;
    private String marca;
    private Float prezzo;
    private Supermercato supermercato;

    public Prodotto(int codiceProdotto, String descrizione, String marca, Float prezzo,
            Supermercato supermercato) {
        this.codiceProdotto = codiceProdotto;
        this.descrizione = descrizione;
        this.marca = marca;
        this.prezzo = prezzo;
        this.supermercato = supermercato;
    }

    public int getCodiceProdotto() {
        return codiceProdotto;
    }

    public void setCodiceProdotto(int codiceProdotto) {
        this.codiceProdotto = codiceProdotto;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Float getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(Float prezzo) {
        this.prezzo = prezzo;
    }

    public Supermercato getSupermercato() {
        return supermercato;
    }

    public void setSupermercato(Supermercato supermercato) {
        this.supermercato = supermercato;
    }

    @Override
    public String toString() {
        return "Prodotto [codiceProdotto=" + codiceProdotto + ", descrizione=" + descrizione + ", marca=" + marca
                + ", prezzo=" + prezzo + ", supermercato=" + supermercato + "]";
    }
}
