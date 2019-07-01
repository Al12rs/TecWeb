package esame.servlets;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import esame.beans.Bean1;
import esame.beans.Bean2;

public class ExampleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String servletParameter;
	private String contextParameter;
	private Bean2 applicationBean;
	private BufferedReader file;

	@Override
	public void init() throws ServletException {
		super.init();
		servletParameter = this.getInitParameter("servletParameter");
		contextParameter = this.getServletContext().getInitParameter("contextParameter");

		ServletContext application = this.getServletContext();
		synchronized (application) {
			applicationBean = (Bean2) application.getAttribute("applicationBean");
			if (applicationBean == null) {
				applicationBean = new Bean2();
				application.setAttribute("applicationBean", applicationBean);
			}
		}
		synchronized (application) {
			file = (BufferedReader) application.getAttribute("fileReader");
			if (file == null) {
				try {
					file = new BufferedReader(new FileReader("resources/file.txt"));
					application.setAttribute("fileReader", file);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	@Override
	public void destroy() {
		if(file!=null) {
			try {
				file.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		super.destroy();
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		getServletContext().getRequestDispatcher("/loginservlet").include(request, response);
		
//		// se uno finisce per caso su questa servlet e non è loggato
//		LoginInfo user = (LoginInfo) session.getAttribute(LoginInfo.LOGIN_INFO);
//		if (user == null) {
//			getServletContext().getRequestDispatcher("/login.html").forward(request, response);
//			return; // to prevent execution to go on after forwarding
//		}

		Bean1 sessionBean = null;
		synchronized (session) {
			sessionBean = (Bean1) session.getAttribute("sessionBean");
			if (sessionBean == null) {
				sessionBean = new Bean1();
				request.getSession().setAttribute("sessionBean", sessionBean);
			}
		}
		
		Object requestBean = request.getAttribute("requestBean");
		Cookie[] cookies = request.getCookies();
		String urlParameter = request.getParameter("stringa");
		Enumeration paramNames = request.getParameterNames();
		while (paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();
			// ...
		}

		PrintWriter out = response.getWriter();

		// se ci sono più possibili servizi
		String mode = request.getParameter("tiposervizio");
		if (mode.equals("servizio1")) {
		} else if (mode.equals("servizio2")) {
		}

		// RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/dovenecessario");
		// dispatcher.forward(request, response); return; // vai e non tornare
		// dispatcher.include(request, response); // per tornare
	}

}
