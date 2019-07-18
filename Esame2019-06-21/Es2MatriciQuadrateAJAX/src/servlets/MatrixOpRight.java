package servlets;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class MatrixOpRight extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private Gson gson;

	@Override
	public void init(ServletConfig config) throws ServletException {
		gson = new Gson();
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		HttpSession session = req.getSession();
		Integer count = (Integer)session.getAttribute("countRight");
		if(count == null) {
			session.setAttribute("countRight", 1);
		} else if(count == 5) {
			resp.getWriter().println("Raggiunto il limite di operazioni");
		} else {
			count = count + 1;
			session.setAttribute("countRight", count);
		}

		// get json object "e" of the post body
		BufferedReader buf = req.getReader();
		StringBuilder jsonReceived = new StringBuilder();
		String currentLine = null;
		while ((currentLine = buf.readLine()) != null) {
			jsonReceived.append(currentLine);
			jsonReceived.append('\n');
		}
		JsonObject e = new JsonParser().parse(jsonReceived.toString()).getAsJsonObject();

		JsonArray matrixA = e.get("A").getAsJsonArray();
		double[][] A = new double[4][4];
		for (int i = 0; i < 4; i++) {
			JsonArray rigaA = matrixA.get(i).getAsJsonArray();
			for (int j = 0; j < 4; j++) {
				A[i][j] = rigaA.get(j).getAsDouble();
			}
		}

		JsonArray matrixB = e.get("B").getAsJsonArray();
		double[][] B = new double[4][4];
		for (int i = 0; i < 4; i++) {
			JsonArray rigaB = matrixB.get(i).getAsJsonArray();
			for (int j = 0; j < 4; j++) {
				B[i][j] = rigaB.get(j).getAsDouble();
			}
		}
		
		/* 
		 * or better
		 * double[][] A = gson.fromJson(matrixA, double[][].class);
		 * double[][] B = gson.fromJson(matrixB, double[][].class);
		 */

		/*double[][] Aleft = new double[4][2];
		double[][] Bleft = new double[4][2];
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 2; j++) {
				Aleft[i][j] = A[i][j];
				Bleft[i][j] = B[i][j];
			}
		}*/

		double[][] resultMatrix = new double[4][2];

		for (int i = 0; i < 4; i++) {
			for (int j = 2; j < 4; j++) {
				resultMatrix[i][j-2] = A[i][j] + B[i][j];
			}
		}

		resp.getWriter().println(gson.toJson(resultMatrix));

	}
	
}
