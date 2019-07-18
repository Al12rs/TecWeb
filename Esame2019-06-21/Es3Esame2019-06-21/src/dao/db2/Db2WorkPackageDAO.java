package dao.db2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import dao.WorkPackageDAO;
import dao.WorkPackageDTO;

public class Db2WorkPackageDAO implements WorkPackageDAO {

	static final String TABLE = "workpackages";

	// Se relazione molti-a-uno aggiungi un id per la FK
	static final String ID = "idPackage";
	static final String NOME = "nomeWP";
	static final String TITOLO = "titolo";
	static final String DESCRIZIONE = "descrizione";
	static final String PROGETTO = "idProgetto";
	// altri campi...

	// == STATEMENT SQL
	// ====================================================================

	// INSERT INTO table ( id, name, description, ...) VALUES ( ?,?, ... );
	static final String insert = "INSERT " + "INTO " + TABLE + " ( " + ID + ", " + NOME + ", " + TITOLO + ", "
			+ DESCRIZIONE + ", " + PROGETTO + " " + ") " + "VALUES (?,?,?,?,?) ";

	// SELECT * FROM table WHERE idcolumn = ?;
	static String read_by_id = "SELECT * " + "FROM " + TABLE + " " + "WHERE " + ID + " = ? ";

	// SELECT * FROM table WHERE idcolumn = ?;
	static String read_by_name = "SELECT * " + "FROM " + TABLE + " " + "WHERE " + NOME + " = ? ";

	// SELECT * FROM table WHERE FK = ?;
	static String read_by_fk = "SELECT * " + "FROM " + TABLE + " " + "WHERE " + PROGETTO + " = ? ";

	// SELECT * FROM table WHERE stringcolumn = ?;
	static String read_all = "SELECT * " + "FROM " + TABLE + " ";

	// DELETE FROM table WHERE idcolumn = ?;
	static String delete = "DELETE " + "FROM " + TABLE + " " + "WHERE " + ID + " = ? ";

	// UPDATE table SET xxxcolumn = ?, ... WHERE idcolumn = ?;
	static String update = "UPDATE " + TABLE + " " + "SET " + NOME + " = ?, " + TITOLO + " = ?, " + DESCRIZIONE
			+ " = ?, " + PROGETTO + " = ? " + "WHERE " + ID + " = ? ";

	// SELECT * FROM table;
	static String query = "SELECT * " + "FROM " + TABLE + " ";

	// -------------------------------------------------------------------------------------

	// CREATE entrytable ( code INT NOT NULL PRIMARY KEY, ... );
	static String create = "CREATE " + "TABLE " + TABLE + " ( " + ID + " INT NOT NULL PRIMARY KEY " + "," + NOME
			+ " VARCHAR(50) NOT NULL UNIQUE, " + TITOLO + " VARCHAR(50), " + DESCRIZIONE + " VARCHAR(50), " + PROGETTO
			+ " INT NOT NULL REFERENCES " + Db2ProgettoDAO.TABLE + "(" + Db2ProgettoDAO.ID + ")" + ") ";
	// for fks:
	// id_citta INT NOT NULL REFERENCES citta(id)

	static String drop = "DROP " + "TABLE " + TABLE + " ";

	// EVENTUALI ALTRE QUERY PER LO SVOLGIMENTO DEI METODI DI SUPPORTO

	// METODI CRUD

