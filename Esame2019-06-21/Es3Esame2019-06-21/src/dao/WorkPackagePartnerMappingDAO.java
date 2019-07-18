package dao;

import java.util.Set;

public interface WorkPackagePartnerMappingDAO {
	
		// --- CRUD -------------
		public void create(int idr, int idp);

		public boolean delete(int idRistorante, int idPiatto);		
		// ----------------------------------
		public Set<PartnerDTO> getPartnersForPackage(int id);
		public Set<WorkPackageDTO> getPackagesForPartner(int id);
		// ----------------------------------		
		public boolean createTable();

		public boolean dropTable();
}
