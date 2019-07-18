package dao;

import java.util.Set;

public interface GiocatoreSquadraPallacanestroMappingDAO {
	
		// --- CRUD -------------
		public void create(int idGiocatore, int idSquadra);

		public boolean delete(int idGiocatore, int idSquadra);		
		// ----------------------------------
		public Set<SquadraPallacanestroDTO> getSquadre(int idGiocatore);
		// ----------------------------------		
		public boolean createTable();

		public boolean dropTable();
}
