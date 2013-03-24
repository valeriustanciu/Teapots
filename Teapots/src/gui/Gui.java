package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.table.TableColumn;

import mediator.IMediatorGui;
import mediator.Mediator;

public class Gui extends JPanel implements IGui{
	private static final long serialVersionUID = -8012095796913881980L;

	private IMediatorGui mediator;
	private JPanel loginScreen;

	private MyDefaultTableModel model;
	private JPanel auctionScreen;
	private JTable auctionTable;
	
	private JTextField user = new JTextField();
	private JPasswordField pass = new JPasswordField();
	private String userName;
	private String userType;
	private ArrayList<String> userServices;
	private JButton logoutButton;
	private ArrayList<ServiceUserStatus> services;
	
	public Gui() {
		mediator = new Mediator();
		mediator.setGui(this);
		this.services = new ArrayList<ServiceUserStatus>();
		this.loginGui();
	}
	
	public IMediatorGui getMediator () {
		return this.mediator;
	}
	
	public JTable getAuctionTable() {
		return this.auctionTable;
	}
	
	public String getUserName () {
		return this.userName;
	}
	
	public String getUserType () {
		return this.userType;
	}
	
	public ArrayList<ServiceUserStatus> getServices () {
		return this.services;
	}
	
	public void loginGui() {
		loginScreen = new JPanel(new GridLayout(3, 2));
		this.setLayout(new BorderLayout());
		this.add(loginScreen, BorderLayout.CENTER);
		
		loginScreen.add(new JLabel("Username: ", JLabel.RIGHT));
		loginScreen.add(user);
		
		loginScreen.add(new JLabel("Password: ", JLabel.RIGHT));
		loginScreen.add(pass);
		
		loginScreen.add(new JLabel(""));
		JButton loginButton = new JButton("Login");
		loginScreen.add(loginButton);
		
		loginButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String usernameText = user.getText();
				String passwordText = new String(pass.getPassword());
				if (usernameText.isEmpty()) {
					JOptionPane.showMessageDialog(
							null, "Name is empty!", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				if (passwordText.isEmpty()) {
					JOptionPane.showMessageDialog(
							null, "Password is empty!", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				System.out.println(usernameText + " " + passwordText);
				userType = mediator.getUserType(usernameText, passwordText);
				if (userType != null) {
					userName = usernameText;
					auctionGui();
				}
				else {
					JOptionPane.showMessageDialog(
							null, "Login error!", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
		});
	}
	
	
	public void auctionGui() {
		this.remove(loginScreen);
		JPanel auctionScreen = new JPanel();
		model = new MyDefaultTableModel();
		this.setLayout(new BorderLayout());
		
		JPanel topPanel = new JPanel(new GridLayout(1, 2));
		logoutButton = new JButton("Logout");
		JLabel userNameAndType = new JLabel(userName + ", " + userType, JLabel.CENTER);
		topPanel.add(userNameAndType);
		topPanel.add(logoutButton);
		logoutButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO: verificare stare servicii!!!
				
				System.exit(0);
			}
		});
		
		String[] columnNames = new String[]{"Service", "Status", "Users", "Progress"};
		for(int i = 0; i < columnNames.length; i++) {
			model.addColumn(columnNames[i]);
		}
		
		userServices = mediator.getUserServices(userName);
		
		for(int i = 0; i < userServices.size(); i++) {
			Object[] rowParams = {userServices.get(i), "inactive", new JComboBox(), new JProgressBar()};
			model.addRow(rowParams);
		}
		
		auctionTable = new JTable(model);
		auctionTable.setCellSelectionEnabled(true);

		
		TableColumn col = auctionTable.getColumnModel().getColumn(2);
		MyTableCellRenderer customRenderer = new MyTableCellRenderer();
		col.setCellRenderer(customRenderer);
				
		col = auctionTable.getColumnModel().getColumn(3);
		col.setCellRenderer(customRenderer);
		
		//TEST TEST TEST
		if(this.userType.equals("seller")) {
			ArrayList<String> aux = new ArrayList<String>();
			aux.add("CUCU");
			aux.add("CECE");
			this.populateServiceUserList("imprimare", aux);
		}
		//TEST TEST TEST
		
		final Gui currentGui = this;
		auctionTable.addMouseListener(new MouseAdapter () {
			public void mouseClicked(MouseEvent e) {
				if (SwingUtilities.isRightMouseButton(e)) {
					// select the cell before expanding popup menu
					Point p = e.getPoint();
					final int rowNumber = auctionTable.rowAtPoint(p);
					final int columnNumber = auctionTable.columnAtPoint(p);
					ListSelectionModel lmodel = auctionTable.getSelectionModel();

					//services column for buyers
					if (columnNumber == 0 && userType.equals("buyer")) {
			    		JPopupMenu jpop = new JPopupMenu();
			    		JMenuItem it1 = new JMenuItem("Launch offer request");
			    		JMenuItem it2 = new JMenuItem("Drop offer request");
			    		it1.addActionListener(new CustomActionListener(currentGui));
			    		it2.addActionListener(new CustomActionListener(currentGui));
			    		jpop.add(it1);
			    		jpop.add(it2);
			    		add(jpop);
			    		jpop.show(e.getComponent(), e.getX(), e.getY());
						lmodel.setSelectionInterval(rowNumber, rowNumber);
					}
					
					//users column for buyers
					if (columnNumber == 2 && userType.equals("buyer")) {
			    		JPopupMenu jpop = new JPopupMenu();
			    		JMenuItem it1 = new JMenuItem("Accept offer");
			    		JMenuItem it2 = new JMenuItem("Refuse offer");
			    		it1.addActionListener(new CustomActionListener(currentGui));
			    		it2.addActionListener(new CustomActionListener(currentGui));
			    		jpop.add(it1);
			    		jpop.add(it2);
			    		add(jpop);
			    		jpop.show(e.getComponent(), e.getX(), e.getY());
						lmodel.setSelectionInterval(rowNumber, rowNumber);						
					}
					
					//users column for sellers
					if (columnNumber == 2 && userType.equals("seller")) {
			    		JPopupMenu jpop = new JPopupMenu();
			    		JMenuItem it1 = new JMenuItem("Make offer");
			    		JMenuItem it2 = new JMenuItem("Drop auction");
			    		it1.addActionListener(new CustomActionListener(currentGui));
			    		it2.addActionListener(new CustomActionListener(currentGui));
			    		jpop.add(it1);
			    		jpop.add(it2);
			    		add(jpop);
			    		jpop.show(e.getComponent(), e.getX(), e.getY());
						lmodel.setSelectionInterval(rowNumber, rowNumber);
						
					}
				}
			}
		});
		auctionScreen.add(new JScrollPane(auctionTable));
		this.add(auctionScreen, BorderLayout.CENTER);
		this.add(topPanel, BorderLayout.NORTH);
		this.revalidate();
	}
	
	
	public static void buildGUI() {
		JFrame frame = new JFrame("Teapots"); // title
		frame.setContentPane(new Gui()); // content: the JPanel above
		frame.setSize(600, 300); // width / height
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // exit application when window is closed
		frame.setVisible(true); // show it!
	}
	
	
	public static void main(String[] args) {
		// run on EDT (event-dispatching thread), not on main thread!
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				buildGUI();
			}
		});
	}
	
	
	
	
	
	
	// metode pe care le apeleaza mediatorul::
	
	// cand un buyer activeaza un serviciu, primeste de la server o lista
	// cu sellerii ce furnizeaza serviciul respectiv
	public void populateServiceUserList (String service, ArrayList<String> users) {
		int row = -1;
		for(int i = 0; i < auctionTable.getRowCount(); i++) {
			if (auctionTable.getValueAt(i, 0).equals(service)) {
				row = i;
				break;
			}
		}
		
		if (row == -1)
			return;
		model.setValueAt("active", row, 1);
		ServiceUserStatus newService = new ServiceUserStatus(service);
		for(int i = 0; i < users.size(); i++) {
			newService.addUser(users.get(i), new String("No offer"));
		}
		
		this.services.add(newService);
		
		String[] auxUsers = new String[users.size()];
		for (int i = 0; i < users.size(); i++) {
			auxUsers[i] = users.get(i);
		}
		final JComboBox comboBox = new JComboBox(auxUsers);
		comboBox.setSelectedItem(null);
		this.auctionTable.getColumn("Users").setCellEditor(new DefaultCellEditor(comboBox));

		final String currentService = service;
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("SERVICE = " + currentService + " " + services.size());
				int row = -1;
				for (int i = 0; i < model.getRowCount(); i++) {
					if (model.getValueAt(i, 0).equals(currentService)) {
						row = i;
						break;
					}
				}
				if (row != -1) {
					for (int i = 0; i < services.size(); i++) {
						if (services.get(i).getService().equals(currentService)) {
							model.setValueAt(services.get(i).getStatus((String)comboBox.getSelectedItem()), row, 1);
							auctionTable.setModel(model);
							break;
						}
					}
				}
			}
			
		});
		
		row = -1;

		for (int i = 0; i < model.getRowCount(); i++) {
			if (model.getValueAt(i, 0).equals(service)) {
				row = i;
				break;
			}
		}

		if (row != -1)
			this.model.setValueAt(comboBox, row, 2);
		
		auctionTable.setModel(this.model);
