package beans;

public class ResultTime extends Result {
	
	private long creationTime; //UNIX time

	public ResultTime(String file, char car, int occurrences, long creationTime) {
		super(file, car, occurrences);
		this.creationTime = creationTime;
	}

	public long getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(long creationTime) {
		this.creationTime = creationTime;
	}
	
	
	
	
}
