package it.unibo.tw.dao.db2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import it.unibo.tw.dao.PiattoDAO;
import it.unibo.tw.dao.PiattoDTO;
import it.unibo.tw.dao.StudentDTO;

public class Db2PiattoDAO implements PiattoDAO {

	// === Costanti letterali per non sbagliarsi a scrivere !!!
	// ============================

	static final String TABLE = "piatti";

	// -------------------------------------------------------------------------------------

	static final String ID = "id";
	static final String NOMEPIATTO = "nomePiatto";
	static final String TIPO = "tipo";

	// == STATEMENT SQL
	// ====================================================================

	// INSERT INTO table ( id, name, description, ...) VALUES ( ?,?, ... );
	static final String insert = "INSERT " + "INTO " + TABLE + " ( " + ID + ", " + NOMEPIATTO + ", " + TIPO + " " + ") "
			+ "VALUES (?,?,?) ";

	// SELECT * FROM table WHERE idcolumn = ?;
	static String read_by_id = "SELECT * " + "FROM " + TABLE + " " + "WHERE " + ID + " = ? ";

	// SELECT * FROM table WHERE stringcolumn = ?;
	static String read_all = "SELECT * " + "FROM " + TABLE + " ";

	// DELETE FROM table WHERE idcolumn = ?;
	static String delete = "DELETE " + "FROM " + TABLE + " " + "WHERE " + ID + " = ? ";

	// UPDATE table SET xxxcolumn = ?, ... WHERE idcolumn = ?;
	static String update = "UPDATE " + TABLE + " " + "SET " + NOMEPIATTO + " = ?, " + TIPO + " = ?, " + "WHERE " + ID
			+ " = ? ";

	// SELECT * FROM table;
	static String query = "SELECT * " + "FROM " + TABLE + " ";

	// -------------------------------------------------------------------------------------

	// CREATE entrytable ( code INT NOT NULL PRIMARY KEY, ... );
	static String create = "CREATE " + "TABLE " + TABLE + " ( " + ID + " INT NOT NULL PRIMARY KEY, " + NOMEPIATTO
			+ " VARCHAR(50) NOT NULL UNIQUE, " + TIPO + " VARCHAR(50) " + ") ";

	static String drop = "DROP " + "TABLE " + TABLE + " ";

	// === METODI DAO
	// =========================================================================

	@Override
	public void create(PiattoDTO piatto) {
		// --- 1. Controlli preliminari sui dati in ingresso ---
		/*
		 * if ( t == null ) { logger.warning("create(): failed to insert a null entry");
		 * return result; }
		 */
		// --- 2. Apertura della connessione ---
		Connection conn = Db2DAOFactory.createConnection();
		// --- 3. Tentativo di accesso al db e impostazione del risultato ---
		try {
			// --- a. Crea (se senza parametri) o prepara (se con parametri) lo statement
			PreparedStatement prep_stmt = conn.prepareStatement(Db2PiattoDAO.insert);
			// --- b. Pulisci e imposta i parametri (se ve ne sono)
			prep_stmt.clearParameters();
			prep_stmt.setInt(1, piatto.getId());
			prep_stmt.setString(2, piatto.getNomePiatto());
			prep_stmt.setString(3, piatto.getTipo());
			// --- c. Esegui l'azione sul database ed estrai il risultato (se atteso)
			prep_stmt.executeUpdate();
			// --- d. Cicla sul risultato (se presente) pe accedere ai valori di ogni sua
			// tupla
			// n.d.
			// --- e. Rilascia la struttura dati del risultato
			// n.d.
			// --- f. Rilascia la struttura dati dello statement
			prep_stmt.close();
		}
		// --- 4. Gestione di eventuali eccezioni ---
		catch (Exception e) {
			e.printStackTrace();
		} finally {
			Db2DAOFactory.closeConnection(conn);
		}

	}

