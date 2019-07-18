package servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import threads.MyThread;

public class SaveToFile extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private String dirPath;
	private Gson gson;

	@Override
	public void init(ServletConfig config) throws ServletException {
		dirPath = config.getInitParameter("dir");
		gson = new Gson();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// get json object "e" of the post body
		BufferedReader buf = req.getReader();
		StringBuilder jsonReceived = new StringBuilder();
		String currentLine = null;
		while ((currentLine = buf.readLine()) != null) {
			jsonReceived.append(currentLine);
			jsonReceived.append('\n');
		}
		JsonObject e = new JsonParser().parse(jsonReceived.toString()).getAsJsonObject();

		String textInput = e.get("textInput").getAsString();
		String fileName = e.get("fileName").getAsString();
		int caratteriTrasformati = 0;
		StringBuilder resultToPrint = new StringBuilder();

		MyThread t1 = new MyThread(textInput.substring(0, textInput.length() / 2));
		MyThread t2 = new MyThread(textInput.substring(textInput.length() / 2));
		t1.start();
		t2.start();
		try {
			t1.join();
			caratteriTrasformati += t1.getCaratteriTrasformati();
			resultToPrint.append(t1.getTestoTrasformato());
			t2.join();
			caratteriTrasformati += t2.getCaratteriTrasformati();
			resultToPrint.append(t2.getTestoTrasformato());
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		PrintWriter pw = new PrintWriter(dirPath + fileName);
		pw.println(resultToPrint.toString());
		pw.close();

		resp.getWriter().println(gson.toJson(caratteriTrasformati));

	}

	/*
	 * Si giustifichi nel codice sorgente tramite commento la scelta effettuata in
	 * termini di modello concorrente di esecuzione(ad esempio, quanti thread?
	 * Quanti oggetti? Possibili problemi di accesso concorrente a che cosae quali
	 * soluzioni adottate?Con quali overhead?). Il modello scelto è quello normale
	 * della servlet (un oggetto servlet e tanti thread quante sono le richieste dei
	 * clienti). Si è scelto di utilizzare due oggetti thread per richiesta, in modo
	 * da effettuare la trasformazione in modo concorrente come richiesto. Non ci
	 * sono problemi di accesso concorrente alle risorse: il testo in input è
	 * acceduto in sola lettura e il file è aperto in scrittura dal solo thread che
	 * gestisce la richiesta http;
	 */

}
