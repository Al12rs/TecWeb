package Resturant;

import java.sql.*;

import Resturant.db.PersistenceException;

import Resturant.db.DataSource;

public class TypeRepository {
    private DataSource dataSource;

    // === Costanti letterali per non sbagliarsi a scrivere !!!
    // ============================

    private static final String TABLE = "#tavolo";

    // -------------------------------------------------------------------------------------

    private static final String ID = "#typeId";
    private static final String #NOME = "#nome";
    private static final String #CAPIENZA = "#capienza";

    // == STATEMENT SQL
    // ====================================================================

    // INSERT INTO table ( email, description, ...) VALUES ( ?,?, ... );
    private static final String insert = "INSERT " + "INTO " + TABLE + " ( " + ID + ", " + #NOME + ", " + #CAPIENZA
            + " " + ") " + "VALUES (?,?,?) ";

    // DELETE FROM table WHERE idcolumn = ?;
    static String delete = "DELETE " + "FROM " + TABLE + " " + "WHERE " + ID + " = ? ";

    // UPDATE table SET xxxcolumn = ?, ... WHERE idcolumn = ?;
    static String update = "UPDATE " + TABLE + " " + "SET " + #NOME + " = ?, " + #CAPIENZA + " = ? " + "WHERE " + ID
            + " = ? ";

    // -------------------------------------------------------------------------------------

    // create table
    private static String create = "CREATE " + "TABLE " + TABLE + " ( " + ID + " INT NOT NULL PRIMARY KEY, " + #NOME
            + " VARCHAR(10) NOT NULL UNIQUE, " + #CAPIENZA + " INT " + ") ";

    // drop table
    private static String drop = "DROP " + "TABLE " + TABLE + " ";

    static String read_available_table = "SELECT " + #NOME + " FROM " + TABLE + " " + "WHERE " + #CAPIENZA
            + " >= ? AND " + ID + " NOT IN ( SELECT idtavolo FROM prenotazione WHERE data = ?)";

    static String read_id_per_code = "SELECT " + ID + " FROM " + TABLE + " " + "WHERE " + #NOME + " = ? ";

    // DO NOT EDIT:
    public TemplateRepository(int databaseType) {
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

    public void persist(#Tavolo t) throws PersistenceException {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = this.dataSource.getConnection();
            statement = connection.prepareStatement(insert);

            // EDIT CODE:
            statement.setInt(1, t.getIdTavolo());
            statement.setString(2, t.get#NOMETavolo());
            statement.setDouble(3, t.get#CAPIENZATavolo());

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
    public int getIdFromNumber(String #NOME) throws PersistenceException {
        int result = -1;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = this.dataSource.getConnection();
            statement = connection.prepareStatement(read_id_per_code);
            statement.setString(1, #NOMETav);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                result = rs.getInt(ID);
            } else
                result = -1;
            return result;
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
    // END CUSTOM METHOD.

    // CUSTOM METHOD:
    public String availableTable(Date data, int persone) throws PersistenceException {
        String result = null;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = this.dataSource.getConnection();
            statement = connection.prepareStatement(read_available_table);
            statement.setInt(1, persone);
            statement.setDate(2, data);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                result = rs.getString(#NOME);
            } else
                result = null;
            return result;
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
    */
    // END CUSTOM METHOD.


    public void update(#Tavolo t) throws PersistenceException {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = this.dataSource.getConnection();
            statement = connection.prepareStatement(update);

            // EDIT CODE:
            statement.setString(1, t.get#NOMETavolo());
            statement.setInt(2, t.get#CAPIENZATavolo());
            statement.setInt(3, t.getIdTavolo());

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

            //Edit if needed
            statement.setInt(1, id);


            statement.executeUpdate();
        } catch (SQLException e) {
            throw new PersistenceException(e.getMessage());
        }
    }

}
