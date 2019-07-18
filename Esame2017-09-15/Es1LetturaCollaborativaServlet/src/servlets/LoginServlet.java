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
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.LoginInfo;

public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    public void init(ServletConfig config) throws ServletException {

        // Simulazione db utenti
        Hashtable<String, LoginInfo> db = new Hashtable<String, LoginInfo>();
        db.put("us1", new LoginInfo("us1", "pass"));
        db.put("us2", new LoginInfo("us2", "pass"));
        db.put("us3", new LoginInfo("us3", "pass"));
        db.put("us4", new LoginInfo("us4", "pass"));
        db.put("us5", new LoginInfo("admin", "admin"));

        this.getServletContext().setAttribute("db", db);
        ServletContext application = this.getServletContext();

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
            Cookie auth = new Cookie("autenticato", "true");
            resp.addCookie(auth);

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

            // REDIRECT TO APPLICATION PAGE, CHANGE ME!:
            resp.sendRedirect("pages/mainPage.html");

        } else {
            PrintWriter out = resp.getWriter();
            out.println("Login not valid.");
        }

    }

}
