package beans;

public class Result {
	
	private double result[][];
	private String operation;
	private int rowsPosition; //0 prime righe, 1 ultime righe
	private long freshness; //since January 1970 UTC
	
	public double[][] getResult() {
		return result;
	}
	public void setResult(double[][] result) {
		this.result = result;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public long getFreshness() {
		return freshness;
	}
	public void setFreshness(long freshness) {
		this.freshness = freshness;
	}
	public int getRowsPosition() {
		return rowsPosition;
	}
	public void setRowsPosition(int rowsPosition) {
		this.rowsPosition = rowsPosition;
	}
	
}
