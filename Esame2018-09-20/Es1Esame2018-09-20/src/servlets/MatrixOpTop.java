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

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
	}
	
}
