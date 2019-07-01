package esame.dao;

public interface VoloDAO
{
	//CRUD
	public void create(VoloDTO obj);
	public VoloDTO read(long id);
	public boolean update(VoloDTO obj);
	public boolean delete(long id);

	public boolean createTable();
	public boolean dropTable();
}