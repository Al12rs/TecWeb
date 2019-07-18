package dao.db2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dao.ProgettoDAO;
import dao.ProgettoDTO;

public class Db2ProgettoDAO implements ProgettoDAO {
	// statement metodi CRUD

	// INSERT
	static final String insert = "INSERT INTO progetto (id,codiceProgetto,nomeProgetto,annoInizio,durata) VALUES (?,?,?,?,?)";

	// DELETE
	static String delete = "DELETE FROM progetto WHERE id=?";

	// UPDATE
	static String update = "UPDATE progetto SET codiceProgetto=?,nomeProgetto=?,annoInizio=?,durata=? WHERE id=?";

	// READ
	static String read_by_id = "SELECT * FROM progetto WHERE id=?";

	// READ ALL
	static String read_all = "SELECT * FROM partner ";

	// -------------------------------------------------------------------------------------

	// create table
	private static String create = "CREATE TABLE progetto (id BIGINT NOT NULL PRIMARY KEY,codiceProgetto VARCHAR(50) NOT NULL UNIQUE,nomeProgetto VARCHAR(50) NOT NULL,annoInizio INT NOT NULL,durata INT NOT NULL)";;

	// drop table
	static String drop = "DROP TABLE progetto";

	// implementazione DAO

	@Override
	public void create(ProgettoDTO obj) {

		Connection conn = Db2DAOFactory.createConnection();
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(insert);
			prep_stmt.clearParameters();

			prep_stmt.setLong(1, obj.getId());
			prep_stmt.setString(2, obj.getCodiceProgetto());
			prep_stmt.setString(3, obj.getNomeProgetto());
			prep_stmt.setInt(4, obj.getAnnoInizio());
			prep_stmt.setInt(5, obj.getDurata());

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
	public ProgettoDTO read(long id) {
		ProgettoDTO result = null;

		if (id < 0)
			return result;

		Connection conn = Db2DAOFactory.createConnection();

		try {
			PreparedStatement prep_stmt = conn.prepareStatement(read_by_id);
			prep_stmt.clearParameters();
			prep_stmt.setLong(1, id);
			ResultSet rs = prep_stmt.executeQuery();

			if (rs.next()) {
				ProgettoDTO res = new ProgettoDTO();

				res.setId(rs.getLong("id"));
				res.setCodiceProgetto(rs.getString("codiceProgetto"));
				res.setNomeProgetto(rs.getString("nomeProgetto"));
				res.setAnnoInizio(rs.getInt("annoInizio"));
				res.setDurata(rs.getInt("durata"));

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
	public boolean update(ProgettoDTO obj) {
		boolean result = false;
		if (obj == null)
			return result;

		Connection conn = Db2DAOFactory.createConnection();

		try {
			PreparedStatement prep_stmt = conn.prepareStatement(update);
			prep_stmt.clearParameters();

			prep_stmt.setString(1, obj.getCodiceProgetto());
			prep_stmt.setString(2, obj.getNomeProgetto());
			prep_stmt.setInt(3, obj.getAnnoInizio());
			prep_stmt.setInt(4, obj.getDurata());
			prep_stmt.setLong(5, obj.getId());

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
	public boolean delete(long id) {
		boolean result = false;
		if (id < 0)
			return result;

		Connection conn = Db2DAOFactory.createConnection();
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(delete);
			prep_stmt.clearParameters();
			prep_stmt.setLong(1, id);
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

	@Override
	public List<ProgettoDTO> readAll() {
		List<ProgettoDTO> result = new ArrayList<ProgettoDTO>();

		Connection conn = Db2DAOFactory.createConnection();

		try {
			PreparedStatement prep_stmt = conn.prepareStatement(read_all);
			prep_stmt.clearParameters();
			ResultSet rs = prep_stmt.executeQuery();

			while (rs.next()) {
				ProgettoDTO res = new ProgettoDTO();

				res.setId(rs.getLong("id"));
				res.setCodiceProgetto(rs.getString("codiceProgetto"));
				res.setNomeProgetto(rs.getString("nomeProgetto"));
				res.setAnnoInizio(rs.getInt("annoInizio"));
				res.setDurata(rs.getInt("durata"));

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
