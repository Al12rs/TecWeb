package esame.dao;

import esame.utils.SqlUtils;

public class DAOTest {

	public static final int DAO = DAOFactory.DB2;

	private static DAOFactory daoFactoryInstance;

	public static void main(String[] args) {

		daoFactoryInstance = DAOFactory.getDAOFactory(DAO);

		System.out.println("\n\n----------------- DDL STATEMENTS ------------------------------------");
		CittaDAO cittaDao = daoFactoryInstance.getCittaDAO();
		MaratonaDAO maratonaDao = daoFactoryInstance.getMaratonaDAO();

		maratonaDao.dropTable();
		cittaDao.dropTable();
		cittaDao.createTable();
		maratonaDao.createTable();

		System.out.println("\n\n----------------- ENTERING ENTRIES -------------------------------");
		CittaDTO c1 = new CittaDTO(1, "bologna", "italia");
		CittaDTO c2 = new CittaDTO(2, "New York", "stati uniti");

		MaratonaDTO m1 = new MaratonaDTO(1, 1, "m1", SqlUtils.randomSqlDate(), "maratona intera", 1);
		MaratonaDTO m2 = new MaratonaDTO(2, 2, "m2", SqlUtils.randomSqlDate(), "maratona mezza", 1);
		MaratonaDTO m3 = new MaratonaDTO(3, 3, "m3", SqlUtils.randomSqlDate(), "maratona mezza", 2);
		MaratonaDTO m4 = new MaratonaDTO(4, 4, "m4", SqlUtils.randomSqlDate(), "maratona mezza", 2);
		MaratonaDTO m5 = new MaratonaDTO(5, 5, "m5", SqlUtils.randomSqlDate(), "maratona intera", 1);
		MaratonaDTO m6 = new MaratonaDTO(6, 6, "m6", SqlUtils.randomSqlDate(), "maratona mezza", 1);
		MaratonaDTO m7 = new MaratonaDTO(7, 7, "m7", SqlUtils.randomSqlDate(), "maratona mezza", 2);
		MaratonaDTO m8 = new MaratonaDTO(8, 8, "m8", SqlUtils.randomSqlDate(), "maratona mezza", 2);

		cittaDao.create(c1);
		cittaDao.create(c2);

		maratonaDao.create(m1);
		maratonaDao.create(m2);
		maratonaDao.create(m3);
		maratonaDao.create(m4);
		maratonaDao.create(m5);
		maratonaDao.create(m6);
		maratonaDao.create(m7);
		maratonaDao.create(m8);

		System.out.println("\n\n----------------- QUERIES ----------------------------------------");
		// Carica una città che carica in maniera eager le sue maratone.
		// Le maratone sono dei proxy che caricherebbero su richiesta la città,
		// in realtà la città viene settata immediatamente dopo il caricamento
		// del set di maratone della città.
		CittaDTO c = cittaDao.read(1);
		System.out.println(c.toString());

		// Carica una maratona che carica in maniera eager la sua città.
		// La città è un proxy che su richiesta carica le maratone 
		MaratonaDTO m = maratonaDao.read(1);
		System.out.println(m);
		m.getCitta().getMaratone();
		
		System.out.println(contaMaratone("New York", "maratona mezza"));
	}

	public static long contaMaratone(String nomecitta, String tipomaratona) {
		return daoFactoryInstance.getCittaDAO().readByName(nomecitta).getMaratone().stream()
				.filter(m -> m.getTipo().equals(tipomaratona)).count();
	}

}
