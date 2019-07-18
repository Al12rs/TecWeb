package it.unibo.tw.dao.db2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;



public class Db2RicettaDAO implements RicettaDAO{
	
	static final String TABLE = "ricetta";
	
	static final String ID = "id";
    static final String NOME = "nomericetta";
	static final String TEMPO = "tempopreparazione";
    static final String DIFFICOLTA = "livellodifficolta";
    static final String CALORIE = "calorie";    
	//altri campi...
	
	// == STATEMENT SQL ====================================================================

		// INSERT INTO table ( id, name, description, ...) VALUES ( ?,?, ... );
		static final String insert = 
			"INSERT " +
				"INTO " + TABLE + " ( " + 
					ID +", "+NOME+", " + TEMPO+ ", " + DIFFICOLTA + ", " + CALORIE + " "+
				") " +
				"VALUES (?,?,?,?,?) "
			;
		
		// SELECT * FROM table WHERE idcolumn = ?;
		static String read_by_id = 
			"SELECT * " +
				"FROM " + TABLE + " " +
				"WHERE " + ID + " = ? "
			;
		
		// SELECT * FROM table WHERE nome = ?;
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
                    NOME + " = ?, " +
                    TEMPO + " = ?, " +
                    DIFFICOLTA + " = ?, " +
                    CALORIE + " = ?, " +
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
                    TEMPO + " FLOAT NOT NULL, " +
                    DIFFICOLTA + " INT NOT NULL, " +
					CALORIE + " FLOAT NOT NULL " +
				") "
			;
		
		static String drop = 
			"DROP " +
				"TABLE " + TABLE + " "
			;
		
		//EVENTUALI ALTRE QUERY PER LO SVOLGIMENTO DEI METODI DI SUPPORTO

	
	// METODI CRUD

