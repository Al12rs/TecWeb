package hibernate;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.*;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class Test {

	public static void main(String[] args) {
		Session session = new Configuration().configure().buildSessionFactory().openSession();
		Transaction tx = null;

		// Creazione delle tabelle da codice java se richieste (Hibernate ha opzione di
		// crearle da solo) con:
		// <property name="hibernate.hbm2ddl.auto">create</property>
		/*
		 * String sql; try { sql = "DROP TABLE proprietario"; System.out.println(sql);
		 * st.executeUpdate(sql); System.out.println("done"); } catch (Exception e) { //
		 * drop fallisce se la tabella non esiste // e.printStackTrace(); }
		 * 
		 * sql = "CREATE TABLE proprietario " + "( " + "id BIGINT NOT NULL PRIMARY KEY,"
		 * + "cf CHAR(16) NOT NULL UNIQUE, " + "nome VARCHAR(40),  " +
		 * "cognome VARCHAR(40)" + ") "; System.out.println(sql); st.executeUpdate(sql);
		 * System.out.println("done");
		 * 
		 * try { sql = "DROP TABLE garage"; System.out.println(sql);
		 * st.executeUpdate(sql); System.out.println("done"); } catch (Exception e) { //
		 * drop fallisce se la tabella non esiste // e.printStackTrace(); } sql =
		 * "CREATE TABLE garage ( " + "id BIGINT NOT NULL PRIMARY KEY," +
		 * "nome VARCHAR(40) NOT NULL UNIQUE,  " + "indirizzo VARCHAR(40)  " + ") ";
		 * System.out.println(sql); st.executeUpdate(sql); System.out.println("done");
		 * 
		 * try { sql = "DROP TABLE macchina"; System.out.println(sql);
		 * st.executeUpdate(sql); System.out.println("done"); } catch (Exception e) { //
		 * drop fallisce se la tabella non esiste // e.printStackTrace(); } try { sql =
		 * "CREATE TABLE macchina ( " + "id BIGINT NOT NULL PRIMARY KEY," +
		 * "targa CHAR(7) NOT NULL UNIQUE, " + "marca VARCHAR(40),  " +
		 * "colore VARCHAR(40),  " +
		 * "idProprietario BIGINT  NOT NULL REFERENCES proprietario," +
		 * "idGarage BIGINT  NOT NULL REFERENCES garage" + ") ";
		 * System.out.println(sql); st.executeUpdate(sql); System.out.println("done");
		 * 
		 * } catch (Exception e) { e.printStackTrace(); }
		 */

		// Popola tabelle:
		try {

			// Popola tabelle:

			Concorso b1 = new Concorso(1, "Con1", "concorso1");
			Concorso b2 = new Concorso(2, "Con2", "concorso2");
			Concorso b3 = new Concorso(3, "Con3", "concorso3");

			Commissario a1 = new Commissario(1, "Com1", "commissario1");
			Commissario a2 = new Commissario(2, "Com2", "commissario2");
			Commissario a3 = new Commissario(3, "Com3", "commissario3");
			Commissario a4 = new Commissario(1, "Com4", "commissario4");

			Set<Commissario> setA1 = new HashSet<Commissario>();
			Set<Commissario> setA2 = new HashSet<Commissario>();
			Set<Commissario> setA3 = new HashSet<Commissario>();

			setA1.add(a1);
			setA1.add(a2);

			setA2.add(a3);

			setA3.add(a4);

			b1.setCommissari(setA1);
			b2.setCommissari(setA2);
			b3.setCommissari(setA3);

			Candidato c1 = new Candidato(1, "Can1", "candidato1");
			Candidato c2 = new Candidato(2, "Can2", "candidato2");
			Candidato c3 = new Candidato(3, "Can3", "candidato3");
			Candidato c4 = new Candidato(4, "Can4", "candidato4");

			Set<Candidato> setC1 = new HashSet<Candidato>();
			Set<Candidato> setC2 = new HashSet<Candidato>();
			Set<Candidato> setC3 = new HashSet<Candidato>();

			setC1.add(c1);
			setC1.add(c2);

			setC2.add(c3);

			setC3.add(c4);

			b1.setCandidati(setC1);
			b2.setCandidati(setC2);

			b3.setCandidati(setC3);

			// inizia transazione
			tx = session.beginTransaction();

			// Con cascading si popolano tutte le tabelle.
			session.persist(b1);
			session.persist(b2);
			session.persist(b3);

			tx.commit();
			// Fine popolazione

			StringBuilder firstQueryResult = new StringBuilder();

			// Query1
			Query query = session.createQuery(
					"from " + Concorso.class.getSimpleName());
			List<Concorso> concorsi = query.list();
			for(Concorso c : concorsi) {
				Set<Commissario> commissari = c.getCommissari();
				for(Commissario com : commissari) {
					if(com.getMatricola() == 3) {
						firstQueryResult.append("Numero candidati: "+c.getCandidati().size()+". Classe: "+c.getClasseConcorso()+".\n");
					}
				}
			}

			// Query2
			query = session.createQuery("from " + Candidato.class.getSimpleName());
			List<Candidato> allCandidati = query.list();

			StringBuilder secondQueryResult = new StringBuilder();
			for (Candidato cand : allCandidati) {

				if (cand.getConcorsi().size() >= 2) {
					secondQueryResult.append("\n" + cand.getNome() + ", " + cand.getCognome() + ";");
				}
			}

			// print Results to file
			PrintWriter pw = new PrintWriter(new FileWriter("resources/concorso.txt"));
			pw.println(firstQueryResult);
			pw.append(secondQueryResult);
			pw.close();

		} catch (Exception e) {
			if (tx != null) {
				try {
					tx.rollback();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
			e.printStackTrace();
		} finally {
			session.close();
		}

	}
}