	@Override
	public PiattoDTO read(int id) {
		// --- 1. Dichiarazione della variabile per il risultato ---
		PiattoDTO result = null;
		// --- 2. Controlli preliminari sui dati in ingresso ---
		if (id < 0) {
			return result;
		}
		// --- 3. Apertura della connessione ---
		Connection conn = Db2DAOFactory.createConnection();
		// --- 4. Tentativo di accesso al db e impostazione del risultato ---
		try {
			// --- a. Crea (se senza parametri) o prepara (se con parametri) lo statement
			PreparedStatement prep_stmt = conn.prepareStatement(read_by_id);
			// --- b. Pulisci e imposta i parametri (se ve ne sono)
			prep_stmt.clearParameters();
			prep_stmt.setInt(1, id);
			// --- c. Esegui l'azione sul database ed estrai il risultato (se atteso)
			ResultSet rs = prep_stmt.executeQuery();
			// --- d. Cicla sul risultato (se presente) pe accedere ai valori di ogni sua
			// tupla
			if (rs.next()) {
				PiattoDTO entry = new PiattoDTO();
				entry.setId(rs.getInt(ID));
				entry.setNomePiatto(rs.getString(NOMEPIATTO));
				entry.setTipo(rs.getString(TIPO));
				result = entry;
			}
			// --- e. Rilascia la struttura dati del risultato
			rs.close();
			// --- f. Rilascia la struttura dati dello statement
			prep_stmt.close();
		}
		// --- 5. Gestione di eventuali eccezioni ---
		catch (Exception e) {
			e.printStackTrace();
		}
		// --- 6. Rilascio, SEMPRE E COMUNQUE, la connessione prima di restituire il
		// controllo al chiamante
		finally {
			Db2DAOFactory.closeConnection(conn);
		}
		// --- 7. Restituzione del risultato (eventualmente di fallimento)
		return result;
	}

	@Override
	public boolean update(PiattoDTO piatto) {
		// --- 1. Dichiarazione della variabile per il risultato ---
		boolean result = false;
		// --- 2. Controlli preliminari sui dati in ingresso ---
		if (piatto == null) {
			return result;
		}
		// --- 3. Apertura della connessione ---
		Connection conn = Db2DAOFactory.createConnection();
		// --- 4. Tentativo di accesso al db e impostazione del risultato ---
		try {
			// --- a. Crea (se senza parametri) o prepara (se con parametri) lo statement
			PreparedStatement prep_stmt = conn.prepareStatement(Db2PiattoDAO.update);
			// --- b. Pulisci e imposta i parametri (se ve ne sono)
			prep_stmt.clearParameters();
			prep_stmt.setString(1, piatto.getNomePiatto());
			prep_stmt.setString(2, piatto.getTipo());
			prep_stmt.setInt(3, piatto.getId());
			// --- c. Esegui l'azione sul database ed estrai il risultato (se atteso)
			prep_stmt.executeUpdate();
			// --- d. Cicla sul risultato (se presente) pe accedere ai valori di ogni sua
			// tupla
			// n.d. qui devo solo dire al chiamante che e' andato tutto liscio
			result = true;
			// --- e. Rilascia la struttura dati del risultato
			// n.d.
			// --- f. Rilascia la struttura dati dello statement
			prep_stmt.close();
		}
		// --- 5. Gestione di eventuali eccezioni ---
		catch (Exception e) {
			e.printStackTrace();
		}
		// --- 6. Rilascio, SEMPRE E COMUNQUE, la connessione prima di restituire il
		// controllo al chiamante
		finally {
			Db2DAOFactory.closeConnection(conn);
		}
		// --- 7. Restituzione del risultato (eventualmente di fallimento)
		return result;

	}

