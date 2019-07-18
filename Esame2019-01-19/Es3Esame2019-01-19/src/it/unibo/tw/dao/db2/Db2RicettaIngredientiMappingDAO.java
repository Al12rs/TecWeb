package it.unibo.tw.dao.db2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import it.unibo.tw.dao.IngredienteDTO;
import it.unibo.tw.dao.RicettaIngredientiMappingDAO;


//TO-DO sostituire tutti gli id1 e id2, e eventuali query

public class Db2RicettaIngredientiMappingDAO implements
		RicettaIngredientiMappingDAO {
	// === Costanti letterali per non sbagliarsi a scrivere !!! ============================
		static final String TABLE = "ricetta_ingredienti";
		// -------------------------------------------------------------------------------------
		static final String ID_1 = "idRicetta";
		static final String ID_2 = "idIngrediente";
		// == STATEMENT SQL ====================================================================
		static final String insert = 
			"INSERT " +
				"INTO " + TABLE + " ( " + 
					ID_1 +", "+ID_2 + " " +
				") " +
				"VALUES (?,?) "
			;
		
		// SELECT * FROM table WHERE idcolumns = ?;
		static String read_by_ids = 
			"SELECT * " +
				"FROM " + TABLE + " " +
				"WHERE " + ID_1 + " = ? " +
				"AND " + ID_2 + " = ? "
			;

		// SELECT * FROM table WHERE idcolumns = ?;
		static String read_by_ricettaID = 
			"SELECT * " +
				"FROM " + TABLE + " " +
				"WHERE " + ID_1 + " = ? "
			;

		// SELECT * FROM table WHERE idcolumns = ?;
		static String read_by_ingredienteID = 
			"SELECT * " +
				"FROM " + TABLE + "  " +
				"WHERE " + ID_2 + " = ? "
			;
		
		// SELECT * FROM table WHERE stringcolumn = ?;
		static String read_all = 
			"SELECT * " +
			"FROM " + TABLE + " ";
		
		// DELETE FROM table WHERE idcolumn = ?;
		static String delete = 
			"DELETE " +
				"FROM " + TABLE + " " +
				"WHERE " + ID_1 + " = ? " +
				"AND " + ID_2 + " = ? "
			;

		// SELECT * FROM table;
		static String query = 
			"SELECT * " +
				"FROM " + TABLE + " " +
				"WHERE  "
			;
		
		//SELECT
		static String read_ingredienti_per_ricetta =
				"SELECT * " +
						"FROM "+TABLE+", "+Db2IngredienteDAO.TABLE+", "+Db2RicettaDAO.TABLE+ " "+
						"WHERE ricette.id="+ID_1+" AND ingredienti.id="+ID_2+" AND ricetta.id=?";
		// -------------------------------------------------------------------------------------
		// CREATE entrytable ( id INT NOT NULL PRIMARY KEY, ... );
		static String create = 
			"CREATE " +
				"TABLE " + TABLE +" ( " +
					ID_1 + " INT NOT NULL, " +
					ID_2 + " INT NOT NULL, " +
					"PRIMARY KEY (" + ID_1 +","+ ID_2 + " ), " +
					"FOREIGN KEY ("+ID_1+") REFERENCES ricette(id), "+
					"FOREIGN KEY ("+ID_2+") REFERENCES ingredienti(id) "+
				") "
			;
		
		static String drop = 
			"DROP " +
				"TABLE " + TABLE + " "
			;

	@Override
	public void create(int idRicetta, int idIngrediente) {
		Connection conn = Db2DAOFactory.createConnection();
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(insert);
			prep_stmt.clearParameters();
			prep_stmt.setInt(1, idRicetta);
			prep_stmt.setInt(2, idIngrediente);
			prep_stmt.executeUpdate();
			prep_stmt.close();
		}
		catch (Exception e) {
			System.out.println("create(): failed to insert entry: " + e.getMessage());
			e.printStackTrace();
		}

	}


	@Override
	public boolean delete(int idRicetta, int idIngrediente) {
		boolean result = false;
		if ( idRicetta < 0 || idIngrediente < 0 )  {
			System.out.println("delete(): cannot delete an entry with an invalid id ");
			return result;
		}
		Connection conn = Db2DAOFactory.createConnection();
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(delete);
			prep_stmt.clearParameters();
			prep_stmt.setInt(1, idRicetta);
			prep_stmt.setInt(2, idIngrediente);
			prep_stmt.executeUpdate();
			result = true;
			prep_stmt.close();
		}
		catch (Exception e) {
			System.out.println("delete(): failed to delete entry with idRicetta = " + idRicetta +" and idIngrediente = " + idIngrediente + ": "+e.getMessage());
			e.printStackTrace();
		}
		finally {
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
		}
		catch (Exception e) {
			System.out.println("createTable(): failed to create table '"+TABLE+"': "+e.getMessage());
		}
		finally {
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
		}
		catch (Exception e) {
			System.out.println("dropTable(): failed to drop table '"+TABLE+"': "+e.getMessage());
		}
		finally {
			Db2DAOFactory.closeConnection(conn);
		}
		return result;
	}


	@Override
	public Set<IngredienteDTO> getIngredientiForRicetta(int idRicetta) {
		Set<IngredienteDTO> result = null;
		if ( idRicetta < 0 )  {
			System.out.println("read(): cannot read an entry with a negative id");
			return result;
		}
		Connection conn = Db2DAOFactory.createConnection();
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(read_ingredienti_per_ricetta);
			prep_stmt.clearParameters();
			prep_stmt.setInt(1, idRicetta);
			ResultSet rs = prep_stmt.executeQuery();
			
			result = new HashSet<IngredienteDTO>();
			while ( rs.next() ) {
				IngredienteDTO entry = new IngredienteDTO();
				entry.setId((rs.getInt(ID_2)));
				entry.setNomeIngrediente(rs.getString(Db2IngredienteDAO.NOME));
				entry.setQuantita(rs.getInt(Db2IngredienteDAO.QUANTITA));
				result.add(entry);
			}
			rs.close();
			prep_stmt.close();
		}
		catch (Exception e) {
			
			e.printStackTrace();
		}
		finally {
			Db2DAOFactory.closeConnection(conn);
		}
		return result;
	}

	//METODI PER LA RESTITUZIONE DI COLLEZIONI

	/*
	@Override
	public List<PiattoDTO> getPiattiFromResturant(int id) {
		List<PiattoDTO> result = null;
		if ( id< 0 )  {
			System.out.println("read(): cannot read an entry with a negative id");
			return result;
		}
		Connection conn = Db2DAOFactory.createConnection();
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(dish_query);
			prep_stmt.clearParameters();
			prep_stmt.setInt(1, id);
			ResultSet rs = prep_stmt.executeQuery();
			
			result = new ArrayList<PiattoDTO>();
			while ( rs.next() ) {
				PiattoDTO entry = new PiattoDTO();
				entry.setId((rs.getInt("id")));
				entry.setNomePiatto(rs.getString("nome"));
				entry.setTipo(rs.getString("tipo"));
				result.add(entry);
			}
			rs.close();
			prep_stmt.close();
		}
		catch (Exception e) {
			
			e.printStackTrace();
		}
		finally {
			Db2DAOFactory.closeConnection(conn);
		}
		return result;
	}
	*/

}
