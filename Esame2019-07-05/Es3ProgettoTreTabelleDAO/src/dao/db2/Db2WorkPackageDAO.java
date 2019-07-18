package dao.db2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dao.WorkPackageDAO;
import dao.WorkPackageDTO;

public class Db2WorkPackageDAO implements WorkPackageDAO {
	// statement metodi CRUD

	// INSERT
	static final String insert = "INSERT INTO workpackage (id,nomeWP,titolo,descrizione,idProgetto) VALUES (?,?,?,?,?)";

	// DELETE
	static String delete = "DELETE FROM workpackage WHERE id=?";

	// UPDATE
	static String update = "UPDATE workpackage SET nomeWP=?,titolo=?,descrizione=?,idProgetto=? WHERE id=?";

	// READ
	static String read_by_id = "SELECT * FROM workpackage WHERE id=?";

	// READ ALL
	static String read_all = "SELECT * FROM partner ";

	// -------------------------------------------------------------------------------------

	// create table
	private static String create = "CREATE TABLE workpackage (id BIGINT NOT NULL PRIMARY KEY,nomeWP VARCHAR(50) NOT NULL UNIQUE,titolo VARCHAR(50) NOT NULL,descrizione VARCHAR(50) NOT NULL, idProgetto BIGINT NOT NULL REFERENCES progetto(id))";;

	// drop table
	static String drop = "DROP TABLE workpackage";

	// implementazione DAO

	@Override
	public void create(WorkPackageDTO obj) {

		Connection conn = Db2DAOFactory.createConnection();
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(insert);
			prep_stmt.clearParameters();

			prep_stmt.setLong(1, obj.getId());
			prep_stmt.setString(2, obj.getNomeWP());
			prep_stmt.setString(3, obj.getTitolo());
			prep_stmt.setString(4, obj.getDescrizione());
			prep_stmt.setLong(5, obj.getProgetto().getId());

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
	public WorkPackageDTO read(long id) {
		WorkPackageDTO result = null;

		if (id < 0)
			return result;

		Connection conn = Db2DAOFactory.createConnection();

		try {
			PreparedStatement prep_stmt = conn.prepareStatement(read_by_id);
			prep_stmt.clearParameters();
			prep_stmt.setLong(1, id);
			ResultSet rs = prep_stmt.executeQuery();

			if (rs.next()) {
				WorkPackageDTO res = new WorkPackageDTO();

				res.setId(rs.getLong("id"));
				res.setNomeWP(rs.getString("nomeWP"));
				res.setTitolo(rs.getString("titolo"));
				res.setDescrizione(rs.getString("descrizione"));
				res.setProgetto((new Db2ProgettoDAO()).read(rs.getLong("idProgetto")));

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
	public boolean update(WorkPackageDTO obj) {
		boolean result = false;
		if (obj == null)
			return result;

		Connection conn = Db2DAOFactory.createConnection();

		try {
			PreparedStatement prep_stmt = conn.prepareStatement(update);
			prep_stmt.clearParameters();

			prep_stmt.setString(1, obj.getNomeWP());
			prep_stmt.setString(2, obj.getTitolo());
			prep_stmt.setString(3, obj.getDescrizione());
			prep_stmt.setLong(4, obj.getProgetto().getId());
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
	public List<WorkPackageDTO> readAll() {
		List<WorkPackageDTO> result = new ArrayList<WorkPackageDTO>();


		Connection conn = Db2DAOFactory.createConnection();

		try {
			PreparedStatement prep_stmt = conn.prepareStatement(read_all);
			prep_stmt.clearParameters();
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
}