	@Override
	public void create(WorkPackageDTO workpackage) {
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
			PreparedStatement prep_stmt = conn.prepareStatement(Db2WorkPackageDAO.insert);
			// --- b. Pulisci e imposta i parametri (se ve ne sono)
			prep_stmt.clearParameters();
			prep_stmt.setInt(1, workpackage.getIdPackage());
			prep_stmt.setString(2, workpackage.getNomeWP());
			prep_stmt.setString(3, workpackage.getTitolo());
			prep_stmt.setString(4, workpackage.getDescrizione());
			prep_stmt.setInt(5, workpackage.getProgetto().getIdProgetto());
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
	public WorkPackageDTO read(int id) {
		// --- 1. Dichiarazione della variabile per il risultato ---
		WorkPackageDTO result = null;
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
			PreparedStatement prep_stmt = conn.prepareStatement(Db2WorkPackageDAO.read_by_id);
			// --- b. Pulisci e imposta i parametri (se ve ne sono)
			prep_stmt.clearParameters();
			prep_stmt.setInt(1, id);
			// --- c. Esegui l'azione sul database ed estrai il risultato (se atteso)
			ResultSet rs = prep_stmt.executeQuery();
			// --- d. Cicla sul risultato (se presente) pe accedere ai valori di ogni sua
			// tupla
			if (rs.next()) {
				WorkPackageDTO entry = new Db2WorkPackageDTOProxy();
				entry.setIdPackage(rs.getInt(ID));
				entry.setNomeWP(rs.getString(NOME));
				entry.setTitolo(rs.getString(TITOLO));
				entry.setDescrizione(rs.getString(DESCRIZIONE));
				entry.setProgetto((new Db2ProgettoDAO()).read(rs.getInt(PROGETTO)));
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

	public Set<WorkPackageDTO> readByIdProgetto(int id) {
		// --- 1. Dichiarazione della variabile per il risultato ---
		Set<WorkPackageDTO> result = new HashSet<WorkPackageDTO>();
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
			PreparedStatement prep_stmt = conn.prepareStatement(Db2WorkPackageDAO.read_by_fk);
			// --- b. Pulisci e imposta i parametri (se ve ne sono)
			prep_stmt.clearParameters();
			prep_stmt.setInt(1, id);
			// --- c. Esegui l'azione sul database ed estrai il risultato (se atteso)
			ResultSet rs = prep_stmt.executeQuery();
			// --- d. Cicla sul risultato (se presente) pe accedere ai valori di ogni sua
			// tupla
			while (rs.next()) {
				WorkPackageDTO entry = new Db2WorkPackageDTOProxy();
				entry.setIdPackage(rs.getInt(ID));
				entry.setNomeWP(rs.getString(NOME));
				entry.setTitolo(rs.getString(TITOLO));
				entry.setDescrizione(rs.getString(DESCRIZIONE));
				entry.setProgetto((new Db2ProgettoDAO()).read(rs.getInt(PROGETTO)));
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

	public Set<WorkPackageDTO> readAll() {
		// --- 1. Dichiarazione della variabile per il risultato ---
		Set<WorkPackageDTO> result = new HashSet<WorkPackageDTO>();
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
			PreparedStatement prep_stmt = conn.prepareStatement(Db2WorkPackageDAO.read_all);
			// --- b. Pulisci e imposta i parametri (se ve ne sono)
			prep_stmt.clearParameters();
			// --- c. Esegui l'azione sul database ed estrai il risultato (se atteso)
			ResultSet rs = prep_stmt.executeQuery();
			// --- d. Cicla sul risultato (se presente) pe accedere ai valori di ogni sua
			// tupla
			while (rs.next()) {
				WorkPackageDTO entry = new Db2WorkPackageDTOProxy();
				entry.setIdPackage(rs.getInt(ID));
				entry.setNomeWP(rs.getString(NOME));
				entry.setTitolo(rs.getString(TITOLO));
				entry.setDescrizione(rs.getString(DESCRIZIONE));
				entry.setProgetto((new Db2ProgettoDAO()).read(rs.getInt(PROGETTO)));
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
	public boolean update(WorkPackageDTO workpackage) {
		// --- 1. Dichiarazione della variabile per il risultato ---
		boolean result = false;
		// --- 2. Controlli preliminari sui dati in ingresso ---
		if (workpackage == null) {

			return result;
		}
		// --- 3. Apertura della connessione ---
		Connection conn = Db2DAOFactory.createConnection();
		// --- 4. Tentativo di accesso al db e impostazione del risultato ---
		try {
			// --- a. Crea (se senza parametri) o prepara (se con parametri) lo statement
			PreparedStatement prep_stmt = conn.prepareStatement(Db2WorkPackageDAO.update);
			// --- b. Pulisci e imposta i parametri (se ve ne sono)
			prep_stmt.clearParameters();

			prep_stmt.setString(1, workpackage.getNomeWP());
			prep_stmt.setString(2, workpackage.getTitolo());
			prep_stmt.setString(3, workpackage.getDescrizione());
			prep_stmt.setInt(4, workpackage.getProgetto().getIdProgetto());
			prep_stmt.setInt(5, workpackage.getIdPackage());
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
			PreparedStatement prep_stmt = conn.prepareStatement(Db2WorkPackageDAO.delete);
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

	/*
	 * @Override public List<RistoranteDTO> getResturantByCity(String citta) { //
	 * TODO Auto-generated method stub List<RistoranteDTO> result = null; // --- 2.
	 * Controlli preliminari sui dati in ingresso --- if ( citta.isEmpty() || citta
	 * == null ) {
	 * 
	 * return result; } // --- 3. Apertura della connessione --- Connection conn =
	 * Db2DAOFactory.createConnection(); // --- 4. Tentativo di accesso al db e
	 * impostazione del risultato --- try { // --- a. Crea (se senza parametri) o
	 * prepara (se con parametri) lo statement PreparedStatement prep_stmt =
	 * conn.prepareStatement(query); // --- b. Pulisci e imposta i parametri (se ve
	 * ne sono)
	 * 
	 * // --- c. Esegui l'azione sul database ed estrai il risultato (se atteso)
	 * ResultSet rs = prep_stmt.executeQuery();
	 * 
	 * 
	 * result = new ArrayList<RistoranteDTO>(); // --- d. Cicla sul risultato (se
	 * presente) pe accedere ai valori di ogni sua tupla String address; while (
	 * rs.next() ) { address = rs.getString("indirizzo").toLowerCase();
	 * if(address.contains("bologna ")) { RistoranteDTO entry = new
	 * Db2RistoranteDTOProxy(); entry.setId(rs.getInt(ID));
	 * entry.setIndirizzo(address); entry.setRating(rs.getInt(RATING));
	 * entry.setNomeRistorante(rs.getString(NOMERISTORANTE)); result.add(entry); }
	 * 
	 * 
	 * }
	 * 
	 * // --- e. Rilascia la struttura dati del risultato rs.close(); // --- f.
	 * Rilascia la struttura dati dello statement prep_stmt.close(); } // --- 5.
	 * Gestione di eventuali eccezioni --- catch (Exception e) {
	 * 
	 * e.printStackTrace(); } // --- 6. Rilascio, SEMPRE E COMUNQUE, la connessione
	 * prima di restituire il controllo al chiamante finally {
	 * Db2DAOFactory.closeConnection(conn); } // --- 7. Restituzione del risultato
	 * (eventualmente di fallimento) return result; }
	 * 
	 * @Override public List<RistoranteDTO> getRatedResturant(int stars) {
	 * List<RistoranteDTO> result = null; // --- 2. Controlli preliminari sui dati
	 * in ingresso --- if ( stars<1 || stars >5 ) return result; // --- 3. Apertura
	 * della connessione --- Connection conn = Db2DAOFactory.createConnection(); //
	 * --- 4. Tentativo di accesso al db e impostazione del risultato --- try { //
	 * --- a. Crea (se senza parametri) o prepara (se con parametri) lo statement
	 * PreparedStatement prep_stmt =
	 * conn.prepareStatement(find_resturant_over_rate); // --- b. Pulisci e imposta
	 * i parametri (se ve ne sono) prep_stmt.clearParameters(); prep_stmt.setInt(1,
	 * stars); // --- c. Esegui l'azione sul database ed estrai il risultato (se
	 * atteso) ResultSet rs = prep_stmt.executeQuery(); result = new
	 * ArrayList<RistoranteDTO>(); // --- d. Cicla sul risultato (se presente) pe
	 * accedere ai valori di ogni sua tupla while ( rs.next() ) { RistoranteDTO
	 * entry = new Db2RistoranteDTOProxy(); entry.setId(rs.getInt(ID));
	 * entry.setIndirizzo(rs.getString(INDIRIZZO));
	 * entry.setRating(rs.getInt(RATING));
	 * entry.setNomeRistorante(rs.getString(NOMERISTORANTE)); result.add(entry); }
	 * // --- e. Rilascia la struttura dati del risultato rs.close(); // --- f.
	 * Rilascia la struttura dati dello statement prep_stmt.close(); } // --- 5.
	 * Gestione di eventuali eccezioni --- catch (Exception e) {
	 * e.printStackTrace(); } // --- 6. Rilascio, SEMPRE E COMUNQUE, la connessione
	 * prima di restituire il controllo al chiamante finally {
	 * Db2DAOFactory.closeConnection(conn); } return result; // --- 7. Restituzione
	 * del risultato (eventualmente di fallimento) }
	 */

}
