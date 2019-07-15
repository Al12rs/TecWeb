package dao.db2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dao.IdBroker;
import dao.SquadraPallacanestroDAO;
import dao.SquadraPallacanestroDTO;

public class Db2SquadraPallacanestroDAO implements SquadraPallacanestroDAO {

	static final String TABLE = "squadre";

	// Se relazione molti-a-uno aggiungi un id per la FK
	static final String ID = "idSquadra";
	static final String NOME = "nomeSquadra";
	static final String TORNEO = "torneo";
	static final String ALLENATORE = "nomeAllenatore";
	// altri campi...

	// == STATEMENT SQL
	// ====================================================================

	// INSERT INTO table ( id, name, description, ...) VALUES ( ?,?, ... );
	static final String insert = "INSERT " + "INTO " + TABLE + " ( " + NOME + ", " + TORNEO + ", "
			+ ALLENATORE + " " + ") " + "VALUES (?,?,?) ";

	// SELECT * FROM table WHERE idcolumn = ?;
	static String read_by_id = "SELECT * " + "FROM " + TABLE + " " + "WHERE " + ID + " = ? ";

	// SELECT * FROM table WHERE idcolumn = ?;
	static String read_by_name = "SELECT * " + "FROM " + TABLE + " " + "WHERE " + NOME + " = ? AND " + TORNEO + " = ?";

	// SELECT * FROM table WHERE stringcolumn = ?;
	static String read_all = "SELECT * " + "FROM " + TABLE + " ";

	// DELETE FROM table WHERE idcolumn = ?;
	static String delete = "DELETE " + "FROM " + TABLE + " " + "WHERE " + ID + " = ? ";

	// UPDATE table SET xxxcolumn = ?, ... WHERE idcolumn = ?;
	static String update = "UPDATE " + TABLE + " " + "SET " + NOME + " = ?, "+ TORNEO +" = ?, "+ALLENATORE+" = ? " + "WHERE " + ID + " = ? ";

	// SELECT * FROM table;
	static String query = "SELECT * " + "FROM " + TABLE + " ";

	// -------------------------------------------------------------------------------------

	// CREATE entrytable ( code INT NOT NULL PRIMARY KEY, ... );
	static String create = "CREATE " + "TABLE " + TABLE + " ( " + ID + " INT NOT NULL PRIMARY KEY " + "," + NOME
			+ " VARCHAR(50) NOT NULL" + "," + TORNEO + " VARCHAR(50) NOT NULL" + "," + ALLENATORE
			+ " VARCHAR(50) NOT NULL, " + "CONSTRAINT chiave_squadra UNIQUE (" + NOME + "," + TORNEO + ") " + ") ";

	static String drop = "DROP " + "TABLE " + TABLE + " ";

	// ------------------------------------------------------------------------------------
	// QUERY DI SUPPORTO A ID SURROGATI

	// get id surrogato
	static String read_id = "SELECT " + ID + " " + "FROM " + TABLE + " " + "WHERE " + NOME + " = ? AND " + TORNEO
			+ " = ?";

	// create table with auto-incremented id
	static String SEQNAME = "sequenza_" + ID;
	
	
	static String createAutoIncId = "CREATE " + "TABLE " + TABLE + " ( " + ID
			+ " INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT BY 1)" + "," + NOME
			+ " VARCHAR(50) NOT NULL" + "," + TORNEO + " VARCHAR(50) NOT NULL" + "," + ALLENATORE
			+ " VARCHAR(50) NOT NULL, " 
			+ "PRIMARY KEY ("+ID+"), "
			+ "CONSTRAINT chiave_squadra UNIQUE (" + NOME + "," + TORNEO + ") " + ") ";
	
	// query for sequence generated id
	static String sequence = "CREATE SEQUENCE " + SEQNAME
			+ " AS INTEGER  START WITH 1 INCREMENT BY 1MINVALUE 0 MAXVALUE 9999999NO CYCLE;";
	
	//insert_with_idbroker
	static final String insert_with_id = "INSERT " + "INTO " + TABLE + " ( "+ID +", " + NOME + ", " + TORNEO + ", "
			+ ALLENATORE + " " + ") " + "VALUES (?,?,?,?) ";

	

	// EVENTUALI ALTRE QUERY PER LO SVOLGIMENTO DEI METODI DI SUPPORTO

	// METODI CRUD

