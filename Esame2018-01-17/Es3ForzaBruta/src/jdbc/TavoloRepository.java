package jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Set;

import jdbc.db.DataSource;
import jdbc.db.PersistenceException;

public class TavoloRepository {

	private DataSource dataSource;

	// === Costanti letterali per non sbagliarsi a scrivere !!!
	// ============================

	static final String TABLE = "tavoli";

	// -------------------------------------------------------------------------------------

	static final String ID = "Tavoloid";
	static final String NUMERO = "numeroTavolo";
	static final String CAPIENZA = "capienza";

	// == STATEMENT SQL
	// ====================================================================

	// INSERT INTO table ( email, description, ...) VALUES ( ?,?, ... );
	static final String insert = "INSERT " + "INTO " + TABLE + " ( " + NUMERO + ", " + CAPIENZA + " " + ") "
			+ "VALUES (?,?) ";

	// DELETE FROM table WHERE idcolumn = ?;
	static String delete = "DELETE " + "FROM " + TABLE + " " + "WHERE " + NUMERO + " = ? ";

	// UPDATE table SET xxxcolumn = ?, ... WHERE idcolumn = ?;
	static String update = "UPDATE " + TABLE + " " + "SET " + CAPIENZA + " = ? " + "WHERE " + NUMERO + " = ? ";

	// READ FROM TABLE WHERE idcolumn=?
	static String read_by_id = "SELECT * FROM " + TABLE + " WHERE " + NUMERO + "=? ";

	static String read_all = "SELECT * FROM " + TABLE;
	// -------------------------------------------------------------------------------------

	// create table
	static String create = "CREATE " + "TABLE " + TABLE + " ( " + NUMERO + " INT NOT NULL PRIMARY KEY, " + CAPIENZA
			+ " INT " + ") ";

	// drop table
	static String drop = "DROP " + "TABLE " + TABLE + " ";

	static String read_available_table = "SELECT " + NUMERO + " FROM " + TABLE + " " + "WHERE " + CAPIENZA
			+ " >= ? AND " + NUMERO + " NOT IN ( SELECT numeroTavolo FROM prenotazione WHERE data = ?)";

	// static String read_id_per_code = "SELECT " + ID + " FROM " + TABLE + " " +
	// "WHERE " + #NOME + " = ? ";

	// DO NOT EDIT:
	public TavoloRepository(int databaseType) {
		dataSource = new DataSource(databaseType);
	}

	// creazione nuova tabella
	public void dropAndCreateTable() throws PersistenceException {
		Connection connection = this.dataSource.getConnection();

		Statement statement = null;
		try {
			statement = connection.createStatement();
			try {
				statement.executeUpdate(drop);
			} catch (SQLException e) {
				// the table does not exist
			}
			statement.executeUpdate(create);
			statement.close();
		} catch (SQLException e) {
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
	// END DO NOT EDIT.

	// CREATE METHOD (C)
	public void persist(Tavolo tavolo) throws PersistenceException {
		Connection connection = null;
		PreparedStatement statement = null;

		try {
			connection = this.dataSource.getConnection();
			statement = connection.prepareStatement(insert);

			// EDIT CODE:
			statement.setInt(1, tavolo.getNumeroTavolo());
			statement.setInt(2, tavolo.getCapienza());

			// STOP EDIT CODE.

			statement.executeUpdate();
		} catch (SQLException e) {
			throw new PersistenceException(e.getMessage());
		} finally {
			try {
				if (statement != null)
					statement.close();
				if (connection != null) {
					connection.close();
					connection = null;
				}
			} catch (SQLException e) {
				throw new PersistenceException(e.getMessage());
			}
		}
	}

	// READ METHOD (R)
	public Tavolo readById(int id) throws PersistenceException {
		Tavolo result = new Tavolo();
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = this.dataSource.getConnection();
			statement = connection.prepareStatement(read_by_id);
			statement.setInt(1, id);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				// CHANGE ME!
				result.setNumeroTavolo(rs.getInt(NUMERO));
				result.setCapienza(rs.getInt(CAPIENZA));
			}
			// CHANGE ME!
			Set<PrenotazioneRistorante> prenotazioni = (new PrenotazioneRistoranteRepository(DataSource.DB2))
					.readByForeignKey(result.getNumeroTavolo());
			result.setPrenotazioni(prenotazioni);
			//
		} catch (SQLException e) {
			throw new PersistenceException(e.getMessage());
		} finally {
			try {
				if (statement != null)
					statement.close();
				if (connection != null) {
					connection.close();
					connection = null;
				}
			} catch (SQLException e) {
				throw new PersistenceException(e.getMessage());
			}
		}
		return result;
	}

	// UPDATE METHOD (U)
	public void update(Tavolo tavolo) throws PersistenceException {
		Connection connection = null;
		PreparedStatement statement = null;

		try {
			connection = this.dataSource.getConnection();
			statement = connection.prepareStatement(update);

			// EDIT CODE:
			statement.setInt(1, tavolo.getCapienza());
			statement.setInt(2, tavolo.getNumeroTavolo());

			// STOP EDIT CODE.
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new PersistenceException(e.getMessage());
		} finally {
			try {
				if (statement != null)
					statement.close();
				if (connection != null) {
					connection.close();
					connection = null;
				}
			} catch (SQLException e) {
				throw new PersistenceException(e.getMessage());
			}
		}
	}

	// DELETE METHOD (D)
	public void delete(int numeroTavolo) throws PersistenceException {
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = this.dataSource.getConnection();
			System.out.println(delete);
			statement = connection.prepareStatement(delete);

			// Edit if needed
			statement.setInt(1, numeroTavolo);

			statement.executeUpdate();
		} catch (SQLException e) {
			throw new PersistenceException(e.getMessage());
		}
	}

	// USEFUL METHODS

	// READ ALL
	public ArrayList<Tavolo> readAll() throws PersistenceException {
		ArrayList<Tavolo> results = new ArrayList<Tavolo>();
		Tavolo result;
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = this.dataSource.getConnection();
			statement = connection.prepareStatement(read_all);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				// CHANGE ME!
				result = new Tavolo();
				result.setNumeroTavolo(rs.getInt(NUMERO));
				result.setCapienza(rs.getInt(CAPIENZA));
				Set<PrenotazioneRistorante> prenotazioni = (new PrenotazioneRistoranteRepository(DataSource.DB2))
						.readByForeignKey(result.getNumeroTavolo());
				result.setPrenotazioni(prenotazioni);
				results.add(result);
				// CHANGE ME!
			}

		} catch (SQLException e) {
			throw new PersistenceException(e.getMessage());
		} finally {
			try {
				if (statement != null)
					statement.close();
				if (connection != null) {
					connection.close();
					connection = null;
				}
			} catch (SQLException e) {
				throw new PersistenceException(e.getMessage());
			}
		}
		return results;
	}

	// CUSTOM METHODS

	public int availableTable(Date data, int persone) throws PersistenceException {
		int result = -1;
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = this.dataSource.getConnection();
			statement = connection.prepareStatement(read_available_table);
			statement.setInt(1, persone);
			statement.setDate(2, data);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				result = rs.getInt(NUMERO);
			}
			return result;
		} catch (SQLException e) {
			throw new PersistenceException(e.getMessage());
		} finally {
			try {
				if (statement != null)
					statement.close();
				if (connection != null) {
					connection.close();
					connection = null;
				}
			} catch (SQLException e) {
				throw new PersistenceException(e.getMessage());
			}
		}
	}
}
