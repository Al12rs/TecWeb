package it.unibo.tw.dao.db2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import it.unibo.tw.dao.IngredienteDAO;
import it.unibo.tw.dao.IngredienteDTO;



public class Db2IngredienteDAO implements IngredienteDAO{
	
	static final String TABLE = "ingredienti";
	
	static final String ID = "id";
	static final String NOME = "nomeIngrediente";
	static final String QUANTITA = "quantita";
	//altri campi...
	
	// == STATEMENT SQL ====================================================================

		// INSERT INTO table ( id, name, description, ...) VALUES ( ?,?, ... );
		static final String insert = 
			"INSERT " +
				"INTO " + TABLE + " ( " + 
					ID +", "+NOME+", " + QUANTITA + " "+
				") " +
				"VALUES (?,?,?) "
			;
		
		// SELECT * FROM table WHERE idcolumn = ?;
		static String read_by_id = 
			"SELECT * " +
				"FROM " + TABLE + " " +
				"WHERE " + ID + " = ? "
			;
		
		// SELECT * FROM table WHERE idcolumn = ?;
				static String read_by_name = 
					"SELECT * " +
						"FROM " + TABLE + " " +
						"WHERE " + NOME + " = ? "
					;

		// SELECT * FROM table WHERE stringcolumn = ?;
		static String read_all = 
			"SELECT * " +
			"FROM " + TABLE + " ";
		
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
				NOME + " = ?, " + QUANTITA + " = ? "+
				"WHERE " + ID + " = ? "
			;

		// SELECT * FROM table;
		static String query = 
			"SELECT * " +
				"FROM " + TABLE + " "
			;

		// -------------------------------------------------------------------------------------

		// CREATE entrytable ( code INT NOT NULL PRIMARY KEY, ... );
		static String create = 
			"CREATE " +
				"TABLE " + TABLE +" ( " +
					ID + " INT NOT NULL PRIMARY KEY, " +
					NOME + " VARCHAR(50) NOT NULL UNIQUE, " +
					QUANTITA +"INT "+
				") "
			;
		
		static String drop = 
			"DROP " +
				"TABLE " + TABLE + " "
			;
		
		//EVENTUALI ALTRE QUERY PER LO SVOLGIMENTO DEI METODI DI SUPPORTO

	
	// METODI CRUD

	@Override
	public void create(IngredienteDTO ingrediente) {
		// --- 1. Dichiarazione della variabile per il risultato ---
				//Long result = new Long(-1);
				// --- 2. Controlli preliminari sui dati in ingresso ---
				/*if ( t == null )  {
					logger.warning("create(): failed to insert a null entry");
					return result;
				}*/
				// --- 3. Apertura della connessione ---
				Connection conn = Db2DAOFactory.createConnection();
				// --- 4. Tentativo di accesso al db e impostazione del risultato ---
				try {
					// --- a. Crea (se senza parametri) o prepara (se con parametri) lo statement
					PreparedStatement prep_stmt = conn.prepareStatement(Db2IngredienteDAO.insert);
					// --- b. Pulisci e imposta i parametri (se ve ne sono)
					prep_stmt.clearParameters();
					prep_stmt.setInt(1, ingrediente.getId());
					prep_stmt.setString(2, ingrediente.getNomeIngrediente());
					prep_stmt.setInt(3, ingrediente.getQuantita());
					// --- c. Esegui l'azione sul database ed estrai il risultato (se atteso)
					prep_stmt.executeUpdate();
					// --- d. Cicla sul risultato (se presente) pe accedere ai valori di ogni sua tupla
					// n.d.
					// --- e. Rilascia la struttura dati del risultato
					// n.d.
					// --- f. Rilascia la struttura dati dello statement
					prep_stmt.close();
				}
				// --- 5. Gestione di eventuali eccezioni ---
				catch (Exception e) {
				
					e.printStackTrace();
					//result = new Long(-2);
				}
				// --- 6. Rilascio, SEMPRE E COMUNQUE, la connessione prima di restituire il controllo al chiamante
				finally {
					Db2DAOFactory.closeConnection(conn);
				}

		}

