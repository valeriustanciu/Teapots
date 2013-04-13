package gui;

import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JTable;

import mediator.IMediatorGui;

public interface IGui {
	public IMediatorGui getMediator ();
	public JTable getAuctionTable();
	public void loginGui();
	public void auctionGui();
	public String getUserName();
	public String getUserType();
	public ArrayList<ServiceUserStatus> getServices ();
	
	// metode pt buyer
	public void populateServiceUserList (String service, ArrayList<String> users);
	public void removeServiceUserList (String service);
	public void acceptOffer (String remoteUser, String service);
	public void refuseOffer (String remoteUser, String service);
	public void sellerMadeOffer (String localUser, String remoteUser, String service);
	public void sellerDroppedAuction (String remoteUser, String service);
	
	// metode pt seller
	public void userActivatedService (String remoteUser, String service);
	public void userDeactivatedService (String remoteUser, String service);
	public void makeOffer (String remoteUser, String service);
	public void dropAuction (String remoteUser, String service);
	public void startTransfer (String remoteUser, String service);
	public void buyerRefusedOffer (String remoteUser, String service);
	public void buyerAcceptedOffer (String remoteUser, String service);
	
	public void userLoggedOut (String username);
}
