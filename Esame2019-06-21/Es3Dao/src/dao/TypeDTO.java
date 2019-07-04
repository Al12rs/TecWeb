package dao;

import java.io.Serializable;
import java.util.List;

public class RistoranteDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String nomeRistorante;
	private String indirizzo;
	private int rating;
	private List<PiattoDTO> piatti;	

}
