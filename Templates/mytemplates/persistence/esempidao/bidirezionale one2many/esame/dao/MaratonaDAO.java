package esame.dao;

import java.util.Set;

public interface MaratonaDAO
{
	//CRUD
	public void create(MaratonaDTO obj);
	public MaratonaDTO read(long id);
	public Set<MaratonaDTO> readByIdCitta(long id_citta);
	public boolean update(MaratonaDTO obj);
	public boolean delete(long id);

	public boolean createTable();
	public boolean dropTable();

	//Altri metodi

}