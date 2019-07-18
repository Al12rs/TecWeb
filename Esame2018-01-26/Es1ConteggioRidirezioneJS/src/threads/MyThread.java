package threads;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class MyThread extends Thread {

	private File file;
	private int occurrences;

	@Override
	public void run() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(this.file));
			int car;
			while ((car = reader.read()) != -1) {
				char c = (char) car;
				if ((c >= '0') && (c <= '9')) {
					this.occurrences++;
				}
			}
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public MyThread(File file) {
		super();
		this.file = file;
		this.occurrences = 0;
	}

	public File getFile() {
		return file;
	}

	public int getOccurrences() {
		return occurrences;
	}

}