package beans;

public class ResultsList {

	private Result[] resultsList;
	private int index;

	public ResultsList() {
		resultsList = new Result[3];
		index = 0;
	}

	public void addResult(Result result) {
		this.resultsList[this.index] = result;
		if (this.index == (this.resultsList.length - 1)) {
			this.index = 0;
		} else {
			this.index++;
		}
	}
	
	public Result getResult(int i) {
		return this.resultsList[i];
	}
	
	public Result[] getResults() {
		return this.resultsList;
	}
}
