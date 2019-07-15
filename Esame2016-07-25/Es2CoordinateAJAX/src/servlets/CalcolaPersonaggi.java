package servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import beans.Coordinate;
import beans.Personaggio;

public class CalcolaPersonaggi extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private Gson gson;
	Hashtable<String, Personaggio> personaggi;

	@Override
	public void init() throws ServletException {
		gson = new Gson();

		personaggi = new Hashtable<String, Personaggio>();
		personaggi.put("pippo", new Personaggio("pippo", "bla bla", new Coordinate(10, 10)));
		personaggi.put("paperino", new Personaggio("paperino", "bla bla", new Coordinate(60, 10)));
		personaggi.put("topolino", new Personaggio("topolino", "bla bla", new Coordinate(15, 12)));

		this.getServletContext().setAttribute("personaggi", personaggi);
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

		double x = e.get("x").getAsDouble();
		double y = e.get("y").getAsDouble();

		ArrayList<Personaggio> result = new ArrayList<Personaggio>();
		for(String nome : personaggi.keySet()) {
			Personaggio p = personaggi.get(nome);
			if(p.getPosizione().calcolaDistanza(x, y) < 10) {
				result.add(p);
			}
		}

		resp.getWriter().println(gson.toJson(result.toArray(new Personaggio[0])));

	}
}
