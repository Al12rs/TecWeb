package dao;


import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.io.*;

public class DAOTest {
	
	static PrintWriter pw=null;
	
	public static final int DAO = DAOFactory.DB2;
	

	//Metodi soluzione
	public static Set<String> progettiByUnibo(PartnerDAO par){
		Set<String> result = new HashSet<String>();
		String sigla="Unibo";
		PartnerDTO unibo = par.readByUnique(sigla);
		for(WorkPackageDTO wp : unibo.getPackages()) {
			result.add(wp.getProgetto().getNomeProgetto());
		}
		return result;		
	}
	
	public static Set<String> partnerProgettiTriennali(PartnerDAO par){
		Set<String> result = new HashSet<String>();
		List<PartnerDTO> partners = par.readAll();
		for(PartnerDTO partner : partners) {
			for(WorkPackageDTO wp : partner.getPackages()) {
				if(wp.getProgetto().getDurata() == 3) {
					result.add(partner.getNome());
					break;
				}
			}
		}
		return result;		
	}
	
	//FINE METODI
	
	public static void main(String[] args) {
		StringBuilder resultToPrint = new StringBuilder();
		resultToPrint.append("Popolo tabelle.\n");
		
		WorkPackagePartnerMappingDAO mappingDAO = DAOFactory.getDAOFactory(DAO).getWorkPackagePartnerMappingDAO();
		mappingDAO.dropTable();
		
		// Progetti		
		DAOFactory daoFactoryInstance = DAOFactory.getDAOFactory(DAO);
		ProgettoDAO progettoDAO = daoFactoryInstance.getProgettoDAO();
		progettoDAO.dropTable();
		progettoDAO.createTable();
		
		ProgettoDTO pr = new ProgettoDTO(1, "A1", "DAO", 2013, 3);
		progettoDAO.create(pr);

		pr = new ProgettoDTO(2, "A2", "DAO", 2014, 3);
		progettoDAO.create(pr);
		
		pr = new ProgettoDTO(3, "A3", "DAO", 2015, 2);
		progettoDAO.create(pr);
		
		for (ProgettoDTO br : progettoDAO.readAll()) {
			resultToPrint.append(br.toString());
		}
		
		
		// WorkPackage
		
		WorkPackageDAO workpackageDAO = DAOFactory.getDAOFactory(DAO).getWorkPackageDAO();
		workpackageDAO.dropTable();
		workpackageDAO.createTable();
		
		WorkPackageDTO wp = new WorkPackageDTO(1, "w1", "titolo", "desc", new ProgettoDTO(1, "A1", "DAO", 2013, 3));
		workpackageDAO.create(wp);
		
		wp = new WorkPackageDTO(2, "w2", "titolo", "desc", pr);
		workpackageDAO.create(wp);

		for (WorkPackageDTO br : workpackageDAO.readAll()) {
			resultToPrint.append(br.toString());
		}
		
		//Partner
		
		PartnerDAO partnerDAO = DAOFactory.getDAOFactory(DAO).getPartnerDAO();
		partnerDAO.dropTable();
		partnerDAO.createTable();
		
		PartnerDTO par = new PartnerDTO(1, "Unibo", "Università di Bologna");
		partnerDAO.create(par);
		
		par = new PartnerDTO(2, "Unife", "Università di Ferrara");
		partnerDAO.create(par);
		
		for (PartnerDTO br : partnerDAO.readAll()) {
			resultToPrint.append(br.toString());
		}
		
		// WorkPackagePartnerMapping
		
		
		mappingDAO.createTable();		
		mappingDAO.create(1,1);
				
		mappingDAO.create(2,1);		
		
		mappingDAO.create(2,2);
		
		// nome dei progetti di unibo
		Set<String> progettiUnibo = progettiByUnibo(partnerDAO);
		resultToPrint.append("Unibo partecipa a " + progettiUnibo.size() +" progetti:\n");
		for(String s : progettiUnibo) {
			resultToPrint.append(s +"\n");
		}

		// Partner partecipanti a progetti triennali
		Set<String> partnerTriennali = partnerProgettiTriennali(partnerDAO);
		resultToPrint.append("Partner partecipanti a progetti triennali:\n");
		for(String s : partnerTriennali) {
			resultToPrint.append(s +"\n");
		}
		
		try {
			pw = new PrintWriter(new FileWriter("Unibo.txt"));
			
			pw.println(resultToPrint.toString());
			
			pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
