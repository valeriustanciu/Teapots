package gui;

import java.awt.Component;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class MyTableCellRenderer extends JLabel implements TableCellRenderer{

	private static final long serialVersionUID = 5429393433447302504L;

	JTable tab;
	
	public MyTableCellRenderer (JTable tabel) {
		this.tab = tabel;
	}
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean arg2, boolean arg3, int arg4, int arg5) {
		if (value instanceof String && value != null) {
			JComboBox combo = new JComboBox();
			combo.addItem((String)value);
			DefaultCellEditor dce = new DefaultCellEditor(combo);
			
			return combo;
		}
		else if (value == null) {
			return new JComboBox();
		}
		
		else return (Component)value;

	}

}
