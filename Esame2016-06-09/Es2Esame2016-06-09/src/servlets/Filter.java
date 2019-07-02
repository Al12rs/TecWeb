/*
 * Problemi  in  caso  di  accesso  concorrenteda  parte  di  più  clienti?
 * Non dovrebbero esserci problemi perché le risorse in comune sono accedute in sola lettura
 * Vantaggi/svantaggi nell’uso del modello di concorrenza deprecated per servlet?
 * Non ci sarebbe alcun vantaggio usando il modello deprecato, anzi si utilizzerebbero più risorse del necessario
 * Quali vantaggi/svantaggi eventuali rispetto alla restituzione delle righe tramite risposta in formato testuale?
 * Utilizzando una risposta in formato testuale puro si avrebbe un minore overhead, ma si complicherebbe la sua
 * interpretazione da parte dell'applicazione Javascript. Utilizzando il formato JSON invece si ha l'opposto: maggiore
 * overhead, ma minore difficoltà di interpretazione della risposta.
 * 
 */

package servlets;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

public class Filter extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private String fileName;
	private Gson gson;

	@Override
	public void init(ServletConfig config) throws ServletException {
		fileName = config.getInitParameter("file");
		gson = new Gson();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String str = req.getParameter("str");
		ArrayList<String> result = new ArrayList<String>(); 
		
		BufferedReader buf = new BufferedReader(new FileReader(this.fileName));
		String currentLine = null;
		while((currentLine = buf.readLine()) != null) {
			//la word search può essere migliorata
			if(currentLine.contains(str)) {
				result.add(currentLine);
			}
		}
		buf.close();
		
		resp.getWriter().println(gson.toJson(result.toArray(new String[0])));
	}
	
	
}
