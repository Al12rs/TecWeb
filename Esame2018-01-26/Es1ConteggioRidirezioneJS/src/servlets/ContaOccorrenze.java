package servlets;

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import threads.MyThread;

public class ContaOccorrenze extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private String dir;
	private Gson gson;

	@Override
	public void init(ServletConfig config) throws ServletException {
		dir = System.getProperty("catalina.base") +"/webapps/"+ config.getInitParameter("dir");
		gson = new Gson();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int result = 0;
		HttpSession session = req.getSession();
		Hashtable<Integer, String> files = (Hashtable<Integer, String>) session.getAttribute("files");
		MyThread[] threads = new MyThread[files.size()];
		int i = 0;
		for (String nomeFile : files.values()) {
			threads[i] = new MyThread(new File(dir + nomeFile));
			threads[i].start();
			i++;
		}
		for (i = 0; i < threads.length; i++) {
			try {
				threads[i].join();
				result += threads[i].getOccurrences();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		// rimozione attributi
		session.removeAttribute("x");
		session.removeAttribute("remaining");
		session.removeAttribute("files");

		resp.getWriter().println(gson.toJson(result));
	}

	/*
	 * Il conteggio concorrente dei caratteri numerici viene effettuato lato server.
	 * Problemi in caso di accesso concorrente agli stessi file di testo da parte di
	 * più clienti? Come evitarli? Non si dovrebbero presentera problemi di accesso
	 * concorrente ai file perché i file sono acceduti in sola lettura.
	 */

}
