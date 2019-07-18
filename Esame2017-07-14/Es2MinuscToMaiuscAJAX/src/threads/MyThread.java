package threads;

public class MyThread extends Thread {

	private String testoDaTrasformare;
	private String testoTrasformato;
	private int caratteriTrasformati;

	@Override
	public void run() {
		char[] buf = new char[this.testoDaTrasformare.length()];
		for(int i = 0; i<this.testoDaTrasformare.length(); i++) {
			if(this.testoDaTrasformare.charAt(i) >= 'a' && this.testoDaTrasformare.charAt(i) <= 'z') {
				buf[i] = Character.toUpperCase(this.testoDaTrasformare.charAt(i));
				caratteriTrasformati++;
			} else {
				buf[i] = this.testoDaTrasformare.charAt(i);
			}
		}
		testoTrasformato = new String(buf);
	}

	public MyThread(String testoDaTrasformare) {
		this.testoDaTrasformare = testoDaTrasformare;
		caratteriTrasformati = 0;
		testoTrasformato = null;
	}

	public String getTestoDaTrasformare() {
		return testoDaTrasformare;
	}

	public String getTestoTrasformato() {
		return testoTrasformato;
	}

	public int getCaratteriTrasformati() {
		return caratteriTrasformati;
	}
	
	

	

}