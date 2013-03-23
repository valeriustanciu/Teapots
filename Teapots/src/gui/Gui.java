package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import mediator.IMediatorGui;
import mediator.Mediator;

public class Gui extends JPanel implements IGui{
	private IMediatorGui mediator;
	private JTextField user = new JTextField();
	private JPasswordField pass = new JPasswordField();
	
	
	public Gui() {
		mediator = new Mediator();
		mediator.setGui(this);
		this.loginGui();
	}
	
	
	public void loginGui() {
		JPanel loginScreen = new JPanel(new GridLayout(3, 2));
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
				// TODO 2: call the method for obtaining the text field's content
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
				// TODO: verifica daca user & pass valide
				
				System.out.println(usernameText + " " + passwordText);
				String userType = mediator.getUserType(usernameText, passwordText);
				if (userType != null) {
					System.out.println(userType);
					// schimbam view
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