	@Override
	public boolean delete(int id) {
		// --- 1. Dichiarazione della variabile per il risultato ---
		boolean result = false;
		// --- 2. Controlli preliminari sui dati in ingresso ---
		// if ( code == null || code < 0 ) {
		if (id < 0) {
			return result;
		}
		// --- 3. Apertura della connessione ---
		Connection conn = Db2DAOFactory.createConnection();
		// --- 4. Tentativo di accesso al db e impostazione del risultato ---
		try {
			// --- a. Crea (se senza parametri) o prepara (se con parametri) lo statement
			PreparedStatement prep_stmt = conn.prepareStatement(Db2PiattoDAO.delete);
			// --- b. Pulisci e imposta i parametri (se ve ne sono)
			prep_stmt.clearParameters();
			prep_stmt.setInt(1, id);
			// --- c. Esegui l'azione sul database ed estrai il risultato (se atteso)
			prep_stmt.executeUpdate();
			// --- d. Cicla sul risultato (se presente) pe accedere ai valori di ogni sua
			// tupla
			// n.d. Qui devo solo dire al chiamante che e' andato tutto liscio
			result = true;
			// --- e. Rilascia la struttura dati del risultato
			// n.d.
			// --- f. Rilascia la struttura dati dello statement
			prep_stmt.close();
		}
		// --- 5. Gestione di eventuali eccezioni ---
		catch (Exception e) {
			e.printStackTrace();
		}
		// --- 6. Rilascio, SEMPRE E COMUNQUE, la connessione prima di restituire il
		// controllo al chiamante
		finally {
			Db2DAOFactory.closeConnection(conn);
		}
		// --- 7. Restituzione del risultato (eventualmente di fallimento)
		return result;
	}

	@Override
	public boolean createTable() {
		// --- 1. Dichiarazione della variabile per il risultato ---
		boolean result = false;
		// --- 2. Controlli preliminari sui dati in ingresso ---
		// n.d.
		// --- 3. Apertura della connessione ---
		Connection conn = Db2DAOFactory.createConnection();
		// --- 4. Tentativo di accesso al db e impostazione del risultato ---
		try {
			// --- a. Crea (se senza parametri) o prepara (se con parametri) lo statement
			Statement stmt = conn.createStatement();
			// --- b. Pulisci e imposta i parametri (se ve ne sono)
			// n.d.
			// --- c. Esegui l'azione sul database ed estrai il risultato (se atteso)
			stmt.execute(create);
			// --- d. Cicla sul risultato (se presente) per accedere ai valori di ogni sua
			// tupla
			// n.d. Qui devo solo dire al chiamante che è andato tutto liscio
			result = true;
			// --- e. Rilascia la struttura dati del risultato
			// n.d.
			// --- f. Rilascia la struttura dati dello statement
			stmt.close();
		}
		// --- 5. Gestione di eventuali eccezioni ---
		catch (Exception e) {
			e.printStackTrace();
		}
		// --- 6. Rilascio, SEMPRE E COMUNQUE, la connessione prima di restituire il
		// controllo al chiamante
		finally {
			Db2DAOFactory.closeConnection(conn);
		}
		// --- 7. Restituzione del risultato (eventualmente di fallimento)
		return result;
	}

	@Override
	public boolean dropTable() {
		// --- 1. Dichiarazione della variabile per il risultato ---
		boolean result = false;
		// --- 2. Controlli preliminari sui dati in ingresso ---
		// n.d.
		// --- 3. Apertura della connessione ---
		Connection conn = Db2DAOFactory.createConnection();
		// --- 4. Tentativo di accesso al db e impostazione del risultato ---
		try {
			// --- a. Crea (se senza parametri) o prepara (se con parametri) lo statement
			Statement stmt = conn.createStatement();
			// --- b. Pulisci e imposta i parametri (se ve ne sono)
			// n.d.
			// --- c. Esegui l'azione sul database ed estrai il risultato (se atteso)
			stmt.execute(drop);
			// --- d. Cicla sul risultato (se presente) pe accedere ai valori di ogni sua
			// tupla
			// n.d. Qui devo solo dire al chiamante che Ã¨ andato tutto a posto.
			result = true;
			// --- e. Rilascia la struttura dati del risultato
			// n.d.
			// --- f. Rilascia la struttura dati dello statement
			stmt.close();
		}
		// --- 5. Gestione di eventuali eccezioni ---
		catch (Exception e) {
			e.printStackTrace();
		}
		// --- 6. Rilascio, SEMPRE E COMUNQUE, la connessione prima di restituire il
		// controllo al chiamante
		finally {
			Db2DAOFactory.closeConnection(conn);
		}
		// --- 7. Restituzione del risultato (eventualmente di fallimento)
		return result;
	}

}