//		comboBox.
	}
	
	public void removeServiceUserList (String service) {
		int index = -1;
		for (int i = 0; i < services.size(); i++) {
			if (services.get(i).getService().equals(service)) {
				index = i;
			}
		}
		for (int i = 0; i < model.getRowCount(); i++) {
			if (model.getValueAt(i, 0).equals(service)) {
				if (index != -1) {
					services.remove(index);
				}
				JComboBox comboBox = new JComboBox();
				model.setValueAt(comboBox, i, 2);
				model.setValueAt("inactive", i, 1);
				auctionTable.setModel(model);
				auctionTable.getColumn("Users").setCellEditor(new DefaultCellEditor(comboBox));
			}
		}
	}
	
	public void makeOffer (String remoteUser, String service) {
		// modificam statusul lui remoteuser pt service in services
		int index = -1;
		for (int i = 0; i < services.size(); i++) {
			if (services.get(i).getService().equals(service)) {
				index = i;
			}
		}
		
		if (index != -1){
			services.get(index).changeStatus(remoteUser, new String("Offer made"));
			model.setValueAt(services.get(index).getStatus(remoteUser), index, 1);
			this.auctionTable.setModel(model);
		}
		
	}
	
	public void dropAuction (String remoteUser, String service) {
		int index = -1;
		for (int i = 0; i < services.size(); i++) {
			if (services.get(i).getService().equals(service)) {
				index = i;
			}
		}
		
		if (index != -1) {
			String currentStatus = model.getValueAt(index,  1).toString();
			
			if (currentStatus.equals("Offer exceeded")) {
				services.get(index).changeStatus(remoteUser, new String("No offer"));
				model.setValueAt(services.get(index).getStatus(remoteUser), index, 1);
				this.auctionTable.setModel(model);
			}
			else {
				JOptionPane.showMessageDialog(
						null, "Cannot drop auction!", "Error", JOptionPane.ERROR_MESSAGE);
			}
			
		}
	}
	
	public void acceptOffer (String remoteUser, String service) {
		int index = -1;
		for (int i = 0; i < services.size(); i++) {
			if (services.get(i).getService().equals(service)) {
				index = i;
			}
		}
		
		if (index != -1){
			String currentStatus = model.getValueAt(index,  1).toString();
			
			if (currentStatus.equals("Offer made")) {
				services.get(index).changeStatus(remoteUser, new String("Offer accepted"));
				model.setValueAt(services.get(index).getStatus(remoteUser), index, 1);
				this.auctionTable.setModel(model);
			}
			else {
				JOptionPane.showMessageDialog(
						null, "No offer made!", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	public void refuseOffer (String remoteUser, String service) {
		int index = -1;
		for (int i = 0; i < services.size(); i++) {
			if (services.get(i).getService().equals(service)) {
				index = i;
			}
		}
		
		if (index != -1){
			String currentStatus = model.getValueAt(index,  1).toString();
			
			if (currentStatus.equals("Offer made")) {
				services.get(index).changeStatus(remoteUser, new String("Offer refused"));
				model.setValueAt(services.get(index).getStatus(remoteUser), index, 1);
				this.auctionTable.setModel(model);
			}
			else {
				JOptionPane.showMessageDialog(
						null, "No offer made!", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
