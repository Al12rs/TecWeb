package jdbc;

import java.sql.*;

import jdbc.db.PersistenceException;

import jdbc.db.DataSource;

public class ProdottoRepository {
    private DataSource dataSource;

    // === Costanti letterali per non sbagliarsi a scrivere !!!
    // ============================

    private static final String TABLE = "prodotti";

    // -------------------------------------------------------------------------------------

    private static final String ID = "codiceProdotto";
    private static final String DESCRIZIONE="descrizione";
    private static final String MARCA = "marca";
    private static final String PREZZO = "prezzo";
    private static final String SUPERFK = "superFK";    

    // == STATEMENT SQL
    // ====================================================================

    // INSERT INTO table ( email, description, ...) VALUES ( ?,?, ... );
    private static final String insert = "INSERT " + "INTO " + TABLE + " ( " + ID + ", " + DESCRIZIONE + 
             ", " + MARCA + ", " + PREZZO + ", " + SUPERFK 
            + " " + ") " + "VALUES (?,?,?,?,?) ";

    // DELETE FROM table WHERE idcolumn = ?;
    static String delete = "DELETE " + "FROM " + TABLE + " " + "WHERE " + ID + " = ? ";

    // UPDATE table SET xxxcolumn = ?, ... WHERE idcolumn = ?;
    static String update = "UPDATE " + TABLE + " " + "SET " + 
            DESCRIZIONE + " = ?, " + MARCA + " = ?, " + PREZZO + " = ?, " + SUPERFK + " = ? "  + "WHERE " + ID
            + " = ? ";

    // -------------------------------------------------------------------------------------

    // create table
    private static String create = "CREATE " + "TABLE " + TABLE + " ( " + 
            ID + " INT NOT NULL PRIMARY KEY, " + 
            DESCRIZIONE + " VARCHAR(50) NOT NULL , " +
            MARCA + " VARCHAR(25) NOT NULL , " +
            PREZZO + " FLOAT NOT NULL , " +
            SUPERFK + " INT NOT NULL FOREIGN KEY REFERENCES supermercati(codiceSuper)" + ") ";

    // drop table
    private static String drop = "DROP " + "TABLE " + TABLE + " ";

    /*static String read_available_table = "SELECT " + DESCRIZIONE + " FROM " + TABLE + " " + "WHERE " + #CAPIENZA
            + " >= ? AND " + ID + " NOT IN ( SELECT idtavolo FROM prenotazione WHERE data = ?)";*/

    /*static String read_id_per_code = "SELECT " + ID + " FROM " + TABLE + " " + "WHERE " + #NOME + " = ? ";*/

    // DO NOT EDIT:
    public void TemplateRepository(int databaseType) {
        dataSource = new DataSource(databaseType);
    }

    public void dropAndCreateTable() throws PersistenceException {
        Connection connection = this.dataSource.getConnection();

        Statement statement = null;
        try {
            statement = connection.createStatement();
            try {
                statement.executeUpdate(drop);
            } catch (SQLException e) {
                // the table does not exist
            }
            statement.executeUpdate(create);
            statement.close();
        } catch (SQLException e) {
            throw new PersistenceException(e.getMessage());
        } finally {
            try {
                if (statement != null)
                    statement.close();
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                throw new PersistenceException(e.getMessage());
            }
        }
    }
    // END DO NOT EDIT.

    public void persist(Prodotto t) throws PersistenceException {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = this.dataSource.getConnection();
            statement = connection.prepareStatement(insert);

            // EDIT CODE:
            statement.setInt(1, t.getCodiceProdotto());
            statement.setString(2, t.getDescrizione());
            statement.setString(3, t.getMarca());
            statement.setFloat(4, t.getPrezzo());
            statement.setInt(5, t.getSupermercato().getCodiceSuper());

            // STOP EDIT CODE.

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new PersistenceException(e.getMessage());
        } finally {
            try {
                if (statement != null)
                    statement.close();
                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException e) {
                throw new PersistenceException(e.getMessage());
            }
        }

    }

    // CUSTOM METHOD:
    /*
     * public int getIdFromNumber(String #NOME) throws PersistenceException { int
     * result = -1; Connection connection = null; PreparedStatement statement =
     * null; try { connection = this.dataSource.getConnection(); statement =
     * connection.prepareStatement(read_id_per_code); statement.setString(1,
     * #NOMETav); ResultSet rs = statement.executeQuery(); if (rs.next()) { result =
     * rs.getInt(ID); } else result = -1; return result; } catch (SQLException e) {
     * throw new PersistenceException(e.getMessage()); } finally { try { if
     * (statement != null) statement.close(); if (connection != null) {
     * connection.close(); connection = null; } } catch (SQLException e) { throw new
     * PersistenceException(e.getMessage()); } } } // END CUSTOM METHOD.
     * 
     * // CUSTOM METHOD: public String availableTable(Date data, int persone) throws
     * PersistenceException { String result = null; Connection connection = null;
     * PreparedStatement statement = null; try { connection =
     * this.dataSource.getConnection(); statement =
     * connection.prepareStatement(read_available_table); statement.setInt(1,
     * persone); statement.setDate(2, data); ResultSet rs =
     * statement.executeQuery(); if (rs.next()) { result = rs.getString(#NOME); }
     * else result = null; return result; } catch (SQLException e) { throw new
     * PersistenceException(e.getMessage()); } finally { try { if (statement !=
     * null) statement.close(); if (connection != null) { connection.close();
     * connection = null; } } catch (SQLException e) { throw new
     * PersistenceException(e.getMessage()); } } }
     */
    // END CUSTOM METHOD.

    public void update(Prodotto t) throws PersistenceException {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = this.dataSource.getConnection();
            statement = connection.prepareStatement(update);

            // EDIT CODE:
            statement.setString(1, t.getDescrizione());
            statement.setString(2, t.getMarca());
            statement.setFloat(3, t.getPrezzo());
            statement.setInt(4, t.getSupermercato().getCodiceSuper());
            statement.setInt(5, t.getCodiceProdotto());

            // STOP EDIT CODE.
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new PersistenceException(e.getMessage());
        } finally {
            try {
                if (statement != null)
                    statement.close();
                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException e) {
                throw new PersistenceException(e.getMessage());
            }
        }
    }

    public void delete(int id) throws PersistenceException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = this.dataSource.getConnection();
            System.out.println(delete);
            statement = connection.prepareStatement(delete);

            // Edit if needed
            statement.setInt(1, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new PersistenceException(e.getMessage());
        }
    }

}
