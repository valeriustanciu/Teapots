package network;

public interface INetwork {
	public void logOut (String username);
	public void acceptOffer (String localUser, String remoteUser, String service);
	public void refuseOffer (String localUser, String remoteUser, String service);
	public void makeOffer(String localUser, String remoteUser, String service);
	public void dropAuction(String localUser, String remoteUser, String service);
	public void addService (String user, String service);
	public void removeService (String user, String service);
}
