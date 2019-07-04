package dao;

import java.io.Serializable;
import java.util.*;

public class ProgettoDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private int ProgettoId;
    private int CodiceProgetto;
    private String nomeProgetto;
    private String annoInizio;
    private Float durata;
    private List<WorkPackageDTO> workPackages = new ArrayList<WorkPackageDTO>();

}
