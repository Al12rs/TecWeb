package servlets;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.MyThread;
import beans.ResultInfo;

public class Dispatcher extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    public void init(ServletConfig config) throws ServletException {

        HashMap<String, ResultInfo> resultMap = new HashMap<String, ResultInfo>();

        this.getServletContext().setAttribute("resultMap", resultMap);
    }

    private synchronized void increaseCounter(Integer counter, Integer increment) {
        counter += increment;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String filename = (String) req.getAttribute("file");
        char character = (char) req.getAttribute("character");

        long fileSize = (new File(filename)).length();
        if (fileSize == 0L) {
            resp.sendRedirect("pages/start.jsp");
        }
        // round up
        long numSections = (fileSize + 1000 - 1) / 1000;
        int numSectionsInt = (int) numSections;
        MyThread[] threads = new MyThread[numSectionsInt];

        Integer counter = 0;

        for (int i = 0; i < numSectionsInt; i++) {

            threads[i] = new MyThread(i, character);
            threads[i].start();
        }

        for (int i = 0; i < numSectionsInt; i++) {
            try {
                threads[i].join();
                counter +=threads[i].getLocalCounter();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        HashMap<String, ResultInfo> resultMap = (HashMap<String, ResultInfo>) this.getServletContext()
                .getAttribute("resultMap");
        ResultInfo currentUserResults = resultMap.get(req.getSession().getId());
        if (currentUserResults == null) {
            currentUserResults = new ResultInfo();
            resultMap.put(req.getSession().getId(), currentUserResults);
        }
        currentUserResults.addResult(counter, new Date().getTime());
        this.getServletContext().setAttribute("resultMap", resultMap);
        
        resp.sendRedirect("pages/start.jsp");

    }

}
