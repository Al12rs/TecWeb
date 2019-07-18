package dao;

import java.util.Set;

public interface WorkPackagePartnerMappingDAO {
	
		// --- CRUD -------------
		public void create(int idPackage, int idPartner);

		public boolean delete(int idPackage, int idPartner);		
		// ----------------------------------
		public Set<WorkPackageDTO> getPackagesFromPartner(long idPartner);
		// ----------------------------------		
		public boolean createTable();

		public boolean dropTable();
}
