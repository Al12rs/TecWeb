package jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import jdbc.db.DataSource;
import jdbc.db.PersistenceException;

public class AcquistoRepository {

	private DataSource dataSource;

	// === Costanti letterali per non sbagliarsi a scrivere !!!
	// ============================

	static final String TABLE = "acquisto";

	// -------------------------------------------------------------------------------------

	static final String ID = "idAcquisto";
	static final String CODICE = "codiceAcquisto";
	static final String IMPORTO = "importo";
	static final String NOME = "nomeAcquirente";
	static final String COGNOME = "cognomeAcquirente";

	// == STATEMENT SQL
	// ====================================================================

	// INSERT INTO table ( email, description, ...) VALUES ( ?,?, ... );
	static final String insert = "INSERT " + "INTO " + TABLE + " ( " + ID + ", " + CODICE + ", " + IMPORTO + ", " + NOME
			+ ", " + COGNOME + " " + ") " + "VALUES (?,?,?,?,?) ";

	// DELETE FROM table WHERE idcolumn = ?;
	static String delete = "DELETE " + "FROM " + TABLE + " " + "WHERE " + ID + " = ? ";

	// UPDATE table SET xxxcolumn = ?, ... WHERE idcolumn = ?;
	static String update = "UPDATE " + TABLE + " " + "SET " + CODICE + " = ?, " + IMPORTO + " = ?, " + NOME + " = ?, "
			+ COGNOME + " = ? " + "WHERE " + ID + " = ? ";

	// READ FROM TABLE WHERE idcolumn=?
	private static String read_by_id = "SELECT * FROM " + TABLE + " WHERE " + ID + "=? ";

	// READ ALL
	private static String read_all = "SELECT * FROM " + TABLE;
	// -------------------------------------------------------------------------------------

	// create table
	private static String create = "CREATE TABLE " + TABLE + " ( " + ID + " INT NOT NULL PRIMARY KEY, " + CODICE
			+ " CHAR(5) NOT NULL UNIQUE, " + IMPORTO + " FLOAT, " + NOME + " VARCHAR(32), " + COGNOME + " VARCHAR(32) "
			+ ") ";

	// drop table
	private static String drop = "DROP " + "TABLE " + TABLE + " ";

	// -------------------------------------------------------------------------------------
	// USEFUL QUERY

	static String read_by_codice = "SELECT * " + " FROM " + TABLE + " " + "WHERE " + CODICE + " = ? ";

	static String delete_by_codice = "DELETE " + " FROM " + TABLE + " " + "WHERE " + CODICE + " = ? ";

	/*
	 * //READ BY FK (USATO DA ALTRA TABELLA PER OTTENERE IL SET DI QUESTI ELEMENTI
	 * CHE CONTENGONO UNA DETERMINATA FK) static String read__by_fk = "SELECT * " +
	 * " FROM " + TABLE + " " + "WHERE " + FK + " = ? ";
	 */

	/*
	 * //READ_ID CON ID SURROGATI AUTOINCREMENTATI static String read_id_per_code =
	 * "SELECT " + ID + " FROM " + TABLE + " " + "WHERE " + CODICE + " = ? ";
	 */

	// -------------------------------------------------------------------------------------
	// CUSTOM QUERY
	
	//read importo > soglia
	static String read_by_threshold = "SELECT * " + " FROM " + TABLE + " " + "WHERE " + IMPORTO + " >= ? ";
	
	// -------------------------------------------------------------------------------------

