package network;

import mediator.IMediatorNetwork;

public class MockNetwork extends Thread implements INetwork {

	private IMediatorNetwork mediator;
	
	
	public MockNetwork(IMediatorNetwork med) {
		this.mediator = med;
		new Thread() {
			@Override
			public void run() {
				
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				mediator.sellerMadeOffer("Gigi", "scaun");
				
				try {
					Thread.sleep(20000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				mediator.userLoggedOut("Gigi");
				
			}
		}.start();
	}
	
	@Override
	public void logOut(String username) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void acceptOffer(String localUser, String remoteUser, String service) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void refuseOffer(String localUser, String remoteUser, String service) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void makeOffer(String localUser, String remoteUser, String service) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dropAuction(String localUser, String remoteUser, String service) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addService(String user, String service) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeService(String user, String service) {
		// TODO Auto-generated method stub
		
	}

}
