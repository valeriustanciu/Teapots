package mediator;

import java.util.ArrayList;

import gui.IGui;

public interface IMediatorGui {
	public void setGui(IGui gui);

	public String getUserType(String username, String password);
	public ArrayList<String> getUserServices (String user);
	
	public void changeState (String newState);
	
	public void addService (String user, String service);
	public void removeService (String user, String service);
	public void makeOffer (String localUser, String remoteUser, String service);
	public void dropAuction (String localUser, String remoteUser, String service);
	public void acceptOffer (String localUser, String remoteUser, String service);
	public void refuseOffer (String localUser, String remoteUser, String service);
	public void userLoggedOut (String username);
}
