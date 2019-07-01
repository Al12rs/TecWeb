package esame.beans;

public class AjaxMessage {
	private String message;

	public AjaxMessage() {
		this("");
	}

	public AjaxMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
