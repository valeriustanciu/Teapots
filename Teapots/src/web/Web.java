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

public class Web implements IWeb{
	private IMediatorWeb mediator;
	
	
	public Web(IMediatorWeb med) {
		this.mediator = med;
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
	
}
