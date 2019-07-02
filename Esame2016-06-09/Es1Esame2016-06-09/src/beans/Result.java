package beans;

public class Result {

	private String file;
	private char car;
	private int occurrences;

	public Result(String file, char car, int occurrences) {
		this.file = file;
		this.car = car;
		this.occurrences = occurrences;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public char getCar() {
		return car;
	}

	public void setCar(char car) {
		this.car = car;
	}

	public int getOccurrences() {
		return occurrences;
	}

	public void setOccurrences(int occurrences) {
		this.occurrences = occurrences;
	}

}
