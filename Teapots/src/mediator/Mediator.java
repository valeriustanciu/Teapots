package mediator;

import java.util.ArrayList;

import gui.IGui;
import network.INetwork;
import network.Network;
import web.IWeb;
import web.Web;



public class Mediator implements IMediatorGui, IMediatorNetwork, IMediatorWeb{
	private INetwork network;
	private IGui gui;
	private IWeb web;
	
	public Mediator() {
		web = new Web(this);
		network = new Network(this);
	}
	
	
	public void setGui(IGui gui) {
		this.gui = gui;
	}
	
	public String getUserType (String user, String pass) {
		return web.getUserType(user, pass);
	}
	
	public ArrayList<String> getUserServices (String user) {
		return web.getUserServices(user);
	}
}
