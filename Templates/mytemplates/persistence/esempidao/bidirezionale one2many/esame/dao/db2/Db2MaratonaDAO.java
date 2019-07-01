package esame.dao.db2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import esame.dao.MaratonaDAO;
import esame.dao.MaratonaDTO;

public class Db2MaratonaDAO implements MaratonaDAO {
	// statement metodi CRUD

	// INSERT
	static final String insert = "INSERT INTO maratona (id,codiceMaratona,titolo,data,tipo,id_citta) VALUES (?,?,?,?,?,?)";

	// DELETE
	static String delete = "DELETE FROM maratona WHERE id=?";

	// UPDATE
	static String update = "UPDATE maratona SET codiceMaratona=?,titolo=?,data=?,tipo=?,id_citta=? WHERE id=?";

	// READ
	static String read_by_id = "SELECT * FROM maratona WHERE id=?";
	static String read_by_id_citta = "SELECT * FROM maratona WHERE id_citta=?";

	// -------------------------------------------------------------------------------------

	// create table
	private static String create = "CREATE TABLE maratona (id BIGINT NOT NULL PRIMARY KEY,codiceMaratona BIGINT NOT NULL UNIQUE,titolo VARCHAR(50) NOT NULL,data DATE NOT NULL,tipo VARCHAR(50) NOT NULL,id_citta BIGINT NOT NULL REFERENCES citta(id))";;

	// drop table
	static String drop = "DROP TABLE maratona";

	// implementazione DAO

	@Override
	public void create(MaratonaDTO obj) {

		Connection conn = Db2DAOFactory.createConnection();
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(insert);
			prep_stmt.clearParameters();

			prep_stmt.setLong(1, obj.getId());
			prep_stmt.setLong(2, obj.getCodiceMaratona());
			prep_stmt.setString(3, obj.getTitolo());
			prep_stmt.setDate(4, obj.getData());
			prep_stmt.setString(5, obj.getTipo());
			prep_stmt.setLong(6, obj.getId_citta());

			prep_stmt.executeUpdate();

			prep_stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Db2DAOFactory.closeConnection(conn);
		}

	}

	// -------------------------------------------------------------------------------------

	// Carica una maratona facendo fetch eager della sua città. La città
	// è caricata come proxy, cioè non carica immediatamente le sue maratone
	@Override
	public MaratonaDTO read(long id) {
		MaratonaDTO result = null;

		if (id < 0)
			return result;

		Connection conn = Db2DAOFactory.createConnection();

		try {
			PreparedStatement prep_stmt = conn.prepareStatement(read_by_id);
			prep_stmt.clearParameters();
			prep_stmt.setLong(1, id);
			ResultSet rs = prep_stmt.executeQuery();

			if (rs.next()) {
				MaratonaDTO res = new MaratonaDTO();

				res.setId(rs.getLong("id"));
				res.setCodiceMaratona(rs.getLong("codiceMaratona"));
				res.setTitolo(rs.getString("titolo"));
				res.setData(rs.getDate("data"));
				res.setTipo(rs.getString("tipo"));
				res.setId_citta(rs.getLong("id_citta"));
				res.setCitta(new Db2CittaDAO().readLazy(res.getId_citta()));

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

	// METODO LAZY NON VISIBILE DALL'INTERFACCIA
	
	// Utilizzato dal DAO citta per creare in maniera eager il set di maratone
	// di una citta. Per evitare cicli infiniti, ogni maratona fetcha in 
	// maniera eager la sua citta, ottenendo un proxy di citta 
	// (una citta che non ha caricato le sue maratone) che carichrebbe le sue maratone
	// su necessità. In realtà il DAO citta eager dopo aver caricato il set di proxy di
	// maratone con questo metodo setta uno ad uno la città, quindi la 
	// query di Db2MaratonaProxy.getCitta() non sarà mai necessaria.
	
	// Utilizzato dai proxy di città per caricare su richiesta un set di 
	// maratone. Anche in questo caso il metodo getCittà dei proxy non farà mai una
	// query perchè non appena il proxy di città ha caricato il set di proxy di maratone
	// ne setta la città usando se stesso come riferimento.
	public Set<MaratonaDTO> readByIdCittaLazy(long id_citta) {
		Set<MaratonaDTO> result = new HashSet<>();

		if (id_citta < 0)
			return result;

		Connection conn = Db2DAOFactory.createConnection();

		try {
			PreparedStatement prep_stmt = conn.prepareStatement(read_by_id_citta);
			prep_stmt.clearParameters();
			prep_stmt.setLong(1, id_citta);
			ResultSet rs = prep_stmt.executeQuery();

			while (rs.next()) {
				MaratonaDTO res = new Db2MaratonaProxy();

				res.setId(rs.getLong("id"));
				res.setCodiceMaratona(rs.getLong("codiceMaratona"));
				res.setTitolo(rs.getString("titolo"));
				res.setData(rs.getDate("data"));
				res.setTipo(rs.getString("tipo"));
				res.setId_citta(rs.getLong("id_citta"));

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
	
	// Non utilizzato dal meccanismo dei DAO e dei Proxy, perchè
	// ogni volta che serve caricare le maratone basandosi sull'id della città
	// si ha a disposizione o la città (Db2CittaDAO.read) oppure il suo proxy
	// (Db2CittaProxy) e non si rende mai necessario ottenere delle maratone
	// che carichino in maniera eager la loro città
	@Override
	public Set<MaratonaDTO> readByIdCitta(long id_citta) {
		Set<MaratonaDTO> result = new HashSet<>();

		if (id_citta < 0)
			return result;

		Connection conn = Db2DAOFactory.createConnection();

		try {
			PreparedStatement prep_stmt = conn.prepareStatement(read_by_id_citta);
			prep_stmt.clearParameters();
			prep_stmt.setLong(1, id_citta);
			ResultSet rs = prep_stmt.executeQuery();

			while (rs.next()) {
				MaratonaDTO res = new MaratonaDTO();

				res.setId(rs.getLong("id"));
				res.setCodiceMaratona(rs.getLong("codiceMaratona"));
				res.setTitolo(rs.getString("titolo"));
				res.setData(rs.getDate("data"));
				res.setTipo(rs.getString("tipo"));
				res.setId_citta(rs.getLong("id_citta"));
				res.setCitta(new Db2CittaDAO().readLazy(res.getId_citta()));

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

	// -------------------------------------------------------------------------------------

	@Override
	public boolean update(MaratonaDTO obj) {
		boolean result = false;
		if (obj == null)
			return result;

		Connection conn = Db2DAOFactory.createConnection();

		try {
			PreparedStatement prep_stmt = conn.prepareStatement(update);
			prep_stmt.clearParameters();

			prep_stmt.setLong(1, obj.getCodiceMaratona());
			prep_stmt.setString(2, obj.getTitolo());
			prep_stmt.setDate(3, obj.getData());
			prep_stmt.setString(4, obj.getTipo());
			prep_stmt.setLong(5, obj.getId_citta());
			prep_stmt.setLong(6, obj.getId());

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

}
