package dao;

public interface PartnerDAO {
	
	// --- CRUD -------------
		public void create(PartnerDTO partner);

		public PartnerDTO read(int id);

		public boolean update(PartnerDTO partner);

		public boolean delete(int id);
		// ----------------------------------
		// dichiarazione metodi di supporto per le operazioni richiesta
		// ----------------------------------	
		public boolean createTable();

		public boolean dropTable();
	
}
