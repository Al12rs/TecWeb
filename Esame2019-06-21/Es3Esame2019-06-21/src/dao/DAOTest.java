package dao;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.io.*;

public class DAOTest {
	
	static PrintWriter pw=null;
	
	public static final int DAO = DAOFactory.DB2;
	

	//Metodi soluzione
	public static String[] codiciProgettoConTuttiPackageLavoratiDaUnPartner(ProgettoDAO pr, WorkPackagePartnerMappingDAO wpp)
	{
		ArrayList<String> result = new ArrayList<String>();
		Set<ProgettoDTO> progetti = pr.readAll();
		for(ProgettoDTO progetto : progetti) {
			WorkPackageDTO[] pkgs = progetto.getPackages().toArray(new WorkPackageDTO[0]);
			WorkPackageDTO progettoWithMostPartners = new WorkPackageDTO();
			progettoWithMostPartners.setPartners(new HashSet<PartnerDTO>());
			for(WorkPackageDTO p : pkgs) {
				if(progettoWithMostPartners.getPartners().size() < p.getPartners().size()) {
					progettoWithMostPartners = p;
				}
			}
			PartnerDTO[] partners = progettoWithMostPartners.getPartners().toArray(new PartnerDTO[0]);
			boolean found = false;
			for(PartnerDTO par : partners) {
				found = true;
				for(WorkPackageDTO p : pkgs) {
					if(!p.getPartners().contains(par)) {
						found = false;
						break;
					}
				}
			}
			if(found) {
				result.add(progetto.getCodiceProgetto());
			}
		}
		return result.toArray(new String[0]);
	}
	
	
	//FINE METODI
	
	public static void main(String[] args) {
		
		
		WorkPackagePartnerMappingDAO mappingDAO = DAOFactory.getDAOFactory(DAO).getWorkPackagePartnerMappingDAO();
		mappingDAO.dropTable();
		
		//Progetti
		DAOFactory daoFactoryInstance = DAOFactory.getDAOFactory(DAO);
		ProgettoDAO progettoDAO = daoFactoryInstance.getProgettoDAO();
		progettoDAO.dropTable();
		progettoDAO.createTable();
		
		ProgettoDTO pr = new ProgettoDTO();
		pr.setIdProgetto(1);
		pr.setCodiceProgetto("1");
		pr.setNomeProgetto("Progetto 1");
		pr.setAnnoInizio(2005);
		pr.setDurata(14);
		progettoDAO.create(pr);

		pr = new ProgettoDTO();
		pr.setIdProgetto(2);
		pr.setCodiceProgetto("2");
		pr.setNomeProgetto("Progetto 2");
		pr.setAnnoInizio(2010);
		pr.setDurata(9);
		progettoDAO.create(pr);
		
		//Packages
		WorkPackageDAO workpackageDAO = DAOFactory.getDAOFactory(DAO).getWorkPackageDAO();
		workpackageDAO.dropTable();
		workpackageDAO.createTable();
		
		WorkPackageDTO wp = new WorkPackageDTO();
		wp.setIdPackage(1);
		wp.setNomeWP("WP 1");
		wp.setDescrizione("bla bla");
		wp.setTitolo("titolo");
		wp.setProgetto(pr);
		workpackageDAO.create(wp);

		wp = new WorkPackageDTO();
		wp.setIdPackage(2);
		wp.setNomeWP("WP 2");
		wp.setDescrizione("bla bla");
		wp.setTitolo("titolo");
		wp.setProgetto(pr);
		workpackageDAO.create(wp);
		
		//Partners
		PartnerDAO partnerDAO = DAOFactory.getDAOFactory(DAO).getPartnerDAO();
		partnerDAO.dropTable();
		partnerDAO.createTable();
		
		PartnerDTO par = new PartnerDTO();
		par.setIdPartner(1);
		par.setSiglaPartner("Par1");
		par.setNome("Pinco");
		partnerDAO.create(par);

		par = new PartnerDTO();
		par.setIdPartner(2);
		par.setSiglaPartner("Par2");
		par.setNome("Pallino");
		partnerDAO.create(par);	
		
		// StudentCoursesMapping
		
		
		mappingDAO.createTable();
		
		
		mappingDAO.create(1,1);
				
		mappingDAO.create(1,2);		
		
		mappingDAO.create(2,1);
				
		
		// Eseguo metodo e stampa su file
		
		try {
			pw = new PrintWriter(new FileWriter("Progetto.txt"));
			
			// codici progetto
			String[] result = codiciProgettoConTuttiPackageLavoratiDaUnPartner(progettoDAO, mappingDAO);
			StringBuilder output = new StringBuilder();
			for(String codice : result) {
				output.append(codice +"\n");
			}
			pw.println(output.toString());
			
			pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println();
		System.out.println();
	}

}
