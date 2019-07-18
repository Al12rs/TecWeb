package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoadingServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		Integer x = (Integer) session.getAttribute("x");
		if( x == null ) {
			session.setAttribute("x", Integer.parseInt(req.getParameter("numFile")));
			session.setAttribute("remaining", Integer.parseInt(req.getParameter("numFile")));
		}
		
		resp.setContentType("text/html");
		PrintWriter pr = resp.getWriter();
		pr.println("<html>\r\n" + 
				"\r\n" + 
				"<head>\r\n" + 
				"    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=ISO-8859-1\">\r\n" + 
				"    <meta http-equiv=\"Pragma\" content=\"no-cache\" />\r\n" + 
				"    <meta http-equiv=\"Expires\" content=\"-1\" />\r\n" + 
				"    <title>LoadingFiles</title>\r\n" +  
				"    <link rel=\"stylesheet\" href=\"../styles/default.css\" type=\"text/css\" />\r\n" + 
				"</head>\r\n" + 
				"\r\n" + 
				"<body>\r\n" + 
				"    <form method=\"post\" action=\"./loadingservlet\">\r\n" + 
				"        <label id=\"inputLabel\" for=\"numFile\">Nome file:</label><br />\r\n" + 
				"        <input id=\"nomeFile\" type=\"text\" name=\"nomeFile\" value=\"\" /><br />\r\n" + 
				"        <input type=\"submit\" value=\"Immetti\" id=\"submitButton\" />\r\n" + 
				"    </form>\r\n" + 
				"</body>\r\n" + 
				"\r\n" + 
				"</html>");
		pr.close();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		Integer remaining = (Integer) session.getAttribute("remaining");
		Hashtable<Integer, String> files = (Hashtable<Integer, String>) session.getAttribute("files");
		if(files == null) {
			files = new Hashtable<Integer, String>();
			session.setAttribute("files", files);
		}
		String fileName = req.getParameter("nomeFile");
		files.put(remaining, fileName);
		remaining = remaining - 1;
		session.setAttribute("remaining", remaining);
		
		resp.sendRedirect("./pages/home.html?x="+remaining);
	}
	
	
}
