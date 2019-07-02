package it.unibo.tw.dao;

import java.util.List;

public interface RicettaDAO {
	
	// --- CRUD -------------
		public void create(RicettaDTO oggetto);

		public RicettaDTO read(int id);

		public boolean update(RicettaDTO oggetto);

		public boolean delete(int id);
		// ----------------------------------
		// dichiarazione metodi di supporto per le operazioni richiesta
		// ----------------------------------	
		public boolean createTable();

		public boolean dropTable();
	
}
