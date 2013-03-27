package mediator;

import java.util.ArrayList;

import gui.IGui;
import network.INetwork;
import network.MockNetwork;
import network.Network;
import web.IWeb;
import web.Web;



public class Mediator implements IMediatorGui, IMediatorNetwork, IMediatorWeb{
	private INetwork network;
	private IGui gui;
	private IWeb web;
	
	public Mediator() {
		web = new Web(this);
		network = new MockNetwork(this);
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
	
	
	public void addService (String user, String service) {
		// apelam in cazul Launch offer request => o sa fie notificati userii care ofera serviciul
		// se notifica toti sellerii care ofera serviciul
		// se populeaza comboboxurile lor cu buyerul acesta
		System.out.println("Add service " + service + " for user " + user);
		
		ArrayList<String> test = new ArrayList<String>();
		test.add("Gigi");
		test.add("Coco");
		test.add("Cucu");
		if (service.equals("canapea"))
			this.gui.populateServiceUserList(service, test);
		
		ArrayList<String> test2 = new ArrayList<String>();
		test2.add("Gigi");
		test2.add("Gaga");
		test2.add("Gogo");
		
		if (service.equals("scaun"))
			this.gui.populateServiceUserList(service, test2);
		
//		this.gui.sellerMadeOffer("Caca", "scaun");
		// 
		
		this.network.addService(user, service);
	}
	
	public void removeService (String user, String service) {
		// apelam in cazul Drop offer request
		// buyerul dispare din comboboxurile sellerilor care ofera serviciul
		this.gui.removeServiceUserList(service);
		this.network.removeService(user, service);
		
	}
	
	
	// cand utilizatorul curent accepta o oferta, face modificarile in gui si anunta
	// ceilalti useri, prin network
	public void acceptOffer (String localUser, String remoteUser, String service) {
		this.gui.acceptOffer(remoteUser, service);
		this.network.acceptOffer(localUser, remoteUser, service);
	}
	
	public void refuseOffer (String localUser, String remoteUser, String service) {
		this.gui.refuseOffer(remoteUser, service);
		this.network.refuseOffer(localUser, remoteUser, service);
	}
	
	public void makeOffer(String localUser, String remoteUser, String service) {
		// cand un furnizor face o oferta unui anumit buyer
		// se trimite pe network catre buyerul respectiv
		
		this.gui.makeOffer(remoteUser, service);
		this.network.makeOffer(localUser, remoteUser, service);
		//this.gui.sellerMadeOffer(remoteUser, service);
	}
	
	public void dropAuction(String localUser, String remoteUser, String service) {
		// cand un furnizor renunta la oferta facuta unui anumit buyer
		// se trimite pe network catre buyerul respectiv
		
		this.gui.dropAuction(remoteUser, service);
		this.network.dropAuction(localUser, remoteUser, service);
	}
	
	
	// se apeleaza de gui cand se incheie programul;
	// in network se vor notifica toti ceilalti prin
	// intermediul mediator.userLoggedOut
	public void logOut(String username) {
		this.network.logOut(username);
	}
	
	
	// se apeleaza de network cand un user face logout
	public void userLoggedOut (String username) {
		// notifica userii ca username s-a delogat
		this.gui.userLoggedOut(username);
	}


	public void sellerMadeOffer(String remoteUser, String service) {
		this.gui.sellerMadeOffer(remoteUser, service);
	}


	public void sellerDroppedAuction(String remoteUser, String service) {
		this.gui.sellerDroppedAuction(remoteUser, service);
	}


	public void userActivatedService(String remoteUser, String service) {
		this.gui.userActivatedService(remoteUser, service);
	}


	public void userDeactivatedService(String remoteUser, String service) {
		this.gui.userDeactivatedService(remoteUser, service);
	}


	public void buyerAcceptedOffer(String remoteUser, String service) {
		this.gui.buyerAcceptedOffer(remoteUser, service);
	}


	public void buyerRefusedOffer(String remoteUser, String service) {
		this.gui.buyerRefusedOffer(remoteUser, service);
	}
	
}
