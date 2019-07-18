package it.unibo.tw.dao;


public interface IngredienteDAO {
	
	// --- CRUD -------------
		public void create(IngredienteDTO ingrediene);

		public IngredienteDTO read(int id);

		public boolean update(IngredienteDTO ingrediene);

		public boolean delete(int id);
		// ----------------------------------
		// dichiarazione metodi di supporto per le operazioni richiesta
		// ----------------------------------	
		public boolean createTable();

		public boolean dropTable();
	
}
