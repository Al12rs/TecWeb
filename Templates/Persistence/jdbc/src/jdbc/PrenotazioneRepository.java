package jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.persistence.PersistenceException;

import Resturant.db.DataSource;

public class PrenotazioneRepository {
	private DataSource dataSource;
	
	// === Costanti letterali per non sbagliarsi a scrivere !!! ============================
		
		private static final String TABLE = "prenotazione";

		// -------------------------------------------------------------------------------------

		private static final String COGNOME = "cognome";
		private static final String DATA = "data";
		private static final String NUMEROPERSONE = "numeroPersone";
		private static final String CELLULARE = "cellulare";	
		private static final String ID = "id";
		private static final String TAVOLO = "idTavolo";
	
		
		// == STATEMENT SQL ====================================================================

	
		private static final String insert = 
			"INSERT " +
				"INTO " + TABLE + " ( " +
						COGNOME+", "+DATA+", "+NUMEROPERSONE+", "+CELLULARE+", "+TAVOLO+" " +
				") " +
				"VALUES (?,?,?,?,?) "
			;
		

		
		
		
		// DELETE FROM table WHERE idcolumn = ?;
				static String delete = 
					"DELETE " +
						"FROM " + TABLE + " " +
						"WHERE " + COGNOME + " = ? "
					;
		
		// UPDATE table SET xxxcolumn = ?, ... WHERE idcolumn = ?;
				static String update = 
					"UPDATE " + TABLE + " " +
						"SET " + 
							DATA + " = ?, " +
							NUMEROPERSONE + " = ?, " +
							CELLULARE + " = ?, " +
							TAVOLO + " = ? " +
						"WHERE " + COGNOME + " = ? "
					;
				
		// SELECT check postinsert
				static String check_query = 
						"SELECT "+ ID + " FROM "+ TABLE + " "+
								" WHERE " + COGNOME +" = ? AND " + DATA +" = ?";

		// -------------------------------------------------------------------------------------
		
		// create table
		private static String create = 
			"CREATE " +
				"TABLE " + TABLE +" ( " +
					ID + " INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT BY 1), " +
					COGNOME + " VARCHAR(20) NOT NULL, " +
					DATA + " DATE NOT NULL, " +
					NUMEROPERSONE + " INT, " +
					CELLULARE + " VARCHAR(10), " +
					TAVOLO + " INT NOT NULL REFERENCES tavolo, " +
					"PRIMARY KEY ("+ID+"), "+
					"CONSTRAINT pt_PrenotazioneID UNIQUE ("+COGNOME+","+DATA+"), " +
					"CONSTRAINT pr_PranotazioneTavoloID UNIQUE ("+DATA+","+TAVOLO+") " +
				") "
			;
		
		// drop table
		private static String drop = 
			"DROP " +
				"TABLE " + TABLE + " "
			;
		
		public PrenotazioneRepository(int databaseType) {
			dataSource = new DataSource(databaseType);
		}
		
		public void dropAndCreateTable() throws PersistenceException{
			Connection connection = this.dataSource.getConnection();
			
			Statement statement = null;
			try {
				statement = connection.createStatement ();
				try{
					statement.executeUpdate(drop);
				}
				catch (SQLException e) {
					// the table does not exist
				}
				statement.executeUpdate (create);
				statement.close ();
			}
			catch (SQLException e) {
				throw new PersistenceException(e.getMessage());
			}
			finally {
				try {
					if (statement != null) 
						statement.close();
					if (connection!= null)
						connection.close();
				}
				catch (SQLException e) {
					throw new PersistenceException(e.getMessage());
				}
			}
		}
			
		@SuppressWarnings("resource")
		public void persist(PrenotazioneRistorante pr) throws PersistenceException{
			Connection connection = null;
			PreparedStatement statement = null; 
			
				try {
					connection = this.dataSource.getConnection();
					statement = connection.prepareStatement(insert);
					statement.setString(1, pr.getCognomePrenotazione()+"");
					statement.setDate(2, pr.getDataPrenotazione());
					statement.setInt(3, pr.getNumeroPersonePrenotazione());
					statement.setString(4, pr.getCellularePrenotazione());
					statement.setInt(5, pr.getIdTavoloPrenotazione());
					statement.executeUpdate();
					
					statement = connection.prepareStatement(check_query);
					statement.setString(1, pr.getCognomePrenotazione());
					statement.setDate(2, pr.getDataPrenotazione());
					ResultSet rs = statement.executeQuery();
					rs.next();
					int idprenotazione = rs.getInt(1);
					pr.setIdPrenotazione(idprenotazione);
				}
				catch (SQLException e) {
					throw new PersistenceException(e.getMessage());
				}
				finally {
					try {
						if (statement != null) 
							statement.close();
						if (connection!= null){
							connection.close();
							connection = null;
						}
					}
					catch (SQLException e) {
						throw new PersistenceException(e.getMessage());
					}
				}
		}
		
		public void update(PrenotazioneRistorante pr)
		{
			Connection connection = null;
			PreparedStatement statement = null; 
			
				try {
					connection = this.dataSource.getConnection();
					statement = connection.prepareStatement(update);
					
					statement.setString(1, pr.getCognomePrenotazione());
					statement.setDate(2, pr.getDataPrenotazione());
					statement.setInt(3, pr.getNumeroPersonePrenotazione());
					statement.setString(4, pr.getCellularePrenotazione());
					statement.setInt(5, pr.getIdTavoloPrenotazione());
					statement.setInt(5, pr.getIdPrenotazione());
					statement.executeUpdate();
				}
				catch (SQLException e) {
					throw new PersistenceException(e.getMessage());
				}
				finally {
					try {
						if (statement != null) 
							statement.close();
						if (connection!= null){
							connection.close();
							connection = null;
						}
					}
					catch (SQLException e) {
						throw new PersistenceException(e.getMessage());
					}
				}
		}

		
		
		
}
