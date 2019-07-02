package servlets;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.*;

import beans.Result;

public class Filter extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private Gson gson;
    private String testo;

    @Override
    public void init(ServletConfig config) throws ServletException {
        gson = new Gson();
        testo = config.getInitParameter("testo");
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String str = req.getParameter("str");

        BufferedReader buf;
        ArrayList<String> foundLines = new ArrayList<String>();
        String currentLine = null;
        try {
            buf = new BufferedReader(new FileReader(testo));
            while ((currentLine = buf.readLine()) != null) {
                if (currentLine.contains(str)) {
                    foundLines.add(currentLine);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        buf.close();

        resp.getWriter().println(gson.toJson(foundLines.toArray()));

    }
    
    /*
    Domanda1, Accesso concorrente
    non si prevedono problemi in quanto l'accesso al file (unica risorsa lato server coninvolta)
    è in sola lettura.
    Non vi sarebbe nessun vantaggio ad usare il modello deprecato con creazione di nuovo oggetto servlet per ogni richiesta, solo maggiore overhead.
    
    Domanda2, risposta formato testuale vs json
    Vi sarebbe un vantaggio di ridotto overhead di dati trasmessi, ma si perde in chiarezza e comunicazione standardizzata. 
    Il formato Json è più organizzato e offre la possibilita di più facile elaborazione lato cliente per esempio per una migliore rappresentazione visiva.
    
    */

}
