package gui;

import java.awt.Component;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class MyTableCellRenderer extends JLabel implements TableCellRenderer{

	private static final long serialVersionUID = 5429393433447302504L;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean arg2, boolean arg3, int arg4, int arg5) {
		try{
			return (Component) value;
		}
		catch(ClassCastException e) {
			String auxString = value.toString();
			String[] result = {auxString};
			
			return new JComboBox(result);
		}
	}

}
