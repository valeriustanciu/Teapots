package web;

import java.util.ArrayList;

import network.UserInfo;

public interface IWeb {
	public String getUserType(String user, String pass);
	public ArrayList<String> getUserServices (String user);
	public UserInfo getOwnInfo (String username);
	public ArrayList<UserInfo> getLoggedUsers(String username);
}
