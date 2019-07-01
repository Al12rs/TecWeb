package esame.dao.db2;

import java.util.HashSet;
import java.util.Set;

import esame.dao.PasseggeroDTO;
import esame.dao.VoloDTO;

public class Db2PasseggeroProxy extends PasseggeroDTO {
	private static final long serialVersionUID = -8181128027106519513L;
	private boolean caricato = false;

	public Db2PasseggeroProxy() {
		super();
	}

	public Db2PasseggeroProxy(long id, String codicePasseggero, String nome, String cognome, String sesso,
			String codicePassaporto) {
		super(id, codicePasseggero, nome, cognome, sesso, codicePassaporto);
	}

	@Override
	public Set<VoloDTO> getVoli() {
		if (!caricato) {
			Set<VoloDTO> Voli = new HashSet<>();
			for (long id_Volo : new Db2LinkDAO().read_by_id_Passeggero(getId())){
				Voli.add(new Db2VoloDAO().readLazy(id_Volo));
			}
			this.setVoli(Voli);
		}
		return super.getVoli();
	}
	
	@Override
	public void setVoli(Set<VoloDTO> Voli) {
		caricato = true;
		super.setVoli(Voli);
	}
}