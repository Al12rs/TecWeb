package m2m.bi.proxy.dao.db2;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import m2m.bi.proxy.dao.PasseggeroDTO;
import m2m.bi.proxy.dao.VoloDTO;



public class Db2VoloProxy extends VoloDTO {
	private static final long serialVersionUID = 5224938053385896589L;
	private boolean caricato = false;

	public Db2VoloProxy() {
	super();
	}

	public Db2VoloProxy(long id, String codVolo, String compagniaAerea, String localitaDestinazione, Date dataPartenza,
			int orarioPartenza) {
		super(id, codVolo, compagniaAerea, localitaDestinazione, dataPartenza, orarioPartenza);
	}

	@Override
	public Set<PasseggeroDTO> getPasseggeri() {
		if (!caricato) {
			Set<PasseggeroDTO> passeggeri = new HashSet<>();
			Db2PasseggeroDAO db2PasseggeroDAO = new Db2PasseggeroDAO();
			for (long id_Passeggero : new Db2LinkDAO().read_by_id_Volo(getId())){
				passeggeri.add(db2PasseggeroDAO.readLazy(id_Passeggero));
			}
			this.setPasseggeri(passeggeri);
		}
		return super.getPasseggeri();
	}
	
	@Override
	public void setPasseggeri(Set<PasseggeroDTO> passeggeri) {
		caricato = true;
		super.setPasseggeri(passeggeri);
	}
}