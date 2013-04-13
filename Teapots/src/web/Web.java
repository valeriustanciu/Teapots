package web;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;

import mediator.IMediatorWeb;
import network.NetworkInfo;
import network.UserInfo;

public class Web implements IWeb{
	private IMediatorWeb mediator;
	
	
	public Web(IMediatorWeb med) {
		this.mediator = med;
	}
	
	
	public String getUserType(String user) {
		try {
			File configFile = new File("config.txt");
			
			FileInputStream fstream = new FileInputStream(configFile);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			
			//Read config file and check if login is OK
			while ((strLine = br.readLine()) != null)   {
			     StringTokenizer st = new StringTokenizer(strLine);
			     String username = st.nextToken();
			     String password = st.nextToken();
			     String type = st.nextToken();
			     
			     if (username.equals(user)) {
			    	 return type;
			     }
			}
			in.close();
			br.close();
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(
					null, "Error " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		
		return null;
	}
	
	public String getUserType(String user, String pass) {
		try {
			File configFile = new File("config.txt");
			
			FileInputStream fstream = new FileInputStream(configFile);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			
			//Read config file and check if login is OK
			while ((strLine = br.readLine()) != null)   {
			     StringTokenizer st = new StringTokenizer(strLine);
			     String username = st.nextToken();
			     String password = st.nextToken();
			     String type = st.nextToken();
			     
			     if (username.equals(user) && password.equals(pass)) {
			    	 return type;
			     }
			}
			in.close();
			br.close();
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(
					null, "Error " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		
		return null;
	}


	public ArrayList<String> getUserServices(String user) {
		ArrayList<String> userServices = new ArrayList<String>();
		try {
			File configFile = new File(user + ".txt");
			
			FileInputStream fstream = new FileInputStream(configFile);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			
			//Read services
			while ((strLine = br.readLine()) != null)   {
			     userServices.add(strLine);
			}
			in.close();
			br.close();
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(
					null, "Error " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		
		return userServices;
	}
	
	// hardcodat - returneaza lista de utilizatori logati, mai putin userul curent
	public ArrayList<UserInfo> getLoggedUsers (String username) {
		ArrayList<UserInfo> users = new ArrayList<UserInfo>();
		users.add(new UserInfo("andreea", "127.0.0.1", 30000));
		users.add(new UserInfo("valeriu", "127.0.0.1", 30001));
		users.add(new UserInfo("student", "127.0.0.1", 30002));
		users.add(new UserInfo("unchiasu", "127.0.0.1", 30003));
		
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getUsername().equals(username)) {
				users.remove(i);
				break;
			}
		}
		
		return users;
	}
	
	public ArrayList<String> getUsersWithService (String username, String service) {
		ArrayList<String> users = new ArrayList<String>();
		ArrayList<UserInfo> usersInfo = this.getLoggedUsers(username);
		
		for (int i = 0; i < usersInfo.size(); i++) {
			ArrayList <String> userServices = this.getUserServices(usersInfo.get(i).getUsername());
			if (userServices.contains(service) && 
					this.getUserType(usersInfo.get(i).getUsername()).equals("seller"))
				users.add(usersInfo.get(i).getUsername());
		}
		return users;
	}
	
	public UserInfo getOwnInfo (String username) {
		int port = -1;
		String ip = "127.0.0.1";
		if (username.equals("andreea")) {
			port = 30000;
		}
		else {
			if (username.equals("valeriu")) {
				port = 30001;
			}
			else {
				if (username.equals("student")) {
					port = 30002;
				}
				else {
					if (username.equals("unchiasu")) {
						port = 30003;
					}
				}
			}
		}
		
		return new UserInfo(username, ip, port);
	}
}
