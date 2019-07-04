package servlets;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import org.hsqldb.SessionContext;

import beans.LoginInfo;
import beans.Prodotto;

public class Carrello extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private Hashtable<String, Prodotto> catalogo;
    private Set<HttpSession> sessionSet;

    @Override
    public void init(ServletConfig config) throws ServletException {
        catalogo = (Hashtable<String, Prodotto>) this.getServletContext().getAttribute("catalogo");
        sessionSet = (Set<HttpSession>) this.getServletContext().getAttribute("sessionSet");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String nomeProdotto = req.getParameter("prodotto");
        if (nomeProdotto != null) {
            Hashtable<String, Integer> cart = (Hashtable<String, Integer>) req.getSession().getAttribute("cart");
            if (cart == null) {
                cart = new Hashtable<String, Integer>();
            }
            Integer currentOrder = cart.get(nomeProdotto);
            if (currentOrder == null) {
                currentOrder = 0;
            }
            currentOrder++;
            Prodotto prod = catalogo.get(nomeProdotto);
            if (prod.getQuantita() < 1) {
                // siamo poveri
                resp.sendRedirect("pages/catalogo.jsp");
            }
            prod.setQuantita(prod.getQuantita() - 1);
            catalogo.put(nomeProdotto, prod);
            cart.put(nomeProdotto, currentOrder);

            String group = ((LoginInfo) req.getSession().getAttribute("loginInfo")).getGroup();

            // Controllo se il carrello va bene
            boolean ok = true;
            for (String prodName : cart.keySet()) {
                int currentOrderCountForGroup = 0;
                for (HttpSession sess : sessionSet) {
                    LoginInfo info = (LoginInfo) sess.getAttribute("loginInfo");
                    if (info != null && group.equals(info.getGroup())) {
                        Hashtable<String, Integer> currCart = (Hashtable<String, Integer>) sess.getAttribute("cart");
                        if (currCart != null) {
                            // possibile nullpointerexception
                            currentOrderCountForGroup += cart.get(prodName);
                        }
                    }
                }
                if (currentOrderCountForGroup < prod.getSoglia()) {
                    ok = false;
                }
            }

            if (ok) {
                req.getSession().setAttribute("finalizzabile", true);
            }
            resp.sendRedirect("pages/catalogo.jsp");

        }
        // Ha premuto finalize:

        req.getSession().setAttribute("cart", null);
        req.getSession().setAttribute("finalizzabile", false);

        resp.sendRedirect("pages/catalogo.jsp");

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sessionId = req.getParameter("sessionId");
        if (sessionId != null) {
            // eliminail carrello di quella session id.
            for (HttpSession sess : sessionSet) {
                if (sess.getId().equals(sessionId)) {
                    sess.setAttribute("cart", null);
                    sess.setAttribute("finalizable", false);
                }
            }
        }
        resp.sendRedirect("adminPage.jsp");
    }

}
