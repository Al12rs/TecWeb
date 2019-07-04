package dao;

import java.util.List;

public interface WorkPackageDAO {

    // --- CRUD -------------
    public void create(WorkPackageDTO oggetto);

    public WorkPackageDTO read(int id);

    public boolean update(WorkPackageDTO oggetto);

    public boolean delete(int id);

    // ----------------------------------
    // dichiarazione metodi di supporto per le operazioni richiesta
    // ----------------------------------
    public boolean createTable();

    public boolean dropTable();
}