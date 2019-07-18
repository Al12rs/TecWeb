package dao;

import java.util.List;

public interface WorkPackageDAO
{
	//CRUD
	public void create(WorkPackageDTO obj);
	public WorkPackageDTO read(long id);
	public boolean update(WorkPackageDTO obj);
	public boolean delete(long id);

	public boolean createTable();
	public boolean dropTable();
	List<WorkPackageDTO> readAll();

	//Altri metodi

}