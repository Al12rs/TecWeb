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
        // crearle da solo) con per esempio:
        // <property name="hibernate.hbm2ddl.auto">create-drop</property>
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
            tx = session.beginTransaction();

            Concorso con1 = new Concorso(1, 1, "class2", "cucina");
            Concorso con2 = new Concorso(2, 2, "class3", "lettere");

            Commissario comm1 = new Commissario(1, "1", "paolo", "villaggio", con1);
            Commissario comm2 = new Commissario(2, "2", "paolo2", "villaggio2", con1);
            Commissario comm3 = new Commissario(3, "X0034", "paolo3", "villaggio3", con2);
            Set<Commissario> commissari = new HashSet<Commissario>();
            commissari.add(comm1);
            commissari.add(comm2);

            Candidato can1 = new Candidato(1, "1", "berto", "spada");
            Candidato can2 = new Candidato(2, "2", "berto2", "spada2");
            Candidato can3 = new Candidato(3, "3", "berto3", "spada3");

            Set<Concorso> concorsi = new HashSet<Concorso>();
            concorsi.add(con1);
            concorsi.add(con2);

            can1.setConcorsi(concorsi);
            can2.setConcorsi(concorsi);
            Set<Concorso> concorsi2 = new HashSet<Concorso>();
            concorsi2.add(con2);
            can3.setConcorsi(concorsi2);

            session.persist(comm1);
            session.persist(comm2);
            session.persist(comm3);

            session.persist(can1);
            session.persist(can2);
            session.persist(can3);
            tx.commit();
            
            //i concorsi sono popolati da soli grazie al cascade del database.

            
            StringBuilder firstQueryResult = new StringBuilder();

            // Query1
            Query query = session.createQuery(
                    "from " + Commissario.class.getSimpleName() + " where matricolaCommissario = X0034 ");
            List<Commissario> commissariResult = (List<Commissario>)query.list();
            if (commissariResult.size() > 0) {
                Concorso concorsoCorr = commissariResult.get(0).getConcorso();
                firstQueryResult.append("Classe Concorso: "+concorsoCorr.getClasseConcorso()+" Num Cand: "+ 
                concorsoCorr.getCandidati().size());
            }

            // Query2
            query = session.createQuery("from " + Candidato.class.getSimpleName());
            List<Candidato> allCand = query.list();

            StringBuilder secondQueryResult = new StringBuilder();
            for (Candidato currentCand : allCand) {

                if (currentCand.getConcorsi().size() > 1){
                    secondQueryResult.append("Nome: " + currentCand.getNome()+" Cognome: "+currentCand.getCognome()+"\n");
                }
            }

            // print Results to file
            PrintWriter pw = new PrintWriter(new FileWriter("concorso.txt"));
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