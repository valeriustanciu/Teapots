package network;

import java.util.ArrayList;

import mediator.IMediatorNetwork;

public class Network implements INetwork {
	private IMediatorNetwork mediator;
	private NetworkInfo users;
	private ReadThread readThread;
	
	public Network(IMediatorNetwork med) {
		this.mediator = med;
		
		ArrayList<UserInfo> usersInfo = med.getLoggedUsersFromServer();
		this.users = new NetworkInfo();
		
		for (int i = 0; i < usersInfo.size(); i++) {
			this.users.addUser(usersInfo.get(i).getUsername(), 
							   usersInfo.get(i).getIp(),
							   usersInfo.get(i).getPort());
		}
		
		
		readThread = new ReadThread(mediator.getOwnInfoFromServer().getIp(),
									mediator.getOwnInfoFromServer().getPort(),
									this);
		readThread.start();
	}
	
	
	//TODO
	public void logOut (String username) {
		//cand un user face logout, se anunta toti ceilalti useri;
		//se apeleaza metoda "userLoggedOut" din mediator
		ArrayList<String> args = new ArrayList<String>();
		
		Message msg = new Message(username, null, "logOut", args);
		ArrayList<UserInfo> usersInfo = users.getLoggedUsers();
		for (int i = 0; i < usersInfo.size(); i++) {
			//de verificat user type!!:-???
			WriteThread wt = new WriteThread(usersInfo.get(i).getIp(), usersInfo.get(i).getPort(),
					msg);
			wt.start();
		}
	}
	
	
	//TODO
	public void acceptOffer (String localUser, String remoteUser, String service) {
		//cand un buyer accepta o oferta, el notifica sellerul;
		//acesta va primi "buyerAcceptedOffer" in mediator
		ArrayList<String> args = new ArrayList<String>();
		args.add(service);
		
		Message msg = new Message(localUser, remoteUser, "acceptOffer", args);
		
		WriteThread wt = new WriteThread(users.getUserIP(remoteUser), users.getUserPort(remoteUser),
				msg);
		wt.start();
	}
	
	
	//TODO
	public void refuseOffer (String localUser, String remoteUser, String service) {
		//cand un buyer refuza o oferta, el notifica sellerul;
		//acesta va primi "buyerRefusedOffer" in mediator
		ArrayList<String> args = new ArrayList<String>();
		args.add(service);
		
		Message msg = new Message(localUser, remoteUser, "refuseOffer", args);
		
		WriteThread wt = new WriteThread(users.getUserIP(remoteUser), users.getUserPort(remoteUser),
				msg);
		wt.start();
	}
	
	
	//TODO
	public void makeOffer(String localUser, String remoteUser, String service) {
		//cand un seller face o oferta, el notifica buyerul;
		//acesta va primi "sellerMadeOffer" in mediator
		ArrayList<String> args = new ArrayList<String>();
		args.add(service);
		
		Message msg = new Message(localUser, remoteUser, "makeOffer", args);
		
		WriteThread wt = new WriteThread(users.getUserIP(remoteUser), users.getUserPort(remoteUser),
				msg);
		wt.start();
	}
	
	
	//TODO
	public void dropAuction(String localUser, String remoteUser, String service) {
		//cand un seller renunta la o licitatie, el notifica buyerul;
		//acesta va primi "sellerDroppedAuction" in mediator
		ArrayList<String> args = new ArrayList<String>();
		args.add(service);
		
		Message msg = new Message(localUser, remoteUser, "dropAuction", args);
		
		WriteThread wt = new WriteThread(users.getUserIP(remoteUser), users.getUserPort(remoteUser),
				msg);
		wt.start();
	}
	
	
	//TODO
	public void addService (String user, String service) {
		//cand un buyer activeaza un serviciu, sunt notificati toti selerii activi;
		//se apeleaza metoda "userActivatedService" din mediator
		ArrayList<String> args = new ArrayList<String>();
		args.add(service);
		
		Message msg = new Message(user, null, "addService", args);
		ArrayList<UserInfo> usersInfo = users.getLoggedUsers();
		for (int i = 0; i < usersInfo.size(); i++) {
			//de verificat user type!!:-???
			if (this.mediator.getUserType(usersInfo.get(i).getUsername()).equals("buyer"))
				continue;
			WriteThread wt = new WriteThread(usersInfo.get(i).getIp(), usersInfo.get(i).getPort(),
					msg);
			wt.start();
		}
	}
	
	
	//TODO
	public void removeService (String user, String service) {
		//cand un buyer dezactiveaza un serviciu, sunt notificati toti selerii activi;
		//se apeleaza metoda "userDeactivatedService" din mediator
		ArrayList<String> args = new ArrayList<String>();
		args.add(service);
		
		Message msg = new Message(user, null, "removeService", args);
		ArrayList<UserInfo> usersInfo = users.getLoggedUsers();
		for (int i = 0; i < usersInfo.size(); i++) {
			//de verificat user type!!:-???
			WriteThread wt = new WriteThread(usersInfo.get(i).getIp(), usersInfo.get(i).getPort(),
					msg);
			wt.start();
		}
	}


	@Override
	public void userLoggedOut(String username) {
		// TODO Auto-generated method stub
		users.removeUser(username);
		mediator.userLoggedOut(username);
	}


	@Override
	public void buyerAcceptedOffer(String remoteUser, String service) {
		// TODO Auto-generated method stub
		mediator.buyerAcceptedOffer(remoteUser, service);
	}


	@Override
	public void buyerRefusedOffer(String remoteUser, String service) {
		// TODO Auto-generated method stub
		mediator.buyerRefusedOffer(remoteUser, service);
	}


	@Override
	public void sellerMadeOffer(String remoteUser, String service) {
		// TODO Auto-generated method stub
		mediator.sellerMadeOffer(remoteUser, service);
	}


	@Override
	public void sellerDroppedAuction(String remoteUser, String service) {
		// TODO Auto-generated method stub
		mediator.sellerDroppedAuction(remoteUser, service);
	}


	@Override
	public void userActivatedService(String remoteUser, String service) {
		// TODO Auto-generated method stub
		mediator.userActivatedService(remoteUser, service);
	}


	@Override
	public void userDeactivatedService(String remoteUser, String service) {
		// TODO Auto-generated method stub
		mediator.userDeactivatedService(remoteUser, service);
	}
	
}
