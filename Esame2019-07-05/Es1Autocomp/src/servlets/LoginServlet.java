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

public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    //private long sessionTimeLimit;

    @Override
    public void init(ServletConfig config) throws ServletException {

        //Forced session time limit in seconds with automatic session invalidation using thread.
        //sessionTimeLimit = Long.parseLong(config.getInitParameter("sessionTimeLimit"));


        // Simulazione db utenti
        //assumo esistano solo 2 gruppi (più quello per l'admin)
        Hashtable<String, LoginInfo> db = new Hashtable<String, LoginInfo>();
        db.put("us1", new LoginInfo("us1", "pass", 1));
        db.put("us2", new LoginInfo("us2", "pass", 1));
        db.put("us3", new LoginInfo("us3", "pass", 2));
        db.put("us4", new LoginInfo("us4", "pass", 2));
        db.put("us5", new LoginInfo("admin", "admin", 0));

        ServletContext application = this.getServletContext();
        application.setAttribute("db", db);        

        //Optionally keep track of sessions for analytics and timeouts.
        /*Set<HttpSession> sessionsSet = (Set<HttpSession>) application.getAttribute("sessionsSet");
        if (sessionsSet == null) {
            sessionsSet = new HashSet<HttpSession>();
            application.setAttribute("sessionsList", sessionsSet);
        }*/

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // super.doPost(req, resp);

        String username = req.getParameter("username");
        String password = req.getParameter("password");
        int groupe = Integer.parseInt(req.getParameter("gruppo"));

        Hashtable<String, LoginInfo> db = (Hashtable<String, LoginInfo>) this.getServletContext().getAttribute("db");
        LoginInfo matchedUser = db.get(username);
        if (matchedUser != null && matchedUser.getPassword().equals(password)) {
            req.getSession().setAttribute("loginInfo", matchedUser);

            //Optionally save sessions
            /*ServletContext application = this.getServletContext();
            synchronized (application) {
                Set<HttpSession> sessionsSet = (Set<HttpSession>) application.getAttribute("sessionsSet");
                if (sessionsSet != null) {
                    sessionsSet.add(req.getSession());
                }
            }*/
            

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
                        // probabilmente sessione giÃ  invalidata
                        e.printStackTrace();
                    }
                };
            }.start();*/

            // REDIRECT TO APPLICATION PAGE, CHANGE ME!:
            resp.sendRedirect("pages/search.html");

        } else {
            PrintWriter out = resp.getWriter();
            out.println("Login not valid.");
        }

    }

}