	// DO NOT EDIT:
	public AcquistoRepository(int databaseType) {
		dataSource = new DataSource(databaseType);
	}

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
	public void persist(Acquisto acquisto) throws PersistenceException {
		Connection connection = null;
		PreparedStatement statement = null;

		try {
			connection = this.dataSource.getConnection();
			statement = connection.prepareStatement(insert);

			// EDIT CODE:
			statement.setInt(1, acquisto.getIdAcquisto());
			statement.setString(2, acquisto.getCodiceAcquisto());
			statement.setDouble(3, acquisto.getImporto());
			statement.setString(4, acquisto.getNomeAcquirente());
			statement.setString(5, acquisto.getCognomeAcquirente());
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
	public Acquisto readById(int id) throws PersistenceException {
		Acquisto result = new Acquisto();
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = this.dataSource.getConnection();
			statement = connection.prepareStatement(read_by_id);
			statement.setInt(1, id);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				// CHANGE ME!
				result.setIdAcquisto(rs.getInt(ID));
				result.setCodiceAcquisto(rs.getString(CODICE));
				result.setImporto(rs.getDouble(IMPORTO));
				result.setNomeAcquirente(rs.getString(NOME));
				result.setCognomeAcquirente(rs.getString(COGNOME));
			}
			// CHANGE ME!
			/*
			 * Nel caso siano presenti dei set nel bean Set<PrenotazioneRistorante>
			 * prenotazioni = (new PrenotazioneRistoranteRepository(DataSource.DB2))
			 * .readByForeignKey(result.getNumeroAcquisto());
			 * result.setPrenotazioni(prenotazioni);
			 */
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
	public void update(Acquisto acquisto) throws PersistenceException {
		Connection connection = null;
		PreparedStatement statement = null;

		try {
			connection = this.dataSource.getConnection();
			statement = connection.prepareStatement(update);

			// EDIT CODE:
			statement.setString(1, acquisto.getCodiceAcquisto());
			statement.setDouble(2, acquisto.getImporto());
			statement.setString(3, acquisto.getNomeAcquirente());
			statement.setString(4, acquisto.getCognomeAcquirente());
			statement.setInt(4, acquisto.getIdAcquisto());
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
	public void delete(int id) throws PersistenceException {
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = this.dataSource.getConnection();
			System.out.println(delete);
			statement = connection.prepareStatement(delete);

			// Edit if needed
			statement.setInt(1, id);

			statement.executeUpdate();
		} catch (SQLException e) {
			throw new PersistenceException(e.getMessage());
		}
	}

	// -------------------------------------------------------------------------------------
	// USEFUL METHODS

	// READ ALL
	public ArrayList<Acquisto> readAll() throws PersistenceException {
		ArrayList<Acquisto> results = new ArrayList<Acquisto>();
		Acquisto result = null;
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = this.dataSource.getConnection();
			statement = connection.prepareStatement(read_all);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				// CHANGE ME!
				result = new Acquisto();
				result.setIdAcquisto(rs.getInt(ID));
				result.setCodiceAcquisto(rs.getString(CODICE));
				result.setImporto(rs.getDouble(IMPORTO));
				result.setNomeAcquirente(rs.getString(NOME));
				result.setCognomeAcquirente(rs.getString(COGNOME));
				/*
				 * nel caso contenga set Set<PrenotazioneRistorante> prenotazioni = (new
				 * PrenotazioneRistoranteRepository(DataSource.DB2))
				 * .readByForeignKey(result.getNumeroAcquisto());
				 * result.setPrenotazioni(prenotazioni);
				 */
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

	// READ_BY_ATTR OR FK(per fk restituire un set)
	public Acquisto readByCodice(String codice) throws PersistenceException {
		Acquisto result = new Acquisto();
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = this.dataSource.getConnection();
			statement = connection.prepareStatement(read_by_codice);
			statement.setString(1, codice);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				// CHANGE ME!
				result.setIdAcquisto(rs.getInt(ID));
				result.setCodiceAcquisto(rs.getString(CODICE));
				result.setImporto(rs.getDouble(IMPORTO));
				result.setNomeAcquirente(rs.getString(NOME));
				result.setCognomeAcquirente(rs.getString(COGNOME));
			}
			// CHANGE ME!
			/*
			 * Nel caso siano presenti dei set nel bean Set<PrenotazioneRistorante>
			 * prenotazioni = (new PrenotazioneRistoranteRepository(DataSource.DB2))
			 * .readByForeignKey(result.getNumeroAcquisto());
			 * result.setPrenotazioni(prenotazioni);
			 */
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

	// DELETE_BY_ATTR
	public void delete(String codice) throws PersistenceException {
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = this.dataSource.getConnection();
			System.out.println(delete);
			statement = connection.prepareStatement(delete_by_codice);

			// Edit if needed
			statement.setString(1, codice);

			statement.executeUpdate();
		} catch (SQLException e) {
			throw new PersistenceException(e.getMessage());
		}
	}
	
	// -------------------------------------------------------------------------------------
	//CUSTOM METHODS
	
	//acquisti di importo superiore alla soglia specificata in input
	public Set<String> AcquistoSoglia(double soglia) throws PersistenceException{
		Set<String> results = new HashSet<String>();
		Acquisto result = null;
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = this.dataSource.getConnection();
			statement = connection.prepareStatement(read_by_threshold);
			statement.setDouble(1, soglia);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				// CHANGE ME!
				result = new Acquisto();
				result.setIdAcquisto(rs.getInt(ID));
				result.setCodiceAcquisto(rs.getString(CODICE));
				result.setImporto(rs.getDouble(IMPORTO));
				result.setNomeAcquirente(rs.getString(NOME));
				result.setCognomeAcquirente(rs.getString(COGNOME));
				/*
				 * nel caso contenga set Set<PrenotazioneRistorante> prenotazioni = (new
				 * PrenotazioneRistoranteRepository(DataSource.DB2))
				 * .readByForeignKey(result.getNumeroAcquisto());
				 * result.setPrenotazioni(prenotazioni);
				 */
				results.add(result.getCodiceAcquisto());
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

}
