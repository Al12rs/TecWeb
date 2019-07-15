package it.unibo.tw.dao;


import java.util.List;

public interface RistoranteDAO {

	// --- CRUD -------------

	public void create(RistoranteDTO Ristorante);

	public RistoranteDTO read(int id);

	public boolean update(RistoranteDTO ristorante);

	public boolean delete(int code);

	
	// ----------------------------------

	public List<RistoranteDTO> getRestaurantByCity(String city);
	
	public List<RistoranteDTO> getRatedRestaurant(int stars);

	// ----------------------------------

	
	public boolean createTable();

	public boolean dropTable();

}
