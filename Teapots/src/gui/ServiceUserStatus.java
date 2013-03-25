package gui;

import java.util.ArrayList;

public class ServiceUserStatus {
	class Pair<A, B> {
		A user;
		B status;
		
		Pair(A user, B status) {
			this.user = user;
			this.status = status;
		}
		
		String getUser () {
			return (String)user;
		}
		
		String getStatus () {
			return (String)status;
		}
		
		void setStatus (B status) {
			this.status = status;
		}
	}
	
	String service;
	ArrayList<Pair<String, String>> userStatus;
	
	public ServiceUserStatus (String service) {
		this.service = service;
		this.userStatus = new ArrayList<Pair<String, String>>();
	}
	
	public String getService () {
		return this.service;
	}
	
	public ArrayList<Pair<String, String>> getList () {
		return this.userStatus;
	}
	
	public void addUser (String user, String status) {
		Pair<String, String> p = new Pair<String, String>(user, status);
		this.userStatus.add(p);
	}
	
	public void removeUser (String user) {
		for (int i = 0; i < this.userStatus.size(); i++) {
			if (this.userStatus.get(i).getUser().equals(user)) {
				this.userStatus.remove(i);
				return;
			}
		}
	}
	
	public void changeStatus (String user, String status) {
		for (int i = 0; i < this.userStatus.size(); i++) {
			if (this.userStatus.get(i).getUser().equals(user)) {
				this.userStatus.get(i).setStatus(status);
				return;
			}
		}
	}
	
	public String getStatus (String user) {
		for (int i = 0; i < this.userStatus.size(); i++) {
			if (this.userStatus.get(i).getUser().equals(user)) {
				return this.userStatus.get(i).getStatus();
			}
		}
		return null;
	}
	
	public int getSize() {
		return this.userStatus.size();
	}
}
