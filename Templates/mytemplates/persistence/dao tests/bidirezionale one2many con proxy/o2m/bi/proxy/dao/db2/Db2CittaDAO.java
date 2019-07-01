package o2m.bi.proxy.dao.db2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import o2m.bi.proxy.dao.CittaDAO;
import o2m.bi.proxy.dao.CittaDTO;
import o2m.bi.proxy.dao.MaratonaDTO;

public class Db2CittaDAO implements CittaDAO {
	// statement metodi CRUD

	// INSERT
	static final String insert = "INSERT INTO citta (id,nome,nazione) VALUES (?,?,?)";

	// DELETE
	static String delete = "DELETE FROM citta WHERE id=?";

	// UPDATE
	static String update = "UPDATE citta SET nome=?,nazione=? WHERE id=?";

	// READ
	static String read_by_id = "SELECT * FROM citta WHERE id=?";
	static String read_by_name = "SELECT * FROM citta WHERE nome=?";

	// -------------------------------------------------------------------------------------

	// create table
	private static String create = "CREATE TABLE citta (id BIGINT NOT NULL PRIMARY KEY,nome VARCHAR(50) NOT NULL UNIQUE,nazione VARCHAR(50) NOT NULL)";;

	// drop table
	static String drop = "DROP TABLE citta";

	// implementazione DAO

	@Override
	public void create(CittaDTO obj) {

		Connection conn = Db2DAOFactory.createConnection();
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(insert);
			prep_stmt.clearParameters();

			prep_stmt.setLong(1, obj.getId());
			prep_stmt.setString(2, obj.getNome());
			prep_stmt.setString(3, obj.getNazione());

			prep_stmt.executeUpdate();

			prep_stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Db2DAOFactory.closeConnection(conn);
		}

	}

	// -------------------------------------------------------------------------------------

	// carica una Citta facendo fetch eager delle sue maratone, per non
	// incorrere in cicli infiniti si richiede il caricamento di 
	// proxy di maratone (che dunque non caricano immediatamente la loro città)
	@Override
	public CittaDTO read(long id) {
		CittaDTO result = null;

		if (id < 0)
			return result;

		Connection conn = Db2DAOFactory.createConnection();

		try {
			PreparedStatement prep_stmt = conn.prepareStatement(read_by_id);
			prep_stmt.clearParameters();
			prep_stmt.setLong(1, id);
			ResultSet rs = prep_stmt.executeQuery();

			if (rs.next()) {
				CittaDTO res = new CittaDTO();

				res.setId(rs.getLong("id"));
				res.setNome(rs.getString("nome"));
				res.setNazione(rs.getString("nazione"));
				
				// readByIdCittaLazy è visibile solo da Db2MaratonaDAO e non
				// dall'interfaccia MaratonaDAO
				res.setMaratone(new Db2MaratonaDAO().readByIdCittaLazy(res.getId()));
				// avendo già qui la citta la setto subito sulle maratone, sarebbe uno spreco
				// lasciare che chiamando getCitta su ognuna di queste parta una query
				// per trovare la citta
				// NB: così facendo ricostruisco in maniera più efficiente gli oggetti in memoria, 
				// ma i toString di questa città falliranno cercando di stampare ricorsivamente 
				// la citta di ogni maratona nel set di maratone di questa città
				for (MaratonaDTO m : res.getMaratone()) {
					m.setCitta(res);
				}

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
	public CittaDTO readByName(String name) {
		CittaDTO result = null;

		if (name==null || name.isEmpty())
			return result;

		Connection conn = Db2DAOFactory.createConnection();

		try {
			PreparedStatement prep_stmt = conn.prepareStatement(read_by_name);
			prep_stmt.clearParameters();
			prep_stmt.setString(1, name);
			ResultSet rs = prep_stmt.executeQuery();

			if (rs.next()) {
				CittaDTO res = new CittaDTO();

				res.setId(rs.getLong("id"));
				res.setNome(rs.getString("nome"));
				res.setNazione(rs.getString("nazione"));
				
				// readByIdCittaLazy è visibile solo da Db2MaratonaDAO e non
				// dall'interfaccia MaratonaDAO
				res.setMaratone(new Db2MaratonaDAO().readByIdCittaLazy(res.getId()));
				// avendo già qui la citta la setto subito sulle maratone, è uno spreco
				// lasciare che chiamando getCitta su ognuna di queste parta una query
				for (MaratonaDTO m : res.getMaratone()) {
					m.setCitta(res);
				}

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
	// Utilizzato dal DAO di maratona per ottenere la città da assegnare alla maratona
	// appena caricata.
	// Carica una città senza rimepirne immediatamente il set di maratone, 
	// restituisce quindi un proxy di città che si occuperà di fare il caricamento.
	public CittaDTO readLazy(long id) {
		CittaDTO result = null;

		if (id < 0)
			return result;

		Connection conn = Db2DAOFactory.createConnection();

		try {
			PreparedStatement prep_stmt = conn.prepareStatement(read_by_id);
			prep_stmt.clearParameters();
			prep_stmt.setLong(1, id);
			ResultSet rs = prep_stmt.executeQuery();

			if (rs.next()) {
				CittaDTO res = new Db2CittaProxy();

				res.setId(rs.getLong("id"));
				res.setNome(rs.getString("nome"));
				res.setNazione(rs.getString("nazione"));
				
				// res.setMaratone(null);

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
	public boolean update(CittaDTO obj) {
		boolean result = false;
		if (obj == null)
			return result;

		Connection conn = Db2DAOFactory.createConnection();

		try {
			PreparedStatement prep_stmt = conn.prepareStatement(update);
			prep_stmt.clearParameters();

			prep_stmt.setString(1, obj.getNome());
			prep_stmt.setString(2, obj.getNazione());
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

}
