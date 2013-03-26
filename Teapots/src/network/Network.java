package network;

import mediator.IMediatorNetwork;

public class Network implements INetwork{
	private IMediatorNetwork mediator;
	
	
	public Network(IMediatorNetwork med) {
		this.mediator = med;
	}
	
	
	//TODO
	public void logOut (String username) {
		//cand un user face logout, se anunta toti ceilalti useri;
		//se apeleaza metoda "userLoggedOut" din mediator
		return;
	}
	
	
	//TODO
	public void acceptOffer (String localUser, String remoteUser, String service) {
		//cand un buyer accepta o oferta, el notifica sellerul;
		//acesta va primi "buyerAcceptedOffer" in mediator
		return;
	}
	
	
	//TODO
	public void refuseOffer (String localUser, String remoteUser, String service) {
		//cand un buyer refuza o oferta, el notifica sellerul;
		//acesta va primi "buyerRefusedOffer" in mediator
		return;
	}
	
	
	//TODO
	public void makeOffer(String localUser, String remoteUser, String service) {
		//cand un seller face o oferta, el notifica buyerul;
		//acesta va primi "sellerMadeOffer" in mediator
		return;
	}
	
	
	//TODO
	public void dropAuction(String localUser, String remoteUser, String service) {
		//cand un seller renunta la o licitatie, el notifica buyerul;
		//acesta va primi "sellerDroppedAuction" in mediator
		return;
	}
	
	
	//TODO
	public void addService (String user, String service) {
		//cand un buyer activeaza un serviciu, sunt notificati toti selerii activi;
		//se apeleaza metoda "userActivatedService" din mediator
		return;
	}
	
	
	//TODO
	public void removeService (String user, String service) {
		//cand un buyer dezactiveaza un serviciu, sunt notificati toti selerii activi;
		//se apeleaza metoda "userDeactivatedService" din mediator
		return;
	}
	
}
