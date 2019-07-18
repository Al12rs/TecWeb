package servlets;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import beans.JsonRequest;

public class ContentGiver extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private Gson gson;
	private String[] fileLetto;
	private Hashtable<String, ArrayList<Integer>> results;
	private Integer c1;
	private Integer c2;
	private Integer c3;
	private Integer c4;

	@Override
	public void init(ServletConfig config) throws ServletException {
		gson = new Gson();
		fileLetto = new String[4];
		BufferedReader buf;
		File f = new File(config.getInitParameter("lettura"));
		long fileSize = f.length();
		int quarterSize = (int) (fileSize / 4);
		try {
			buf = new BufferedReader(new FileReader(f));
			char[] cbuf = new char[(int) fileSize];
			buf.read(cbuf, 0, (int) fileSize);
			fileLetto[0] = new String(cbuf, 0, quarterSize);
			fileLetto[1] = new String(cbuf, quarterSize, 2*quarterSize);
			fileLetto[2] = new String(cbuf, 2*quarterSize, 3*quarterSize);
			fileLetto[3] = new String(cbuf, 3*quarterSize, 4*quarterSize-1);
		} catch (IOException e) {
			e.printStackTrace();
		}

		results = new Hashtable<String, ArrayList<Integer>>();
		results.put("p1", new ArrayList<Integer>());
		results.put("p2", new ArrayList<Integer>());
		results.put("p3", new ArrayList<Integer>());
		results.put("p4", new ArrayList<Integer>());

		this.getServletContext().setAttribute("results", results);
		
		c1 = 0;
		c2 = 0;
		c3 = 0;
		c4 = 0;

	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String portion = null;
		int minSize = Integer.MAX_VALUE;
		//la ricerca del minimo dovrebbe essere fatta sui c[1-4]
		for (String s : results.keySet()) {
			if (results.get(s).size() < minSize) {
				minSize = results.get(s).size();
				portion = s;
			}
		}
		JsonRequest jreq = new JsonRequest();
		if (portion.equals("p1")) {
			jreq.setContent(fileLetto[0]);
			jreq.setLength(fileLetto[0].length());
			req.getSession().setAttribute("por", "p1");
			c1++;
		}
		if (portion.equals("p2")) {
			jreq.setContent(fileLetto[1]);
			jreq.setLength(fileLetto[1].length());
			req.getSession().setAttribute("por", "p2");
			c2++;
		}
		if (portion.equals("p3")) {
			jreq.setContent(fileLetto[2]);
			jreq.setLength(fileLetto[2].length());
			req.getSession().setAttribute("por", "p3");
			c3++;
		}
		if (portion.equals("p4")) {
			jreq.setContent(fileLetto[3]);
			jreq.setLength(fileLetto[3].length());
			req.getSession().setAttribute("por", "p4");
			c4++;
		}

		resp.getWriter().println(gson.toJson(jreq));
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		HttpSession session = req.getSession(false);
		if (session == null) {
			resp.addCookie(new Cookie("autenticato", "false"));
			resp.sendRedirect("./pages/login.html");
		} else {
			Integer count = Integer.parseInt(req.getParameter("count"));
			results.get((String) req.getSession().getAttribute("por")).add(count);
			boolean readyToCount = true;
			for (String s : results.keySet()) {
				if (results.get(s).size() < 1) {
					readyToCount = false;
					break;
				}
			}
			if (readyToCount) {
				Integer finalCount = 0;
				for (String s : results.keySet()) {
					finalCount = finalCount + results.get(s).get(0);
				}
				this.getServletContext().setAttribute("finalCount", finalCount);
				c1--;
				c2--;
				c3--;
				c4--;
			}
		}

	}

}
