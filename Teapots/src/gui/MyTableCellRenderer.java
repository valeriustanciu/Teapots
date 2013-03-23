package gui;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class MyTableCellRenderer extends JLabel implements TableCellRenderer{

  private static final long serialVersionUID = 5429393433447302504L;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean arg2, boolean arg3, int arg4, int arg5) {
		return (Component) value;

	}

}
