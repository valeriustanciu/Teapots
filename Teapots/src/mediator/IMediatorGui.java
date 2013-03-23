package mediator;

import java.util.ArrayList;

import gui.IGui;

public interface IMediatorGui {
	public void setGui(IGui gui);

	public String getUserType(String username, String password);
	public ArrayList<String> getUserServices (String user);
}
