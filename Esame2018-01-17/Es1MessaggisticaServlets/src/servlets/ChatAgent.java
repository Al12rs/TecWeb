package servlets;

import java.io.IOException;
import java.util.Date;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import beans.LoginInfo;
import beans.Message;
import beans.MessageList;

public class ChatAgent extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private Gson gson;

	@Override
	public void init() throws ServletException {
		gson = new Gson();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String check = req.getParameter("check");
		if (check != null && check.equals("true")) {
			Hashtable<String, MessageList> msgLists = (Hashtable<String, MessageList>) this.getServletContext()
					.getAttribute("msgLists");
			String groupName = (String) req.getSession().getAttribute("groupName");
			MessageList msgs = msgLists.get(groupName);
			resp.getWriter().println(gson.toJson(msgs.getNumberOfMessages()));
		} else {
			String refresh = req.getParameter("refresh");
			if (refresh != null && refresh.equals("true")) {
				resp.sendRedirect("./pages/mainPage.jsp");
			}
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String message = req.getParameter("message");
		String groupName = (String) req.getSession().getAttribute("groupName");
		Hashtable<String, MessageList> msgLists = (Hashtable<String, MessageList>) this.getServletContext()
				.getAttribute("msgLists");
		MessageList msgs = msgLists.get(groupName);
		msgs.addMessage(
				new Message(message, (LoginInfo) req.getSession().getAttribute("loginInfo"), (new Date()).getTime()));
		resp.sendRedirect("./pages/mainPage.jsp");
	}

	/*
	 * A causa di operazioni di invio, sono possibili modifiche concorrenti allo
	 * stato del servizio di messaggistica che possano portare a inconsistenze per i
	 * vari utenti con sessioni attive? Sì, si possono presentare inconsistenze. Per
	 * esempio se un utente sta inviando un messaggio mentre un altro sta
	 * controllando la presenza di nuovi messaggi, il messaggio appena inviato
	 * potrebbe non essere visto da quest'ultimo: si ha un accesso concorrente in
	 * lettura e scrittura a uno stesso attributo di applicazione. Per risolvere questo
	 * problema di incosistenza si potrebbe rendere tale attributo una risorsa synchronized,
	 * sequenzializzando così l'accesso ad essa.
	 */

}
