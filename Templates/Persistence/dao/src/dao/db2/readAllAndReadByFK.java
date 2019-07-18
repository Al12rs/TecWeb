// READ_BY_FK
static String read_by_fk = "SELECT * FROM #table WHERE #fk=?";

// READ_ALL
static String read_all = "SELECT * FROM #table ";



//READ ALL
@Override
public Set<#Type> readAll() {
    Set<#Type> result = new HashSet<#Type>();

    Connection conn = Db2DAOFactory.createConnection();

    try {
        PreparedStatement prep_stmt = conn.prepareStatement(read_all);
        prep_stmt.clearParameters();
        ResultSet rs = prep_stmt.executeQuery();

        while (rs.next()) {
            #Type res = new #Type();

           //copy setters from read method


            result.add(res);
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

//READ_BY_FK
@Override
public Set<#Type> readByFk(int fk) {
    Set<#Type> result = new HashSet<#Type>();

    if (fk < 0)
        return result;

    Connection conn = Db2DAOFactory.createConnection();

    try {
        PreparedStatement prep_stmt = conn.prepareStatement(read_by_fk);
        prep_stmt.clearParameters();
        prep_stmt.setInt(1, fk);
        ResultSet rs = prep_stmt.executeQuery();

        while (rs.next()) {
            #Type res = new #Type();

            //copy set from read method

            result.add(res);
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