	@Override
	public void create(SquadraPallacanestroDTO squadra) {
		// --- 1. Dichiarazione della variabile per il risultato ---
		// Long result = new Long(-1);
		// --- 2. Controlli preliminari sui dati in ingresso ---
		/*
		 * if ( t == null ) { logger.warning("create(): failed to insert a null entry");
		 * return result; }
		 */
		// --- 3. Apertura della connessione ---
		Connection conn = Db2DAOFactory.createConnection();
		// --- 4. Tentativo di accesso al db e impostazione del risultato ---
		try {
			// --- a. Crea (se senza parametri) o prepara (se con parametri) lo statement
			PreparedStatement prep_stmt = conn.prepareStatement(Db2SquadraPallacanestroDAO.insert);
			// --- b. Pulisci e imposta i parametri (se ve ne sono)
			prep_stmt.clearParameters();
			prep_stmt.setString(1, squadra.getNome());
			prep_stmt.setString(2, squadra.getTorneo());
			prep_stmt.setString(3, squadra.getAllenatore());
			// --- c. Esegui l'azione sul database ed estrai il risultato (se atteso)
			prep_stmt.executeUpdate();
			// --- d. Ottieni l'id della tupla inserita
			PreparedStatement chck_stmt = conn.prepareStatement(Db2SquadraPallacanestroDAO.read_id);
			chck_stmt.setString(1, squadra.getNome());
			chck_stmt.setString(2, squadra.getTorneo());
			ResultSet rs = chck_stmt.executeQuery();
			if(rs.next()) {
				squadra.setIdSquadra(rs.getInt(Db2SquadraPallacanestroDAO.ID));
			}
			// --- f. Rilascia la struttura dati dello statement
			prep_stmt.close();
			chck_stmt.close();
		}
		// --- 5. Gestione di eventuali eccezioni ---
		catch (Exception e) {

			e.printStackTrace();
			// result = new Long(-2);
		}
		// --- 6. Rilascio, SEMPRE E COMUNQUE, la connessione prima di restituire il
		// controllo al chiamante
		finally {
			Db2DAOFactory.closeConnection(conn);
		}

	}
	
