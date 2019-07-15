package it.unibo.tw.dao.db2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import it.unibo.tw.dao.PiattoDTO;
import it.unibo.tw.dao.RistoranteDAO;
import it.unibo.tw.dao.RistoranteDTO;

public class Db2RistoranteDAO implements RistoranteDAO {

	// === Costanti letterali per non sbagliarsi a scrivere !!!
	// ============================

	static final String TABLE = "ristoranti";

	// -------------------------------------------------------------------------------------

	static final String ID = "id";
	static final String NOMERISTORANTE = "nomeRistorante";
	static final String INDIRIZZO = "indirizzo";
	static final String RATING = "rating";

	// == STATEMENT SQL
	// ====================================================================

	// INSERT INTO table ( id, name, description, ...) VALUES ( ?,?, ... );
	static final String insert = "INSERT " + "INTO " + TABLE + " ( " + ID + ", " + NOMERISTORANTE + ", " + INDIRIZZO
			+ ", " + RATING + ") " + "VALUES (?,?,?,?) ";

	// SELECT * FROM table WHERE idcolumn = ?;
	static String read_by_id = "SELECT * " + "FROM " + TABLE + " " + "WHERE " + ID + " = ? ";

	// SELECT * FROM table WHERE stringcolumn = ?;
	static String read_all = "SELECT * " + "FROM " + TABLE + " ";

	// SELECT * FROM table WHERE stringcolumn = ?;
	static String find_restaurant_over_rate = read_all + "WHERE " + RATING + " >= ? ";

	// DELETE FROM table WHERE idcolumn = ?;
	static String delete = "DELETE " + "FROM " + TABLE + " " + "WHERE " + ID + " = ? ";

	// UPDATE table SET xxxcolumn = ?, ... WHERE idcolumn = ?;
	static String update = "UPDATE " + TABLE + " " + "SET " + NOMERISTORANTE + " = ?, " + INDIRIZZO + " = ?, " + RATING
			+ " = ?, " + "WHERE " + ID + " = ? ";

	// SELECT * FROM table;
	static String query = "SELECT * " + "FROM " + TABLE + " ";

	// -------------------------------------------------------------------------------------

	// CREATE entrytable ( code INT NOT NULL PRIMARY KEY, ... );
	static String create = "CREATE " + "TABLE " + TABLE + " ( " + ID + " INT NOT NULL PRIMARY KEY, " + NOMERISTORANTE
			+ " VARCHAR(50) NOT NULL UNIQUE, " + INDIRIZZO + " VARCHAR(50), " + RATING + " INT ) ";

	static String drop = "DROP " + "TABLE " + TABLE + " ";

	// === METODI DAO
	// =========================================================================

