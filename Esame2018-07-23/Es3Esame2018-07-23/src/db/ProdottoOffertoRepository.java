package db;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

import db.PersistenceException;
import model.ProdottoOfferto;
import db.DataSource;

public class ProdottoOffertoRepository {
    private DataSource dataSource;

    // === Costanti letterali per non sbagliarsi a scrivere !!!
    // ============================

    private static final String TABLE = "prodottiOfferti";

    // -------------------------------------------------------------------------------------

    private static final String ID = "codiceProdotto";
    private static final String DESCRIZIONE = "descrizione";
    private static final String MARCA = "marca";
    private static final String PREZZO = "prezzo";
    private static final String CODICESUPERMERCATO = "codiceSupermercato";

    // == STATEMENT SQL
    // ====================================================================

    // INSERT INTO table ( email, description, ...) VALUES ( ?,?, ... );
    private static final String insert = "INSERT " + "INTO " + TABLE + " ( " + ID + ", " + DESCRIZIONE + ", " + MARCA
            + ", "+PREZZO  + ", " + CODICESUPERMERCATO +") " + "VALUES (?,?,?,?,?) ";

    // DELETE FROM table WHERE idcolumn = ?;
    private static String delete = "DELETE " + "FROM " + TABLE + " " + "WHERE " + ID + " = ? ";

    // UPDATE table SET xxxcolumn = ?, ... WHERE idcolumn = ?;
    private static String update = "UPDATE " + TABLE + " " + "SET " + DESCRIZIONE + "=?, " + MARCA
            + "=?, "+PREZZO  + "=?, " + CODICESUPERMERCATO +"=? "+ "WHERE " + ID
            + " = ? ";
    
    // READ FROM TABLE WHERE idcolumn=?
    private static String read_by_id = "SELECT * FROM " + TABLE + " WHERE " + ID + "=? ";

    // -------------------------------------------------------------------------------------

    // create table
    private static String create = "CREATE " + "TABLE " + TABLE + " ( " + ID + " INT NOT NULL PRIMARY KEY, " + DESCRIZIONE
            + " VARCHAR(64), " + MARCA + " VARCHAR(32), " + PREZZO +" FLOAT, " + CODICESUPERMERCATO + 
            "INT FOREIGN KEY REFERENCES supermercati(codiceSupermercato) " + ") ";

    // drop table
    private static String drop = "DROP " + "TABLE " + TABLE + " ";

    static String read_prodotti_by_codice_supermercato = "SELECT *  FROM " + TABLE + " WHERE " + CODICESUPERMERCATO
            + " = ? ";

    // DO NOT EDIT:
    public ProdottoOffertoRepository(int databaseType) {
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

    public void persist(ProdottoOfferto po) throws PersistenceException {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = this.dataSource.getConnection();
            statement = connection.prepareStatement(insert);

            // EDIT CODE:
            statement.setInt(1, po.getCodiceProdotto());
            statement.setString(2, po.getDescrizione());
            statement.setString(3, po.getMarca());
            statement.setFloat(4, po.getPrezzo());
            statement.setInt(5, po.getCodiceSupermercato());
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
    
    public ProdottoOfferto readProdottoById(int id) throws PersistenceException {
    	ProdottoOfferto result = new ProdottoOfferto();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = this.dataSource.getConnection();
            statement = connection.prepareStatement(read_by_id);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                result.setCodiceProdotto(rs.getInt(ID));
                result.setDescrizione(rs.getString(DESCRIZIONE));
                result.setMarca(rs.getString(MARCA));
                result.setPrezzo(rs.getFloat(PREZZO));
                result.setCodiceSupermercato(rs.getInt(CODICESUPERMERCATO));
            }
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
        return result;
    }
    
    public void update(ProdottoOfferto po) throws PersistenceException {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = this.dataSource.getConnection();
            statement = connection.prepareStatement(update);

            // EDIT CODE:
            
            statement.setString(1, po.getDescrizione());
            statement.setString(2, po.getMarca());
            statement.setFloat(3, po.getPrezzo());
            statement.setInt(4, po.getCodiceSupermercato());
            statement.setInt(5, po.getCodiceProdotto());
            
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

    public Set<ProdottoOfferto> readProdottiByCodiceSupermercato(int codiceSupermercato) throws PersistenceException {
    	Set<ProdottoOfferto> result = new HashSet<ProdottoOfferto>();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = this.dataSource.getConnection();
            statement = connection.prepareStatement(read_by_id);
            statement.setInt(1, codiceSupermercato);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
            	ProdottoOfferto entry = new ProdottoOfferto();
            	entry.setCodiceProdotto(rs.getInt(ID));
            	entry.setDescrizione(rs.getString(DESCRIZIONE));
            	entry.setMarca(rs.getString(MARCA));
            	entry.setPrezzo(rs.getFloat(PREZZO));
            	entry.setCodiceSupermercato(rs.getInt(CODICESUPERMERCATO));
            	result.add(entry);
            }
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
        return result;
    }
    

}
