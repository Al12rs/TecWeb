package dao;

import java.io.Serializable;
import java.util.*;

public class PartnerDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private int partnerId;
    private String signaPartner;
    private String nome;
    private List<WorkPackageDTO> wps = new ArrayList<WorkPackageDTO>();

}
