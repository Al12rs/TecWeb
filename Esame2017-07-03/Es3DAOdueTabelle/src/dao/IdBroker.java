package dao;

public interface IdBroker {
	
	public int newId(String sequenceName) throws Exception;
	
}
