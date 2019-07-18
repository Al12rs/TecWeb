package jdbc;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jdbc.db.DataSource;
import jdbc.db.PersistenceException;

public class Test {

	// Dichiara repositories
	private static AcquistoRepository ar;


	public static void main(String[] arg) {

		// Ottieni repositories
		ar = new AcquistoRepository(DataSource.DB2);
		
		StringBuilder resultToPrint = new StringBuilder();
		

		try {
			// crea tabelle
			ar.dropAndCreateTable();
			resultToPrint.append("Creata tabella " + ar.TABLE+"\n");

			System.out.println("Everything is fine so far");

			// inseriretre acquisti
			Acquisto a = new Acquisto(1, "00001", 50, "Pinco", "Pallino");
			ar.persist(a);
			resultToPrint.append("Inserito: " + a.toString() +"\n");
			a = new Acquisto(2, "00002", 250, "Pinco", "Pallino");
			ar.persist(a);
			resultToPrint.append("Inserito: " + a.toString() +"\n");
			a = new Acquisto(3, "00003", 150, "Pinco", "Pallino");
			ar.persist(a);
			resultToPrint.append("Inserito: " + a.toString() +"\n");
			
			//modificare il costo di uno dei tre acquisti inseriti
			a.setImporto(350);
			ar.update(a);
			resultToPrint.append("Aggiornato importo: " + a.toString() +"\n");
			
			// ottenere l’insieme di degli acqui-sti(CodiceAcquisto)con importo inferiore a 200 euro
			List<Acquisto> acquisti = ar.readAll();
			Set<String> acquistiSopraAiDuecento = ar.AcquistoSoglia(200);
			Set<String> acquistiSottoAiDuecento = new HashSet<String>();
			for(Acquisto acq : acquisti) {
				if(!acquistiSopraAiDuecento.contains(acq.getCodiceAcquisto())) {
					acquistiSottoAiDuecento.add(acq.getCodiceAcquisto());
					resultToPrint.append("Acquisti sotto i 200: " + acq.toString() +"\n");
				}
			}
			
			//eliminare gli acquisti ottenuti al punto precedente
			for(String codice : acquistiSottoAiDuecento) {
				ar.delete(codice);
				resultToPrint.append("Eliminato acquisto con il seguente codice: " + codice +"\n");
			}

			
			try {
				// creazione PrintWriter per output su file
				PrintWriter pw = new PrintWriter(new FileWriter("Acquisto.txt"));
				//stampa su file
				pw.println(resultToPrint.toString());
				//Secmpre close del PrintWriter.
				pw.close();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (PersistenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
