package esame.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jabsorb.JSONSerializer;

import esame.beans.AjaxMessage;
import esame.beans.LoginInfo;

public class WaitingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
//	private Object applicationSemaphore;
	private JSONSerializer serializer;

	@Override
	public void init() throws ServletException {
		super.init();
		
//		ServletContext application = this.getServletContext();
//		synchronized (application) {
//			applicationSemaphore = application.getAttribute("applicationSemaphore");
//			if (applicationSemaphore == null) {
//				applicationSemaphore = new Object();
//				application.setAttribute("applicationSemaphore", applicationSemaphore);
//			}
//		}

		serializer = new JSONSerializer();
		try {
			// inizializza i tipi serializzatori forniti di default
			serializer.registerDefaultSerializers();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		AjaxMessage message = new AjaxMessage();

		// se uno finisce per caso su questa servlet e non è loggato
		LoginInfo user = (LoginInfo) session.getAttribute(LoginInfo.LOGIN_INFO);
		if (user == null) {
			message.setMessage("Not logged");
		} else {
			message.setMessage("Hey " + user.getName() + ", you have a message");
		}

		Object sessionSemaphore = null;
		synchronized (session) {
			sessionSemaphore = session.getAttribute("sessionSemaphore");
			if (sessionSemaphore == null) {
				sessionSemaphore = new Object();
				session.setAttribute("sessionSemaphore", sessionSemaphore);
			}
		}
		
		// simple thread to notify the semaphores,
		// notifying should be handled somewhere else
		new Thread() {
			@Override
			public void run() {
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					e.printStackTrace();
					message.setMessage("Server Error");
				}
								
				// Object applicationSemaphore = WaitingServlet.this.getServletContext().getAttribute("applicationSemaphore");
				// synchronized (applicationSemaphore) {
				//     applicationSemaphore.notify();
				// }
				
				Object sessionSemaphore = session.getAttribute("sessionSemaphore");
				synchronized (sessionSemaphore) {
					sessionSemaphore.notifyAll();
				}
			};
		}.start();

		try {
			// synchronized (applicationSemaphore) {
			// applicationSemaphore.wait();
			// }
			synchronized (sessionSemaphore) {
				sessionSemaphore.wait();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
			message.setMessage("Server error");
		}

		request.setAttribute(JsonServlet.OBJECT_TO_SERIALIZE, message);
		getServletContext().getRequestDispatcher("/jsonservlet").include(request, response);
		String json = (String) request.getAttribute(JsonServlet.SERIALIZED_OBJECT);
	
		response.getWriter().println(json);
	}

}
