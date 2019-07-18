package esame.hibernate;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.dialect.Dialect;
import org.hibernate.jdbc.util.FormatStyle;
import org.hibernate.jdbc.util.Formatter;

public class HibernateTest {

	private static Configuration configuration;
	private static SessionFactory sessionFactory;

	public static void main(String[] args) {
		configuration = new Configuration().configure();
		sessionFactory = configuration.buildSessionFactory();
		generateDdlStatements();
		tableCreationViaJdbc();
		tableCreationViaHibernateApi();
		enterEntries();
		queries();
	}

	private static void generateDdlStatements() {
		System.out.println("\n\n----------------- DDL STATEMENTS ------------------------------------");
		{
			Dialect dialect = Dialect.getDialect(configuration.getProperties());
			String[] createSQL = configuration.generateSchemaCreationScript(dialect);
			String[] dropSQL = configuration.generateDropSchemaScript(dialect);

			Formatter formatter = FormatStyle.DDL.getFormatter();
			try (PrintWriter writer = new PrintWriter(new FileOutputStream("ddl.sql"))) {
				for (String string : dropSQL) {
					System.out.println(formatter.format(string) + ";");
					writer.println(string + ";");
				}
				System.out.println("\n\n\n");
				writer.println("\n\n\n");
				for (String string : createSQL) {
					System.out.println(formatter.format(string) + ";");
					writer.println(string + ";");
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	private static void tableCreationViaJdbc() {
		System.out.println("\n\n----------------- TABLE CREATION VIA JDBC -------------------------------");

		Connection conn = null;
		try {
			// Unibo Intranet DB2 tw_stud connection
			// Class.forName("COM.ibm.db2.jdbc.app.DB2Driver").newInstance();
			// String url = "jdbc:db2:tw_stud";

			// Remote DB2 tw_stud connection
			// Class.forName("com.ibm.db2.jcc.DB2Driver");
			// String url = "jdbc:db2://diva.deis.unibo.it:50000/tw_stud";
			// String username = "00000000";
			// String password = "s0000000000";

			// Local mysql tw_stud connection
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/tw_stud";
			String username = "root";
			String password = "";

			conn = DriverManager.getConnection(url, username, password);
			// conn.setAutoCommit(false);
			Statement st = conn.createStatement();

			List<String> queries = new ArrayList<>();

			// Drop sql commands
			queries.add("drop table TipoAccertamento_struttura");
			queries.add("drop table accertamento");
			queries.add("drop table richiesta_medica");
			queries.add("drop table struttura");
			queries.add("drop table paziente");
			queries.add("drop table tipo_accertamento");

			// Create sql commands
			queries.add(
					"create table tipo_accertamento (id bigint not null, codice integer not null unique, descrizione varchar(255), primary key (id));");
			queries.add(
					"create table paziente (id bigint not null, codiceFiscale varchar(255) not null unique, nome varchar(255) not null, cognome varchar(255) not null, sesso char(1) not null, primary key (id));");
			queries.add(
					"create table struttura (id bigint not null, codice integer not null unique, nome varchar(255) not null, indirizzo varchar(255) not null, primary key (id));");
			queries.add(
					"create table richiesta_medica (id bigint not null, codicePaziente varchar(255) not null, data datetime not null, nomeMedico varchar(255) not null, paziente_id bigint, primary key (id), unique (codicePaziente, data), foreign key (paziente_id) references paziente (id));");
			queries.add(
					"create table accertamento (id bigint not null, codice integer not null unique, esito varchar(255), tipo_accertamento_id bigint, richiesta_medica_id bigint, primary key (id), foreign key (tipo_accertamento_id) references tipo_accertamento (id), foreign key (richiesta_medica_id) references richiesta_medica (id));");
			queries.add(
					"create table TipoAccertamento_Struttura (tipo_accertamento_ids bigint not null, struttura_ids bigint not null, primary key (struttura_ids, tipo_accertamento_ids), foreign key (tipo_accertamento_ids) references tipo_accertamento (id), foreign key (struttura_ids) references struttura (id));");

			// executing
			queries.stream().forEachOrdered(sql -> {
				System.out.println("Executing: " + sql);
				try {
					st.executeUpdate(sql);
				} catch (Exception e) {
					e.printStackTrace();
				}
			});

			// conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private static void tableCreationViaHibernateApi() {
		Session session = null;
		Transaction tx = null;

		System.out.println("\n\n----------------- TABLE CREATION ------------------------------------");
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();

			List<String> queries = new ArrayList<>();

			// Drop sql commands
			queries.add("drop table TipoAccertamento_struttura");
			queries.add("drop table accertamento");
			queries.add("drop table richiesta_medica");
			queries.add("drop table struttura");
			queries.add("drop table paziente");
			queries.add("drop table tipo_accertamento");

			// Create sql commands
			queries.add(
					"create table tipo_accertamento (id bigint not null, codice integer not null unique, descrizione varchar(255), primary key (id));");
			queries.add(
					"create table paziente (id bigint not null, codiceFiscale varchar(255) not null unique, nome varchar(255) not null, cognome varchar(255) not null, sesso char(1) not null, primary key (id));");
			queries.add(
					"create table struttura (id bigint not null, codice integer not null unique, nome varchar(255) not null, indirizzo varchar(255) not null, primary key (id));");
			queries.add(
					"create table richiesta_medica (id bigint not null, codicePaziente varchar(255) not null, data datetime not null, nomeMedico varchar(255) not null, paziente_id bigint, primary key (id), unique (codicePaziente, data), foreign key (paziente_id) references paziente (id));");
			queries.add(
					"create table accertamento (id bigint not null, codice integer not null unique, esito varchar(255), tipo_accertamento_id bigint, richiesta_medica_id bigint, primary key (id), foreign key (tipo_accertamento_id) references tipo_accertamento (id), foreign key (richiesta_medica_id) references richiesta_medica (id));");
			queries.add(
					"create table TipoAccertamento_Struttura (tipo_accertamento_ids bigint not null, struttura_ids bigint not null, primary key (struttura_ids, tipo_accertamento_ids), foreign key (tipo_accertamento_ids) references tipo_accertamento (id), foreign key (struttura_ids) references struttura (id));");

			// executing
			queries.stream().map(session::createSQLQuery).forEachOrdered(sqlQuery -> {
				try {
					sqlQuery.executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
			});

			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("Done");
			session.close();
		}
	}

	private static void enterEntries() {
		Session session = null;
		Transaction tx = null;
		System.out.println("\n\n----------------- ENTERING ENTRIES -------------------------------");
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			//
			// Paziente p1 = new Paziente("paziente1", "nome1", "cognome1",
			// 'M');
			// Paziente p2 = new Paziente("paziente2", "nome2", "cognome2",
			// 'F');
			//
			// RichiestaMedica rm1 = new RichiestaMedica("paziente1", "medico1",
			// new GregorianCalendar(2016, 4, 1).getTime());
			// RichiestaMedica rm2 = new RichiestaMedica("paziente1", "medico2",
			// new GregorianCalendar(2016, 4, 2).getTime());
			// RichiestaMedica rm3 = new RichiestaMedica("paziente2", "medico3",
			// new GregorianCalendar(2016, 4, 3).getTime());
			// RichiestaMedica rm4 = new RichiestaMedica("paziente2", "medico4",
			// new GregorianCalendar(2016, 4, 4).getTime());
			//
			// p1.getRichiesteMediche().add(rm1);
			// p1.getRichiesteMediche().add(rm2);
			// p2.getRichiesteMediche().add(rm3);
			// p2.getRichiesteMediche().add(rm4);
			//
			// rm1.setPaziente(p1);
			// rm2.setPaziente(p1);
			// rm3.setPaziente(p2);
			// rm4.setPaziente(p2);
			// rm1.setCodicePaziente(p1.getCodiceFiscale());
			// rm2.setCodicePaziente(p1.getCodiceFiscale());
			// rm3.setCodicePaziente(p2.getCodiceFiscale());
			// rm4.setCodicePaziente(p2.getCodiceFiscale());
			//
			// Accertamento a1 = new Accertamento(111, "esito111");
			// Accertamento a2 = new Accertamento(222, "esito222");
			// Accertamento a3 = new Accertamento(333, "esito333");
			// Accertamento a4 = new Accertamento(444, "esito444");
			// Accertamento a5 = new Accertamento(555, "esito555");
			// Accertamento a6 = new Accertamento(666, "esito666");
			//
			// rm1.getAccertamenti().add(a1);
			// rm1.getAccertamenti().add(a2);
			// rm2.getAccertamenti().add(a3);
			// rm2.getAccertamenti().add(a4);
			// rm3.getAccertamenti().add(a5);
			// rm4.getAccertamenti().add(a6);
			//
			// a1.setRichiestaMedica(rm1);
			// a2.setRichiestaMedica(rm1);
			// a3.setRichiestaMedica(rm2);
			// a4.setRichiestaMedica(rm2);
			// a5.setRichiestaMedica(rm3);
			// a6.setRichiestaMedica(rm4);
			//
			// TipoAccertamento ta1 = new TipoAccertamento(1, "risonanza");
			// TipoAccertamento ta2 = new TipoAccertamento(2, "acc2");
			// TipoAccertamento ta3 = new TipoAccertamento(3, "acc3");
			//
			// a1.setTipoAccertamento(ta1);
			// a2.setTipoAccertamento(ta1);
			// a3.setTipoAccertamento(ta2);
			// a4.setTipoAccertamento(ta2);
			// a5.setTipoAccertamento(ta3);
			// a6.setTipoAccertamento(ta3);
			//
			// ta1.getAccertamentiDiQuestoTipo().add(a1);
			// ta1.getAccertamentiDiQuestoTipo().add(a2);
			// ta2.getAccertamentiDiQuestoTipo().add(a3);
			// ta2.getAccertamentiDiQuestoTipo().add(a4);
			// ta3.getAccertamentiDiQuestoTipo().add(a5);
			// ta3.getAccertamentiDiQuestoTipo().add(a6);
			//
			// Struttura s1 = new Struttura(11, "struttura1", "indirizzo1");
			// Struttura s2 = new Struttura(22, "struttura2", "indirizzo2");
			// Struttura s3 = new Struttura(33, "struttura3", "indirizzo3");
			//
			// ta1.getStruttureCheLoOffrono().add(s1);
			// ta1.getStruttureCheLoOffrono().add(s2);
			// ta1.getStruttureCheLoOffrono().add(s3);
			// ta2.getStruttureCheLoOffrono().add(s1);
			// ta2.getStruttureCheLoOffrono().add(s2);
			// ta3.getStruttureCheLoOffrono().add(s3);
			//
			// s1.getTipiDiAccertamentoOfferti().add(ta1);
			// s1.getTipiDiAccertamentoOfferti().add(ta2);
			// s2.getTipiDiAccertamentoOfferti().add(ta1);
			// s2.getTipiDiAccertamentoOfferti().add(ta2);
			// s3.getTipiDiAccertamentoOfferti().add(ta1);
			// s3.getTipiDiAccertamentoOfferti().add(ta3);
			//
			// session.persist(p1);
			// session.persist(p2);
			//
			// session.persist(rm1);
			// session.persist(rm2);
			// session.persist(rm3);
			// session.persist(rm4);
			//
			// session.persist(a1);
			// session.persist(a2);
			// session.persist(a3);
			// session.persist(a4);
			// session.persist(a5);
			// session.persist(a6);
			//
			// session.persist(ta1);
			// session.persist(ta2);
			// session.persist(ta3);
			//
			// session.persist(s1);
			// session.persist(s2);
			// session.persist(s3);
			//
			tx.commit();
		} catch (Exception e1) {
			if (tx != null) {
				try {
					tx.rollback();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
			e1.printStackTrace();
		} finally {
			session.close();
		}
	}

	private static void queries() {
		Session session = null;
		System.out.println("\n\n----------------- QUERIES ----------------------------------------");
		try {
			session = sessionFactory.openSession();

//			Query medicoConPiuRisonanzePerFemmineQuery = session.getNamedQuery("medicoConPiuRisonanzePerFemmine");
//			String medico = (String) medicoConPiuRisonanzePerFemmineQuery.uniqueResult();
//			System.out.println(medico);

//			String sql =
//			"select P.Nome, P.Cognome from Garage G, Macchina M, Proprietario
//			P where G.id=M.garage_id and M.proprietario_id=P.id and
//			G.nomeGarage=:nomeGarage";
//			SQLQuery query = session.createSQLQuery(sql);
//			query.setParameter("nomeGarage", "g1");
//			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
//			List data = query.list();
//			for (Object object : data) {
//			Map row = (Map) object;
//			System.out.println(row.get("Nome") + " " + row.get("Nognome"));
//			}

//			for(Object o : session.createCriteria(Commissario.class).list()){
//				System.out.println(o);
//			}
//
//			List singleResult = session.createCriteria(ConcorsoTFA.class).add(Restrictions.eq("classeConcorso", "A042")).list();
//			if (!singleResult.isEmpty()) {
//				ConcorsoTFA c = (ConcorsoTFA) singleResult.get(0);
//				System.out.println(c.getCandidati().size());
//				System.out.println(c.getCommissari().stream().map(Commissario::getNome).collect(Collectors.joining(", ")));
//			}

//			Garage garage = (Garage)
//			session.createCriteria(Garage.class).add(Restrictions.eq("nomeGarage",
//			"g1"))
//			.uniqueResult();
//			for (Macchina m : garage.getMacchine()) {
//			System.out.println(m.getProprietario().getNome() + " " +
//			m.getProprietario().getCognome());
//			}

//			HQL Query
//			Query query1 = session.createQuery("from
//			"+Corso.class.getSimpleName()+" where codice= ?");
//			int codice = 26;
//			query1.setInteger(0, codice);
//			System.out.println("Retrieving number of women attending course
//			"+codice+"...");
//			Corso c = (Corso)query1.uniqueResult();
//			if(c != null)
//			{
//			int res = 0;
//			for(Studente s : c.getStudenti())
//			{
//			if(s.getSesso().equals("F"))
//			{
//			res++;
//			}
//			}
//			if(res == 0)
//			{
//			System.out.println("No women are attending course "+codice);
//			}
//			else
//			{
//			System.out.println(res+" women are attending course "+codice);

//			System.out.println("Printing on Studentesse.txt the details of
//			the students just found...");
//			File f = new File("Studentesse.txt");
//			if(!f.exists())
//			{
//			f.createNewFile();
//			}
//			FileOutputStream fos = new FileOutputStream(f);
//			PrintWriter out = new PrintWriter(fos);
//			for(Studente s : c.getStudenti())
//			{
//			if(s.getSesso().equals("F"))
//			{
//			System.out.println(s.getNome()+" "+s.getCognome()+", Nata il
//			"+s.getData_nascita().toString());
//			out.println(s.getNome()+" "+s.getCognome()+", Nata il
//			"+s.getData_nascita().toString());
//			}
//			}
//			out.close();
//			System.out.println("Written to file!");
//			}
//			}

//			Criteria allConcorsi = session.createCriteria(Concorso.class);
//			((List<Concorso>)allConcorsi.list()).stream()
//			.filter(con -> con.getCommissari().stream()
//					.anyMatch(com -> { return com.getMatricola().equals("X0034");}))
//			.findFirst()
//			.ifPresent(con -> {System.out.println("classe: "+con.getClasseConcorso() + " partecipanti:"+con.getCandidati().size());});
//
//			 String sqlInner =
//			 "select C.matricola as matricola, count(CC.concorso_id) as count from candidato C join candidato_concorso CC on C.id=CC.candidato_id group by matricola";
//			 String sqlOuter = "select nome, cognome, count from ("+sqlInner+") as MC, candidato C where MC.matricola = C.matricola";
//			 SQLQuery query = session.createSQLQuery(sqlOuter);
//			 query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
//			 List data = query.list();
//			 BigInteger max = new BigInteger("0");
//			 String result = "Nessuno trovato";
//			 for (Object object : data) {
//				 Map row = (Map) object;
//				 if (max.compareTo((BigInteger)row.get("count"))<0) {
//					 max = (BigInteger)row.get("count");
//					 result = row.get("nome") + " " + row.get("cognome");
//				 }
//			 }
//			 System.out.println("Candidato che ha partecipato a pi� concorsi: " + result);
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			session.close();
		}
	}

}
