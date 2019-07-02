package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Set;

import model.ProdottoOfferto;
import model.Supermercato;

public class SupermercatoRepository {

	private DataSource dataSource;

	// === Costanti letterali per non sbagliarsi a scrivere !!!
	// ============================

	private static final String TABLE = "supermercati";

	// -------------------------------------------------------------------------------------

	private static final String ID = "codiceSupermercato";
	private static final String NOME = "nome";
	private static final String RATING = "ratingGradimento";

	// == STATEMENT SQL
	// ====================================================================

	// INSERT INTO table ( email, description, ...) VALUES ( ?,?, ... );
	private static final String insert = "INSERT " + "INTO " + TABLE + " ( " + ID + ", " + NOME + ", " + RATING + ") "
			+ "VALUES (?,?,?) ";

	// DELETE FROM table WHERE idcolumn = ?;
	private static String delete = "DELETE " + "FROM " + TABLE + " " + "WHERE " + ID + " = ? ";

	// UPDATE table SET xxxcolumn = ?, ... WHERE idcolumn = ?;
	private static String update = "UPDATE " + TABLE + " " + "SET " + NOME + "=?, " + RATING + "=? " + "WHERE " + ID
			+ " = ? ";

	// READ FROM TABLE WHERE idcolumn=?
	private static String read_by_id = "SELECT * FROM " + TABLE + " WHERE " + ID + "=? ";

	// READ
	private static String read_by_name = "SELECT * FROM " + TABLE + " WHERE " + NOME + "=? ";

	// READ
	private static String cheaper_supermarket = "SELECT " + ID + ", " + NOME + ", " + RATING + " FROM prodotti, "
			+ TABLE + " WHERE supermercati.codiceSupermercato = prodotti.codiceSupermercato AND"
			+"prodotti.descrizione=? AND prodotti.marca=? AND"
			+ " prodotti.prezzo = (SELECT MIN(p.prezzo) FROM prodotti AS p WHERE p.descrizione=? AND p.marca=?)";

	// -------------------------------------------------------------------------------------

	// create table
	private static String create = "CREATE " + "TABLE " + TABLE + " ( " + ID + " INT NOT NULL PRIMARY KEY, " + NOME
			+ " VARCHAR(32) NOT NULL UNIQUE, " + RATING + " INT " + ") ";

	// drop table
	private static String drop = "DROP " + "TABLE " + TABLE + " ";

	// DO NOT EDIT:
	public SupermercatoRepository(int databaseType) {
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

	public void persist(Supermercato su) throws PersistenceException {
		Connection connection = null;
		PreparedStatement statement = null;

		try {
			connection = this.dataSource.getConnection();
			statement = connection.prepareStatement(insert);

			// EDIT CODE:
			statement.setInt(1, su.getCodiceSuper());
			statement.setString(2, su.getNome());
			statement.setInt(3, su.getRatingGradimento());

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

	public Supermercato readSupermercatoById(int id) throws PersistenceException {
		Supermercato result = new Supermercato();
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = this.dataSource.getConnection();
			statement = connection.prepareStatement(read_by_id);
			statement.setInt(1, id);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				result.setCodiceSuper(rs.getInt(ID));
				result.setNome(rs.getString(NOME));
				result.setRatingGradimento(rs.getInt(RATING));
			}
			Set<ProdottoOfferto> prodotti = (new ProdottoOffertoRepository(0)).readProdottiByCodiceSupermercato(id);
			result.setProdottiOfferti(prodotti);
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

	public void update(Supermercato su) throws PersistenceException {
		Connection connection = null;
		PreparedStatement statement = null;

		try {
			connection = this.dataSource.getConnection();
			statement = connection.prepareStatement(update);

			// EDIT CODE:

			statement.setInt(1, su.getCodiceSuper());
			statement.setString(2, su.getNome());
			statement.setInt(3, su.getRatingGradimento());

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

	public Supermercato readSupermercatoByName(String name) throws PersistenceException {
		Supermercato result = new Supermercato();
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = this.dataSource.getConnection();
			statement = connection.prepareStatement(read_by_name);
			statement.setString(1, name);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				result.setCodiceSuper(rs.getInt(ID));
				result.setNome(rs.getString(NOME));
				result.setRatingGradimento(rs.getInt(RATING));
			}
			Set<ProdottoOfferto> prodotti = (new ProdottoOffertoRepository(0))
					.readProdottiByCodiceSupermercato(result.getCodiceSuper());
			result.setProdottiOfferti(prodotti);
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

	public Supermercato readSupermercatoByLowestCostForSearchedProduct(String descrizione, String marca)
			throws PersistenceException {
		Supermercato result = new Supermercato();
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = this.dataSource.getConnection();
			statement = connection.prepareStatement(cheaper_supermarket);
			statement.setString(1, descrizione);
			statement.setString(2, marca);
			statement.setString(3, descrizione);
			statement.setString(4, marca);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				result.setCodiceSuper(rs.getInt(ID));
				result.setNome(rs.getString(NOME));
				result.setRatingGradimento(rs.getInt(RATING));
			}
			Set<ProdottoOfferto> prodotti = (new ProdottoOffertoRepository(0))
					.readProdottiByCodiceSupermercato(result.getCodiceSuper());
			result.setProdottiOfferti(prodotti);
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
