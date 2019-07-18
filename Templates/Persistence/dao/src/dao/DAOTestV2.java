package dao;


import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

public class DAOTest {
	
	static PrintWriter pw=null;
	
	public static final int DAO = DAOFactory.DB2;
	
	
	public static void main(String[] args) {
		
		StringBuilder resultToPrint = new StringBuilder();
		resultToPrint.append("Popolo tabelle \n");
		// A
		
		DAOFactory daoFactoryInstance = DAOFactory.getDAOFactory(DAO);
		ADAO aDAO = daoFactoryInstance.getADAO();
		aDAO.dropTable();
		aDAO.createTable();
		
		ADTO a = new ADTO(1,1,1,1);
		aDAO.create(a);
		a = new ADTO(2,1,1,1);
		aDAO.create(a);
		a = new ADTO(3,1,1,2);
		aDAO.create(a);		
		
		// B
		
		BDAO bDAO = DAOFactory.getDAOFactory(DAO).getBDAO();
		bDAO.dropTable();
		bDAO.createTable();
		
		BDTO b = new BDTO(1,1,1);
		bDAO.create(b);
		b = new BDTO(2,1,1);
		bDAO.create(b);
		b = new BDTO(3,1,2);
		bDAO.create(b);
		
		// C
		CDAO cDAO = DAOFactory.getDAOFactory(DAO).getCDAO();
		cDAO.dropTable();
		cDAO.createTable();
		
		CDTO c = new CDTO(1,1,1);
		cDAO.create(c);
		c = new CDTO(2,1,1);
		cDAO.create(c);
		
		resultToPrint.append("...\nFine popolamento.\n");
		
		// prima richiesta
		resultToPrint.append("Prima Richiesta.\n");
		Set<CDTO> cs = cDAO.readAll();
		for(CDTO cElement : cs) {
			resultToPrint.append("C tupla id: "+ cElement.getC1()+"\nNumero Bs: "+cElement.getBs().size()+"\n");
			for(BDTO bElement : cElement.getBs()) {
				resultToPrint.append("\tB tupla id: "+ bElement.getB1()+"\nNumero As: "+bElement.getAs().size()+"\n");
				for(ADTO aElement : bElement.getAs()) {
					resultToPrint.append("\t\tA tupla id: "+ aElement.getA1()+"\n");					
				}
			}
		}
		
		// seconda richiesta
		resultToPrint.append("Seconda Richiesta.\n");
		Set<BDTO> bs = bDAO.readAll();
		int min = Integer.MAX_VALUE;
		BDTO smallestB = null;
		for(BDTO bElement : bs) {
			if(bElement.getAs().size() < min) {
				min = bElement.getAs().size();
				smallestB = bElement;
			}
		}
		resultToPrint.append("Tupla ceracata: "+ smallestB.toString() +"\n");
		
		resultToPrint.append("Fine.\n");
		
		try {
			pw = new PrintWriter(new FileWriter("ABC.txt"));
			pw.println(resultToPrint.toString());
			pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println();
		System.out.println();
	}

}
