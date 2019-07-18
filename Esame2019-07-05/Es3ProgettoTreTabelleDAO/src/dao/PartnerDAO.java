package dao;

import java.util.List;

public interface PartnerDAO
{
	//CRUD
	public void create(PartnerDTO obj);
	public PartnerDTO read(long id);
	public boolean update(PartnerDTO obj);
	public boolean delete(long id);

	public boolean createTable();
	public boolean dropTable();

	//Altri metodi
	
	PartnerDTO readByUnique(String sigla);
	List<PartnerDTO> readAll();

}