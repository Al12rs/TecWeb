package it.unibo.tw.dao;

import java.util.List;

public interface IngredienteDAO {

    // --- CRUD -------------
    public void create(IngredienteDTO oggetto);

    public IngredienteDTO read(int id);

    public boolean update(IngredienteDTO oggetto);

    public boolean delete(int id);

    // ----------------------------------
    // dichiarazione metodi di supporto per le operazioni richiesta
    // ----------------------------------
    public boolean createTable();

    public boolean dropTable();

}
