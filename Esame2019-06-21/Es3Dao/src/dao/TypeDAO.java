package dao;


import java.util.List;

public interface TypeDAO {
	
	// --- CRUD -------------
		public void create(#TypeDTO #oggetto);

		public #TypeDTO read(/* chiave per accedere al db */);

		public boolean update(#TypeDTO #oggetto);

		public boolean delete(/* chiave per accedere al db */);
		// ----------------------------------
		// dichiarazione metodi di supporto per le operazioni richiesta
		// ----------------------------------	
		public boolean createTable();

		public boolean dropTable();
	
}
