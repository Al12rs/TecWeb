package it.unibo.tw.dao;

import java.util.List;

public interface RistorantePiattoMappingDAO {

	// --- CRUD -------------

		public void create(int idRistorante, int idPiatto);

		public boolean delete(int idRistorante, int idPiatto);

		
		// ----------------------------------

		public List<PiattoDTO> getPiattiFromRestaurant(int idRistorante);

		// ----------------------------------

		
		public boolean createTable();

		public boolean dropTable();
}
