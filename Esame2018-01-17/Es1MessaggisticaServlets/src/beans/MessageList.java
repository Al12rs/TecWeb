package beans;

import java.util.ArrayList;

public class MessageList {

	private ArrayList<Message> messages;

	public MessageList() {
		this.messages = new ArrayList<Message>();
	}

	public void addMessage(Message m) {
		this.messages.add(m);
	}

	public int getNumberOfMessages() {
		return this.messages.size();
	}

	public void eraseMessages() {
		this.messages = new ArrayList<Message>();
	}

	public ArrayList<Message> getMessages() {
		return this.messages;
	}

}
