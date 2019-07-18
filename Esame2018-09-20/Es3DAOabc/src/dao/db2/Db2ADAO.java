package dao.db2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import dao.ADAO;
import dao.ADTO;

public class Db2ADAO implements ADAO {
	// statement metodi CRUD

	// INSERT
	static final String insert = "INSERT INTO a (a1,a2,a3,id_b) VALUES (?,?,?,?)";

	// DELETE
	static String delete = "DELETE FROM a WHERE a1=?";

	// UPDATE
	static String update = "UPDATE a SET a2=?,a3=?,id_b=? WHERE a1=?";

	// READ
	static String read_by_id = "SELECT * FROM a WHERE a1=?";

	// READ_BY_FK
	static String read_by_fk = "SELECT * FROM a WHERE id_b=?";

	// READ_ALL
	static String read_all = "SELECT * FROM a ";

	// -------------------------------------------------------------------------------------

	// create table
	private static String create = "CREATE TABLE a (a1 INT NOT NULL PRIMARY KEY,a2 INT NOT NULL,a3 INT NOT NULL,id_b INT NOT NULL REFERENCES b(b1)";

	// drop table
	static String drop = "DROP TABLE a";

	// implementazione DAO

	@Override
	public void create(ADTO obj) {

		Connection conn = Db2DAOFactory.createConnection();
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(insert);
			prep_stmt.clearParameters();

			prep_stmt.setInt(1, obj.getA1());
			prep_stmt.setInt(2, obj.getA2());
			prep_stmt.setInt(3, obj.getA3());
			prep_stmt.setInt(4, obj.getId_b());

			prep_stmt.executeUpdate();

			prep_stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Db2DAOFactory.closeConnection(conn);
		}

	}

	// -------------------------------------------------------------------------------------

	@Override
	public ADTO read(int id) {
		ADTO result = null;

		if (id < 0)
			return result;

		Connection conn = Db2DAOFactory.createConnection();

		try {
			PreparedStatement prep_stmt = conn.prepareStatement(read_by_id);
			prep_stmt.clearParameters();
			prep_stmt.setInt(1, id);
			ResultSet rs = prep_stmt.executeQuery();

			if (rs.next()) {
				ADTO res = new ADTO();

				res.setA1(rs.getInt("a1"));
				res.setA2(rs.getInt("a2"));
				res.setA3(rs.getInt("a3"));
				res.setId_b(rs.getInt("id_b"));

				result = res;
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

	@Override
	public boolean update(ADTO obj) {
		boolean result = false;
		if (obj == null)
			return result;

		Connection conn = Db2DAOFactory.createConnection();

		try {
			PreparedStatement prep_stmt = conn.prepareStatement(update);
			prep_stmt.clearParameters();

			prep_stmt.setInt(1, obj.getA2());
			prep_stmt.setInt(2, obj.getA3());
			prep_stmt.setInt(3, obj.getId_b());
			prep_stmt.setInt(4, obj.getA1());

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

	@Override
	public boolean delete(int id) {
		boolean result = false;
		if (id < 0)
			return result;

		Connection conn = Db2DAOFactory.createConnection();
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(delete);
			prep_stmt.clearParameters();
			prep_stmt.setInt(1, id);
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

	// Metodi Aggiuntivi

	// READ_ALL
	@Override
	public Set<ADTO> readAll() {
		Set<ADTO> result = new HashSet<ADTO>();

		// if (id < 0)
		// return result;

		Connection conn = Db2DAOFactory.createConnection();

		try {
			PreparedStatement prep_stmt = conn.prepareStatement(read_all);
			prep_stmt.clearParameters();
			// prep_stmt.setInt(1, id);
			ResultSet rs = prep_stmt.executeQuery();

			while (rs.next()) {
				ADTO res = new ADTO();

				res.setA1(rs.getInt("a1"));
				res.setA2(rs.getInt("a2"));
				res.setA3(rs.getInt("a3"));
				res.setId_b(rs.getInt("id_b"));

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

	// READ_BY_FK
	@Override
	public Set<ADTO> readByFk(int fk) {
		Set<ADTO> result = new HashSet<ADTO>();

		if (fk < 0)
			return result;

		Connection conn = Db2DAOFactory.createConnection();

		try {
			PreparedStatement prep_stmt = conn.prepareStatement(read_by_fk);
			prep_stmt.clearParameters();
			prep_stmt.setInt(1, fk);
			ResultSet rs = prep_stmt.executeQuery();

			while (rs.next()) {
				ADTO res = new ADTO();

				res.setA1(rs.getInt("a1"));
				res.setA2(rs.getInt("a2"));
				res.setA3(rs.getInt("a3"));
				res.setId_b(rs.getInt("id_b"));

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
}
