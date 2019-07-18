package dao.db2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import dao.WorkPackageDTO;
import dao.WorkPackagePartnerMappingDAO;

//TO-DO sostituire tutti gli id1 e id2, e eventuali query

public class Db2WorkPackagePartnerMappingDAO implements WorkPackagePartnerMappingDAO
{
	// === Costanti letterali per non sbagliarsi a scrivere !!! ============================
	static final String TABLE = "nomeTabella";
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
	//static String read_by_ID_1 = "SELECT * " + "FROM " + TABLE + " " + "WHERE " + ID_1 + " = ? ";

	// SELECT * FROM table WHERE idcolumns = ?;
	static String read_by_ID_2 = "SELECT * " + "FROM " + TABLE + ", workpackage " + "WHERE workpackage.id=idPackage AND" + ID_2 + " = ? ";

	// SELECT * FROM table WHERE stringcolumn = ?;
	static String read_all = "SELECT * " + "FROM " + TABLE + " ";

	// DELETE FROM table WHERE idcolumn = ?;
	static String delete = "DELETE " + "FROM " + TABLE + " " + "WHERE " + ID_1 + " = ? " + "AND " + ID_2 + " = ? ";

	// SELECT * FROM table;
	static String query = "SELECT * " + "FROM " + TABLE + " " + "WHERE  ";
	// -------------------------------------------------------------------------------------
	// CREATE entrytable ( id INT NOT NULL PRIMARY KEY, ... );
	static String create = "CREATE " + "TABLE " + TABLE + " ( " + ID_1 + " INT NOT NULL, " + ID_2 + " INT NOT NULL, "
			+ "PRIMARY KEY (" + ID_1 + "," + ID_2 + " ), " + "FOREIGN KEY (" + ID_1 + ") REFERENCES tabella1(id), "
			+ "FOREIGN KEY (" + ID_2 + ") REFERENCES tabella2(id) " + ") ";

	static String drop = 
			"DROP " +
				"TABLE " + TABLE + " "
			;

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
		}
		catch (Exception e) {
			System.out.println("create(): failed to insert entry: " + e.getMessage());
			e.printStackTrace();
		}

	}

	@Override
	public boolean delete(int idPackage, int idPartner) {
		boolean result = false;
		if ( idPackage < 0 || idPartner < 0 )  {
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
		}
		catch (Exception e) {
			System.out.println("delete(): failed to delete entry with idPackage = " + idPackage +" and idPartner = " + idPartner + ": "+e.getMessage());
			e.printStackTrace();
		}
		finally {
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
		}
		catch (Exception e) {
			System.out.println("createTable(): failed to create table '"+TABLE+"': "+e.getMessage());
		}
		finally {
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
		}
		catch (Exception e) {
			System.out.println("dropTable(): failed to drop table '"+TABLE+"': "+e.getMessage());
		}
		finally {
			Db2DAOFactory.closeConnection(conn);
		}
		return result;
	}

	@Override
	public Set<WorkPackageDTO> getPackagesFromPartner(long idPartner) {
		Set<WorkPackageDTO> result = new HashSet<WorkPackageDTO>();

		if (idPartner < 0)
			return result;

		Connection conn = Db2DAOFactory.createConnection();

		try {
			PreparedStatement prep_stmt = conn.prepareStatement(read_by_ID_2);
			prep_stmt.clearParameters();
			prep_stmt.setLong(1, idPartner);
			ResultSet rs = prep_stmt.executeQuery();

			while (rs.next()) {
				WorkPackageDTO res = new WorkPackageDTO();

				res.setId(rs.getLong("id"));
				res.setNomeWP(rs.getString("nomeWP"));
				res.setTitolo(rs.getString("titolo"));
				res.setDescrizione(rs.getString("descrizione"));
				res.setProgetto((new Db2ProgettoDAO()).read(rs.getLong("idProgetto")));

				result.add(res);
			}

			rs.close();
			prep_stmt.close();
		}

		catch (Exception e) {
			e.printStackTrace();
		} finally {
			Db2DAOFactory.closeConnection(conn);
		}

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
