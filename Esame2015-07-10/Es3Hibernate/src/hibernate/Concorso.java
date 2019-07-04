package hibernate;

import java.io.Serializable;
import java.util.*;

public class Concorso implements Serializable {

    private static final long serialVersionUID = 1L;

    int concorsoId;
    int codiceConcorso;
    String classeConcorso;
    String descrizione;
    Set<Candidato> candidati = new HashSet<Candidato>();
    Set<Commissario> commissari = new HashSet<Commissario>();

    public int getConcorsoId() {
        return concorsoId;
    }

    public void setConcorsoId(int concorsoId) {
        this.concorsoId = concorsoId;
    }

    public int getCodiceConcorso() {
        return codiceConcorso;
    }

    public void setCodiceConcorso(int codiceConcorso) {
        this.codiceConcorso = codiceConcorso;
    }

    public String getClasseConcorso() {
        return classeConcorso;
    }

    public void setClasseConcorso(String classeConcorso) {
        this.classeConcorso = classeConcorso;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public Set<Candidato> getCandidati() {
        return candidati;
    }

    public void setCandidati(Set<Candidato> candidati) {
        this.candidati = candidati;
    }

    public Set<Commissario> getCommissari() {
        return commissari;
    }

    public void setCommissari(Set<Commissario> commissari) {
        this.commissari = commissari;
    }

    public Concorso(int concorsoId, int codiceConcorso, String classeConcorso, String descrizione) {
        this.concorsoId = concorsoId;
        this.codiceConcorso = codiceConcorso;
        this.classeConcorso = classeConcorso;
        this.descrizione = descrizione;
    }

}