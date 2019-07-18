package servlets;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.LoginInfo;

public class SalesAgent extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private Hashtable<String, Set<HttpSession>> groupSessionMap;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getSession().setAttribute("statoAcquisto", "pending");
		boolean acquistoFallito = false;
		boolean finalizzaAcquisto = true;
		for (HttpSession session : groupSessionMap
				.get(((LoginInfo) req.getSession().getAttribute("loginInfo")).getGroup())) {
			// controlla che la sessione non sia invalida
			try {
				long sd = session.getCreationTime();
			} catch (IllegalStateException ise) {
				// it's invalid
				acquistoFallito = true;
				break;
			}
			if (session == null) {
				// almeno un membro del gruppo non ha completato l'acquisto
				acquistoFallito = true;
				break;
			}
			String statoAcquisto = (String) session.getAttribute("statoAcquisto");
			if (statoAcquisto == null || !statoAcquisto.equals("pending")) {
				finalizzaAcquisto = false;
				break;
			}

		}
		if (acquistoFallito) {
			for (HttpSession session : groupSessionMap
					.get(((LoginInfo) req.getSession().getAttribute("loginInfo")).getGroup())) {
				if (session != null) {
					session.setAttribute("statoAcquisto", "fallito");
				}
			}
		} else if (finalizzaAcquisto) {
			for (HttpSession session : groupSessionMap
					.get(((LoginInfo) req.getSession().getAttribute("loginInfo")).getGroup())) {
				if (session != null) {
					session.setAttribute("statoAcquisto", "concluso con successo");
				}
			}
		}
	}

	@Override
	public void init() throws ServletException {
		groupSessionMap = (Hashtable<String, Set<HttpSession>>) this.getServletContext()
				.getAttribute("groupSessionMap");
	}

}