	@Override
	public void create(RicettaDTO oggetto) {
		// --- 1. Dichiarazione della variabile per il risultato ---
				//Long result = new Long(-1);
				// --- 2. Controlli preliminari sui dati in ingresso ---
				/*if ( t == null )  {
					logger.warning("create(): failed to insert a null entry");
					return result;
				}*/
				// --- 3. Apertura della connessione ---
                Connection conn = Db2DAOFactory.createConnection();
                Ricetta_IngredientiDAO rpm = new Db2Ricetta_IngredientiDAO();
                
				// --- 4. Tentativo di accesso al db e impostazione del risultato ---
				try {
					// --- a. Crea (se senza parametri) o prepara (se con parametri) lo statement
					PreparedStatement prep_stmt = conn.prepareStatement(Db2RicettaDAO.insert);
					// --- b. Pulisci e imposta i parametri (se ve ne sono)
                    prep_stmt.clearParameters();
                    
					prep_stmt.setInt(1, oggetto.getId());
                    prep_stmt.setString(2, oggetto.getNomeRicetta());
					prep_stmt.setFloat(3, oggetto.getTempoPreparazione());
					prep_stmt.setInt(4, oggetto.getLivelloDifficolta());
                    prep_stmt.setFloat(5, oggetto.getCalorie());
                    //popolo la tabella di mappuing
                    for (IngredienteDTO ing : oggetto.getIngredienti())
                    {
                        rpm.create(oggetto.getId(), ing.getId());
                    }
                    

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
	public RicettaDTO read(int id) {
		// --- 1. Dichiarazione della variabile per il risultato ---
				RicettaDTO result = null;
				// --- 2. Controlli preliminari sui dati in ingresso ---
				/*if ( chiave.isEmpty() || chiave == null )  {
					
					return result;
				}*/
				// --- 3. Apertura della connessione ---
				Connection conn = Db2DAOFactory.createConnection();
				// --- 4. Tentativo di accesso al db e impostazione del risultato ---
				try {
					// --- a. Crea (se senza parametri) o prepara (se con parametri) lo statement
					PreparedStatement prep_stmt = conn.prepareStatement(Db2RicettaDAO.read_by_id);
					// --- b. Pulisci e imposta i parametri (se ve ne sono)
					prep_stmt.clearParameters();
					prep_stmt.setInt(1, id);
					// --- c. Esegui l'azione sul database ed estrai il risultato (se atteso)
					ResultSet rs = prep_stmt.executeQuery();
                    // --- d. Cicla sul risultato (se presente) pe accedere ai valori di ogni sua tupla
                    Ricetta_IngredientiDAO rpm = new Db2Ricetta_IngredientiDAO();

					if ( rs.next() ) {
						RicettaDTO entry = new Db2RicettaDTOProxy();
						entry.setId(rs.getInt(ID));
                        entry.setNomeRicetta(rs.getString(NOME));
                        entry.setTempoPreparazione(rs.getFloat(TEMPO));
						entry.setLivelloDifficolta(rs.getInt(DIFFICOLTA));
                        entry.setCalorie(rs.getFloat(CALORIE));
                        
                        
			            entry.setIngredienti( rpm.getIngredientiFromRicetta(rs.getInt(ID)));
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
	public boolean update(RicettaDTO oggetto) {
		// --- 1. Dichiarazione della variabile per il risultato ---
		boolean result = false;
		// --- 2. Controlli preliminari sui dati in ingresso ---
		if ( r== null )  {
			
			return result;
		}
		// --- 3. Apertura della connessione ---
		Connection conn = Db2DAOFactory.createConnection();
        // --- 4. Tentativo di accesso al db e impostazione del risultato ---
        Ricetta_IngredientiDAO rpm = new Db2Ricetta_IngredientiDAO();
        
		try {
			// --- a. Crea (se senza parametri) o prepara (se con parametri) lo statement
			PreparedStatement prep_stmt = conn.prepareStatement(Db2RicettaDAO.update);
			// --- b. Pulisci e imposta i parametri (se ve ne sono)
			prep_stmt.clearParameters();
			
            prep_stmt.setString(1, oggetto.getNomeRicetta());
            prep_stmt.setString(2, oggetto.getTempoPreparazione());
            prep_stmt.setString(3, oggetto.getLivelloDifficolta());
            prep_stmt.setString(4, oggetto.getCalorie());

			prep_stmt.setInt(5, oggetto.getId());
			// --- c. Esegui l'azione sul database ed estrai il risultato (se atteso)
            prep_stmt.executeUpdate();
            for (IngredienteDTO ing : oggetto.getIngredienti()) {
                rpm.create(oggetto.getId(), ing.getId());
            }
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
					PreparedStatement prep_stmt = conn.prepareStatement(Db2RicettaDAO.delete);
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

	
	/*
	@Override
	public List<RistoranteDTO> getResturantByCity(String citta) {
		// TODO Auto-generated method stub
		List<RistoranteDTO> result = null;
		// --- 2. Controlli preliminari sui dati in ingresso ---
		if ( citta.isEmpty() || citta == null )  {
			
			return result;
		}
		// --- 3. Apertura della connessione ---
		Connection conn = Db2DAOFactory.createConnection();
		// --- 4. Tentativo di accesso al db e impostazione del risultato ---
		try {
			// --- a. Crea (se senza parametri) o prepara (se con parametri) lo statement
			PreparedStatement prep_stmt = conn.prepareStatement(query);
			// --- b. Pulisci e imposta i parametri (se ve ne sono)
			
			// --- c. Esegui l'azione sul database ed estrai il risultato (se atteso)
			ResultSet rs = prep_stmt.executeQuery();
			
			
			result = new ArrayList<RistoranteDTO>();
			// --- d. Cicla sul risultato (se presente) pe accedere ai valori di ogni sua tupla
			String address;
			while ( rs.next() ) {
				address = rs.getString("indirizzo").toLowerCase();
				if(address.contains("bologna "))
				{
					RistoranteDTO entry = new Db2RistoranteDTOProxy();
					entry.setId(rs.getInt(ID));
					entry.setIndirizzo(address);
					entry.setRating(rs.getInt(RATING));
					entry.setNomeRistorante(rs.getString(NOMERISTORANTE));
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
		// --- 6. Rilascio, SEMPRE E COMUNQUE, la connessione prima di restituire il controllo al chiamante
		finally {
			Db2DAOFactory.closeConnection(conn);
		}
		// --- 7. Restituzione del risultato (eventualmente di fallimento)
		return result;
	}

	@Override
	public List<RistoranteDTO> getRatedResturant(int stars) {
				List<RistoranteDTO> result = null;
				// --- 2. Controlli preliminari sui dati in ingresso ---
				if ( stars<1 || stars >5 )	
					return result;
				// --- 3. Apertura della connessione ---
				Connection conn = Db2DAOFactory.createConnection();
				// --- 4. Tentativo di accesso al db e impostazione del risultato ---
				try {
					// --- a. Crea (se senza parametri) o prepara (se con parametri) lo statement
					PreparedStatement prep_stmt = conn.prepareStatement(find_resturant_over_rate);
					// --- b. Pulisci e imposta i parametri (se ve ne sono)
					prep_stmt.clearParameters();
					prep_stmt.setInt(1, stars);
					// --- c. Esegui l'azione sul database ed estrai il risultato (se atteso)
					ResultSet rs = prep_stmt.executeQuery();
					result = new ArrayList<RistoranteDTO>();
					// --- d. Cicla sul risultato (se presente) pe accedere ai valori di ogni sua tupla
					while ( rs.next() ) {					
							RistoranteDTO entry = new Db2RistoranteDTOProxy();
							entry.setId(rs.getInt(ID));
							entry.setIndirizzo(rs.getString(INDIRIZZO));
							entry.setRating(rs.getInt(RATING));
							entry.setNomeRistorante(rs.getString(NOMERISTORANTE));
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
				// --- 6. Rilascio, SEMPRE E COMUNQUE, la connessione prima di restituire il controllo al chiamante
				finally {
					Db2DAOFactory.closeConnection(conn);
				}	
				return result; // --- 7. Restituzione del risultato (eventualmente di fallimento)
	}
	*/

}
