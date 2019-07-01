package esame.servlets;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import esame.beans.LoginInfo;
import esame.beans.AtomicCounter;

public class LoginServlet extends HttpServlet {
	public static final String LOGGED_USERS = "loggedUsers";
	public static final String LOGIN_ERROR_MESSAGE = "loginError";
	private static final long serialVersionUID = 1L;
	private String firstPageAfterLogin;
	private AtomicCounter loggedUsers;
	private Integer loginLimit;

	@Override
	public void init() throws ServletException {
		super.init();
		firstPageAfterLogin = this.getServletContext().getInitParameter("firstPageAfterLogin");

		loginLimit = Integer.valueOf(this.getInitParameter("loginLimit"));

		ServletContext application = this.getServletContext();
		synchronized (application) {
			loggedUsers = (AtomicCounter) application.getAttribute(LOGGED_USERS);
			if (loggedUsers == null) {
				loggedUsers = new AtomicCounter();
				application.setAttribute(LOGGED_USERS, loggedUsers);
			}
		}
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();

		if (session.getAttribute(LoginInfo.LOGIN_INFO) == null) {
			// User autentication needed
			String username = request.getParameter("username");
			String password = request.getParameter("password");

			if (username == null || password == null) {
				// coming from somewhere else
				request.setAttribute(LOGIN_ERROR_MESSAGE, "need to autenticate before accessing this page");
				getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
			} else {
				// coming from login.jsp after form submission
				if (checkCredentialis(username, password)) {
					if (loggedUsers.incrementNoMoreThan(loginLimit)) {
						request.setAttribute(LOGIN_ERROR_MESSAGE, null);
						session.setAttribute(LoginInfo.LOGIN_INFO, new LoginInfo(username));
						getServletContext().getRequestDispatcher("/sessioninvalidatorservlet").include(request, response);
						response.sendRedirect(request.getContextPath() + firstPageAfterLogin);
					} else {
						request.setAttribute(LOGIN_ERROR_MESSAGE, "too many logins");
						getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
					}
				} else {
					request.setAttribute(LOGIN_ERROR_MESSAGE, "invalid credentialis");
					getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
				}
			}
		} else {
			// User already logged
			// do nothing and let the request go back to the forwarding page
			// if any (if there's none it means that the user is specifically
			// trying to reach this servlet which alone is useless)
			;
		}
	}

	private boolean checkCredentialis(String username, String password) {
		return true;
	}
}
