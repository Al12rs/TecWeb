package it.unibo.tw.dao;

import java.util.List;

public interface Ricetta_IngredientiDAO {

    // --- CRUD -------------
    public void create(Ricetta_IngredientiDAO oggetto);

    public Ricetta_IngredientiDAO read(int id);

    public boolean update(Ricetta_IngredientiDAO oggetto);

    public boolean delete(int id);

    // ----------------------------------
    // dichiarazione metodi di supporto per le operazioni richiesta
    // ----------------------------------
    public List<IngredienteDTO> getIngredientiFromRicetta(int id);

    public boolean createTable();

    public boolean dropTable();

}
