package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JTable;

import mediator.IMediatorGui;

public class CustomActionListener implements ActionListener {
	JTable table;
	IMediatorGui mediator;
	IGui gui;
	
	public CustomActionListener (IGui gui) {
		this.mediator = gui.getMediator();
		this.table = gui.getAuctionTable();
		this.gui = gui;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// e.getActionCommand => menuitem-ul ales
		System.out.println("Popup menu item ["
	            + e.getActionCommand() + "] was pressed.");
		
		if (e.getActionCommand().equals("Launch offer request")) {
			// apelam metoda din mediator si set status to active
			MyDefaultTableModel model = (MyDefaultTableModel) table.getModel();
			String service = model.getValueAt(table.getSelectedRow(), 0).toString();
			mediator.addService(gui.getUserName(), service);
			
			return;
		}
		
		if (e.getActionCommand().equals("Drop offer request")) {
			//remove service in mediator si reset combobox / status
			MyDefaultTableModel model = (MyDefaultTableModel) table.getModel();
			String service = model.getValueAt(table.getSelectedRow(), 0).toString();
			mediator.removeService(gui.getUserName(), service);
			
			return;
		}
		
		if (e.getActionCommand().equals("Accept offer")) {
			
			MyDefaultTableModel model = (MyDefaultTableModel) table.getModel();
			String service = model.getValueAt(table.getSelectedRow(), 0).toString();
			String remoteUser = model.getValueAt(table.getSelectedRow(), 2).toString();
			mediator.acceptOffer(gui.getUserName(), remoteUser, service);	
			
			return;
		}
		
		if (e.getActionCommand().equals("Refuse offer")) {
			
			MyDefaultTableModel model = (MyDefaultTableModel) table.getModel();
			String service = model.getValueAt(table.getSelectedRow(), 0).toString();
			String remoteUser = model.getValueAt(table.getSelectedRow(), 2).toString();
			mediator.refuseOffer(gui.getUserName(), remoteUser, service);
			
			return;
		}
		
		if (e.getActionCommand().equals("Make offer")) {
			// transforma statusul in offer made pentru cumparatorul din combobox
			// apeleaza metoda makeOffer din mediator
			
			MyDefaultTableModel model = (MyDefaultTableModel) table.getModel();
			String service = model.getValueAt(table.getSelectedRow(), 0).toString();
			String remoteUser = model.getValueAt(table.getSelectedRow(), 2).toString();
			mediator.makeOffer(gui.getUserName(), remoteUser, service);
			
			return;
		}
		
		if (e.getActionCommand().equals("Drop auction")) {
			System.out.println(table.getSelectedColumn());
			
			MyDefaultTableModel model = (MyDefaultTableModel) table.getModel();
			String service = model.getValueAt(table.getSelectedRow(), 0).toString();
			String remoteUser = model.getValueAt(table.getSelectedRow(), 2).toString();
			mediator.dropAuction(gui.getUserName(), remoteUser, service);
			return;
		}
	}
	

}
