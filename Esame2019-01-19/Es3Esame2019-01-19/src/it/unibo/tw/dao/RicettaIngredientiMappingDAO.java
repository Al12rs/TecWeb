package it.unibo.tw.dao;

import java.util.Set;

public interface RicettaIngredientiMappingDAO {
	
		// --- CRUD -------------
		public void create(int idRicetta, int idIngrediente);

		public boolean delete(int idRicetta, int idIngrediente);		
		// ----------------------------------
		public Set<IngredienteDTO> getIngredientiForRicetta(int idRicetta);
		// ----------------------------------		
		public boolean createTable();

		public boolean dropTable();
}
