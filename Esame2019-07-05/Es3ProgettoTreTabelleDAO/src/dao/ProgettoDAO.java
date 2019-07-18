package dao;

import java.util.List;

public interface ProgettoDAO
{
	//CRUD
	public void create(ProgettoDTO obj);
	public ProgettoDTO read(long id);
	public boolean update(ProgettoDTO obj);
	public boolean delete(long id);

	public boolean createTable();
	public boolean dropTable();
	List<ProgettoDTO> readAll();

	//Altri metodi

}