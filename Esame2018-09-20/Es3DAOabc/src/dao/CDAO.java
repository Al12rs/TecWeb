package dao;

import java.util.Set;

public interface CDAO
{
	//CRUD
	public void create(CDTO obj);
	public CDTO read(int id);
	public boolean update(CDTO obj);
	public boolean delete(int id);

	public boolean createTable();
	public boolean dropTable();
	Set<CDTO> readAll();

	//Altri metodi

}