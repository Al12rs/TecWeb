package m2m.bi.proxy.dao;

public interface PasseggeroDAO
{
	//CRUD
	public void create(PasseggeroDTO obj);
	public PasseggeroDTO read(long id);
	public boolean update(PasseggeroDTO obj);
	public boolean delete(long id);

	public boolean createTable();
	public boolean dropTable();
	boolean isRelationshipOwner();
	void setRelationshipOwner(boolean relationshipOwner);
}