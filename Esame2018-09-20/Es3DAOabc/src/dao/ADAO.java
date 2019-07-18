package dao;

import java.util.Set;

public interface ADAO
{
	//CRUD
	public void create(ADTO obj);
	public ADTO read(int id);
	public boolean update(ADTO obj);
	public boolean delete(int id);

	public boolean createTable();
	public boolean dropTable();
	Set<ADTO> readAll();
	Set<ADTO> readByFk(int fk);

	//Altri metodi

}