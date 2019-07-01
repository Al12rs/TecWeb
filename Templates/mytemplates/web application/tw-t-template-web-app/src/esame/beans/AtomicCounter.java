package esame.beans;

public class AtomicCounter {
	private int counter;

	public AtomicCounter() {
		counter = 0;
	}
	
	public synchronized void increment(){
		this.counter++;
	}
	
	/**Increment the counter if the final value does not exceed the
	 * limit passed as parameter (limit is inclusive).
	 * If limit is negative no check is performed.
	 * @param limit
	 * @return true if the counter has been incremented, false otherwise
	 */
	public synchronized boolean incrementNoMoreThan(int limit){
		if (limit<0 || counter+1 <= limit) {
			this.counter++;
			return true;
		} else {
			return false;
		}
	}
	
	public synchronized void decrement(){
		if (counter>0)
			this.counter--;
	}

	public synchronized int get() {
		return counter;
	}
}
