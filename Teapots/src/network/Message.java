package network;

import java.io.Serializable;
import java.util.ArrayList;

public class Message implements Serializable{
	private String fromUser;
	private String toUser;
	private String action;
	private ArrayList<String> args;
	
	public Message(String fromUser, String toUser, String action, ArrayList<String> args) {
		this.fromUser = fromUser;
		this.toUser = toUser;
		this.action = action;
		this.args = args;
	}
	
	public String getFromUser() {
		return this.fromUser;
	}
	
	public String getToUser() {
		return this.toUser;
	}
	
	public String getAction() {
		return this.action;
	}
	
	public ArrayList<String> getArgs() {
		return this.args;
	}
}
