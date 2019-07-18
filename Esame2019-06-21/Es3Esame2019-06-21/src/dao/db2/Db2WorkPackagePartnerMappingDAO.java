package dao.db2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import dao.PartnerDTO;
import dao.WorkPackageDTO;
import dao.WorkPackagePartnerMappingDAO;

//TO-DO sostituire tutti gli id1 e id2, e eventuali query

public class Db2WorkPackagePartnerMappingDAO implements WorkPackagePartnerMappingDAO {
	// === Costanti letterali per non sbagliarsi a scrivere !!!
	// ============================
	static final String TABLE = "packages_partners";
	// -------------------------------------------------------------------------------------
	static final String ID_1 = "idPackage";
	static final String ID_2 = "idPartner";
	// == STATEMENT SQL
	// ====================================================================
	static final String insert = "INSERT " + "INTO " + TABLE + " ( " + ID_1 + ", " + ID_2 + " " + ") "
			+ "VALUES (?,?) ";

	// SELECT * FROM table WHERE idcolumns = ?;
	static String read_by_ids = "SELECT * " + "FROM " + TABLE + " " + "WHERE " + ID_1 + " = ? " + "AND " + ID_2
			+ " = ? ";

	// SELECT * FROM table WHERE idcolumns = ?;
	static String read_by_packageID = "SELECT * " + "FROM partners" + TABLE + " " + "WHERE partners.idPartner = " + ID_2
			+ " AND " + ID_1 + " = ? ";

	// SELECT * FROM table WHERE idcolumns = ?;
	static String read_by_partnerID = "SELECT * " + "FROM packages," + TABLE + "  " + "WHERE packages.idPackage = "
			+ ID_1 + " AND " + ID_2 + " = ? ";

	// SELECT * FROM table WHERE stringcolumn = ?;
	static String read_all = "SELECT * " + "FROM " + TABLE + " ";

	// DELETE FROM table WHERE idcolumn = ?;
	static String delete = "DELETE " + "FROM " + TABLE + " " + "WHERE " + ID_1 + " = ? " + "AND " + ID_2 + " = ? ";

	// SELECT * FROM table;
	static String query = "SELECT * " + "FROM " + TABLE + " " + "WHERE  ";
	// -------------------------------------------------------------------------------------
	// CREATE entrytable ( id INT NOT NULL PRIMARY KEY, ... );
	static String create = "CREATE " + "TABLE " + TABLE + " ( " + ID_1 + " INT NOT NULL, " + ID_2 + " INT NOT NULL, "
			+ "PRIMARY KEY (" + ID_1 + "," + ID_2 + " ), " + "FOREIGN KEY (" + ID_1 + ") REFERENCES packages(id), "
			+ "FOREIGN KEY (" + ID_2 + ") REFERENCES partners(id) " + ") ";

	static String drop = "DROP " + "TABLE " + TABLE + " ";

