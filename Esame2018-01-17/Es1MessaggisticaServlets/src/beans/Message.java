package beans;

public class Message {

	private String message;
	private LoginInfo sender;
	private long time;

	public Message() {
		// TODO Auto-generated constructor stub
	}

	public Message(String message, LoginInfo sender, long time) {
		this.message = message;
		this.sender = sender;
		this.time = time;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public LoginInfo getSender() {
		return sender;
	}

	public void setSender(LoginInfo sender) {
		this.sender = sender;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

}
