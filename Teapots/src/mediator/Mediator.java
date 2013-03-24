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


	public void changeState(String newState) {
		// modifica starea 
		
	}
	
	public void addService (String user, String service) {
		// apelam in cazul Launch offer request => o sa fie notificati userii care ofera serviciul
		// se notifica toti sellerii care ofera serviciul
		// se populeaza comboboxurile lor cu buyerul acesta
		System.out.println("Add service " + service + " for user " + user);
		
		ArrayList<String> test = new ArrayList<String>();
		test.add("Cici");
		test.add("Caca");
		test.add("Coco");
		this.gui.populateServiceUserList("scaun", test);
		// 
	}
	
	public void removeService (String user, String service) {
		// apelam in cazul Drop offer request
		// buyerul dispare din comboboxurile sellerilor care ofera serviciul
		this.gui.removeServiceUserList(service);
		
	}
	
	public void acceptOffer (String localUser, String remoteUser, String service) {
		this.gui.acceptOffer(remoteUser, service);
	}
	
	public void refuseOffer (String localUser, String remoteUser, String service) {
		this.gui.refuseOffer(remoteUser, service);
	}
	
	public void makeOffer(String localUser, String remoteUser, String service) {
		// cand un furnizor face o oferta unui anumit buyer
		// se trimite pe network catre buyerul respectiv
		
		this.gui.makeOffer(remoteUser, service);		
	}
	
	public void dropAuction(String localUser, String remoteUser, String service) {
		// cand un furnizor renunta la oferta facuta unui anumit buyer
		// se trimite pe network catre buyerul respectiv
		
		this.gui.dropAuction(remoteUser, service);
	}
	
}
