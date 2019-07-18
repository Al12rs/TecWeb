package dao;

import java.util.Set;

public interface WorkPackageDAO {
	
	// --- CRUD -------------
		public void create(WorkPackageDTO workpackage);

		public WorkPackageDTO read(int id);

		public boolean update(WorkPackageDTO workpackage);

		public boolean delete(int id);
		// ----------------------------------
		// dichiarazione metodi di supporto per le operazioni richiesta
		public Set<WorkPackageDTO> readAll();
		
		public Set<WorkPackageDTO> readByIdProgetto(int id);
		// ----------------------------------	
		public boolean createTable();

		public boolean dropTable();
	
}
