package web;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
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

	public ArrayList<UserInfo> getLoggedUsers () {
		ArrayList<UserInfo> loggedUsers = new ArrayList<UserInfo>();
		
		File f = new File("loggedUsers.txt");
		RandomAccessFile raf;
		try {
			raf = new RandomAccessFile(f, "rw");
			
			String line;
			
			while ((line = raf.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(line);
				String user = st.nextToken();
				
				loggedUsers.add(new UserInfo(user, 
						   this.getOwnInfo(user).getIp(),
						   this.getOwnInfo(user).getPort()));
			}
			
			raf.close();
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return loggedUsers;
	}

	public ArrayList<UserInfo> getLoggedUsers (String username) {
		ArrayList<UserInfo> loggedUsers = new ArrayList<UserInfo>();
		
		File f = new File("loggedUsers.txt");
		RandomAccessFile raf;
		try {
			raf = new RandomAccessFile(f, "rw");
			
			String line;
			
			while ((line = raf.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(line);
				String user = st.nextToken();
				
				if (user.equals(username))
					continue;
				loggedUsers.add(new UserInfo(user, 
						   this.getOwnInfo(user).getIp(),
						   this.getOwnInfo(user).getPort()));
			}
			
			raf.close();
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return loggedUsers;
	}

	public ArrayList<String> getUsersWithService (String username, String service) {
		ArrayList<String> usersWithService = new ArrayList<String>();
		ArrayList<UserInfo> usersInfo = this.getLoggedUsers(username);

		for (int i = 0; i < usersInfo.size(); i++) {
			ArrayList <String> userServices = this.getUserServices(usersInfo.get(i).getUsername());
			if (userServices.contains(service) && 
					this.getUserType(usersInfo.get(i).getUsername()).equals("seller"))
				usersWithService.add(usersInfo.get(i).getUsername());
		}
		System.out.println(usersWithService);
		return usersWithService;
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


	@Override
	public void userLoggedOut(String username) {
		// TODO Auto-generated method stub
		System.out.println("User " + username + " logged out!");
		try {
			File inFile = new File("loggedUsers.txt");
		
			if (!inFile.isFile()) {
				System.out.println("Parameter is not an existing file");
				return;
			}

			//Construct the new file that will later be renamed to the original filename. 

			File tempFile = new File(inFile.getAbsolutePath() + ".tmp");
			BufferedReader br = new BufferedReader(new FileReader(inFile));
			PrintWriter pw = new PrintWriter(new FileWriter(tempFile));

			String line = null;
			String lineToRemove = username + " " + this.getOwnInfo(username).getIp() +
		    		  " " + this.getOwnInfo(username).getPort();

			//Read from the original file and write to the new 
			//unless content matches data to be removed.
			while ((line = br.readLine()) != null) {
		        
		        if (!line.equals(lineToRemove)) {
		        	pw.println(line);
		        	pw.flush();
		        }
			}
			pw.close();
			br.close();

			//Delete the original file
			if (!inFile.delete()) {
				System.out.println("Could not delete file");
				return;
			} 
		      
			//Rename the new file to the filename the original file had.
			if (!tempFile.renameTo(inFile))
				System.out.println("Could not rename file");

		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}


	@Override
	public void userLoggedIn(String username) {
		try {
			File f = new File("loggedUsers.txt");
			
			if (!f.exists()) {
				f.createNewFile();
				// first user to log in
				String strLine = username + " " + 
								this.getOwnInfo(username).getIp() + " " + 
								this.getOwnInfo(username).getPort() + "\r\n"; 
				
				FileWriter fw = new FileWriter(f.getAbsoluteFile());
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(strLine);
				bw.close();
			}
			
			else {
				RandomAccessFile raf = new RandomAccessFile(f, "rw");
				
				if (this.getLoggedUsers().size() == 0)
					raf.setLength(0);
				
				String ip = "127.0.0.1";
				int port = this.getOwnInfo(username).getPort();
				String strLine = username + " " + ip + " " + port + "\r\n";
				String line;
				
				while ((line = raf.readLine()) != null) {
					StringTokenizer st = new StringTokenizer(line);
					String user = st.nextToken();
					
					if (user.equals(username)) {
						raf.close();
						return;
					}
				}
				
				raf.writeBytes(strLine);
				raf.close();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}