	public void createWithSequence(SquadraPallacanestroDTO squadra) {
		// --- 1. Dichiarazione della variabile per il risultato ---
		// Long result = new Long(-1);
		// --- 2. Controlli preliminari sui dati in ingresso ---
		/*
		 * if ( t == null ) { logger.warning("create(): failed to insert a null entry");
		 * return result; }
		 */
		// --- 3. Apertura della connessione ---
		Connection conn = Db2DAOFactory.createConnection();
		IdBroker idBroker = new IdBrokerSequenceDB2();
		// --- 4. Tentativo di accesso al db e impostazione del risultato ---
		try {
			// --- a. Crea (se senza parametri) o prepara (se con parametri) lo statement
			PreparedStatement prep_stmt = conn.prepareStatement(Db2SquadraPallacanestroDAO.insert_with_id);
			// --- b. Pulisci e imposta i parametri (se ve ne sono)
			prep_stmt.clearParameters();
			prep_stmt.setInt(1, idBroker.newId(Db2SquadraPallacanestroDAO.SEQNAME));
			prep_stmt.setString(2, squadra.getNome());
			prep_stmt.setString(3, squadra.getTorneo());
			prep_stmt.setString(4, squadra.getAllenatore());
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
		// --- 5. Gestione di eventuali eccezioni ---
		catch (Exception e) {

			e.printStackTrace();
			// result = new Long(-2);
		}
		// --- 6. Rilascio, SEMPRE E COMUNQUE, la connessione prima di restituire il
		// controllo al chiamante
		finally {
			Db2DAOFactory.closeConnection(conn);
		}

	}

	@Override
	public SquadraPallacanestroDTO read(int id) {
		// --- 1. Dichiarazione della variabile per il risultato ---
		SquadraPallacanestroDTO result = null;
		// --- 2. Controlli preliminari sui dati in ingresso ---
		/*
		 * if ( chiave.isEmpty() || chiave == null ) {
		 * 
		 * return result; }
		 */
		// --- 3. Apertura della connessione ---
		Connection conn = Db2DAOFactory.createConnection();
		// --- 4. Tentativo di accesso al db e impostazione del risultato ---
		try {
			// --- a. Crea (se senza parametri) o prepara (se con parametri) lo statement
			PreparedStatement prep_stmt = conn.prepareStatement(Db2SquadraPallacanestroDAO.read_by_id);
			// --- b. Pulisci e imposta i parametri (se ve ne sono)
			prep_stmt.clearParameters();
			prep_stmt.setInt(1, id);
			// --- c. Esegui l'azione sul database ed estrai il risultato (se atteso)
			ResultSet rs = prep_stmt.executeQuery();
			// --- d. Cicla sul risultato (se presente) pe accedere ai valori di ogni sua
			// tupla
			if (rs.next()) {
				SquadraPallacanestroDTO entry = new SquadraPallacanestroDTO();
				entry.setIdSquadra(rs.getInt(ID));
				entry.setNome(rs.getString(NOME));
				entry.setTorneo(rs.getString(TORNEO));
				entry.setAllenatore(rs.getString(ALLENATORE));
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
	
	public SquadraPallacanestroDTO readByName(String nome, String torneo) {
		// --- 1. Dichiarazione della variabile per il risultato ---
		SquadraPallacanestroDTO result = null;
		// --- 2. Controlli preliminari sui dati in ingresso ---
		/*
		 * if ( chiave.isEmpty() || chiave == null ) {
		 * 
		 * return result; }
		 */
		// --- 3. Apertura della connessione ---
		Connection conn = Db2DAOFactory.createConnection();
		// --- 4. Tentativo di accesso al db e impostazione del risultato ---
		try {
			// --- a. Crea (se senza parametri) o prepara (se con parametri) lo statement
			PreparedStatement prep_stmt = conn.prepareStatement(Db2SquadraPallacanestroDAO.read_by_name);
			// --- b. Pulisci e imposta i parametri (se ve ne sono)
			prep_stmt.clearParameters();
			prep_stmt.setString(1, nome);
			prep_stmt.setString(2, torneo);
			// --- c. Esegui l'azione sul database ed estrai il risultato (se atteso)
			ResultSet rs = prep_stmt.executeQuery();
			// --- d. Cicla sul risultato (se presente) pe accedere ai valori di ogni sua
			// tupla
			if (rs.next()) {
				SquadraPallacanestroDTO entry = new SquadraPallacanestroDTO();
				entry.setIdSquadra(rs.getInt(ID));
				entry.setNome(rs.getString(NOME));
				entry.setTorneo(rs.getString(TORNEO));
				entry.setAllenatore(rs.getString(ALLENATORE));
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
	public boolean update(SquadraPallacanestroDTO squadra) {
		// --- 1. Dichiarazione della variabile per il risultato ---
		boolean result = false;
		// --- 2. Controlli preliminari sui dati in ingresso ---
		if ( squadra== null )  {
			
			return result;
		}
		// --- 3. Apertura della connessione ---
		Connection conn = Db2DAOFactory.createConnection();
		// --- 4. Tentativo di accesso al db e impostazione del risultato ---
		try {
			// --- a. Crea (se senza parametri) o prepara (se con parametri) lo statement
			PreparedStatement prep_stmt = conn.prepareStatement(Db2SquadraPallacanestroDAO.update);
			// --- b. Pulisci e imposta i parametri (se ve ne sono)
			prep_stmt.clearParameters();
			
			prep_stmt.setString(1, squadra.getNome());
			prep_stmt.setString(2, squadra.getTorneo());
			prep_stmt.setString(3, squadra.getAllenatore());
			prep_stmt.setInt(4, squadra.getIdSquadra());
			// --- c. Esegui l'azione sul database ed estrai il risultato (se atteso)
			prep_stmt.executeUpdate();
			// --- d. Cicla sul risultato (se presente) pe accedere ai valori di ogni sua tupla
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
		// --- 6. Rilascio, SEMPRE E COMUNQUE, la connessione prima di restituire il controllo al chiamante
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
		/*
		 * if ( chiave.isEmpty() || chiave==null ) {
		 * 
		 * return result; }
		 */
		// --- 3. Apertura della connessione ---
		Connection conn = Db2DAOFactory.createConnection();
		// --- 4. Tentativo di accesso al db e impostazione del risultato ---
		try {
			// --- a. Crea (se senza parametri) o prepara (se con parametri) lo statement
			PreparedStatement prep_stmt = conn.prepareStatement(Db2SquadraPallacanestroDAO.delete);
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
			stmt.execute(createAutoIncId);
			// --- d. Cicla sul risultato (se presente) pe accedere ai valori di ogni sua
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
	
	public boolean createTableWithSequence() {
		// --- 1. Dichiarazione della variabile per il risultato ---
		boolean result = false;
		// --- 2. Controlli preliminari sui dati in ingresso ---
		// n.d.
		// --- 3. Apertura della connessione ---
		Connection conn = Db2DAOFactory.createConnection();
		// --- 4. Tentativo di accesso al db e impostazione del risultato ---
		try {
			conn.setAutoCommit(false);
			// --- a. Crea (se senza parametri) o prepara (se con parametri) lo statement
			Statement stmt = conn.createStatement();
			Statement sqstmt = conn.createStatement();
			// --- b. Pulisci e imposta i parametri (se ve ne sono)
			// n.d.
			// --- c. Esegui l'azione sul database ed estrai il risultato (se atteso)
			stmt.execute(create);
			sqstmt.execute(sequence);
			
			conn.commit();
			// --- d. Cicla sul risultato (se presente) pe accedere ai valori di ogni sua
			// tupla
			// n.d. Qui devo solo dire al chiamante che è andato tutto liscio
			result = true;
			// --- e. Rilascia la struttura dati del risultato
			// n.d.
			// --- f. Rilascia la struttura dati dello statement
			stmt.close();
			sqstmt.close();
			
		}
		// --- 5. Gestione di eventuali eccezioni ---
		catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		// --- 6. Rilascio, SEMPRE E COMUNQUE, la connessione prima di restituire il
		// controllo al chiamante
		finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
			// n.d. Qui devo solo dire al chiamante che è andato tutto a posto.
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

	// METODI DI SUPPORTO ALLE RICHIESTE

}
