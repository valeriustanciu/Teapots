package mediator;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;

import web.IWeb;
import gui.Gui;
import gui.IGui;
import network.INetwork;



public class Mediator implements IMediatorGui, IMediatorNetwork, IMediatorWeb{
	private INetwork network;
	private IGui gui;
	private IWeb web;
	
	public Mediator() {
		
	}
	
	
	public void setGui(IGui gui) {
		this.gui = gui;
	}
	
	public String getUserType (String user, String pass) {
		try {
			File configFile = new File("config.txt");
			
			FileInputStream fstream = new FileInputStream(configFile);
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			
			//Read File Line By Line
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
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(
					null, "Error " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		
		return null;
	}
}
