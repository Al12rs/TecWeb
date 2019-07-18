package servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Hashtable;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.*;

import beans.LoginInfo;

public class CompletaParola extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private Gson gson;

	@Override
	public void init(ServletConfig config) throws ServletException {
		gson = new Gson();

		Hashtable<String, Integer> globalPopularity = new Hashtable<String, Integer>();
		Hashtable<String, Integer> group1Popularity = new Hashtable<String, Integer>();
		Hashtable<String, Integer> group2Popularity = new Hashtable<String, Integer>();
		globalPopularity.put("cane", 10);
		globalPopularity.put("gatto", 15);
		group1Popularity.put("cane", 7);
		group1Popularity.put("gatto", 10);
		group2Popularity.put("cane", 3);
		group2Popularity.put("gatto", 5);

		ServletContext application = this.getServletContext();
		application.setAttribute("globalPopularity", globalPopularity);
		application.setAttribute("group1Popularity", group1Popularity);
		application.setAttribute("group2Popularity", group2Popularity);
		new Thread() {
			public void run() {
				try {
					Thread.sleep(1000 * 60 * 60 * 24 * 7);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				// azzeramento (distruzione)
				application.setAttribute("globalPopularity", null);
				application.setAttribute("group1Popularity", null);
				application.setAttribute("group2Popularity", null);

			};
		}.start();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String attr = req.getParameter("rimuovi");
		this.getServletContext().setAttribute(attr, null);
		resp.sendRedirect("/pages/adminPage.jsp");
		
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

		String caratteriDigitati = e.get("car").getAsString();
		int group = ((LoginInfo) req.getSession().getAttribute("loginInfo")).getGroup();
		Hashtable<String, Integer> groupPopularity = null;
		if (group == 1) {
			groupPopularity = (Hashtable<String, Integer>) this.getServletContext().getAttribute("group1Popularity");
		} else if (group == 2) {
			groupPopularity = (Hashtable<String, Integer>) this.getServletContext().getAttribute("group2Popularity");
		}
		Hashtable<String, Integer> globalPopularity = (Hashtable<String, Integer>) this.getServletContext()
				.getAttribute("globalPopularity");
		int maxscoreGlobal = 0;
		String globalTopScorer = null;
		for (String parola : globalPopularity.keySet()) {
			if (parola.startsWith(caratteriDigitati)) {
				Integer score = (Integer) globalPopularity.get("parola");
				if (score > maxscoreGlobal) {
					maxscoreGlobal = score;
					globalTopScorer = parola;
				}
			}
		}
		int maxscoreGroup = 0;
		String groupTopScorer = null;
		for (String parola : groupPopularity.keySet()) {
			if (parola.startsWith(caratteriDigitati)) {
				Integer score = (Integer) groupPopularity.get("parola");
				if (score > maxscoreGroup) {
					maxscoreGroup = score;
					groupTopScorer = parola;
				}
			}
		}

		double globalScore = maxscoreGlobal * 0.75;
		double groupScore = maxscoreGroup * 0.25;
		if (globalScore > groupScore) {
			resp.getWriter().println(gson.toJson(globalTopScorer));
		} else {
			resp.getWriter().println(gson.toJson(groupTopScorer));
		}

	}

}
