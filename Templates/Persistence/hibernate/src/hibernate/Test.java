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

            TipoAccertamento ta = new TipoAccertamento(1, 1, "lab");
            Set<Accertamento> setAcc = new HashSet<Accertamento>();

            session.persist(ta);

            Accertamento acc = new Accertamento(1, 1, "prelDomi", "controllo san", ta);
            setAcc.add(acc);

            session.persist(acc);

            acc = new Accertamento(2, 2, "altro", "testa", ta);
            setAcc.add(acc);

            session.persist(acc);

            ta.setAccertamenti(setAcc);

            Set<Ospedale> setOsp = new HashSet<Ospedale>();
            Set<TipoAccertamento> setTipoAcc = new HashSet<TipoAccertamento>();
            setTipoAcc.add(ta);

            Ospedale osp = new Ospedale(1, 1, "S.Orsola", "Bologna", "Via Massarenti");
            setOsp.add(osp);
            osp.setTipiAccertamento(setTipoAcc);
            session.persist(osp);

            osp = new Ospedale(2, 2, "ospEx", "city", "add");
            session.persist(osp);

            ta.setOspedali(setOsp);
            session.saveOrUpdate(ta);

            tx.commit();
            // Fine popolazion

            StringBuilder firstQueryResult = new StringBuilder();

            // Query1
            Query query = session.createQuery(
                    "from " + Ospedale.class.getSimpleName() + " where nome = S.Orsola AND citta = Bologna");
            List<Ospedale> ospedaliBologna = query.list();
            if (ospedaliBologna.size() > 0) {
                for (TipoAccertamento tipoAcc : ospedaliBologna.get(0).getTipiAccertamento()) {
                    for (Accertamento accert : tipoAcc.getAccertamenti()) {
                        firstQueryResult.append(", " + accert.getNome());
                    }
                }
            }

            // Query2
            query = session.createQuery("from " + Ospedale.class.getSimpleName());
            List<Ospedale> allHosp = query.list();

            StringBuilder secondQueryResult = new StringBuilder();
            for (Ospedale hosp : allHosp) {

                int numAcc = 0;
                for (TipoAccertamento tipoAcc : hosp.getTipiAccertamento()) {
                    numAcc += tipoAcc.getAccertamenti().size();
                }
                secondQueryResult.append("\n" + hosp.getNome() + ", " + hosp.getCitta() + ", " + hosp.getIndirizzo()
                        + ", numero Acc:" + numAcc);
            }

            // print Results to file
            PrintWriter pw = new PrintWriter(new FileWriter("Ospedali.txt"));
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