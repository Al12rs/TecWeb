package m2m.bi.proxy.dao;

import m2m.bi.proxy.dao.DAOFactory;
import m2m.bi.proxy.utils.SqlUtils;

public class DAOTest {

	public static final int DAO = DAOFactory.DB2;

	private static DAOFactory daoFactoryInstance;

	private static VoloDAO voloDao;

	private static PasseggeroDAO passeggeroDao;

	public static void main(String[] args) {
		daoFactoryInstance = DAOFactory.getDAOFactory(DAO);
		voloDao = daoFactoryInstance.getVoloDAO();
		passeggeroDao = daoFactoryInstance.getPaseggeroDAO();
		
		// passeggero � l'owner della relazione e aggiorna la tabella di link
		// in alternativa ci si crea LinkDAO qui e si aggiorna a mano
		passeggeroDao.setRelationshipOwner(true);

		tableCreation();
		enterEntries();
		queries();
	}

	private static void tableCreation() {
		System.out.println("\n\n----------------- DDL STATEMENTS ------------------------------------");
		// passeggero � l'owner della relazione: deve creare per ultimo la
		// tabella
		passeggeroDao.dropTable();
		voloDao.dropTable();
		voloDao.createTable();
		passeggeroDao.createTable();
	}

	private static void enterEntries() {
		System.out.println("\n\n----------------- ENTERING ENTRIES -------------------------------");
		VoloDTO v1 = new VoloDTO(1, "v1", "v1", "v1", SqlUtils.randomSqlDate(), 1500);
		VoloDTO v2 = new VoloDTO(2, "v2", "v2", "v2", SqlUtils.randomSqlDate(), 1500);
		VoloDTO v3 = new VoloDTO(3, "v3", "v3", "v3", SqlUtils.randomSqlDate(), 1500);

		PasseggeroDTO p1 = new PasseggeroDTO(1, "p1", "p1", "p1", "M", "p1");
		PasseggeroDTO p2 = new PasseggeroDTO(2, "p2", "p2", "p2", "M", "p2");
		PasseggeroDTO p3 = new PasseggeroDTO(3, "p3", "p3", "p3", "M", "p3");

		p1.getVoli().add(v1);
		p2.getVoli().add(v1);

		// passeggero � l'owner della relazione: deve creare per ultimo le entry
		voloDao.create(v1);
		voloDao.create(v2);
		voloDao.create(v3);

		passeggeroDao.create(p1);
		passeggeroDao.create(p2);
		passeggeroDao.create(p3);

		// scrive 2 anche se nessuno ha messo passeggeri nel set di v1
		System.out.println("Num pass nel volo v1: " + voloDao.read(1).getPasseggeri().size());

		p3.getVoli().add(v1);
		passeggeroDao.update(p3);
		// scrive 3 perch� � stata aggiornata la tabella centrale
		System.out.println("Num pass nel volo v1: " + voloDao.read(1).getPasseggeri().size());
	}

	private static void queries() {
		System.out.println("\n\n----------------- QUERIES ----------------------------------------");
		{
			VoloDTO v = voloDao.read(1);
			System.out.println("Passeggeri di: " + v.getCodVolo());
			for (PasseggeroDTO p : v.getPasseggeri()) {
				System.out.println("\tProxy: " + p.getClass());
				System.out.println("\tnumero voli di " + p.getNome() + ": " + p.getVoli().size());
				PasseggeroDTO p2 = passeggeroDao.read(p.getId());
				System.out.println("\tDTO: " + p2.getClass());
				System.out.println("\tnumero voli di " + p2.getNome() + ": " + p2.getVoli().size());
				System.out.println();
			}
		}
		{
			PasseggeroDTO p = passeggeroDao.read(1);
			System.out.println("Voli di: " + p.getNome());
			for (VoloDTO v : p.getVoli()) {
				System.out.println("\tProxy: " + v.getClass());
				System.out.println("\tnumero passeggeri di " + v.getCodVolo() + ": " + v.getPasseggeri().size());
				VoloDTO v2 = voloDao.read(v.getId());
				System.out.println("\tProxy: " + v2.getClass());
				System.out.println("\tnumero passeggeri di " + v2.getCodVolo() + ": " + v2.getPasseggeri().size());
				System.out.println();
			}
		}
	}
}