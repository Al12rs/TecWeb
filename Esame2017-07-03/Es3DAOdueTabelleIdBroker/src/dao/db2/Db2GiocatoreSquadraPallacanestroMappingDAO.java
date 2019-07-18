package dao.db2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import dao.GiocatoreSquadraPallacanestroMappingDAO;
import dao.SquadraPallacanestroDTO;

//TO-DO sostituire tutti gli id1 e id2, e eventuali query

public class Db2GiocatoreSquadraPallacanestroMappingDAO implements GiocatoreSquadraPallacanestroMappingDAO {
	// === Costanti letterali per non sbagliarsi a scrivere !!!
	// ============================
	static final String TABLE = "giocatori_squadre";
	// -------------------------------------------------------------------------------------
	static final String ID_1 = "idGiocatore";
	static final String ID_2 = "idSquadra";
	// == STATEMENT SQL
	// ====================================================================
	static final String insert = "INSERT " + "INTO " + TABLE + " ( " + ID_1 + ", " + ID_2 + " " + ") "
			+ "VALUES (?,?) ";

	// SELECT * FROM table WHERE idcolumns = ?;
	static String read_by_ids = "SELECT * " + "FROM " + TABLE + " " + "WHERE " + ID_1 + " = ? " + "AND " + ID_2
			+ " = ? ";

	// select
	static String read_squadre_by_id_giocatore = "SELECT * FROM " + TABLE + ", " + Db2SquadraPallacanestroDAO.TABLE
			+ " WHERE " + Db2SquadraPallacanestroDAO.TABLE + "." + Db2SquadraPallacanestroDAO.ID + " = " + TABLE + "."
			+ ID_2 + " AND "+ ID_1 + " = ?";

	// SELECT * FROM table WHERE stringcolumn = ?;
	static String read_all = "SELECT * " + "FROM " + TABLE + " ";

	// DELETE FROM table WHERE idcolumn = ?;
	static String delete = "DELETE " + "FROM " + TABLE + " " + "WHERE " + ID_1 + " = ? " + "AND " + ID_2 + " = ? ";

	// SELECT * FROM table;
	static String query = "SELECT * " + "FROM " + TABLE + " " + "WHERE  ";
	// -------------------------------------------------------------------------------------
	// CREATE entrytable ( id INT NOT NULL PRIMARY KEY, ... );
	static String create = "CREATE " + "TABLE " + TABLE + " ( " + ID_1 + " INT NOT NULL, " + ID_2 + " INT NOT NULL, "
			+ "PRIMARY KEY (" + ID_1 + "," + ID_2 + " ), " + "FOREIGN KEY (" + ID_1 + ") REFERENCES tabella1(id), "
			+ "FOREIGN KEY (" + ID_2 + ") REFERENCES tabella2(id) " + ") ";

	static String drop = "DROP " + "TABLE " + TABLE + " ";

	@Override
	public void create(int idGiocatore, int idSquadra) {
		Connection conn = Db2DAOFactory.createConnection();
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(insert);
			prep_stmt.clearParameters();
			prep_stmt.setInt(1, idGiocatore);
			prep_stmt.setInt(2, idSquadra);
			prep_stmt.executeUpdate();
			prep_stmt.close();
		} catch (Exception e) {
			System.out.println("create(): failed to insert entry: " + e.getMessage());
			e.printStackTrace();
		}

	}

	@Override
	public boolean delete(int idGiocatore, int idSquadra) {
		boolean result = false;
		if (idGiocatore < 0 || idSquadra < 0) {
			System.out.println("delete(): cannot delete an entry with an invalid id ");
			return result;
		}
		Connection conn = Db2DAOFactory.createConnection();
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(delete);
			prep_stmt.clearParameters();
			prep_stmt.setInt(1, idGiocatore);
			prep_stmt.setInt(2, idSquadra);
			prep_stmt.executeUpdate();
			result = true;
			prep_stmt.close();
		} catch (Exception e) {
			System.out.println("delete(): failed to delete entry with idGiocatore = " + idGiocatore
					+ " and idSquadra = " + idSquadra + ": " + e.getMessage());
			e.printStackTrace();
		} finally {
			Db2DAOFactory.closeConnection(conn);
		}
		return result;
	}

	@Override
	public boolean createTable() {
		boolean result = false;
		Connection conn = Db2DAOFactory.createConnection();
		try {
			Statement stmt = conn.createStatement();
			stmt.execute(create);
			result = true;
			stmt.close();
		} catch (Exception e) {
			System.out.println("createTable(): failed to create table '" + TABLE + "': " + e.getMessage());
		} finally {
			Db2DAOFactory.closeConnection(conn);
		}
		return result;
	}

	@Override
	public boolean dropTable() {
		boolean result = false;
		Connection conn = Db2DAOFactory.createConnection();
		try {
			Statement stmt = conn.createStatement();
			stmt.execute(drop);
			result = true;
			stmt.close();
		} catch (Exception e) {
			System.out.println("dropTable(): failed to drop table '" + TABLE + "': " + e.getMessage());
		} finally {
			Db2DAOFactory.closeConnection(conn);
		}
		return result;
	}

	// METODI PER LA RESTITUZIONE DI COLLEZIONI

	@Override
	public Set<SquadraPallacanestroDTO> getSquadre(int idGiocatore) {
		Set<SquadraPallacanestroDTO> result = null;
		if (idGiocatore < 0) {
			System.out.println("read(): cannot read an entry with a negative id");
			return result;
		}
		Connection conn = Db2DAOFactory.createConnection();
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(read_squadre_by_id_giocatore);
			prep_stmt.clearParameters();
			prep_stmt.setInt(1, idGiocatore);
			ResultSet rs = prep_stmt.executeQuery();

			result = new HashSet<SquadraPallacanestroDTO>();
			while (rs.next()) {
				SquadraPallacanestroDTO entry = new SquadraPallacanestroDTO();
				entry.setIdSquadra(rs.getInt(Db2SquadraPallacanestroDAO.ID));
				entry.setNome(rs.getString(Db2SquadraPallacanestroDAO.NOME));
				entry.setTorneo(rs.getString(Db2SquadraPallacanestroDAO.TORNEO));
				entry.setAllenatore(rs.getString(Db2SquadraPallacanestroDAO.ALLENATORE));
				result.add(entry);
			}
			rs.close();
			prep_stmt.close();
		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			Db2DAOFactory.closeConnection(conn);
		}
		return result;
	}

}
