package mediator;

import java.io.IOException;
import java.util.ArrayList;

import log.IUserLog;
import log.UserLog;

import gui.IGui;
import network.INetwork;
import network.Network;
import network.UserInfo;
import web.IWeb;
import web.Web;

public class Mediator implements IMediatorGui, IMediatorNetwork, IMediatorWeb{
	private INetwork network;
	private IGui gui;
	private IWeb web;
	private IUserLog log;
	
	public Mediator() {
		web = new Web(this);
		log = new UserLog(this);
	}
	
	
	public void setGui(IGui gui) {
		this.gui = gui;
	}
	
	public String getUserType (String user) {
		return web.getUserType(user);
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
		
		this.log.writeInfo("Activated service " + service);
		this.gui.populateServiceUserList(service, this.web.getUsersWithService(user, service));
		this.network.addService(user, service);
		
	}
	
	public void removeService (String user, String service) {
		// apelam in cazul Drop offer request
		// buyerul dispare din comboboxurile sellerilor care ofera serviciul
		this.log.writeInfo("Deactivated service " + service);
		this.gui.removeServiceUserList(service);
		this.network.removeService(user, service);
	}
	
	
	// cand utilizatorul curent accepta o oferta, face modificarile in gui si anunta
	// ceilalti useri, prin network
	public void acceptOffer (String localUser, String remoteUser, String service) {
		this.log.writeInfo("Accept offer from " + remoteUser + " for service " + service);
		this.gui.acceptOffer(remoteUser, service);
		this.network.acceptOffer(localUser, remoteUser, service);
	}
	
	public void refuseOffer (String localUser, String remoteUser, String service) {
		this.log.writeInfo("Refuse offer from " + remoteUser + " for service " + service);
		this.gui.refuseOffer(remoteUser, service);
		this.network.refuseOffer(localUser, remoteUser, service);
	}
	
	public void makeOffer(String localUser, String remoteUser, String service, Integer price) {
		// cand un furnizor face o oferta unui anumit buyer
		// se trimite pe network catre buyerul respectiv
		
		this.log.writeInfo("Make offer to " + remoteUser + " for service " + service);
		this.gui.makeOffer(remoteUser, service, price);
		this.network.makeOffer(localUser, remoteUser, service, price);
	}
	
	public void dropAuction(String localUser, String remoteUser, String service) {
		// cand un furnizor renunta la oferta facuta unui anumit buyer
		// se trimite pe network catre buyerul respectiv
		
		int ret_value = this.gui.dropAuction(remoteUser, service);
		
		if (ret_value == -1)
			return;
		
		this.log.writeInfo("Drop auction for " + remoteUser + " for service " + service);
		this.network.dropAuction(localUser, remoteUser, service);
	}
	
	
	// se apeleaza de gui cand se incheie programul;
	// in network se vor notifica toti ceilalti prin
	// intermediul mediator.userLoggedOut
	public void logOut(String username) {
		this.log.writeInfo("Log out");
		this.network.logOut(username);
		this.web.userLoggedOut(username);
	}
	
	
	// se apeleaza de network cand un user face logout
	public void userLoggedOut (String username) {
		// notifica userii ca username s-a delogat
		this.log.writeInfo("User " + username + " logged out");
		this.gui.userLoggedOut(username);
	}


	public void sellerMadeOffer(String localUser, String remoteUser, String service, Integer price) {
		this.log.writeInfo("User " + localUser + " made offer to user " + remoteUser + " for service " + service);
		this.gui.sellerMadeOffer(localUser, remoteUser, service, price);
	}


	public void sellerDroppedAuction(String remoteUser, String service) {
		this.log.writeInfo("Seller " + remoteUser + "dropped auction for service " + service);
		this.gui.sellerDroppedAuction(remoteUser, service);
	}


	public void userActivatedService(String remoteUser, String service) {
		this.log.writeInfo("User " + remoteUser + " activated service " + service);
		this.gui.userActivatedService(remoteUser, service);
	}


	public void userDeactivatedService(String remoteUser, String service) {
		this.log.writeInfo("User " + remoteUser + " deactivated service " + service);
		this.gui.userDeactivatedService(remoteUser, service);
	}


	public void buyerAcceptedOffer(String remoteUser, String service) {
		this.log.writeInfo("User " + remoteUser + " accepted offer for " + service);
		this.gui.buyerAcceptedOffer(remoteUser, service);
	}


	public void buyerRefusedOffer(String remoteUser, String service) {
		this.log.writeInfo("User " + remoteUser + " refused offer for " + service);
		this.gui.buyerRefusedOffer(remoteUser, service);
	}
	
	public void startNetwork() {
		this.network = new Network(this);
	}

	public void startLogging (){
		this.log.setLogInfo();
		this.web.userLoggedIn(this.getCurrentUser());
		this.log.writeInfo("User logged in");
	}

	public String getCurrentUser () {
		return this.gui.getUserName();
	}
	
	public UserInfo getOwnInfoFromServer () {
		return this.web.getOwnInfo(this.getCurrentUser());
	}
	
	public ArrayList<UserInfo> getLoggedUsersFromServer () {
		return this.web.getLoggedUsers(this.getCurrentUser());
	}
	
}
