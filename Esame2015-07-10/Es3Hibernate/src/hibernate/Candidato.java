package hibernate;

import java.io.Serializable;
import java.util.*;

public class Candidato implements Serializable {

    private static final long serialVersionUID = 1L;

    int candidatoId;
    String matricolaCandidato;
    String nome;
    String cognome;
    Set<Concorso> concorsi = new HashSet<Concorso>();

    public int getCandidatoId() {
        return candidatoId;
    }

    public void setCandidatoId(int candidatoId) {
        this.candidatoId = candidatoId;
    }

    public String getMatricolaCandidato() {
        return matricolaCandidato;
    }

    public void setMatricolaCandidato(String matricolaCandidato) {
        this.matricolaCandidato = matricolaCandidato;
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

    public Set<Concorso> getConcorsi() {
        return concorsi;
    }

    public void setConcorsi(Set<Concorso> concorsi) {
        this.concorsi = concorsi;
    }

    public Candidato(int candidatoId, String matricolaCandidato, String nome, String cognome) {
        this.candidatoId = candidatoId;
        this.matricolaCandidato = matricolaCandidato;
        this.nome = nome;
        this.cognome = cognome;
    }

    
}