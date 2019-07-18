package it.unibo.tw.dao;


import java.util.List;

public interface RicettaDAO {
	
	// --- CRUD -------------
		public void create(RicettaDTO ricetta);

		public RicettaDTO read(int id);

		public boolean update(RicettaDTO ricetta);

		public boolean delete(int id);
		// ----------------------------------
		// dichiarazione metodi di supporto per le operazioni richiesta
		
		public List<RicettaDTO> readAll();
		// ----------------------------------	
		public boolean createTable();

		public boolean dropTable();
	
}
