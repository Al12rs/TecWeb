package jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import jdbc.db.DataSource;
import jdbc.db.PersistenceException;

public class PrenotazioneRistoranteRepository {

	private DataSource dataSource;

	// === Costanti letterali per non sbagliarsi a scrivere !!!
	// ============================

	static final String TABLE = "prenotazioni";

	// -------------------------------------------------------------------------------------

	static final String ID = "PrenotazioneRistoranteid";
	static final String COGNOME = "cognome";
	static final String DATA = "data";
	static final String PERSONE = "numeroPersone";
	static final String CELLULARE = "numeroCellulare";
	static final String TAVOLO = "numeroTavolo";

	// == STATEMENT SQL
	// ====================================================================

	// INSERT INTO table ( email, description, ...) VALUES ( ?,?, ... );
	static final String insert = "INSERT " + "INTO " + TABLE + " ( " + COGNOME + ", " + DATA + ", " + PERSONE + ", "
			+ CELLULARE + ", " + TAVOLO + " " + ") " + "VALUES (?,?,?,?,?) ";

	// DELETE FROM table WHERE idcolumn = ?;
	static String delete = "DELETE " + "FROM " + TABLE + " " + "WHERE " + COGNOME + " = ? AND " + DATA + " = ?";

	// UPDATE table SET xxxcolumn = ?, ... WHERE idcolumn = ?;
	static String update = "UPDATE " + TABLE + " " + "SET " + PERSONE + " = ?, " + CELLULARE + " = ?, " + TAVOLO
			+ " = ? " + " WHERE " + COGNOME + " = ? AND " + DATA + " = ?";

	// READ FROM TABLE WHERE idcolumn=?
	static String read_by_id = "SELECT * FROM " + TABLE + " WHERE " + COGNOME + " = ? AND " + DATA + " = ?";

	static String read_all = "SELECT * FROM " + TABLE;
	
	static String read_by_fk = "SELECT * FROM " + TABLE + " WHERE " + TAVOLO + " = ?";
	// -------------------------------------------------------------------------------------

	// create table
	static String create = "CREATE " + "TABLE " + TABLE + " ( " + 
			COGNOME + " VARCHAR(40) NOT NULL, " + 
			DATA + " DATE NOT NULL, "+
			PERSONE + " INT, " +
			CELLULARE + " CHAR(13), " +
			TAVOLO + " INT NOT NULL REFERENCES tavoli(numeroTavolo), "+
			"PRIMARY KEY ("+COGNOME+","+DATA+"), "+
			"CONSTRAINT pr_PranotazioneTavoloID UNIQUE ("+DATA+","+TAVOLO+") " +
			") ";

	// drop table
	static String drop = "DROP " + "TABLE " + TABLE + " ";


	// static String read_id_per_code = "SELECT " + ID + " FROM " + TABLE + " " +
	// "WHERE " + #NOME + " = ? ";

	// DO NOT EDIT:
	public PrenotazioneRistoranteRepository(int databaseType) {
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
	public void persist(PrenotazioneRistorante prenotazione) throws PersistenceException {
		Connection connection = null;
		PreparedStatement statement = null;

		try {
			connection = this.dataSource.getConnection();
			statement = connection.prepareStatement(insert);

			// EDIT CODE:
			statement.setString(1, prenotazione.getCognome());
			statement.setDate(2, prenotazione.getData());
			statement.setInt(3, prenotazione.getNumeroPersone());
			statement.setString(4, prenotazione.getCellulare());
			statement.setInt(5, prenotazione.getNumeroTavolo());

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
	public PrenotazioneRistorante readById(String cognome, Date data) throws PersistenceException {
		PrenotazioneRistorante result = new PrenotazioneRistorante();
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = this.dataSource.getConnection();
			statement = connection.prepareStatement(read_by_id);
			statement.setString(1, cognome);
			statement.setDate(2, data);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				// CHANGE ME!
				result.setCognome(rs.getString(COGNOME));
				result.setData(rs.getDate(DATA));
				result.setNumeroPersone(rs.getInt(PERSONE));
				result.setCellulare(rs.getString(CELLULARE));
				result.setNumeroTavolo(rs.getInt(TAVOLO));
			}
			// CHANGE ME!
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
	public void update(PrenotazioneRistorante prenotazione) throws PersistenceException {
		Connection connection = null;
		PreparedStatement statement = null;

		try {
			connection = this.dataSource.getConnection();
			statement = connection.prepareStatement(update);

			// EDIT CODE:
			statement.setInt(1, prenotazione.getNumeroPersone());
			statement.setString(2, prenotazione.getCellulare());
			statement.setInt(3, prenotazione.getNumeroTavolo());
			statement.setString(4, prenotazione.getCognome());
			statement.setDate(5, prenotazione.getData());
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
	public void delete(String cognome, Date data) throws PersistenceException {
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = this.dataSource.getConnection();
			System.out.println(delete);
			statement = connection.prepareStatement(delete);

			// Edit if needed
			statement.setString(1, cognome);
			statement.setDate(2, data);

			statement.executeUpdate();
		} catch (SQLException e) {
			throw new PersistenceException(e.getMessage());
		}
	}

	// USEFUL METHODS

	// READ ALL
	public ArrayList<PrenotazioneRistorante> readAll() throws PersistenceException {
		ArrayList<PrenotazioneRistorante> results = new ArrayList<PrenotazioneRistorante>();
		PrenotazioneRistorante result;
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = this.dataSource.getConnection();
			statement = connection.prepareStatement(read_all);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				// CHANGE ME!
				result = new PrenotazioneRistorante();
				result.setCognome(rs.getString(COGNOME));
				result.setData(rs.getDate(DATA));
				result.setNumeroPersone(rs.getInt(PERSONE));
				result.setCellulare(rs.getString(CELLULARE));
				result.setNumeroTavolo(rs.getInt(TAVOLO));
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
	
	//READ BY FK
	public Set<PrenotazioneRistorante> readByForeignKey(int numeroTavolo) throws PersistenceException {
		Set<PrenotazioneRistorante> result = new HashSet<PrenotazioneRistorante>();
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = this.dataSource.getConnection();
			statement = connection.prepareStatement(read_by_fk);
			statement.setInt(1, numeroTavolo);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				// CHANGE ME!
				PrenotazioneRistorante entry = new PrenotazioneRistorante();
				entry.setCognome(rs.getString(COGNOME));
				entry.setData(rs.getDate(DATA));
				entry.setNumeroPersone(rs.getInt(PERSONE));
				entry.setCellulare(rs.getString(CELLULARE));
				entry.setNumeroTavolo(rs.getInt(TAVOLO));
				result.add(entry);
			}
			// CHANGE ME!
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

	// CUSTOM METHODS

}
