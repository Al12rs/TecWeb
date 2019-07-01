package esame.dao.db2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import esame.dao.PasseggeroDTO;
import esame.dao.VoloDAO;
import esame.dao.VoloDTO;

public class Db2VoloDAO implements VoloDAO {

	// statement metodi CRUD

	// INSERT
	static final String insert = "INSERT INTO volo (id,codVolo,compagniaAerea,localitaDestinazione,dataPartenza,orarioPartenza) VALUES (?,?,?,?,?,?)";

	// DELETE
	static String delete = "DELETE FROM volo WHERE id=?";

	// UPDATE
	static String update = "UPDATE volo SET codVolo=?,compagniaAerea=?,localitaDestinazione=?,dataPartenza=?,orarioPartenza=? WHERE id=?";

	// READ
	static String read_by_id = "SELECT * FROM volo WHERE id=?";

	// -------------------------------------------------------------------------------------

	// create table
	private static String create = "CREATE TABLE volo (id BIGINT NOT NULL PRIMARY KEY,codVolo VARCHAR(50) NOT NULL,compagniaAerea VARCHAR(50) NOT NULL,localitaDestinazione VARCHAR(50) NOT NULL,dataPartenza DATE NOT NULL,orarioPartenza INT NOT NULL)";;

	// drop table
	static String drop = "DROP TABLE volo";

	// -------------------------------------------------------------------------------------
	// should update the link table of the relationship?
	private boolean relationshipOwner = false;

	// -------------------------------------------------------------------------------------

	// implementazione DAO
	@Override
	public void create(VoloDTO obj) {

		Connection conn = Db2DAOFactory.createConnection();
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(insert);
			prep_stmt.clearParameters();

			prep_stmt.setLong(1, obj.getId());
			prep_stmt.setString(2, obj.getCodVolo());
			prep_stmt.setString(3, obj.getCompagniaAerea());
			prep_stmt.setString(4, obj.getLocalitaDestinazione());
			prep_stmt.setDate(5, obj.getDataPartenza());
			prep_stmt.setInt(6, obj.getOrarioPartenza());

			prep_stmt.executeUpdate();

			prep_stmt.close();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Db2DAOFactory.closeConnection(conn);
		}

		if (relationshipOwner && obj.getPasseggeri() != null && !obj.getPasseggeri().isEmpty()) {
			Db2LinkDAO dao = new Db2LinkDAO();
			for (PasseggeroDTO p : obj.getPasseggeri()) {
				dao.create(p.getId(), obj.getId());
			}
		}
	}

	// -------------------------------------------------------------------------------------

	@Override
	public VoloDTO read(long id) {
		VoloDTO result = null;

		if (id < 0)
			return result;

		Connection conn = Db2DAOFactory.createConnection();

		try {
			PreparedStatement prep_stmt = conn.prepareStatement(read_by_id);
			prep_stmt.clearParameters();
			prep_stmt.setLong(1, id);
			ResultSet rs = prep_stmt.executeQuery();

			if (rs.next()) {
				VoloDTO res = new VoloDTO();

				res.setId(rs.getLong("id"));
				res.setCodVolo(rs.getString("codVolo"));
				res.setCompagniaAerea(rs.getString("compagniaAerea"));
				res.setLocalitaDestinazione(rs.getString("localitaDestinazione"));
				res.setDataPartenza(rs.getDate("dataPartenza"));
				res.setOrarioPartenza(rs.getInt("orarioPartenza"));
				
				Set<PasseggeroDTO> passeggeri = new HashSet<>();
				for (long id_Passeggero : new Db2LinkDAO().read_by_id_Volo(res.getId())){
					passeggeri.add(new Db2PasseggeroDAO().readLazy(id_Passeggero));
				}
				res.setPasseggeri(passeggeri);

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
	
	public VoloDTO readLazy(long id) {
		VoloDTO result = null;

		if (id < 0)
			return result;

		Connection conn = Db2DAOFactory.createConnection();

		try {
			PreparedStatement prep_stmt = conn.prepareStatement(read_by_id);
			prep_stmt.clearParameters();
			prep_stmt.setLong(1, id);
			ResultSet rs = prep_stmt.executeQuery();

			if (rs.next()) {
				VoloDTO res = new Db2VoloProxy();

				res.setId(rs.getLong("id"));
				res.setCodVolo(rs.getString("codVolo"));
				res.setCompagniaAerea(rs.getString("compagniaAerea"));
				res.setLocalitaDestinazione(rs.getString("localitaDestinazione"));
				res.setDataPartenza(rs.getDate("dataPartenza"));
				res.setOrarioPartenza(rs.getInt("orarioPartenza"));

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
	public boolean update(VoloDTO obj) {
		boolean result = false;
		if (obj == null)
			return result;

		Connection conn = Db2DAOFactory.createConnection();

		try {
			PreparedStatement prep_stmt = conn.prepareStatement(update);
			prep_stmt.clearParameters();

			prep_stmt.setString(1, obj.getCodVolo());
			prep_stmt.setString(2, obj.getCompagniaAerea());
			prep_stmt.setString(3, obj.getLocalitaDestinazione());
			prep_stmt.setDate(4, obj.getDataPartenza());
			prep_stmt.setInt(5, obj.getOrarioPartenza());
			prep_stmt.setLong(6, obj.getId());

			prep_stmt.executeUpdate();
			result = true;
			prep_stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Db2DAOFactory.closeConnection(conn);
		}
		
		if (relationshipOwner) {
			Db2LinkDAO dao = new Db2LinkDAO();
			if (obj.getPasseggeri()==null||obj.getPasseggeri().isEmpty()){
				dao.delete_by_id_Volo(obj.getId());
			} else {
				Set<Long> olds = dao.read_by_id_Volo(obj.getId());
				Set<Long> news = obj.getPasseggeri().stream().map(p -> p.getId()).collect(Collectors.toSet());
				for(long oldId : olds) {
					if (!news.contains(oldId))
						dao.delete(oldId, obj.getId());
				}
				for(long newId : news) {
					if (!olds.contains(newId))
						dao.create(newId, obj.getId());
				}
			}
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

		if (relationshipOwner) {
			new Db2LinkDAO().delete_by_id_Volo(id);
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
		
		if (relationshipOwner) {
			new Db2LinkDAO().createTable();
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
		} finally {
			Db2DAOFactory.closeConnection(conn);
		}
		
		if (relationshipOwner) {
			new Db2LinkDAO().dropTable();
		}

		return result;
	}
}
