package it.unibo.tw.hibernate;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class HibernateMain {

	public static void main(String[] args) {

		populateTables();
		
		Session session = HibernateUtil.getSessionFactory().openSession();

		// analisi del sant'orsola
		Query firstQuery = session
				.createQuery("from " + Ospedale.class.getSimpleName() + " where citta = ? and nome = ? ");
		firstQuery.setString(1, "Bologna");
		firstQuery.setString(2, "S. Orsola");
		List<Ospedale> ospedaliCercati = firstQuery.list();
		String firstQueryResult = "";
		for (Ospedale ospedale : ospedaliCercati) {
			Set<TipoAccertamento> tipiAccertamento = ospedale.getTipiAccertamento();
			for (TipoAccertamento tipoAccertamento : tipiAccertamento) {
				if (tipoAccertamento.getDescrizione().equals("Analisi di Laboratorio")) {
					for (Accertamento accertamento : tipoAccertamento.getAccertamenti()) {
						firstQueryResult = firstQueryResult + accertamento.getNome() + "\n";
					}
				}
			}
		}

		// info ospedali
		Query secondQuery = session.createQuery("from " + Ospedale.class.getSimpleName());
		String secondQueryResult = "";
		ospedaliCercati = secondQuery.list();
		for (Ospedale ospedale : ospedaliCercati) {
			int numeroAccertamenti = 0;
			for (TipoAccertamento tipoAccertamento : ospedale.getTipiAccertamento()) {
				numeroAccertamenti += tipoAccertamento.getAccertamenti().size();
			}
			secondQueryResult = secondQueryResult + ospedale.getNome() + " " + ospedale.getCitta() + " "
					+ ospedale.getIndirizzo() + " Numero accertamenti: " + numeroAccertamenti + "\n";
		}
		
		session.close();
		
		//stampa su file
		try {
			FileWriter fw = new FileWriter("Ospedali.txt");
			fw.write(firstQueryResult);
			fw.write(secondQueryResult);
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		HibernateUtil.shutdown();

	}

	private static void populateTables() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;

		// Creazione delle tabelle da codice java se richieste (Hibernate ha opzione di
        // crearle da solo) con:
        // <property name="hibernate.hbm2ddl.auto">create</property>
        /*
        String sql;
        try {
            sql = "DROP TABLE proprietario";
            System.out.println(sql);
            st.executeUpdate(sql);
            System.out.println("done");
        } catch (Exception e) {
            // drop fallisce se la tabella non esiste
            // e.printStackTrace();
        }

        sql = "CREATE TABLE proprietario " + "( " + "id BIGINT NOT NULL PRIMARY KEY," + "cf CHAR(16) NOT NULL UNIQUE, "
                + "nome VARCHAR(40),  " + "cognome VARCHAR(40)" + ") ";
        System.out.println(sql);
        st.executeUpdate(sql);
        System.out.println("done");

        try {
            sql = "DROP TABLE garage";
            System.out.println(sql);
            st.executeUpdate(sql);
            System.out.println("done");
        } catch (Exception e) {
            // drop fallisce se la tabella non esiste
            // e.printStackTrace();
        }
        sql = "CREATE TABLE garage ( " + "id BIGINT NOT NULL PRIMARY KEY," + "nome VARCHAR(40) NOT NULL UNIQUE,  "
                + "indirizzo VARCHAR(40)  " + ") ";
        System.out.println(sql);
        st.executeUpdate(sql);
        System.out.println("done");

        try {
            sql = "DROP TABLE macchina";
            System.out.println(sql);
            st.executeUpdate(sql);
            System.out.println("done");
        } catch (Exception e) {
            // drop fallisce se la tabella non esiste
            // e.printStackTrace();
        }
        try {
            sql = "CREATE TABLE macchina ( " + "id BIGINT NOT NULL PRIMARY KEY," + "targa CHAR(7) NOT NULL UNIQUE, "
                    + "marca VARCHAR(40),  " + "colore VARCHAR(40),  "
                    + "idProprietario BIGINT  NOT NULL REFERENCES proprietario,"
                    + "idGarage BIGINT  NOT NULL REFERENCES garage" + ") ";
            System.out.println(sql);
            st.executeUpdate(sql);
            System.out.println("done");

        } catch (Exception e) {
            e.printStackTrace();
        }
        */

        // Popola tabelle:
        try {

            // Popola tabelle:

            TipoAccertamento b1 = new TipoAccertamento(1, 1, "lab");
            TipoAccertamento b2 = new TipoAccertamento(2, 2, "lab2");
            
            Accertamento a1 = new Accertamento(1, 1, "prelDomi", "controllo san");
            Accertamento a2 = new Accertamento(2, 2, "prelDomi", "controllo san");
            Accertamento a3 = new Accertamento(3, 2, "prelDomi", "controllo san");

            Set<Accertamento> setA1 = new HashSet<Accertamento>();
            Set<Accertamento> setA2 = new HashSet<Accertamento>();

            setA1.add(a1);
            setA1.add(a2);

            setA2.add(a3);

            b1.setAccertamenti(setA1);
            b2.setAccertamenti(setA2);

            Ospedale c1 = new Ospedale(1, 1, "ospEx", "city", "add");
            Ospedale c2 = new Ospedale(2, 2, "ospEx", "city", "add");
            Ospedale c3 = new Ospedale(3, 3, "ospEx", "city", "add");

            Set<Ospedale> setC1 = new HashSet<Ospedale>();
            Set<Ospedale> setC2 = new HashSet<Ospedale>();

            setC1.add(c1);
            setC1.add(c2);

            setA2.add(c3);

            b1.setOspedali(setC1);
            b2.setOspedali(setC2);

            b1.setOspedali(setC1);

            //inizia transazione
            tx = session.beginTransaction();

            //Con cascading si popolano tutte le tabelle.
            session.persist(b1);
            session.persist(b2);


            tx.commit();
            // Fine popolazione
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
