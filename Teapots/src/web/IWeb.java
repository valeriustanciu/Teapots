package web;

import java.io.IOException;
import java.util.ArrayList;

import network.UserInfo;

public interface IWeb {
	public String getUserType(String user);
	public String getUserType(String user, String pass);
	public ArrayList<String> getUserServices (String user);
	public UserInfo getOwnInfo (String username);
	public void userLoggedOut(String username);
	public void userLoggedIn(String username);
	public ArrayList<UserInfo> getLoggedUsers(String username);
	public ArrayList<String> getUsersWithService (String username, String service);
}
