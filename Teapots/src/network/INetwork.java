package network;

public interface INetwork {
	public void logOut (String username);
	public void acceptOffer (String localUser, String remoteUser, String service);
	public void refuseOffer (String localUser, String remoteUser, String service);
	public void makeOffer(String localUser, String remoteUser, String service);
	public void dropAuction(String localUser, String remoteUser, String service);
	public void addService (String user, String service);
	public void removeService (String user, String service);
	
	public void userLoggedOut (String username);
	public void buyerAcceptedOffer (String remoteUser, String service);
	public void buyerRefusedOffer (String remoteUser, String service);
	public void sellerMadeOffer (String remoteUser, String service);
	public void sellerDroppedAuction (String remoteUser, String service);
	public void userActivatedService(String remoteUser, String service);
	public void userDeactivatedService(String remoteUser, String service);
}
