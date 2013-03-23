package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import mediator.IMediatorGui;
import mediator.Mediator;

public class Gui extends JPanel implements IGui{
	private IMediatorGui mediator;
	private JPanel loginScreen;
	private JPanel auctionScreen;
	private JTextField user = new JTextField();
	private JPasswordField pass = new JPasswordField();
	private DefaultTableModel model;
	private String userName;
	private String userType;
	private ArrayList<String> userServices;
	private JTable auctionTable;
	private JButton logoutButton;
	
	public Gui() {
		mediator = new Mediator();
		mediator.setGui(this);
		this.loginGui();
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
		model = new DefaultTableModel();
		this.setLayout(new BorderLayout());
		
		JPanel topPanel = new JPanel(new GridLayout(1, 2));
		logoutButton = new JButton("Logout");
		JLabel userNameAndType = new JLabel(userName + ", " + userType, JLabel.CENTER);
		topPanel.add(userNameAndType);
		topPanel.add(logoutButton);
		
		String[] columnNames = new String[]{"Service", "Status", "Users", "Progress"};
		for(int i = 0; i < columnNames.length; i++) {
			model.addColumn(columnNames[i]);
		}
		
		userServices = mediator.getUserServices(userName);
		
		for(int i = 0; i < userServices.size(); i++) {
			Object[] rowParams = {userServices.get(i), "inactive", null, null};
			model.addRow(rowParams);
		}
		
		auctionTable = new JTable(model);
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
}
