package test;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import db.DataSource;
import db.PersistenceException;
import db.ProdottoOffertoRepository;
import db.SupermercatoRepository;
import model.ProdottoOfferto;
import model.Supermercato;

public class Test {

	// Dichiara repositories
	private static ProdottoOffertoRepository pr;
	private static SupermercatoRepository sr;

	// CUSTOM METHODS
	private static String nomeSuper(String Descrizione, String Marca) throws PersistenceException {
		return sr.readSupermercatoByLowestCostForSearchedProduct(Descrizione, Marca).getNome();
	}

	private static boolean prodottoOfferto(String NomeSuper, String Descrizione, String Marca) throws PersistenceException {
		Supermercato supermercato = sr.readSupermercatoByName(NomeSuper);
		for(ProdottoOfferto po : supermercato.getProdottiOfferti()) {
			if(po.getDescrizione().equals(Descrizione) && po.getMarca().equals(Marca)) {
				return true;
			}
		}
		return false;
	}
	// END CUSTOM METHODS

	public static void main(String[] arg) throws PersistenceException {

		// Ottieni repositories
		sr = new SupermercatoRepository(DataSource.DB2);
		pr = new ProdottoOffertoRepository(DataSource.DB2);

		// crea tabelle
		pr.dropAndCreateTable();
		sr.dropAndCreateTable();

		System.out.println("Everything is fine so far");

		// Genera oggetti da persistere
		Supermercato su = new Supermercato(1, "Coop", 8);
		sr.persist(su);
		su = new Supermercato(2, "Esselunga", 7);
		sr.persist(su);
		su = new Supermercato(3, "Carrefour", 7);
		sr.persist(su);
		su = new Supermercato(4, "Eurospin", 6);
		sr.persist(su);

		ProdottoOfferto po = new ProdottoOfferto();
		po.setCodiceProdotto(1);
		po.setCodiceSupermercato(1);
		po.setDescrizione("Abbracci");
		po.setMarca("Mulino Bianco");
		po.setPrezzo(2.90f);
		pr.persist(po);

		po.setCodiceSupermercato(2);
		po.setPrezzo(3.90f);
		pr.persist(po);

		po.setCodiceSupermercato(3);
		pr.persist(po);

		// Risoluzione delle operazioni richieste
		// Implementazione delle soluzioni solitamente fatta ed esposta nei file
		// somethingRepository.java come metodi aggiuntivi.
		try {
			// creazione PrintWriter per output su file
			PrintWriter pw = new PrintWriter(new FileWriter("Ordine.txt"));

			// soluzione specifica
			StringBuilder result = new StringBuilder();
			Supermercato sup = new Supermercato(1, "Coop", 8);
			ProdottoOfferto pro = new ProdottoOfferto();
			pro.setCodiceProdotto(1);
			pro.setCodiceSupermercato(1);
			pro.setDescrizione("Abbracci");
			pro.setMarca("Mulino Bianco");

			result.append("Controllo se " + pro.getDescrizione() + " di " + pro.getMarca()
					+ " sono presenti nel supermercato " + sup.getNome() + "\n");
			if (prodottoOfferto(sup.getNome(), pro.getDescrizione(), pro.getMarca())) {
				result.append("Sì, è presente.\n");
			} else {
				result.append("No, non è presente.\n");
			}
			result.append("Cerco il supermercato dove " +pro.getDescrizione()+" di "+pro.getMarca()+" costa meno.\n");
			String nomeSupermercato = nomeSuper(pro.getDescrizione(), pro.getMarca());
			if (nomeSupermercato != null) {
				result.append("Tale supermercato è " + nomeSupermercato + "\n");
			} else
				result.append("Nessun supermercato in database vende questo prodotto.\n");
			pw.println(result.toString());

			// Sempre close del PrintWriter.
			pw.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
