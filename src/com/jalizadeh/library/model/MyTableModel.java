package com.jalizadeh.library.model;

import javax.swing.table.DefaultTableModel;

public class MyTableModel extends DefaultTableModel{
	private static final long serialVersionUID = 1L;

	public MyTableModel(String[][] data, String[] columns) {
		super(data, columns);
	}
	
	//it will disable Cell Editing feature
	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}
	
}
