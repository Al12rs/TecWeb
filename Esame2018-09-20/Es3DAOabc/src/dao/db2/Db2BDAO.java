package dao.db2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import dao.BDAO;
import dao.BDTO;

public class Db2BDAO implements BDAO {
	// statement metodi CRUD

	// INSERT
	static final String insert = "INSERT INTO b (b1,b2,id_c) VALUES (?,?,?)";

	// DELETE
	static String delete = "DELETE FROM b WHERE b1=?";

	// UPDATE
	static String update = "UPDATE b SET b2=?,id_c=? WHERE b1=?";

	// READ
	static String read_by_id = "SELECT * FROM b WHERE b1=?";

	// READ_BY_FK
	static String read_by_fk = "SELECT * FROM b WHERE id_c=?";

	// READ_ALL
	static String read_all = "SELECT * FROM b ";

	// -------------------------------------------------------------------------------------

	// create table
	private static String create = "CREATE TABLE b (b1 INT NOT NULL PRIMARY KEY,b2 INT NOT NULL,id_c INT NOT NULL REFERENCES c(c1))";;

	// drop table
	static String drop = "DROP TABLE b";

	// implementazione DAO

	@Override
	public void create(BDTO obj) {

		Connection conn = Db2DAOFactory.createConnection();
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(insert);
			prep_stmt.clearParameters();

			prep_stmt.setInt(1, obj.getB1());
			prep_stmt.setInt(2, obj.getB2());
			prep_stmt.setInt(3, obj.getId_c());

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
	public BDTO read(int id) {
		BDTO result = null;

		if (id < 0)
			return result;

		Connection conn = Db2DAOFactory.createConnection();

		try {
			PreparedStatement prep_stmt = conn.prepareStatement(read_by_id);
			prep_stmt.clearParameters();
			prep_stmt.setInt(1, id);
			ResultSet rs = prep_stmt.executeQuery();

			if (rs.next()) {
				BDTO res = new BDTO();

				res.setB1(rs.getInt("b1"));
				res.setB2(rs.getInt("b2"));
				res.setId_c(rs.getInt("id_c"));
				res.setAs((new Db2ADAO()).readByFk(res.getB1()));

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
	public boolean update(BDTO obj) {
		boolean result = false;
		if (obj == null)
			return result;

		Connection conn = Db2DAOFactory.createConnection();

		try {
			PreparedStatement prep_stmt = conn.prepareStatement(update);
			prep_stmt.clearParameters();

			prep_stmt.setInt(1, obj.getB2());
			prep_stmt.setInt(2, obj.getId_c());
			prep_stmt.setInt(3, obj.getB1());

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
	
	//READ ALL
	@Override
	public Set<BDTO> readAll() {
		Set<BDTO> result = new HashSet<BDTO>();

		//if (id < 0)
			//return result;

		Connection conn = Db2DAOFactory.createConnection();

		try {
			PreparedStatement prep_stmt = conn.prepareStatement(read_all);
			prep_stmt.clearParameters();
			//prep_stmt.setInt(1, id);
			ResultSet rs = prep_stmt.executeQuery();

			while (rs.next()) {
				BDTO res = new BDTO();

				res.setB1(rs.getInt("b1"));
				res.setB2(rs.getInt("b2"));
				res.setId_c(rs.getInt("id_c"));
				res.setAs((new Db2ADAO()).readByFk(res.getB1()));

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
	
	@Override
	public Set<BDTO> readByFk(int fk) {
		Set<BDTO> result = new HashSet<BDTO>();

		if (fk < 0)
			return result;

		Connection conn = Db2DAOFactory.createConnection();

		try {
			PreparedStatement prep_stmt = conn.prepareStatement(read_by_fk);
			prep_stmt.clearParameters();
			prep_stmt.setInt(1, fk);
			ResultSet rs = prep_stmt.executeQuery();

			while (rs.next()) {
				BDTO res = new BDTO();

				res.setB1(rs.getInt("b1"));
				res.setB2(rs.getInt("b2"));
				res.setId_c(rs.getInt("id_c"));
				res.setAs((new Db2ADAO()).readByFk(res.getB1()));

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
