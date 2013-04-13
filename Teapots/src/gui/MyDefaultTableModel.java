package gui;

import javax.swing.JComboBox;
import javax.swing.JProgressBar;
import javax.swing.table.DefaultTableModel;

public class MyDefaultTableModel extends DefaultTableModel {
	private static final long serialVersionUID = 1L;

	public boolean isCellEditable (int row, int column) {
		if (column < 2)
			return false;
		return true;
	}
}
