package dao;

public interface GiocatoreDAO {
	
	// --- CRUD -------------
		public void create(GiocatoreDTO giocatore);

		public GiocatoreDTO read(int id);

		public boolean update(GiocatoreDTO giocatore);

		public boolean delete(int id);
		// ----------------------------------
		// dichiarazione metodi di supporto per le operazioni richiesta
		// ----------------------------------	
		public boolean createTable();

		public boolean dropTable();
	
}
