package servlets;

import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;

import java.io.FileReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.*;

public class ContaOccorrenze extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private Gson gson;

	@Override
	public void init() throws ServletException {
		gson = new Gson();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Integer x = Integer.parseInt(req.getParameter("x"));

		HttpSession session = req.getSession(true);
		Integer sessionX = (Integer) session.getAttribute("x");

		if (sessionX == null || sessionX != x) {
			session.setAttribute("x", x);
			session.setAttribute("remaining", x);
			session.setAttribute("totalResult", 0);
		}
		String fileURI = (String) req.getParameter("fileLocation");
		Integer result = contaOccorrenze(fileURI);
		//prse the file and calculate the nulber of occurrances. If I have time I will do this later.
		//for now assume some random result.
		Integer remaining = updateSessionResults(result, session);
		if (remaining <= 0) {
			//allora mandiamo il risutato totale
			Integer FinalResult = ((Integer) session.getAttribute("totalResult"));
			resp.getWriter().println(gson.toJson(FinalResult));
			session.invalidate();
				
		}
		//check if it's the last request
	}
	
	private synchronized Integer updateSessionResults(Integer result, HttpSession session){
		Integer currentResult = ((Integer) session.getAttribute("totalResult")) + result;
		session.setAttribute("totalResult", currentResult);
		Integer remaining = (Integer) session.getAttribute("remaining");
		remaining--;
		session.setAttribute("remaining", remaining);
		return remaining;
	} 

	private Integer contaOccorrenze(String fileURI) throws IOException{
		FileReader fr = new FileReader(fileURI);
		int counter = 0;
		char[] buff = new char[1];
		while (fr.read(buff) > 0) {
			if (buff[0]>='0' && buff[0] <= '9') {
				counter++;
			}
		}
		return counter;
	}


}
