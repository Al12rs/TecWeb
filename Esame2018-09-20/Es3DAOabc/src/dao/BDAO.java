package dao;

import java.util.Set;

public interface BDAO
{
	//CRUD
	public void create(BDTO obj);
	public BDTO read(int id);
	public boolean update(BDTO obj);
	public boolean delete(int id);

	public boolean createTable();
	public boolean dropTable();
	Set<BDTO> readAll();
	Set<BDTO> readByFk(int fk);

	//Altri metodi

}