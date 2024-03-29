package dao;


import java.util.List;

public interface TypeDAO {
	
	// --- CRUD -------------
		public void create(#TypeDTO oggetto);

		public #TypeDTO read(int id);

		public boolean update(#TypeDTO oggetto);

		public boolean delete(int id);
		// ----------------------------------
		// dichiarazione metodi di supporto per le operazioni richiesta
		// ----------------------------------	
		public boolean createTable();

		public boolean dropTable();
	
}
