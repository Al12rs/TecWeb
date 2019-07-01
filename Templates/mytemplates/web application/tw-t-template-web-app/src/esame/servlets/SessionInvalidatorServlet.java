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

public class SessionInvalidatorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;;
	private AtomicCounter loggedUsers;
	private Integer loginTimeLimit;

	@Override
	public void init() throws ServletException {
		super.init();
		ServletContext application = this.getServletContext();
		loginTimeLimit = Integer.valueOf(this.getInitParameter("loginTimeLimit"));
		synchronized (application) {
			loggedUsers = (AtomicCounter) application.getAttribute(LoginServlet.LOGGED_USERS);
			if (loggedUsers == null) {
				loggedUsers = new AtomicCounter();
				application.setAttribute(LoginServlet.LOGGED_USERS, loggedUsers);
			}
		}
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();

		if (loginTimeLimit>0 && session.getAttribute(LoginInfo.LOGIN_INFO) != null) {
			new Thread() {
				public void run() {
					try {
						Thread.sleep(loginTimeLimit*1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					try {
						session.invalidate();
					} catch (Exception e) {
						// probabilmente sessione già invalidata
						e.printStackTrace();
					}
					loggedUsers.decrement();
				};
			}.start();
		}
	}
}
