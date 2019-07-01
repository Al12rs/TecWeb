package Resturant;

import java.sql.*;


import javax.persistence.PersistenceException;

import Resturant.db.DataSource;



public class TavoloRepository {
	private DataSource dataSource;
	
// === Costanti letterali per non sbagliarsi a scrivere !!! ============================
	
	private static final String TABLE = "tavolo";

	// -------------------------------------------------------------------------------------

	private static final String ID = "id";
	private static final String NUMERO = "numero";
	private static final String CAPIENZA = "capienza";
	
	
	
	
	
	// == STATEMENT SQL ====================================================================

	// INSERT INTO table ( email, description, ...) VALUES ( ?,?, ... );
	private static final String insert = 
		"INSERT " +
			"INTO " + TABLE + " ( " +
					ID+", "+NUMERO+", "+CAPIENZA+" " +
			") " +
			"VALUES (?,?,?) "
		;
	
	
	
	// DELETE FROM table WHERE idcolumn = ?;
			static String delete = 
				"DELETE " +
					"FROM " + TABLE + " " +
					"WHERE " + ID + " = ? "
				;
	
	// UPDATE table SET xxxcolumn = ?, ... WHERE idcolumn = ?;
			static String update = 
				"UPDATE " + TABLE + " " +
					"SET " + 
						NUMERO + " = ?, " +
						CAPIENZA + " = ? " +
					"WHERE " + ID + " = ? "
				;

	// -------------------------------------------------------------------------------------
	
	// create table
	private static String create = 
		"CREATE " +
			"TABLE " + TABLE +" ( " +
				ID + " INT NOT NULL PRIMARY KEY, " +
				NUMERO + " VARCHAR(10) NOT NULL UNIQUE, " +
				CAPIENZA + " INT " +
			") "
		;
	
	// drop table
	private static String drop = 
		"DROP " +
			"TABLE " + TABLE + " "
		;
	
	static String read_available_table = 
			"SELECT "+ NUMERO +
				" FROM " + TABLE + " " +
				"WHERE " + "capienza" + " >= ? AND "+ID+" NOT IN ( SELECT idTavolo FROM prenotazione WHERE data = ?)";
	
	static String read_id_per_code = 
			"SELECT "+ ID +
				" FROM " + TABLE + " " +
				"WHERE " + NUMERO + " = ? ";
	
	public TavoloRepository(int databaseType) {
		dataSource = new DataSource(databaseType);
	}
	
	public void dropAndCreateTable() throws PersistenceException{
		Connection connection = this.dataSource.getConnection();
		
		Statement statement = null;
		try {
			statement = connection.createStatement ();
			try{
				statement.executeUpdate(drop);
			}
			catch (SQLException e) {
				// the table does not exist
			}
			statement.executeUpdate (create);
			statement.close ();
		}
		catch (SQLException e) {
			throw new PersistenceException(e.getMessage());
		}
		finally {
			try {
				if (statement != null) 
					statement.close();
				if (connection!= null)
					connection.close();
			}
			catch (SQLException e) {
				throw new PersistenceException(e.getMessage());
			}
		}
	}
		
	public void persist(Tavolo t) throws PersistenceException{
		Connection connection = null;
		PreparedStatement statement = null; 
		
			try {
				connection = this.dataSource.getConnection();
				statement = connection.prepareStatement(insert);
				statement.setInt(1, t.getIdTavolo());
				statement.setString(2, t.getNumeroTavolo());
				statement.setDouble(3, t.getCapienzaTavolo());
				
				statement.executeUpdate();
			}
			catch (SQLException e) {
				throw new PersistenceException(e.getMessage());
			}
			finally {
				try {
					if (statement != null) 
						statement.close();
					if (connection!= null){
						connection.close();
						connection = null;
					}
				}
				catch (SQLException e) {
					throw new PersistenceException(e.getMessage());
				}
			}
		
	}
	
	public int getIdFromNumber(String numeroTav)
	{
		int result=-1;
		Connection connection = null;
		PreparedStatement statement = null;
		try{
			connection = this.dataSource.getConnection();
			statement = connection.prepareStatement(read_id_per_code);
			statement.setString(1, numeroTav);
			ResultSet rs = statement.executeQuery();
			if(rs.next())
			{
				result = rs.getInt(ID);
			}
			else
				result = -1;
			return result;
		}catch (SQLException e) {
			throw new PersistenceException(e.getMessage());
		}
		finally {
			try {
				if (statement != null) 
					statement.close();
				if (connection!= null){
					connection.close();
					connection = null;
				}
			}
			catch (SQLException e) {
				throw new PersistenceException(e.getMessage());
			}
		}
	}
	
	public String availableTable(Date data, int persone)
	{
		String result=null;
		Connection connection = null;
		PreparedStatement statement = null;
		try{
			connection = this.dataSource.getConnection();
			statement = connection.prepareStatement(read_available_table);
			statement.setInt(1, persone);
			statement.setDate(2, data);
			ResultSet rs = statement.executeQuery();
			if(rs.next())
			{
				result = rs.getString(NUMERO);
			}
			else
				result = null;
			return result;
		}catch (SQLException e) {
			throw new PersistenceException(e.getMessage());
		}
		finally {
			try {
				if (statement != null) 
					statement.close();
				if (connection!= null){
					connection.close();
					connection = null;
				}
			}
			catch (SQLException e) {
				throw new PersistenceException(e.getMessage());
			}
		}
	}
	
	public void update(Tavolo t)
	{
		Connection connection = null;
		PreparedStatement statement = null; 
		
			try {
				connection = this.dataSource.getConnection();
				statement = connection.prepareStatement(update);
				
				statement.setString(1, t.getNumeroTavolo());
				statement.setInt(2, t.getCapienzaTavolo());
				statement.setInt(3, t.getIdTavolo());
				statement.executeUpdate();
			}
			catch (SQLException e) {
				throw new PersistenceException(e.getMessage());
			}
			finally {
				try {
					if (statement != null) 
						statement.close();
					if (connection!= null){
						connection.close();
						connection = null;
					}
				}
				catch (SQLException e) {
					throw new PersistenceException(e.getMessage());
				}
			}
	}

	
	
}
