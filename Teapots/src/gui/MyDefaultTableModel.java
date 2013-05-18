package gui;

import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JProgressBar;
import javax.swing.table.DefaultTableModel;

public class MyDefaultTableModel extends DefaultTableModel {
	private static final long serialVersionUID = 1L;

	public boolean isCellEditable (int row, int column) {
		if (column < 2 || column == 3)
			return false;
		return true;
	}
	
	public Class<?> getColumnClass(int c)  
    {  
		if (c < 2) return String.class;
		if (c == 2) return JComboBox.class;
        if (c == 3) return Integer.class;  
        return JProgressBar.class;
    }  
	
	@Override
	public void setValueAt (Object aValue, int row, int column) {
		if (column == 2) {
			Vector rowVector = (Vector)dataVector.elementAt(row);
			if (aValue instanceof String) {
				JComboBox cb = (JComboBox)rowVector.get(column);
				cb.setSelectedItem(aValue);
				rowVector.setElementAt(cb, column);
			}
			else
				rowVector.setElementAt((JComboBox)aValue, column);
			
	        fireTableCellUpdated(row, column);
		}
		else super.setValueAt(aValue, row, column);
	}
}