	@Override
	public IngredienteDTO read(int id) {
		// --- 1. Dichiarazione della variabile per il risultato ---
				IngredienteDTO result = null;
				// --- 2. Controlli preliminari sui dati in ingresso ---
				/*if ( chiave.isEmpty() || chiave == null )  {
					
					return result;
				}*/
				// --- 3. Apertura della connessione ---
				Connection conn = Db2DAOFactory.createConnection();
				// --- 4. Tentativo di accesso al db e impostazione del risultato ---
				try {
					// --- a. Crea (se senza parametri) o prepara (se con parametri) lo statement
					PreparedStatement prep_stmt = conn.prepareStatement(Db2IngredienteDAO.read_by_id);
					// --- b. Pulisci e imposta i parametri (se ve ne sono)
					prep_stmt.clearParameters();
					prep_stmt.setInt(1, id);
					// --- c. Esegui l'azione sul database ed estrai il risultato (se atteso)
					ResultSet rs = prep_stmt.executeQuery();
					// --- d. Cicla sul risultato (se presente) pe accedere ai valori di ogni sua tupla
					if ( rs.next() ) {
						IngredienteDTO entry = new IngredienteDTO();
						entry.setId(rs.getInt(ID));
						entry.setNomeIngrediente(rs.getString(NOME));
						entry.setQuantita(rs.getInt(ID));
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
				// --- 6. Rilascio, SEMPRE E COMUNQUE, la connessione prima di restituire il controllo al chiamante
				finally {
					Db2DAOFactory.closeConnection(conn);
				}
				// --- 7. Restituzione del risultato (eventualmente di fallimento)
				return result;
	}

	@Override
	public boolean update(IngredienteDTO ingrediente) {
		// --- 1. Dichiarazione della variabile per il risultato ---
		boolean result = false;
		// --- 2. Controlli preliminari sui dati in ingresso ---
		if ( ingrediente== null )  {
			
			return result;
		}
		// --- 3. Apertura della connessione ---
		Connection conn = Db2DAOFactory.createConnection();
		// --- 4. Tentativo di accesso al db e impostazione del risultato ---
		try {
			// --- a. Crea (se senza parametri) o prepara (se con parametri) lo statement
			PreparedStatement prep_stmt = conn.prepareStatement(Db2IngredienteDAO.update);
			// --- b. Pulisci e imposta i parametri (se ve ne sono)
			prep_stmt.clearParameters();
			
			prep_stmt.setString(1, ingrediente.getNomeIngrediente());
			prep_stmt.setInt(2, ingrediente.getQuantita());
			prep_stmt.setInt(3, ingrediente.getId());
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
				/*if ( chiave.isEmpty() || chiave==null )  {
					
					return result;
				}*/
				// --- 3. Apertura della connessione ---
				Connection conn = Db2DAOFactory.createConnection();
				// --- 4. Tentativo di accesso al db e impostazione del risultato ---
				try {
					// --- a. Crea (se senza parametri) o prepara (se con parametri) lo statement
					PreparedStatement prep_stmt = conn.prepareStatement(Db2IngredienteDAO.delete);
					// --- b. Pulisci e imposta i parametri (se ve ne sono)
					prep_stmt.clearParameters();
					prep_stmt.setInt(1, id);
					// --- c. Esegui l'azione sul database ed estrai il risultato (se atteso)
					prep_stmt.executeUpdate();
					// --- d. Cicla sul risultato (se presente) pe accedere ai valori di ogni sua tupla
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
				// --- 6. Rilascio, SEMPRE E COMUNQUE, la connessione prima di restituire il controllo al chiamante
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
					// --- d. Cicla sul risultato (se presente) pe accedere ai valori di ogni sua tupla
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
				// --- 6. Rilascio, SEMPRE E COMUNQUE, la connessione prima di restituire il controllo al chiamante
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
					// --- d. Cicla sul risultato (se presente) pe accedere ai valori di ogni sua tupla
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
				// --- 6. Rilascio, SEMPRE E COMUNQUE, la connessione prima di restituire il controllo al chiamante
				finally {
					Db2DAOFactory.closeConnection(conn);
				}
				// --- 7. Restituzione del risultato (eventualmente di fallimento)
				return result;
	}

	//METODI DI SUPPORTO ALLE RICHIESTE

}
