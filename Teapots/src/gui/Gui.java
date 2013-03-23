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
	private IMediatorGui mediator;
	private JPanel loginScreen;
	private JPanel auctionScreen;
	private JTextField user = new JTextField();
	private JPasswordField pass = new JPasswordField();
	private MyDefaultTableModel model;
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
			    		it1.addActionListener(new CustomActionListener(auctionTable));
			    		it2.addActionListener(new CustomActionListener(auctionTable));
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
			    		it1.addActionListener(new CustomActionListener(auctionTable));
			    		it2.addActionListener(new CustomActionListener(auctionTable));
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
			    		it1.addActionListener(new CustomActionListener(auctionTable));
			    		it2.addActionListener(new CustomActionListener(auctionTable));
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
}
