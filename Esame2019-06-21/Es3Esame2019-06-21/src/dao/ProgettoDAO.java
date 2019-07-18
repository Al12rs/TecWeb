package dao;

import java.util.Set;

public interface ProgettoDAO {
	
	// --- CRUD -------------
		public void create(ProgettoDTO progetto);

		public ProgettoDTO read(int id);

		public boolean update(ProgettoDTO progetto);

		public boolean delete(int id);
		// ----------------------------------
		// dichiarazione metodi di supporto per le operazioni richiesta
		public Set<ProgettoDTO> readAll();
		// ----------------------------------	
		public boolean createTable();

		public boolean dropTable();
	
}
