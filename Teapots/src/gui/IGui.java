package gui;

import java.util.ArrayList;

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
	
	// metode pt seller
//	public void userActivatedService (String remoteUser, String service);
	public void makeOffer (String remoteUser, String service);
	public void dropAuction (String remoteUser, String service);
}
