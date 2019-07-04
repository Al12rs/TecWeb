package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.LoginInfo;
import beans.Prodotto;

public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private long sessionTimeLimit;

    @Override
    public void init(ServletConfig config) throws ServletException {

        //Forced session time limit in seconds with automatic session invalidation using thread.
        sessionTimeLimit = Long.parseLong(config.getInitParameter("sessionTimeLimit"));


        // Simulazione db utenti
        Hashtable<String, LoginInfo> db = new Hashtable<String, LoginInfo>();
        db.put("us1", new LoginInfo("us1", "pass","gr1"));
        db.put("us2", new LoginInfo("us2", "pass", "gr1"));
        db.put("us3", new LoginInfo("us3", "pass","gr2"));
        db.put("us4", new LoginInfo("us4", "pass", "gr2"));
        db.put("us5", new LoginInfo("admin", "admin"));

        // Simulazione catalogo
        Hashtable<String, Prodotto> catalogo = new Hashtable<String, Prodotto>();
        catalogo.put("pasta", new Prodotto("pasta", 100, 2));
        catalogo.put("carne", new Prodotto("carne", 100, 3));
        catalogo.put("carta", new Prodotto("carta", 50, 3));
        catalogo.put("pesce", new Prodotto("pesce", 40, 5));

        ServletContext application = this.getServletContext();
        application.setAttribute("db", db);
        application.setAttribute("catalogo", catalogo);        

        //Optionally keep track of sessions for analytics and timeouts.
        Set<HttpSession> sessionsSet = (Set<HttpSession>) application.getAttribute("sessionsSet");
        if (sessionsSet == null) {
            sessionsSet = new HashSet<HttpSession>();
            application.setAttribute("sessionsList", sessionsSet);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // super.doPost(req, resp);

        String username = req.getParameter("username");
        String password = req.getParameter("password");
        // String groupName = null;

        Hashtable<String, LoginInfo> db = (Hashtable<String, LoginInfo>) this.getServletContext().getAttribute("db");
        LoginInfo matchedUser = db.get(username);
        if (matchedUser != null && matchedUser.getPassword().equals(password)) {
            req.getSession().setAttribute("loginInfo", matchedUser);

            //Optionally save sessions
            ServletContext application = this.getServletContext();
            synchronized (application) {
                Set<HttpSession> sessionsSet = (Set<HttpSession>) application.getAttribute("sessionsSet");
                if (sessionsSet != null) {
                    sessionsSet.add(req.getSession());
                }
            }
            

            if (matchedUser.getUsername() == "admin") {
                //Optional custom admin page
                resp.sendRedirect("pages/adminPage.jsp");
            }

            //OPTIONAL THREAD FOR FORCEFUL SESSION TIMEOUT
            /*HttpSession session = req.getSession();
            new Thread() {
                public void run() {
                    try {
                        Thread.sleep(sessionTimeLimit * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    try {
                        session.invalidate();
                    } catch (Exception e) {
                        // probabilmente sessione già invalidata
                        e.printStackTrace();
                    }
                };
            }.start();*/

            // REDIRECT TO APPLICATION PAGE, CHANGE ME!:
            resp.sendRedirect("pages/catalogo.jsp");

        } else {
            PrintWriter out = resp.getWriter();
            out.println("Login not valid.");
        }

    }

}
