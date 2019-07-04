package beans;
public class Prodotto{

    private String nome;
    private int quantita;
    private int soglia;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getQuantita() {
        return quantita;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }

    public int getSoglia() {
        return soglia;
    }

    public void setSoglia(int soglia) {
        this.soglia = soglia;
    }

    public Prodotto(String nome, int quantita, int soglia) {
        this.nome = nome;
        this.quantita = quantita;
        this.soglia = soglia;
    }


}