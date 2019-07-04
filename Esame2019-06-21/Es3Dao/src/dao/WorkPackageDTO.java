package dao;

import java.io.Serializable;
import java.util.*;

public class WorkPackageDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private int workPackageId;
    private String nomeWP;
    private String titolo;
    private String descrizione;
    ProgettoDTO progetto;
    private List<PartnerDTO> partners = new ArrayList<PartnerDTO>();

    public int getWorkPackageId() {
        return workPackageId;
    }

    public void setWorkPackageId(int workPackageId) {
        this.workPackageId = workPackageId;
    }

    public String getNomeWP() {
        return nomeWP;
    }

    public void setNomeWP(String nomeWP) {
        this.nomeWP = nomeWP;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public ProgettoDTO getProgetto() {
        return progetto;
    }

    public void setProgetto(ProgettoDTO progetto) {
        this.progetto = progetto;
    }

    public List<PartnerDTO> getPartners() {
        return partners;
    }

    public void setPartners(List<PartnerDTO> partners) {
        this.partners = partners;
    }

}
