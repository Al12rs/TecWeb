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

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import beans.Result;

public class MatrixOpBottom extends HttpServlet {
	
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
		double[][] aBottom = new double[5][10];
		double[][] bBottom = new double[5][10];
		for(int i=5; i<10; i++) {
			for(int j=0; j<10; j++) {
				aBottom[i][j] = A[i][j];
				bBottom[i][j] = B[i][j];
			}
		}
		
		this.getServletContext().setAttribute("ABottom", aBottom);
		this.getServletContext().setAttribute("BBottim", bBottom);

		ArrayList<Result> cacheBottom = new ArrayList<Result>();
		this.getServletContext().setAttribute("cacheBottom", cacheBottom);
		
	}

	synchronized private void cachePut(Result resultToCache) {
		ArrayList<Result> cacheBottom = (ArrayList<Result>) this.getServletContext().getAttribute("cacheBottom");
		cacheBottom.add(resultToCache);
	}

	private Result findCachedResult(String operation, long freshness){
		ArrayList<Result> cacheBottom = (ArrayList<Result>) this.getServletContext().getAttribute("cacheBottom");
		int size = cacheBottom.size();
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

		//calcolo vero
		double[][] A = (double[][]) this.getServletContext().getAttribute("ABottom");
		double[][] B = (double[][]) this.getServletContext().getAttribute("BBottom");

		//invert lower half of B in case of subtraction.
		double [][] resultMatrix = new double[5][10];

		if (operation.equals("-")){
			for(int i = 5; i<10; i++){
				for(int j=0; j<10; j++){
					resultMatrix[i-5][j] = A[i][j] + B[i][j];
				}
			}
		} else {
			for(int i = 5; i<10; i++){
				for(int j=0; j<10; j++){
					resultMatrix[i-5][j] = A[i][j] + B[i][j];
				}
			}
		}
		

		//sum lower half of A and B
		
		

		//save result in cache:
		Result result = new Result();
		result.setResult(resultMatrix);
		result.setOperation(operation);
		result.setRowsPosition(1);
		result.setFreshness((new Date()).getTime());
		
		
		resp.getWriter().println(gson.toJson(resultMatrix));

	}
	
}
