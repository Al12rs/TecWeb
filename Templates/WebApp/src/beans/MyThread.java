package threads;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class MyThread extends Thread {

	private File file;
	private char car;
	private int offset;
	private int occurrences;

	@Override
	public void run() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(this.file));
			char[] cbuf = null;
			reader.read(cbuf, this.offset, 1000);
			for(int i=0; i<cbuf.length; i++) {
				if(cbuf[i] == this.car) {
					this.occurrences++;
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public MyThread(File file, char car, int offset) {
		super();
		this.file = file;
		this.car = car;
		this.offset = offset;
		this.occurrences = 0;
	}

	public File getFile() {
		return file;
	}

	public char getCar() {
		return car;
	}

	public int getOccurrences() {
		return occurrences;
	}

}