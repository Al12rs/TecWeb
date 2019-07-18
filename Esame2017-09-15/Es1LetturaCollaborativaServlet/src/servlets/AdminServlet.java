package servlets;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.LoginInfo;

public class AdminServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private Set<HttpSession> sessionsSet;

	@Override
	public void init() throws ServletException {
		sessionsSet = (Set<HttpSession>) this.getServletContext().getAttribute("sessionsSet");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String username = req.getParameter("userSession");
		
		if(username != null) {
			for(HttpSession session : sessionsSet) {
				if(username.equals(((LoginInfo)session.getAttribute("loginInfo")).getUsername())){
					//manca la parte di contare le occorrenze di "esame"
					//dall'attributo "por", posso capire quale parte di file ha ricevuto
					//e fare il conteggio delle occorrenze su di essa
					session.invalidate();
					sessionsSet.remove(session);
					break;
				}
			}
		}
		
		resp.sendRedirect("./pages/adminPage.jsp");
	}

}
