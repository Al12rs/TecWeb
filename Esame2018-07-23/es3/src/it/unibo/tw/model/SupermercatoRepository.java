package esame.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import it.unibo.tw.db.DataSource;
import it.unibo.tw.db.PersistenceException;
 
public class SupermercatoRepository 
{ 
	private DataSource dataSource; 
 
	public SupermercatoRepository () throws PersistenceException 
	{ 
		dataSource = new DataSource(DataSource.DB2); 
	} 
 
	// == STATEMENT SQL preconfigurati 
 
	//statement metodi CRUD 
 
	//INSERT 
	private static final String insert = "INSERT INTO supermercato (CodiceSupermercato,nome,ratingGradimento,prodottiOfferti) VALUES (?,?,?,?)";
 
 
	//DELETE 
	private static final String delete = "DELETE FROM supermercato WHERE CodiceSupermercato=?";
 
 
	//UPDATE 
	private static final String update = "UPDATE supermercato SET nome=?,ratingGradimento=?,prodottiOfferti=? WHERE CodiceSupermercato=?";
 
 
	//READ 
	private static String read_by_id = "SELECT * FROM supermercato WHERE CodiceSupermercato=?";
 
 
	// implementazione metodi CRUD 
 
	// create table 
	private static String create = 
"CREATE TABLE supermercato (CodiceSupermercato BIGINT NOT NULL PRIMARY KEY,nome VARCHAR(50) NOT NULL,ratingGradimento FLOAT NOT NULL,prodottiOfferti SET<PRODOTTIOFFERTI> NOT NULL, UNIQUE (nome), FOREIGN KEY ())"; 
 
	//drop table 
	private static String drop = "DROP TABLE supermercato";
 
 
	// init 
	public void dropAndCreateTable() throws PersistenceException { 
		dropTable(); 
		createTable(); 
	} 
 
	public void dropTable() throws PersistenceException { 
		Connection connection = this.dataSource.getConnection(); 
		Statement statement = null; 
		try { 
			statement = connection.createStatement(); 
			System.out.println(statement); 
			statement.executeUpdate(drop); 
		} catch (SQLException e) { 
			e.printStackTrace(); 
			// the table does not exist or is referenced by a FK 
			// that prevents the drop 
		} finally { 
			try { 
				if (statement != null) 
					statement.close(); 
				if (connection != null) 
					connection.close(); 
			} catch (SQLException e) { 
				throw new PersistenceException(e.getMessage()); 
			} 
		} 
	} 
 
	public void createTable() throws PersistenceException { 
		Connection connection = this.dataSource.getConnection(); 
		Statement statement = null; 
		try { 
			statement = connection.createStatement(); 
			System.out.println(statement); 
			statement.executeUpdate(create); 
		} catch (SQLException e) { 
			throw new PersistenceException(e.getMessage()); 
			// the table might be referenced by a FK 
			// that prevents the drop 
		} finally { 
			try { 
				if (statement != null) 
					statement.close(); 
				if (connection != null) 
					connection.close(); 
			} catch (SQLException e) { 
				throw new PersistenceException(e.getMessage()); 
			} 
		} 
	} 
 
	public void insert( Supermercato obj) throws PersistenceException 
	{ 
		Connection connection = null; 
		PreparedStatement statement = null; 
		try 
		{ 
			connection = this.dataSource.getConnection(); 
			statement = connection.prepareStatement(insert); 
			statement.setLong(1,obj.getCodiceSupermercato());
			statement.setString(2,obj.getNome());
			statement.setFloat(3,obj.getRatingGradimento());
			statement.setSet<ProdottiOfferti>(4,obj.getProdottiOfferti());
 
			System.out.println(statement); 
			statement.executeUpdate(); 
		} 
		catch (SQLException e) 
		{ 
			throw new PersistenceException(e.getMessage()); 
		} finally { 
			try { 
				if (statement != null) 
					statement.close(); 
				if (connection != null) 
					connection.close(); 
			} catch (SQLException e) { 
				throw new PersistenceException(e.getMessage()); 
			} 
		} 
	} 
 
	public void delete(long id) throws PersistenceException 
	{ 
		Connection connection = null; 
		PreparedStatement statement = null; 
		try 
		{ 
			connection = this.dataSource.getConnection(); 
			statement = connection.prepareStatement(delete); 
			statement.setLong(1, id); 
			System.out.println(statement); 
			statement.executeUpdate(); 
		} 
		catch (SQLException e) 
		{ 
			throw new PersistenceException(e.getMessage()); 
		} finally { 
			try { 
				if (statement != null) 
					statement.close(); 
				if (connection != null) 
					connection.close(); 
			} catch (SQLException e) { 
				throw new PersistenceException(e.getMessage()); 
			} 
		} 
	} 
 
	public void update( Supermercato obj) throws PersistenceException 
	{ 
		Connection connection = null; 
		PreparedStatement statement = null; 
		try 
		{ 
			connection = this.dataSource.getConnection(); 
			statement = connection.prepareStatement(update); 
			statement.setString(1,obj.getNome());
			statement.setFloat(2,obj.getRatingGradimento());
			statement.setSet<ProdottiOfferti>(3,obj.getProdottiOfferti());
			statement.setLong(4,obj.getCodiceSupermercato());
 
			System.out.println(statement); 
			statement.executeUpdate(); 
		} 
		catch (SQLException e) 
		{ 
			throw new PersistenceException(e.getMessage()); 
		} finally { 
			try { 
				if (statement != null) 
					statement.close(); 
				if (connection != null) 
					connection.close(); 
			} catch (SQLException e) { 
				throw new PersistenceException(e.getMessage()); 
			} 
		} 
	} 
 
	public Supermercato read(long id) throws PersistenceException 
	{ 
Supermercato res = null; 
		Connection connection = null; 
		PreparedStatement statement = null; 
		try { 
			connection = this.dataSource.getConnection(); 
			statement = connection.prepareStatement(read_by_id); 
			statement.setLong(1, id); 
			System.out.println(statement); 
			ResultSet results = statement.executeQuery(); 
			while(results.next()) 
			{ 
				res = new Supermercato (); 
				res.setCodiceSupermercato(results.getLong("CodiceSupermercato"));
				res.setNome(results.getString("nome"));
				res.setRatingGradimento(results.getFloat("ratingGradimento"));
				res.setProdottiOfferti(results.getSet<ProdottiOfferti>("prodottiOfferti"));
 
			} 
		} 
		catch (SQLException e) 
		{ 
			throw new PersistenceException(e.getMessage()); 
		} finally { 
			try { 
				if (statement != null) 
					statement.close(); 
				if (connection != null) 
					connection.close(); 
			} catch (SQLException e) { 
				throw new PersistenceException(e.getMessage()); 
			} 
		} 
		return res; 
	} 
 
} 
