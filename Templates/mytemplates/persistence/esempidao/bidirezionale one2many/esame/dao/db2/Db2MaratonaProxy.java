package esame.dao.db2;

import java.sql.Date;

import esame.dao.CittaDTO;
import esame.dao.MaratonaDTO;

public class Db2MaratonaProxy extends MaratonaDTO {
	private static final long serialVersionUID = 1L;
	private boolean caricato = false;
		
	public Db2MaratonaProxy() {
		super();
	}

	public Db2MaratonaProxy(long id, long codiceMaratona, String titolo, Date data, String tipo, long id_citta) {
		super(id, codiceMaratona, titolo, data, tipo, id_citta);
	}
	
	// Metodo del proxy per fare lazy load, carica la città di questa maratona
	// facendo in modo che la città faccia un fetch eager delle sue maratone
	// NB: in realtà questo metodo non dovrebbe mai essere necessario perchè 
	// al momento della caricamento di questo set (Db2CittaDao.read()) viene 
	// settata la città immediatamente
	@Override
	public CittaDTO getCitta() {
		if (!caricato) {
			setCitta(new Db2CittaDAO().read(getId_citta()));
		}
		return super.getCitta();
	}
	
	@Override
	public void setCitta(CittaDTO citta) {
		caricato = true;
		super.setCitta(citta);
	}
}