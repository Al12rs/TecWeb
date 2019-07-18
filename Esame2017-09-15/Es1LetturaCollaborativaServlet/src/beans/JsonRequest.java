package beans;

import java.io.Serializable;

public class JsonRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int length;
	private String content;

	public JsonRequest(int length, String content) {
		this.length = length;
		this.content = content;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public JsonRequest() {
		// TODO Auto-generated constructor stub
	}

}
