package dao.db2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import dao.CDAO;
import dao.CDTO;

public class Db2CDAO implements CDAO {
	// statement metodi CRUD

	// INSERT
	static final String insert = "INSERT INTO c (c1,c2,c3) VALUES (?,?,?)";

	// DELETE
	static String delete = "DELETE FROM c WHERE c1=?";

	// UPDATE
	static String update = "UPDATE c SET c2=?,c3=? WHERE c1=?";

	// READ
	static String read_by_id = "SELECT * FROM c WHERE c1=?";

	// READ_ALL
	static String read_all = "SELECT * FROM a ";

	// -------------------------------------------------------------------------------------

	// create table
	private static String create = "CREATE TABLE c (c1 INT NOT NULL PRIMARY KEY,c2 INT NOT NULL,c3 INT NOT NULL)";;

	// drop table
	static String drop = "DROP TABLE c";

	// implementazione DAO

	@Override
	public void create(CDTO obj) {

		Connection conn = Db2DAOFactory.createConnection();
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(insert);
			prep_stmt.clearParameters();

			prep_stmt.setInt(1, obj.getC1());
			prep_stmt.setInt(2, obj.getC2());
			prep_stmt.setInt(3, obj.getC3());

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
	public CDTO read(int id) {
		CDTO result = null;

		if (id < 0)
			return result;

		Connection conn = Db2DAOFactory.createConnection();

		try {
			PreparedStatement prep_stmt = conn.prepareStatement(read_by_id);
			prep_stmt.clearParameters();
			prep_stmt.setInt(1, id);
			ResultSet rs = prep_stmt.executeQuery();

			if (rs.next()) {
				CDTO res = new CDTO();

				res.setC1(rs.getInt("c1"));
				res.setC2(rs.getInt("c2"));
				res.setC3(rs.getInt("c3"));
				res.setBs((new Db2BDAO()).readByFk(res.getC1()));

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
	public boolean update(CDTO obj) {
		boolean result = false;
		if (obj == null)
			return result;

		Connection conn = Db2DAOFactory.createConnection();

		try {
			PreparedStatement prep_stmt = conn.prepareStatement(update);
			prep_stmt.clearParameters();

			prep_stmt.setInt(1, obj.getC2());
			prep_stmt.setInt(2, obj.getC3());
			prep_stmt.setInt(3, obj.getC1());

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
	public Set<CDTO> readAll() {
		Set<CDTO> result = new HashSet<CDTO>();

		// if (id < 0)
		// return result;

		Connection conn = Db2DAOFactory.createConnection();

		try {
			PreparedStatement prep_stmt = conn.prepareStatement(read_all);
			prep_stmt.clearParameters();
			// prep_stmt.setInt(1, id);
			ResultSet rs = prep_stmt.executeQuery();

			while (rs.next()) {
				CDTO res = new CDTO();

				res.setC1(rs.getInt("c1"));
				res.setC2(rs.getInt("c2"));
				res.setC3(rs.getInt("c3"));
				res.setBs((new Db2BDAO()).readByFk(res.getC1()));

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
