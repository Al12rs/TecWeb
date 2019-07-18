package dao.db2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dao.IdBroker;

public class IdBrokerSequenceDB2 implements IdBroker {
	
	public int newId(String sequenceName) throws Exception {
		int newId = -1;
		ResultSet result = null;
		PreparedStatement statement = null;
		Connection conn = Db2DAOFactory.createConnection();
		try {
			String newIdName = sequenceName + "newId";
			String sqlQuery = "SELECT(NEXTVAL FOR "+sequenceName+") INTO "+newIdName;
			statement = conn.prepareStatement(sqlQuery);
			result = statement.executeQuery();
			if (result.next())
				newId = result.getInt(newIdName);
			else
				throw new Exception("invalid id");
			statement.close();
		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		} finally {
			Db2DAOFactory.closeConnection(conn);
		}
		return newId;
	}
}
