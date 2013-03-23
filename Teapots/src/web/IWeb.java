package web;

import java.util.ArrayList;

public interface IWeb {
	public String getUserType(String user, String pass);
	public ArrayList<String> getUserServices (String user);
}
