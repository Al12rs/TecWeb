package o2m.bi.proxy.dao.db2;

import java.util.Set;

import o2m.bi.proxy.dao.CittaDTO;
import o2m.bi.proxy.dao.MaratonaDTO;

public class Db2CittaProxy extends CittaDTO {
	private static final long serialVersionUID = 1L;
	private boolean caricato = false;

	public Db2CittaProxy() {
		super();
	}

	public Db2CittaProxy(long id, String nome, String nazione) {
		super(id, nome, nazione);
	}
	
	// Su richiesta carica il set di maratone di questa città. Si richiede al DAO di caricare
	// delle maratone che non carichino immediatamente la loro citta (lazy load). 
	// Questo perchè non è in realtà necessario andare sul database a ricercare la città
	// in quanto la città è this.
	// Non appena caricato il set di proxy di maratone si setta la città usando 
	// questa stessa istanza come riferimento.
	@Override
	public Set<MaratonaDTO> getMaratone() {
		if (!caricato) {
			Set<MaratonaDTO> maratone = new Db2MaratonaDAO().readByIdCittaLazy(getId());
			for (MaratonaDTO m : maratone)
				m.setCitta(this);
			this.setMaratone(maratone);
		}
		return super.getMaratone();
	}	
	
	@Override
	public void setMaratone(Set<MaratonaDTO> maratone) {
		caricato = true;
		super.setMaratone(maratone);
	}
}
