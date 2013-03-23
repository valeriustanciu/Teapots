package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTable;

public class CustomActionListener implements ActionListener {
  JTable tabel;
	
	public CustomActionListener (JTable tab) {
		tabel = tab;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// e.getActionCommand => menuitem-ul ales
		System.out.println("Popup menu item ["
	            + e.getActionCommand() + "] was pressed.");
		
		if (e.getActionCommand().equals("Launch offer request")) {
			// apelam metoda din mediator
			MyDefaultTableModel model = (MyDefaultTableModel) tabel.getModel();
			model.setValueAt("active", tabel.getSelectedRow(), tabel.getSelectedColumn());
			return;
		}
		
		if (e.getActionCommand().equals("Drop offer request")) {
			return;
		}
		
		if (e.getActionCommand().equals("Accept offer")) {
			return;
		}
		
		if (e.getActionCommand().equals("Refuse offer")) {
			return;
		}
		
		if (e.getActionCommand().equals("Make offer")) {
			return;
		}
		
		if (e.getActionCommand().equals("Drop auction")) {
			System.out.println(tabel.getSelectedColumn());
			return;
		}
	}
	

}
