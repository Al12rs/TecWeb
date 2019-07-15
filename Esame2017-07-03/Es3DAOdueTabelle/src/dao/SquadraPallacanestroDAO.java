package dao;

public interface SquadraPallacanestroDAO {
	
	// --- CRUD -------------
		public void create(SquadraPallacanestroDTO squadra);

		public SquadraPallacanestroDTO read(int id);

		public boolean update(SquadraPallacanestroDTO squadra);

		public boolean delete(int id);
		// ----------------------------------
		// dichiarazione metodi di supporto per le operazioni richiesta
		// ----------------------------------	
		public boolean createTable();

		public boolean dropTable();
	
}
