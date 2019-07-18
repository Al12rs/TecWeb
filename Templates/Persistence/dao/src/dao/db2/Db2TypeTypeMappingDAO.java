package dao.db2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


//TO-DO sostituire tutti gli id1 e id2, e eventuali query

public class Db2TypeMappingDAO implements
		#TypeMappingDAO {
	// === Costanti letterali per non sbagliarsi a scrivere !!! ============================
		static final String TABLE = "nomeTabella";
		// -------------------------------------------------------------------------------------
		static final String ID_1 = "id1";
		static final String ID_2 = "id2";
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
		static String read_by_ID_1 = 
			"SELECT * " +
				"FROM " + TABLE + " " +
				"WHERE #tabella2.id2 = " +ID_2+ " AND " + ID_1 + " = ? "
			;

		// SELECT * FROM table WHERE idcolumns = ?;
		static String read_by_ID_2 = 
			"SELECT * " +
				"FROM " + TABLE + "  " +
				"WHERE #tabella1.id1 = " +ID_1+" AND " + ID_2 + " = ? "
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
		// -------------------------------------------------------------------------------------
		// CREATE entrytable ( id INT NOT NULL PRIMARY KEY, ... );
		static String create = 
			"CREATE " +
				"TABLE " + TABLE +" ( " +
					ID_1 + " INT NOT NULL, " +
					ID_2 + " INT NOT NULL, " +
					"PRIMARY KEY (" + ID_1 +","+ ID_2 + " ), " +
					"FOREIGN KEY ("+ID_1+") REFERENCES tabella1(id), "+
					"FOREIGN KEY ("+ID_2+") REFERENCES tabella2(id) "+
				") "
			;
		
		static String drop = 
			"DROP " +
				"TABLE " + TABLE + " "
			;

	@Override
	public void create(int id1, int id2) {
		Connection conn = Db2DAOFactory.createConnection();
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(insert);
			prep_stmt.clearParameters();
			prep_stmt.setInt(1, id1);
			prep_stmt.setInt(2, id2);
			prep_stmt.executeUpdate();
			prep_stmt.close();
		}
		catch (Exception e) {
			System.out.println("create(): failed to insert entry: " + e.getMessage());
			e.printStackTrace();
		}

	}


	@Override
	public boolean delete(int id1, int id2) {
		boolean result = false;
		if ( id1 < 0 || id2 < 0 )  {
			System.out.println("delete(): cannot delete an entry with an invalid id ");
			return result;
		}
		Connection conn = Db2DAOFactory.createConnection();
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(delete);
			prep_stmt.clearParameters();
			prep_stmt.setInt(1, id1);
			prep_stmt.setInt(2, id2);
			prep_stmt.executeUpdate();
			result = true;
			prep_stmt.close();
		}
		catch (Exception e) {
			System.out.println("delete(): failed to delete entry with id1 = " + id1 +" and id2 = " + id2 + ": "+e.getMessage());
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
