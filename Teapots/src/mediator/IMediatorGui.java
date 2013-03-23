package mediator;

import gui.IGui;

public interface IMediatorGui {
	public void setGui(IGui gui);

	public String getUserType(String username, String password);
}
