package jdbc;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;

import jdbc.db.DataSource;
import jdbc.db.PersistenceException;

public class Test {

	// Dichiara repositories
	private static PrenotazioneRistoranteRepository pr;
	private static TavoloRepository tr;

	// CUSTOM METHODS
	private static int Disponibilit‡Tavolo(Date data, int persone) throws PersistenceException {
		return tr.availableTable(data, persone);
	}

	private static boolean RichiestaPrenotazione(String cognome, Date data, int persone, String cellulare) {
		int numTavolo = -1;
		try {
			numTavolo = Disponibilit‡Tavolo(data, persone);
		} catch (PersistenceException e) {
			return false;
		}
		if (numTavolo == -1)
			return false;
		PrenotazioneRistorante prr = new PrenotazioneRistorante();
		prr.setCellulare(cellulare);
		prr.setCognome(cognome);
		prr.setData(data);
		prr.setNumeroTavolo(numTavolo);
		prr.setNumeroPersone(persone);
		try {
			pr.persist(prr);
		} catch (PersistenceException e) {
			return false;
		}
		return true;
	}
	//END CUSTOM METHODS


	public static void main(String[] arg) throws PersistenceException {

		// Ottieni repositories
		pr = new PrenotazioneRistoranteRepository(DataSource.DB2);
		tr = new TavoloRepository(DataSource.DB2);

		// crea tabelle
		pr.dropAndCreateTable();
		tr.dropAndCreateTable();

		System.out.println("Everything is fine so far");

		// Genera oggetti da persistere
		Tavolo t = new Tavolo(1, 3);
		tr.persist(t);
		t = new Tavolo(2, 2);
		tr.persist(t);
		t = new Tavolo(3, 10);
		tr.persist(t);
		t = new Tavolo(4, 6);
		tr.persist(t);

		PrenotazioneRistorante prr = new PrenotazioneRistorante();

		prr.setCognome("Pallino Pinco");
		prr.setData(new Date(2018, 1, 14));
		prr.setCellulare("+391234567890");
		prr.setNumeroTavolo(4);
		prr.setNumeroPersone(5);

		pr.persist(prr);

		// Risoluzione delle operazioni richieste
		// Implementazione delle soluzioni solitamente fatta ed esposta nei file somethingRepository.java come metodi aggiuntivi.
		try {
			// creazione PrintWriter per output su file
			PrintWriter pw = new PrintWriter(new FileWriter("Ristorante.txt"));

			//soluzione specifica
			String result = "";
			if (RichiestaPrenotazione("PincoPanco", new Date(2017, 1, 18), 4, "asdasd")) {
				result = "La prenotazione Ë avvenuta con successo ";
			} else
				result = "Non Ë stato possibile prenotare un tavolo per la data e/o le persone indicate causa indisponibilit‡ di tavolo";
			pw.println(result);
			if (RichiestaPrenotazione("PincoPallino", new Date(2017, 1, 18), 10, "asdasd")) {
				result = "La prenotazione Ë avvenuta con successo ";
			} else
				result = "Non Ë stato possibile prenotare un tavolo per la data e/o le persone indicate causa indisponibilit‡ di tavolo";
			pw.println(result);
			if (RichiestaPrenotazione("PallinoPanco", new Date(2017, 1, 18), 3, "asdasd")) {
				result = "La prenotazione Ë avvenuta con successo ";
			} else
				result = "Non Ë stato possibile prenotare un tavolo per la data e/o le persone indicate causa indisponibilit‡ di tavolo";
			pw.println(result);
			pw.append("Puoi ache aggiungere qualcosa con append");

			//Secmpre close del PrintWriter.
			pw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
