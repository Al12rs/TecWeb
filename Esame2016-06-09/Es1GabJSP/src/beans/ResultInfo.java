package beans;

public class ResultInfo {
    private int[] results = new int[3];
    private long[] timeStamps = new long[3];
    private int index;

    public ResultInfo() {
        index = 0;
        results[0] = -1;
        results[1] = -1;
        results[2] = -1;
        timeStamps[0] = 0L;
        timeStamps[1] = 0L;
        timeStamps[2] = 0L;

    }

    public void addResult(int count, long timestamp) {
        results[index] = count;
        timeStamps[index] = timestamp;
        index++;
        if (index > 2) {
            index = 0;
        }
    }

    public int[] getResults() {
        return results;
    }

    public long[] getTimeStamps(){
        return timeStamps;
    }
}