package gui;

import java.util.ArrayList;

public class ServiceUserStatus {
	class Pair<A, B, C> {
		A user;
		B status;
		C price;
		
		Pair(A user, B status, C price) {
			this.user = user;
			this.status = status;
			this.price = price;
		}
		
		String getUser () {
			return (String)user;
		}
		
		String getStatus () {
			return (String)status;
		}
		
		// TODO: convert to int?
		Integer getPrice () {
			return (Integer)price;
		}
		
		void setStatus (B status) {
			this.status = status;
		}
		
		void setPrice (C price) {
			this.price = price;
		}
	}
	
	String service;
	ArrayList<Pair<String, String, Integer>> userStatus;
	
	public ServiceUserStatus (String service) {
		this.service = service;
		this.userStatus = new ArrayList<Pair<String, String, Integer>>();
	}
	
	public String getService () {
		return this.service;
	}
	
	public ArrayList<Pair<String, String, Integer>> getList () {
		return this.userStatus;
	}
	
	public void addUser (String user, String status, Integer price) {
		Pair<String, String, Integer> p = new Pair<String, String, Integer>(user, status, price);
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
	
	public void changePrice (String user, Integer price) {
		for (int i = 0; i < this.userStatus.size(); i++) {
			if (this.userStatus.get(i).getUser().equals(user)) {
				this.userStatus.get(i).setPrice(price);
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
	
	public Integer getPrice (String user) {
		for (int i = 0; i < this.userStatus.size(); i++) {
			if (this.userStatus.get(i).getUser().equals(user)) {
				return this.userStatus.get(i).getPrice();
			}
		}
		return null;
	}
		
	public int getSize() {
		return this.userStatus.size();
	}
}