	@Override
	public void create(RistoranteDTO ristorante) {
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
			PreparedStatement prep_stmt = conn.prepareStatement(Db2RistoranteDAO.insert);
			// --- b. Pulisci e imposta i parametri (se ve ne sono)
			prep_stmt.clearParameters();
			prep_stmt.setInt(1, ristorante.getId());
			prep_stmt.setString(2, ristorante.getNomeRistorante());
			prep_stmt.setString(3, ristorante.getIndirizzo());
			prep_stmt.setInt(4, ristorante.getRating());
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
	public RistoranteDTO read(int id) {
		// --- 1. Dichiarazione della variabile per il risultato ---
		RistoranteDTO result = null;
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
				RistoranteDTO entry = new RistoranteDTO();
				entry.setId(rs.getInt(ID));
				entry.setNomeRistorante(rs.getString(NOMERISTORANTE));
				entry.setIndirizzo(rs.getString(INDIRIZZO));
				entry.setRating(rs.getInt(RATING));
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
	public boolean update(RistoranteDTO ristorante) {
		// --- 1. Dichiarazione della variabile per il risultato ---
		boolean result = false;
		// --- 2. Controlli preliminari sui dati in ingresso ---
		if (ristorante == null) {
			return result;
		}
		// --- 3. Apertura della connessione ---
		Connection conn = Db2DAOFactory.createConnection();
		// --- 4. Tentativo di accesso al db e impostazione del risultato ---
		try {
			// --- a. Crea (se senza parametri) o prepara (se con parametri) lo statement
			PreparedStatement prep_stmt = conn.prepareStatement(Db2RistoranteDAO.update);
			// --- b. Pulisci e imposta i parametri (se ve ne sono)
			prep_stmt.clearParameters();
			prep_stmt.setString(1, ristorante.getNomeRistorante());
			prep_stmt.setString(2, ristorante.getIndirizzo());
			prep_stmt.setInt(3, ristorante.getRating());
			prep_stmt.setInt(4, ristorante.getId());
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
			PreparedStatement prep_stmt = conn.prepareStatement(Db2RistoranteDAO.delete);
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
	public List<RistoranteDTO> getRestaurantByCity(String city) {
		// --- 1. Dichiarazione della variabile per il risultato ---
		List<RistoranteDTO> result = new ArrayList<RistoranteDTO>();
		// --- 2. Controlli preliminari sui dati in ingresso ---
		if (city == null) {
			return result;
		}
		// --- 3. Apertura della connessione ---
		Connection conn = Db2DAOFactory.createConnection();
		// --- 4. Tentativo di accesso al db e impostazione del risultato ---
		try {
			// --- a. Crea (se senza parametri) o prepara (se con parametri) lo statement
			PreparedStatement prep_stmt = conn.prepareStatement(query);
			// --- b. Pulisci e imposta i parametri (se ve ne sono)
			prep_stmt.clearParameters();
			// --- c. Esegui l'azione sul database ed estrai il risultato (se atteso)
			ResultSet rs = prep_stmt.executeQuery();
			// --- d. Cicla sul risultato (se presente) pe accedere ai valori di ogni sua
			// tupla
			while (rs.next()) {
				RistoranteDTO entry = new RistoranteDTO();
				entry.setId(rs.getInt(ID));
				entry.setNomeRistorante(rs.getString(NOMERISTORANTE));
				entry.setIndirizzo(rs.getString(INDIRIZZO));
				entry.setRating(rs.getInt(RATING));
				if (entry.getIndirizzo().toLowerCase().contains(city.toLowerCase())) {
					result.add(entry);
				}

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
	public List<RistoranteDTO> getRatedRestaurant(int stars) {
		// --- 1. Dichiarazione della variabile per il risultato ---
		List<RistoranteDTO> result = new ArrayList<RistoranteDTO>();
		// --- 2. Controlli preliminari sui dati in ingresso ---
		if (stars < 0) {
			return result;
		}
		// --- 3. Apertura della connessione ---
		Connection conn = Db2DAOFactory.createConnection();
		// --- 4. Tentativo di accesso al db e impostazione del risultato ---
		try {
			// --- a. Crea (se senza parametri) o prepara (se con parametri) lo statement
			PreparedStatement prep_stmt = conn.prepareStatement(find_restaurant_over_rate);
			// --- b. Pulisci e imposta i parametri (se ve ne sono)
			prep_stmt.clearParameters();
			prep_stmt.setInt(1, stars);
			// --- c. Esegui l'azione sul database ed estrai il risultato (se atteso)
			ResultSet rs = prep_stmt.executeQuery();
			// --- d. Cicla sul risultato (se presente) pe accedere ai valori di ogni sua
			// tupla
			while (rs.next()) {
				RistoranteDTO entry = new RistoranteDTO();
				entry.setId(rs.getInt(ID));
				entry.setNomeRistorante(rs.getString(NOMERISTORANTE));
				entry.setIndirizzo(rs.getString(INDIRIZZO));
				entry.setRating(rs.getInt(RATING));
				result.add(entry);
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
