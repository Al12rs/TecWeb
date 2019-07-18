package it.unibo.tw.dao;


import java.util.List;
import java.io.*;

public class DAOTest {
	
	static PrintWriter pw=null;
	
	public static final int DAO = DAOFactory.DB2;
	

	//Metodi soluzione
	public static String poorestRecipe(RicettaDAO r)
	{
		List<RicettaDTO> ricette = r.readAll();
		int numIngredientiMin = Integer.MAX_VALUE;
		String result="";
		for(RicettaDTO ricetta : ricette)
		{
			int size = ricetta.getIngredienti().size();
			if(size < numIngredientiMin) {
				result = ricetta.getNomeRicetta();
				numIngredientiMin = size;
			}
		}
		return result;
	}
	
	public static String richestRecipe(RicettaDAO r)
	{
		List<RicettaDTO> ricette = r.readAll();
		int numIngredientiMax = Integer.MIN_VALUE;
		String result="";
		for(RicettaDTO ricetta : ricette)
		{
			int size = ricetta.getIngredienti().size();
			if(size > numIngredientiMax) {
				result = ricetta.getNomeRicetta();
				numIngredientiMax = size;
			}
		}
		return result;
	}
	//FINE METODI
	
	public static void main(String[] args) {
		
		
		RicettaIngredientiMappingDAO mappingDAO = DAOFactory.getDAOFactory(DAO).getRicettaIngredientiMappingDAO();
		mappingDAO.dropTable();
		// Ristoranti
		
		DAOFactory daoFactoryInstance = DAOFactory.getDAOFactory(DAO);
		RicettaDAO ricettaDAO = daoFactoryInstance.getRicettaDAO();
		ricettaDAO.dropTable();
		ricettaDAO.createTable();
		
		RicettaDTO r = new RicettaDTO();
		r.setId(1);
		r.setNomeRicetta("Bucatini all'amatriciana");
		r.setTempoPreparazione(30);
		r.setLivelloDifficolta(3);
		r.setCalorie(1000);
		ricettaDAO.create(r);
		
		// Piatti
		
		IngredienteDAO ingredienteDAO = DAOFactory.getDAOFactory(DAO).getIngredienteDAO();
		ingredienteDAO.dropTable();
		ingredienteDAO.createTable();
		
		IngredienteDTO p = new IngredienteDTO();
		p.setId(1);
		p.setNomeIngrediente("Salsa di pomodoro");
		p.setQuantita(50);
		ingredienteDAO.create(p);

		p = new IngredienteDTO();
		p.setId(2);
		p.setNomeIngrediente("Bucatini");
		p.setQuantita(60);
		ingredienteDAO.create(p);
				
		
		// Mapping
		
		
		mappingDAO.createTable();
		
		
		mappingDAO.create(1,1);
				
		mappingDAO.create(1,2);		
		
		
		try {
			pw = new PrintWriter(new FileWriter("Ricetta.txt"));
			
			String outPut = DAOTest.poorestRecipe(ricettaDAO);
			pw.println(outPut);
			System.out.println(outPut);
			System.out.println();
			
			outPut = DAOTest.richestRecipe(ricettaDAO);
			pw.append("\n"+outPut);
			pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println();
		System.out.println();
	}

}
