package o2m.bi.noproxy.dao;

public interface CittaDAO
{
	//CRUD
	public void create(CittaDTO obj);
	public CittaDTO read(long id);
	public CittaDTO readByName(String name);
	public boolean update(CittaDTO obj);
	public boolean delete(long id);

	public boolean createTable();
	public boolean dropTable();

	//Altri metodi

}