package esame.dao.db2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

public class Db2LinkDAO {
	// statement metodi CRUD

	// INSERT
	static final String insert = "INSERT INTO link (id_Passeggero,id_Volo) VALUES (?,?)";

	// DELETE
	static String delete = "DELETE FROM link WHERE id_Passeggero=? AND id_Volo=?";
	static String delete_by_id_Passeggero = "DELETE FROM link WHERE id_Passeggero=?";
	static String delete_by_id_Volo = "DELETE FROM link WHERE id_Volo=?";

	// READ
	static String read_by_id_Passeggero = "SELECT id_Volo FROM link WHERE id_Passeggero=?";
	static String read_by_id_Volo = "SELECT id_Passeggero FROM link WHERE id_Volo=?";

	// -------------------------------------------------------------------------------------

	// create table
	private static String create = "CREATE TABLE link (id_Passeggero BIGINT NOT NULL,id_Volo BIGINT NOT NULL, PRIMARY KEY(id_Passeggero, id_Volo))";

	// drop table
	static String drop = "DROP TABLE link";

	public void create(long id_Passeggero, long id_Volo) {

		Connection conn = Db2DAOFactory.createConnection();
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(insert);
			prep_stmt.clearParameters();

			prep_stmt.setLong(1, id_Passeggero);
			prep_stmt.setLong(2, id_Volo);

			prep_stmt.executeUpdate();

			prep_stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Db2DAOFactory.closeConnection(conn);
		}

	}

	// -------------------------------------------------------------------------------------

	public Set<Long> read_by_id_Passeggero(long id_Passeggero) {
		Set<Long> result = new HashSet<>();

		if (id_Passeggero < 0)
			return result;

		Connection conn = Db2DAOFactory.createConnection();

		try {
			PreparedStatement prep_stmt = conn.prepareStatement(read_by_id_Passeggero);
			prep_stmt.clearParameters();
			prep_stmt.setLong(1, id_Passeggero);
			ResultSet rs = prep_stmt.executeQuery();
			while (rs.next()) {
				result.add(rs.getLong("id_Volo"));
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
	
	public Set<Long> read_by_id_Volo(long id_Volo) {
		Set<Long> result = new HashSet<>();

		if (id_Volo < 0)
			return result;

		Connection conn = Db2DAOFactory.createConnection();

		try {
			PreparedStatement prep_stmt = conn.prepareStatement(read_by_id_Volo);
			prep_stmt.clearParameters();
			prep_stmt.setLong(1, id_Volo);
			ResultSet rs = prep_stmt.executeQuery();
			while (rs.next()) {
				result.add(rs.getLong("id_Passeggero"));
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

	// -------------------------------------------------------------------------------------

	public boolean delete(long id_Passeggero, long id_Volo) {
		boolean result = false;
		Connection conn = Db2DAOFactory.createConnection();
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(delete);
			prep_stmt.clearParameters();

			prep_stmt.setLong(1, id_Passeggero);
			prep_stmt.setLong(2, id_Volo);

			prep_stmt.executeUpdate();
			result = true;
			prep_stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Db2DAOFactory.closeConnection(conn);
		}
		return result;
	}
	
	public boolean delete_by_id_Passeggero(long id_Passeggero) {
		boolean result = false;
		if (id_Passeggero < 0)
			return result;

		Connection conn = Db2DAOFactory.createConnection();
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(delete_by_id_Passeggero);
			prep_stmt.clearParameters();
			prep_stmt.setLong(1, id_Passeggero);
			prep_stmt.executeUpdate();
			result = true;
			prep_stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Db2DAOFactory.closeConnection(conn);
		}

		return result;
	}
	
	public boolean delete_by_id_Volo(long id_Volo) {
		boolean result = false;
		if (id_Volo < 0)
			return result;

		Connection conn = Db2DAOFactory.createConnection();
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(delete_by_id_Volo);
			prep_stmt.clearParameters();
			prep_stmt.setLong(1, id_Volo);
			prep_stmt.executeUpdate();
			result = true;
			prep_stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Db2DAOFactory.closeConnection(conn);
		}

		return result;
	}

	// -------------------------------------------------------------------------------------

	public boolean createTable() {
		boolean result = false;
		Connection conn = Db2DAOFactory.createConnection();
		try {
			Statement prep_stmt = conn.createStatement();
			prep_stmt.execute(create);
			result = true;
			prep_stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Db2DAOFactory.closeConnection(conn);
		}

		return result;
	}

	// -------------------------------------------------------------------------------------

	public boolean dropTable() {
		boolean result = false;
		Connection conn = Db2DAOFactory.createConnection();
		try {
			Statement prep_stmt = conn.createStatement();
			prep_stmt.execute(drop);
			result = true;
			prep_stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		finally {
			Db2DAOFactory.closeConnection(conn);
		}

		return result;
	}
}
