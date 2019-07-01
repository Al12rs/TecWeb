package esame.dao.db2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import esame.dao.PasseggeroDAO;
import esame.dao.PasseggeroDTO;
import esame.dao.VoloDTO;

public class Db2PasseggeroDAO implements PasseggeroDAO {
	// statement metodi CRUD

	// INSERT
	static final String insert = "INSERT INTO passeggero (id,codicePasseggero,nome,cognome,sesso,codicePassaporto) VALUES (?,?,?,?,?,?)";

	// DELETE
	static String delete = "DELETE FROM passeggero WHERE id=?";

	// UPDATE
	static String update = "UPDATE passeggero SET codicePasseggero=?,nome=?,cognome=?,sesso=?,codicePassaporto=? WHERE id=?";

	// READ
	static String read_by_id = "SELECT * FROM passeggero WHERE id=?";

	// -------------------------------------------------------------------------------------

	// create table
	private static String create = "CREATE TABLE passeggero (id BIGINT NOT NULL PRIMARY KEY,codicePasseggero VARCHAR(50) NOT NULL,nome VARCHAR(50) NOT NULL,cognome VARCHAR(50) NOT NULL,sesso VARCHAR(50) NOT NULL,codicePassaporto VARCHAR(50) NOT NULL)";;

	// drop table
	static String drop = "DROP TABLE passeggero";

	// -------------------------------------------------------------------------------------
	// should update the link table of the relationship?
	private boolean relationshipOwner = true;
	
	// -------------------------------------------------------------------------------------

	// implementazione DAO

	@Override
	public void create(PasseggeroDTO obj) {

		Connection conn = Db2DAOFactory.createConnection();
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(insert);
			prep_stmt.clearParameters();

			prep_stmt.setLong(1, obj.getId());
			prep_stmt.setString(2, obj.getCodicePasseggero());
			prep_stmt.setString(3, obj.getNome());
			prep_stmt.setString(4, obj.getCognome());
			prep_stmt.setString(5, obj.getSesso());
			prep_stmt.setString(6, obj.getCodicePassaporto());

			prep_stmt.executeUpdate();

			prep_stmt.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Db2DAOFactory.closeConnection(conn);
		}

		if (relationshipOwner && obj.getVoli()!=null && !obj.getVoli().isEmpty()) {
			Db2LinkDAO dao = new Db2LinkDAO();
			for(VoloDTO v : obj.getVoli()){
				dao.create(obj.getId(), v.getId());
			}
		}
	}

	// -------------------------------------------------------------------------------------

	@Override
	public PasseggeroDTO read(long id) {
		PasseggeroDTO result = null;

		if (id < 0)
			return result;

		Connection conn = Db2DAOFactory.createConnection();

		try {
			PreparedStatement prep_stmt = conn.prepareStatement(read_by_id);
			prep_stmt.clearParameters();
			prep_stmt.setLong(1, id);
			ResultSet rs = prep_stmt.executeQuery();

			if (rs.next()) {
				PasseggeroDTO res = new PasseggeroDTO();

				res.setId(rs.getLong("id"));
				res.setCodicePasseggero(rs.getString("codicePasseggero"));
				res.setNome(rs.getString("nome"));
				res.setCognome(rs.getString("cognome"));
				res.setSesso(rs.getString("sesso"));
				res.setCodicePassaporto(rs.getString("codicePassaporto"));
				
				Set<VoloDTO> voli = new HashSet<>();
				for (long id_Volo : new Db2LinkDAO().read_by_id_Passeggero(res.getId())){
					voli.add(new Db2VoloDAO().readLazy(id_Volo));
				}
				res.setVoli(voli);

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
	

	public PasseggeroDTO readLazy(long id) {
		PasseggeroDTO result = null;

		if (id < 0)
			return result;

		Connection conn = Db2DAOFactory.createConnection();

		try {
			PreparedStatement prep_stmt = conn.prepareStatement(read_by_id);
			prep_stmt.clearParameters();
			prep_stmt.setLong(1, id);
			ResultSet rs = prep_stmt.executeQuery();

			if (rs.next()) {
				PasseggeroDTO res = new Db2PasseggeroProxy();

				res.setId(rs.getLong("id"));
				res.setCodicePasseggero(rs.getString("codicePasseggero"));
				res.setNome(rs.getString("nome"));
				res.setCognome(rs.getString("cognome"));
				res.setSesso(rs.getString("sesso"));
				res.setCodicePassaporto(rs.getString("codicePassaporto"));

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
	public boolean update(PasseggeroDTO obj) {
		boolean result = false;
		if (obj == null)
			return result;

		Connection conn = Db2DAOFactory.createConnection();

		try {
			PreparedStatement prep_stmt = conn.prepareStatement(update);
			prep_stmt.clearParameters();

			prep_stmt.setString(1, obj.getCodicePasseggero());
			prep_stmt.setString(2, obj.getNome());
			prep_stmt.setString(3, obj.getCognome());
			prep_stmt.setString(4, obj.getSesso());
			prep_stmt.setString(5, obj.getCodicePassaporto());
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
			if (obj.getVoli()==null||obj.getVoli().isEmpty()){
				dao.delete_by_id_Volo(obj.getId());
			} else {
				Set<Long> olds = dao.read_by_id_Passeggero(obj.getId());
				Set<Long> news = obj.getVoli().stream().map(p -> p.getId()).collect(Collectors.toSet());
				for(long oldId : olds) {
					if (!news.contains(oldId))
						dao.delete(obj.getId(),oldId);
				}
				for(long newId : news) {
					if (!olds.contains(newId))
						dao.create(obj.getId(),newId);
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
			new Db2LinkDAO().delete_by_id_Passeggero(id);
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
		}

		finally {
			Db2DAOFactory.closeConnection(conn);
		}
		
		if (relationshipOwner) {
			new Db2LinkDAO().dropTable();
		}

		return result;
	}

}
