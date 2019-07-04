package dao;


import java.util.List;

public interface PartnerDAO {
	
	// --- CRUD -------------
		public void create(PartnerDTO oggetto);

		public PartnerDTO read(int id);

		public boolean update(PartnerDTO oggetto);

		public boolean delete(int id);
		// ----------------------------------
		// dichiarazione metodi di supporto per le operazioni richiesta
		// ----------------------------------	
		public boolean createTable();

		public boolean dropTable();
	
}
