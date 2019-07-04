package dao;


import java.util.List;

public interface ProgettoDAO {

    // --- CRUD -------------
    public void create(ProgettoDTO oggetto);

    public ProgettoDTO read(int id);

    public boolean update(ProgettoDTO oggetto);

    public boolean delete(int id);

    // ----------------------------------
    // dichiarazione metodi di supporto per le operazioni richiesta
    // ----------------------------------	
    public boolean createTable();

    public boolean dropTable();
}