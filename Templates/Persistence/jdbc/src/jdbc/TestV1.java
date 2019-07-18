package jdbc;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.List;

import jdbc.db.DataSource;

public class Test {

	// Dichiara repositories
	private static PrenotazioneRepository pr;
	private static TavoloRepository tr;

	// CUSTOM METHODS
	private static String DisponibilitàTavolo(Date data, int persone) {
		return tr.availableTable(data, persone);
	}

	private static boolean RichiestaPrenotazione(String cognome, Date data, int persone, String cellulare) {
		String tableavailable = DisponibilitàTavolo(data, persone);
		if (tableavailable == null)
			return false;
		int numTavolo = tr.getIdFromNumber(tableavailable);
		PrenotazioneRistorante prr = new PrenotazioneRistorante();
		prr.setCellularePrenotazione(cellulare);
		prr.setCognomePrenotazione(cognome);
		prr.setDataPrenotazione(data);
		prr.setIdTavoloPrenotazione(numTavolo);
		prr.setNumeroPersonePrenotazione(persone);
		pr.persist(prr);
		return true;
	}
	//END CUSTOM METHODS


	public static void main(String[] arg) {

		// Ottieni repositories
		pr = new PrenotazioneRepository(DataSource.DB2);
		tr = new TavoloRepository(DataSource.DB2);

		// crea tabelle
		pr.dropAndCreateTable();
		tr.dropAndCreateTable();

		System.out.println("Everything is fine so far");

		// Genera oggetti da persistere
		Tavolo t = new Tavolo(1, "1", 3);
		tr.persist(t);
		t = new Tavolo(2, "2", 2);
		tr.persist(t);
		t = new Tavolo(3, "3", 10);
		tr.persist(t);
		t = new Tavolo(4, "4", 6);
		tr.persist(t);

		PrenotazioneRistorante prr = new PrenotazioneRistorante();

		prr.setCognomePrenotazione("Pallino Pinco");
		prr.setDataPrenotazione(new Date(2018, 1, 14));
		prr.setCellularePrenotazione("blablabla");
		prr.setIdTavoloPrenotazione(4);
		prr.setNumeroPersonePrenotazione(5);

		pr.persist(prr);

		// Risoluzione delle operazioni richieste
		// Implementazione delle soluzioni solitamente fatta ed esposta nei file somethingRepository.java come metodi aggiuntivi.
		try {
			// creazione PrintWriter per output su file
			PrintWriter pw = new PrintWriter(new FileWriter("Ristorante.txt"));

			//soluzione specifica
			String result = "";
			if (RichiestaPrenotazione("PincoPanco", new Date(2017, 1, 18), 4, "asdasd")) {
				result = "La prenotazione è avvenuta con successo ";
			} else
				result = "Non è stato possibile prenotare un tavolo per la data e/o le persone indicate causa indisponibilità di tavolo";
			pw.println(result);
			if (RichiestaPrenotazione("PincoPallino", new Date(2017, 1, 18), 10, "asdasd")) {
				result = "La prenotazione è avvenuta con successo ";
			} else
				result = "Non è stato possibile prenotare un tavolo per la data e/o le persone indicate causa indisponibilità di tavolo";
			pw.println(result);
			if (RichiestaPrenotazione("PallinoPanco", new Date(2017, 1, 18), 3, "asdasd")) {
				result = "La prenotazione è avvenuta con successo ";
			} else
				result = "Non è stato possibile prenotare un tavolo per la data e/o le persone indicate causa indisponibilità di tavolo";
			pw.println(result);
			pw.append("Puoi ache aggiungere qualcosa con append");

			//Secmpre close del PrintWriter.
			pw.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
