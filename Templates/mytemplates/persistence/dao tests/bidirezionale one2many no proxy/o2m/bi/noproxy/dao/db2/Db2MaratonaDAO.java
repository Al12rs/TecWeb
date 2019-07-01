package o2m.bi.noproxy.dao.db2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import o2m.bi.noproxy.dao.CittaDTO;
import o2m.bi.noproxy.dao.MaratonaDAO;
import o2m.bi.noproxy.dao.MaratonaDTO;

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
			prep_stmt.setLong(6, obj.getCitta().getId());

			prep_stmt.executeUpdate();

			prep_stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Db2DAOFactory.closeConnection(conn);
		}

	}

	// -------------------------------------------------------------------------------------

	// Carica una maratona facendo fetch eager della sua città. 
	// Al DAO di citta si chiede che la citta carichi immediatamente le sue
	// maratone.
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

				// ricostruzione del riferimento alla citta
				long id_citta = rs.getLong("id_citta");
				res.setCitta(new Db2CittaDAO().read(id_citta));

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
	// di una citta. Per evitare cicli infiniti, ogni maratona NON fetcha
	// la sua citta. 
	// Il DAO citta eager dopo aver caricato il set di maratone con questo 
	// metodo setta uno ad uno la città, terminando la ricostruzione 
	// delle maratone.	
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
				MaratonaDTO res = new MaratonaDTO();

				res.setId(rs.getLong("id"));
				res.setCodiceMaratona(rs.getLong("codiceMaratona"));
				res.setTitolo(rs.getString("titolo"));
				res.setData(rs.getDate("data"));
				res.setTipo(rs.getString("tipo"));

				// NO ricostruzione del riferimento alla citta,
				// solo check superfluo
				long id_citta_read = rs.getLong("id_citta");
				if (id_citta == id_citta_read)
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

	// NON utilizzato dal meccanismo dei DAO, perchè
	// ogni volta che serve caricare le maratone basandosi sull'id della città
	// si ha a disposizione la città (Db2CittaDAO.read) e non si rende 
	// mai necessario ottenere delle maratone che carichino 
	// in maniera eager la loro città.
	//
	// E' giusto comodo averlo perchè dall'esterno si potrebbe voler caricare
	// le maratone basandosi sull'id della città senza aver caricato la città
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

				// NO ricostruzione del riferimento alla citta,
				// solo check superfluo				
				long id_citta_read = rs.getLong("id_citta");
				if (id_citta == id_citta_read) {
					result.add(res);
				}
			}
			rs.close();
			prep_stmt.close();
		}

		catch (Exception e) {
			e.printStackTrace();
		} finally {
			Db2DAOFactory.closeConnection(conn);
		}

		// ricostruzione del riferimento alla citta,
		// fatto fuori dal ciclo per caricare una volta sola
		// la città comune a tutte le maratone appena lette
		CittaDTO citta = new Db2CittaDAO().read(id_citta);
		result.forEach(maratona -> maratona.setCitta(citta));

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
			prep_stmt.setLong(5, obj.getCitta().getId());
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
}
