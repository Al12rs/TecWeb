package beans;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class MyThread extends Thread {

	private int i;
	private char character;
	private Integer localCounter = 0;

	@Override
	public void run() {
		
		char[] kilo = new char[1000];
		int readRes = 0;
		BufferedReader buf;
		try {
			buf = new BufferedReader(new FileReader(filename));
			readRes = buf.read(kilo, 1000 * i, 1000);
			buf.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (int index = 0; index < readRes; index++) {
			if (character == kilo[index]) {
				localCounter++;
			}
		}
		return;
	}

	public Integer getLocalCounter() {
		return localCounter;
	}

	public MyThread(int i, char character) {
		this.i = i;
		this.character = character;
		this.localCounter = 0;
	}


}