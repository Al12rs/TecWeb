package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
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
import javax.tools.JavaFileObject;

import com.google.gson.*;

import beans.*;

public class CopySession extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private Gson gson;

    @Override
    public void init(ServletConfig config) throws ServletException {
        gson = new Gson();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // get json object "e" of the post body
        BufferedReader buf = req.getReader();
        StringBuilder jsonReceived = new StringBuilder();
        String currentLine = null;
        while ((currentLine = buf.readLine()) != null) {
            jsonReceived.append(currentLine);
            jsonReceived.append('\n');
        }
        JsonObject e = new JsonParser().parse(jsonReceived.toString()).getAsJsonObject();

        String sessionIdSorgente = e.get("source").getAsString();
        String sessionIdDestinazione = e.get("destination").getAsString();

        Hashtable<String, SessionInfo> sessionTracker = (Hashtable<String, SessionInfo>)this.getServletContext().getAttribute("sessionTracker");
        HttpSession sourceSession = sessionTracker.get(sessionIdSorgente).getSession();
        HttpSession destinationSession = sessionTracker.get(sessionIdDestinazione).getSession();

        
        if (sourceSession != null && destinationSession != null) {

                        
            ArrayList<String> attrList = new ArrayList<String>();
            for (Enumeration<String> enume = (Enumeration<String>) sourceSession.getAttributeNames(); enume
                    .hasMoreElements();) {
                String attrName = enume.nextElement();
                Object value = sourceSession.getAttribute(attrName);
                destinationSession.setAttribute(attrName, value);
                attrList.add(attrName);
            }

            resp.getWriter().println(gson.toJson(attrList.toArray()));
        }
    }

}
