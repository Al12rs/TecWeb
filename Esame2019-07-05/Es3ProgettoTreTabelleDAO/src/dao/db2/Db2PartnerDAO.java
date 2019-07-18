package dao.db2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dao.PartnerDAO;
import dao.PartnerDTO;

public class Db2PartnerDAO implements PartnerDAO {
	// statement metodi CRUD

	// INSERT
	static final String insert = "INSERT INTO partner (id,siglaPartner,nome) VALUES (?,?,?)";

	// DELETE
	static String delete = "DELETE FROM partner WHERE id=?";

	// UPDATE
	static String update = "UPDATE partner SET siglaPartner=?,nome=? WHERE id=?";

	// READ
	static String read_by_id = "SELECT * FROM partner WHERE id=?";

	// READ ALL
	static String read_all = "SELECT * FROM partner ";

	// READ_BY_UNIQUE
	static String read_by_unique = "SELECT * FROM partner WHERE siglaPartner=?";

	// -------------------------------------------------------------------------------------

	// create table
	private static String create = "CREATE TABLE partner (id BIGINT NOT NULL PRIMARY KEY,siglaPartner VARCHAR(50) NOT NULL UNIQUE,nome VARCHAR(50) NOT NULL)";;

	// drop table
	static String drop = "DROP TABLE partner";

	// implementazione DAO

	@Override
	public void create(PartnerDTO obj) {

		Connection conn = Db2DAOFactory.createConnection();
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(insert);
			prep_stmt.clearParameters();

			prep_stmt.setLong(1, obj.getId());
			prep_stmt.setString(2, obj.getSiglaPartner());
			prep_stmt.setString(3, obj.getNome());

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
	public PartnerDTO read(long id) {
		PartnerDTO result = null;

		if (id < 0)
			return result;

		Connection conn = Db2DAOFactory.createConnection();

		try {
			PreparedStatement prep_stmt = conn.prepareStatement(read_by_id);
			prep_stmt.clearParameters();
			prep_stmt.setLong(1, id);
			ResultSet rs = prep_stmt.executeQuery();

			if (rs.next()) {
				PartnerDTO res = new Db2PartnerDTOProxy();

				res.setId(rs.getLong("id"));
				res.setSiglaPartner(rs.getString("siglaPartner"));
				res.setNome(rs.getString("nome"));

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
	public boolean update(PartnerDTO obj) {
		boolean result = false;
		if (obj == null)
			return result;

		Connection conn = Db2DAOFactory.createConnection();

		try {
			PreparedStatement prep_stmt = conn.prepareStatement(update);
			prep_stmt.clearParameters();

			prep_stmt.setString(1, obj.getSiglaPartner());
			prep_stmt.setString(2, obj.getNome());
			prep_stmt.setLong(3, obj.getId());

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
	public PartnerDTO readByUnique(String sigla) {
		PartnerDTO result = null;

		if (sigla == null)
			return result;

		Connection conn = Db2DAOFactory.createConnection();

		try {
			PreparedStatement prep_stmt = conn.prepareStatement(read_by_unique);
			prep_stmt.clearParameters();
			prep_stmt.setString(1, sigla);
			ResultSet rs = prep_stmt.executeQuery();

			if (rs.next()) {
				PartnerDTO res = new Db2PartnerDTOProxy();

				res.setId(rs.getLong("id"));
				res.setSiglaPartner(rs.getString("siglaPartner"));
				res.setNome(rs.getString("nome"));

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
	
	@Override
	public List<PartnerDTO> readAll() {
		List<PartnerDTO> result = new ArrayList<PartnerDTO>();

		Connection conn = Db2DAOFactory.createConnection();

		try {
			PreparedStatement prep_stmt = conn.prepareStatement(read_all);
			prep_stmt.clearParameters();
			ResultSet rs = prep_stmt.executeQuery();

			while (rs.next()) {
				PartnerDTO res = new Db2PartnerDTOProxy();

				res.setId(rs.getLong("id"));
				res.setSiglaPartner(rs.getString("siglaPartner"));
				res.setNome(rs.getString("nome"));

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
