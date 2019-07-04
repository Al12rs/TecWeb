package hibernate;

import java.io.Serializable;

public class Commissario implements Serializable {

    private static final long serialVersionUID = 1L;

    int commissarioId;
    String matricolaCommissario;
    String nome;
    String cognome;
    Concorso concorso;

    public int getCommissarioId() {
        return commissarioId;
    }

    public void setCommissarioId(int commissarioId) {
        this.commissarioId = commissarioId;
    }

    public String getMatricolaCommissario() {
        return matricolaCommissario;
    }

    public void setMatricolaCommissario(String matricolaCommissario) {
        this.matricolaCommissario = matricolaCommissario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public Concorso getConcorso() {
        return concorso;
    }

    public void setConcorso(Concorso concorso) {
        this.concorso = concorso;
    }

    public Commissario(int commissarioId, String matricolaCommissario, String nome, String cognome, Concorso concorso) {
        this.commissarioId = commissarioId;
        this.matricolaCommissario = matricolaCommissario;
        this.nome = nome;
        this.cognome = cognome;
        this.concorso = concorso;
    }

}