package servlets;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

public class SplitCounterBottom extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private Gson gson;
	private ArrayList<File> files;

	@Override
	public void init() throws ServletException {
		gson = new Gson();
		files = (ArrayList<File>) this.getServletContext().getAttribute("files");
		if (files == null) {
			files = new ArrayList<File>();
			String dirPath = System.getProperty("catalina.base")
					+ "/webapps/Es1SplitCounting.jsp/resources/Split_Counting/";
			File directory = new File(dirPath);
			for (File f : directory.listFiles()) {
				files.add(f);
			}
			this.getServletContext().setAttribute("files", files);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String fileName = req.getParameter("fileName");
		String option = req.getParameter("option");
		HttpSession session = req.getSession();
		Integer counter = (Integer) session.getAttribute("counterBottom");
		if(counter == null) {
			counter = 0;
			session.setAttribute("counterBottom", counter);
		} else if(counter == 3) {
			resp.getWriter().println(gson.toJson("Conteggi esauriti"));
			return;
		}
		File file = null;
		for(File f : files) {
			if(f.getName().equals(fileName)) {
				file = f;
				break;
			}
		}
		long fileSize = file.length();
		int occorrenze = 0;
		if(option.equals("option1")) {
			BufferedReader buf = new BufferedReader(new FileReader(file));
			buf.skip(fileSize/2);
			int car;
			while((car = buf.read()) != -1) {
				char c = (char) car;
				if(c>='A' && c<='Z') {
					occorrenze++;
				}
			}
			buf.close();
			counter = counter + 1;
			session.setAttribute("counterBottom", counter);
		}
		if(option.equals("option2")) {
			BufferedReader buf = new BufferedReader(new FileReader(file));
			buf.skip(fileSize/2);
			int car;
			while((car = buf.read()) != -1) {
				char c = (char) car;
				if(!((c>='A' && c<='Z') || (c>='a' && c<='z'))) {
					occorrenze++;
				}
			}
			buf.close();
			counter = counter + 1;
			session.setAttribute("counterBottom", counter);
		}
		
		resp.getWriter().println(gson.toJson(occorrenze));
		
	}

}

