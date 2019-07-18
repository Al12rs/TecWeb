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

public class AdminServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private Hashtable<String, Set<HttpSession>> groupSessionMap;

	@Override
	public void init() throws ServletException {
		groupSessionMap = (Hashtable<String, Set<HttpSession>>) this.getServletContext()
				.getAttribute("groupSessionMap");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String gruppo = req.getParameter("comitiva");
		String azione = req.getParameter("azione");
		
		if(azione.equals("completa")) {
			for (HttpSession session : groupSessionMap
					.get(gruppo)) {
				if (session != null) {
					session.setAttribute("statoAcquisto", "concluso con successo");
				}
			}
		} else {
			for (HttpSession session : groupSessionMap
					.get(gruppo)) {
				if (session != null) {
					session.setAttribute("statoAcquisto", "fallito");
				}
			}
		}
		
		resp.sendRedirect("./pages/adminPage.jsp");
	}

}