	@Override
	public void create(int idPackage, int idPartner) {
		Connection conn = Db2DAOFactory.createConnection();
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(insert);
			prep_stmt.clearParameters();
			prep_stmt.setInt(1, idPackage);
			prep_stmt.setInt(2, idPartner);
			prep_stmt.executeUpdate();
			prep_stmt.close();
		} catch (Exception e) {
			System.out.println("create(): failed to insert entry: " + e.getMessage());
			e.printStackTrace();
		}

	}

	@Override
	public boolean delete(int idPackage, int idPartner) {
		boolean result = false;
		if (idPackage < 0 || idPartner < 0) {
			System.out.println("delete(): cannot delete an entry with an invalid id ");
			return result;
		}
		Connection conn = Db2DAOFactory.createConnection();
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(delete);
			prep_stmt.clearParameters();
			prep_stmt.setInt(1, idPackage);
			prep_stmt.setInt(2, idPartner);
			prep_stmt.executeUpdate();
			result = true;
			prep_stmt.close();
		} catch (Exception e) {
			System.out.println("delete(): failed to delete entry with idPackage = " + idPackage + " and idPartner = "
					+ idPartner + ": " + e.getMessage());
			e.printStackTrace();
		} finally {
			Db2DAOFactory.closeConnection(conn);
		}
		return result;
	}

	@Override
	public boolean createTable() {
		boolean result = false;
		Connection conn = Db2DAOFactory.createConnection();
		try {
			Statement stmt = conn.createStatement();
			stmt.execute(create);
			result = true;
			stmt.close();
		} catch (Exception e) {
			System.out.println("createTable(): failed to create table '" + TABLE + "': " + e.getMessage());
		} finally {
			Db2DAOFactory.closeConnection(conn);
		}
		return result;
	}

	@Override
	public boolean dropTable() {
		boolean result = false;
		Connection conn = Db2DAOFactory.createConnection();
		try {
			Statement stmt = conn.createStatement();
			stmt.execute(drop);
			result = true;
			stmt.close();
		} catch (Exception e) {
			System.out.println("dropTable(): failed to drop table '" + TABLE + "': " + e.getMessage());
		} finally {
			Db2DAOFactory.closeConnection(conn);
		}
		return result;
	}

	@Override
	public Set<PartnerDTO> getPartnersForPackage(int id) {
		// --- 1. Dichiarazione della variabile per il risultato ---
		Set<PartnerDTO> result = new HashSet<PartnerDTO>();
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
			PreparedStatement prep_stmt = conn.prepareStatement(read_by_packageID);
			// --- b. Pulisci e imposta i parametri (se ve ne sono)
			prep_stmt.clearParameters();
			prep_stmt.setInt(1, id);
			// --- c. Esegui l'azione sul database ed estrai il risultato (se atteso)
			ResultSet rs = prep_stmt.executeQuery();
			// --- d. Cicla sul risultato (se presente) pe accedere ai valori di ogni sua
			// tupla
			while (rs.next()) {
				PartnerDTO entry = new Db2PartnerDTOProxy();
				entry.setIdPartner(rs.getInt(Db2PartnerDAO.ID));
				entry.setNome(rs.getString(Db2PartnerDAO.NOME));
				entry.setSiglaPartner(rs.getString(Db2PartnerDAO.SIGLA));
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
	public Set<WorkPackageDTO> getPackagesForPartner(int id) {
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
			PreparedStatement prep_stmt = conn.prepareStatement(read_by_partnerID);
			// --- b. Pulisci e imposta i parametri (se ve ne sono)
			prep_stmt.clearParameters();
			prep_stmt.setInt(1, id);
			// --- c. Esegui l'azione sul database ed estrai il risultato (se atteso)
			ResultSet rs = prep_stmt.executeQuery();
			// --- d. Cicla sul risultato (se presente) pe accedere ai valori di ogni sua
			// tupla
			while (rs.next()) {
				WorkPackageDTO entry = new Db2WorkPackageDTOProxy();
				entry.setIdPackage(rs.getInt(Db2WorkPackageDAO.ID));
				entry.setNomeWP(rs.getString(Db2WorkPackageDAO.NOME));
				entry.setTitolo(rs.getString(Db2WorkPackageDAO.TITOLO));
				entry.setDescrizione(rs.getString(Db2WorkPackageDAO.DESCRIZIONE));
				entry.setProgetto((new Db2ProgettoDAO()).read(rs.getInt(Db2WorkPackageDAO.PROGETTO)));
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

	// METODI PER LA RESTITUZIONE DI COLLEZIONI

	/*
	 * @Override public List<PiattoDTO> getPiattiFromResturant(int id) {
	 * List<PiattoDTO> result = null; if ( id< 0 ) {
	 * System.out.println("read(): cannot read an entry with a negative id"); return
	 * result; } Connection conn = Db2DAOFactory.createConnection(); try {
	 * PreparedStatement prep_stmt = conn.prepareStatement(dish_query);
	 * prep_stmt.clearParameters(); prep_stmt.setInt(1, id); ResultSet rs =
	 * prep_stmt.executeQuery();
	 * 
	 * result = new ArrayList<PiattoDTO>(); while ( rs.next() ) { PiattoDTO entry =
	 * new PiattoDTO(); entry.setId((rs.getInt("id")));
	 * entry.setNomePiatto(rs.getString("nome"));
	 * entry.setTipo(rs.getString("tipo")); result.add(entry); } rs.close();
	 * prep_stmt.close(); } catch (Exception e) {
	 * 
	 * e.printStackTrace(); } finally { Db2DAOFactory.closeConnection(conn); }
	 * return result; }
	 */

}
