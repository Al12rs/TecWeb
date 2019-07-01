package o2m.bi.proxy.dao;

import o2m.bi.proxy.dao.db2.Db2DAOFactory;

public abstract class DAOFactory {

	// --- List of supported DAO types ---

	// Numeric constant '0' corresponds to explicit DB2 choice
	public static final int DB2 = 0;	
	//	Numeric constant '1' corresponds to explicit Hsqldb choice
	//	public static final int HSQLDB = 1;
	//	Numeric constant '2' corresponds to explicit MySQL choice
	//	public static final int MYSQL = 2;

	// --- Actual factory method ---
	
	/**
	 * Depending on the input parameter
	 * this method returns one out of several possible 
	 * implementations of this factory spec 
	 */
	public static DAOFactory getDAOFactory(int whichFactory) {
		switch ( whichFactory ) {
		case DB2:
			return new Db2DAOFactory();
		// case HSQLDB:
		// return new HsqldbDAOFactory();
		// case MYSQL:
		// return new MySqlDAOFactory();
		default:
			return null;
		}
	}

	public CittaDAO getCittaDAO() {
		// TODO Auto-generated method stub
		return null;
	}

	public MaratonaDAO getMaratonaDAO() {
		// TODO Auto-generated method stub
		return null;
	}	
}
