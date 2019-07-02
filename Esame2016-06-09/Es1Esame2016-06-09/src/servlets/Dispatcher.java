package servlets;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Result;
import beans.ResultTime;
import beans.ResultsList;
import threads.MyThread;

public class Dispatcher extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private String dirPath;

	@Override
	public void init(ServletConfig config) throws ServletException {
		dirPath = config.getInitParameter("dir");
		ArrayList<ResultTime> lastResults = new ArrayList<ResultTime>();
		this.getServletContext().setAttribute("lastResults", lastResults);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String file = req.getParameter("file");
		char car = req.getParameter("car").charAt(0);
		HttpSession session = req.getSession(true);

		File fileToAnalyze = new File(dirPath + file);
		if (fileToAnalyze.exists()
				&& ((car >= '0' && car <= '9') || (car >= 'A' && car <= 'Z') || (car >= 'a' && car <= 'z'))) {
			long fileSize = fileToAnalyze.length();
			int threadsToStart = (int) (fileSize / 1000);
			MyThread[] threadsArray = new MyThread[threadsToStart];
			int offset = 0;
			int occurrences = 0;
			for (int i = 0; i < threadsToStart; i++) {
				threadsArray[i] = new MyThread(fileToAnalyze, car, offset);
				threadsArray[i].start();
			}
			for (int i = 0; i < threadsToStart; i++) {
				try {
					threadsArray[i].join();
					occurrences += threadsArray[i].getOccurrences();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			saveResult(file, car, session, occurrences);
			resp.sendRedirect("pages/start.jsp");
		} else {
			resp.sendRedirect("pages/start.jsp");
		}
	}

	synchronized private void saveResult(String file, char car, HttpSession session, int occurrences) {
		Result result = new Result(file, car, occurrences);
		ResultTime resultTime = new ResultTime(file, car, occurrences, (new Date()).getTime());
		ResultsList results = (ResultsList) session.getAttribute("results");
		if( results == null) {
			ResultsList rl = new ResultsList();
			rl.addResult(result);
			session.setAttribute("results", rl);
		} else {
			results.addResult(result);
			//session.setAttribute("results", results);
		}
		ArrayList<ResultTime> lastResults = (ArrayList<ResultTime>) this.getServletContext().getAttribute("lastResults");
		lastResults.add(resultTime);
	}

}
