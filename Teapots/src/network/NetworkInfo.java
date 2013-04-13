package network;

import java.util.ArrayList;

public class NetworkInfo {
	
	private ArrayList<UserInfo> users;
	
	public NetworkInfo () {
		users = new ArrayList<UserInfo>();
	}
	
	public void addUser (String username, String ip, int port) {
		UserInfo user = new UserInfo(username, ip, port);
		users.add(user);
	}
	
	public String getUserIP (String username) {
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getUsername().equals(username)) {
				return users.get(i).getIp();
			}
		}
		
		return null;
	}
	
	public int getUserPort (String username) {
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getUsername().equals(username)) {
				return users.get(i).getPort();
			}
		}
		
		return -1;
	}

	public void removeUser (String username) {
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getUsername().equals(username)) {
				users.remove(i);
				return;
			}
		}
		
	}
	
	public ArrayList<UserInfo> getLoggedUsers () {
		return this.users;
	}
}
