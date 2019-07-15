package beans;

public class Coordinate {

	private double x;
	private double y;

	public Coordinate(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public Coordinate() {
		// TODO Auto-generated constructor stub
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double calcolaDistanza(double x, double y) {
		return Math.sqrt((this.x - x) * (this.x - x) + (this.y - y) * (this.y - y));
	}
}
