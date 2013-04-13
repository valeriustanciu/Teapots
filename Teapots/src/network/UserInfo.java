package network;

public class UserInfo {
	private String username;
	private String ip;
	private int port;
	
	public UserInfo (String username, String ip, int port) {
		this.username = username;
		this.ip = ip;
		this.port = port;
	}
	
	public String getUsername () {
		return this.username;
	}
	
	public String getIp () {
		return this.ip;
	}
	
	public int getPort() {
		return this.port;
	}
}
