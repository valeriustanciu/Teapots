package mediator;

import java.util.ArrayList;

import network.UserInfo;

public interface IMediatorNetwork {
	
	public void userLoggedOut (String username);
	public void sellerMadeOffer (String localUser, String remoteUser, String service);
	public void sellerDroppedAuction (String remoteUser, String service);
	public void userActivatedService(String remoteUser, String service);
	public void userDeactivatedService(String remoteUser, String service);
	public void buyerAcceptedOffer (String remoteUser, String service);
	public void buyerRefusedOffer (String remoteUser, String service);
	
	public String getUserType (String user);
	public String getCurrentUser ();
	public UserInfo getOwnInfoFromServer ();
	public ArrayList<UserInfo> getLoggedUsersFromServer();
}
