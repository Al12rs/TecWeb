package servlets;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.*;

import beans.Result;

public class MatrixOpTop extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private Gson gson;

	@Override
	public void init(ServletConfig config) throws ServletException {
		gson = new Gson();
		BufferedReader buf;
		StringBuilder jsonReadFromFile = new StringBuilder();
		String currentLine=null;
		try {
			buf = new BufferedReader(new FileReader(config.getInitParameter("matrixFile")));
			while((currentLine = buf.readLine())!=null) {
				jsonReadFromFile.append(currentLine);
				jsonReadFromFile.append('\n');
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		JsonObject e = new JsonParser().parse(jsonReadFromFile.toString()).getAsJsonObject();
		String matrixA = e.get("A").getAsString();
		String matrixB = e.get("B").getAsString();
		
		double[][] A = gson.fromJson(matrixA, double[][].class);
		double[][] B = gson.fromJson(matrixB, double[][].class);
		double[][] aTop = new double[5][10];
		double[][] bTop = new double[5][10];
		for(int i=0; i<5; i++) {
			for(int j=0; j<10; j++) {
				aTop[i][j] = A[i][j];
				bTop[i][j] = B[i][j];
			}
		}
		
		this.getServletContext().setAttribute("ATop", aTop);
		this.getServletContext().setAttribute("BTop", bTop);
		
		ArrayList<Result> cacheTop = new ArrayList<Result>();
		this.getServletContext().setAttribute("cacheTop", cacheTop);
	}

    synchronized private void cachePut(Result resultToCache) {
		ArrayList<Result> cacheBottom = (ArrayList<Result>) this.getServletContext().getAttribute("cacheBottom");
		cacheBottom.add(resultToCache);
	}

	private Result findCachedResult(String operation, long freshness){
		ArrayList<Result> cacheBottom = (ArrayList<Result>) this.getServletContext().getAttribute("cacheBottom");
		int size = cacheBottom.size();
		for (int i = size - 1; i >= 0; i--) {
			if (cacheBottom.get(i).getOperation().equals(operation)) {
				if ((new Date()).getTime() - cacheBottom.get(i).getFreshness() <= freshness) {
					return cacheBottom.get(i);
				} else {
					// visto che i risultati sono salvati sequenzialmente se questo risultato non è abbastanza fresco non ha senso controllare i più vecchi.
					return null;
				}
			}
		}
		return null;
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
        //get jason object "e" of the post body
		BufferedReader buf = req.getReader();
		StringBuilder jsonReceived = new StringBuilder();
		String currentLine=null;
		while((currentLine = buf.readLine())!=null) {
			jsonReceived.append(currentLine);
			jsonReceived.append('\n');
		}
		JsonObject e = new JsonParser().parse(jsonReceived.toString()).getAsJsonObject();

		String operation = e.get("operation").getAsString();
		long freshness = e.get("freshness").getAsLong();
		
		//check di esistenza
		Result cachedResult = findCachedResult(operation, freshness);
		if (cachedResult != null) {
			// cache hit
			resp.getWriter().println(gson.toJson(cachedResult.getResult()));
			return;
		}
		//cache miss

		//calcolo vero
		double[][] A = (double[][]) this.getServletContext().getAttribute("ATop");
		double[][] B = (double[][]) this.getServletContext().getAttribute("BTop");

		double [][] resultMatrix = new double[5][10];

		if (operation.equals("-")){
			for(int i = 0; i<5; i++){
				for(int j=0; j<10; j++){
					resultMatrix[i][j] = A[i][j] - B[i][j];
				}
			}
		} else {
			for (int i = 0; i < 5; i++) {
				for (int j = 0; j < 10; j++) {
					resultMatrix[i][j] = A[i][j] + B[i][j];
				}
			}
		}

		//save result in cache:
		Result result = new Result();
		result.setResult(resultMatrix);
		result.setOperation(operation);
		result.setRowsPosition(1);
		result.setFreshness((new Date()).getTime());
		cachePut(result);
		
		
		resp.getWriter().println(gson.toJson(resultMatrix));
        
	}
	
}
