package com.jalizadeh.library.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.jalizadeh.library.model.MyTableModel;

public class BrowseLibraryPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	
	private Box mainBox,
				hBox1, hBox2, hBox3, hBox4, hBox5;
	private JLabel jlBookTable, jlFileTable;
	private JScrollPane spBookTable, spFileTable;
	private JButton jbOpenBook, jbDeleteBook, jbOpenFile, jbDeleteFile, 
					jbSave, jbSaveAndQuit;
	private JTable tBooks, tFiles;
	private MyTableModel model;
	
	
	String[] bookColumns = {"Title", "Author", "Price", "ISBN"};
	String[] fileColumns = {"Sound", "Image", "Video"};
	
	String[][] bookData = {{" ", " ", " ", " "}};
	String[][] fileData = {{" ", " ", " ", " "}};
	
	
	public BrowseLibraryPanel() {
		super(new FlowLayout());
		initWidgets();
		addWidgets();
		setBackground(new Color(194, 230, 248));
	}


	private void initWidgets() {
		mainBox = Box.createVerticalBox();
		
		hBox1 = Box.createHorizontalBox();
		hBox2 = Box.createHorizontalBox();
		hBox3 = Box.createHorizontalBox();
		hBox4 = Box.createHorizontalBox();
		hBox5 = Box.createHorizontalBox();
		
		jlBookTable = new JLabel("Showing All Books In Library");
		jlFileTable = new JLabel("Showing All Files In Book");
		
		jbOpenBook = new JButton("Open");
		jbDeleteBook = new JButton("Delete");
		jbOpenFile = new JButton("Open");
		jbDeleteFile = new JButton("Delete");
		jbSave = new JButton("Save");
		jbSaveAndQuit = new JButton("Save & Quit");
		
		//Book Table
		model = new MyTableModel(bookData, bookColumns);
		tBooks = new JTable(model);
		tBooks.setPreferredScrollableViewportSize(new Dimension(328, 120)); 
		tBooks.setFillsViewportHeight(true); //wrap the whole space given to table
		tBooks.getColumnModel().getColumn(0).setPreferredWidth(200); //change the size of Title
		tBooks.getColumnModel().getColumn(1).setPreferredWidth(150); //change the size of Author
		tBooks.setAutoCreateRowSorter(true); //enable click and sort  by item
		tBooks.getTableHeader().setReorderingAllowed(false); //disable dragable header
		
		//VIM File Table
		model = new MyTableModel(fileData, fileColumns);
		tFiles = new JTable(model);
		tFiles.setPreferredScrollableViewportSize(new Dimension(328, 120)); 
		tFiles.setFillsViewportHeight(true); //wrap the whole space given to table
		tFiles.setAutoCreateRowSorter(true); //enable click and sort  by item
		tFiles.getTableHeader().setReorderingAllowed(false); //disable dragable header
		
		spBookTable = new JScrollPane(tBooks);
		spFileTable = new JScrollPane(tFiles);
		
		spBookTable.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		spBookTable.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		spFileTable.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		spFileTable.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
	}


	private void addWidgets() {
		hBox1.add(jlBookTable);
		hBox1.add(Box.createHorizontalStrut(50));
		hBox1.add(jbDeleteBook);
		hBox1.add(jbOpenBook);
		
		hBox2.add(spBookTable);
		
		hBox3.add(jlFileTable);
		hBox3.add(Box.createHorizontalStrut(70));
		hBox3.add(jbDeleteFile);
		hBox3.add(jbOpenFile);
		
		hBox4.add(spFileTable);
		
		hBox5.add(Box.createHorizontalStrut(185));
		hBox5.add(jbSave);
		hBox5.add(jbSaveAndQuit);
		
		mainBox.add(hBox1);
		mainBox.add(Box.createVerticalStrut(5));
		mainBox.add(hBox2);
		mainBox.add(Box.createVerticalStrut(5));
		mainBox.add(hBox3);
		mainBox.add(Box.createVerticalStrut(5));
		mainBox.add(hBox4);
		mainBox.add(Box.createVerticalStrut(5));
		mainBox.add(hBox5);
		
		add(mainBox);
	}


	public void addActionListener(ActionListener a) {
		jbOpenBook.addActionListener(a);
		jbDeleteBook.addActionListener(a);
		jbOpenFile.addActionListener(a);
		jbDeleteFile.addActionListener(a);
		jbSave.addActionListener(a);
		jbSaveAndQuit.addActionListener(a);
	}
	
	
	public JButton getButtonOpenBook() {
		return jbOpenBook;
	}
	
	public JButton getButtonDeleteBook() {
		return jbDeleteBook;
	}
	
	public JButton getButtonOpenFile() {
		return jbOpenFile;
	}
	
	public JButton getButtonDeleteFile() {
		return jbDeleteFile;
	}
	
	public JButton getButtonSave() {
		return jbSave;
	}
	
	public JButton getButtonSaveAndQuit() {
		return jbSaveAndQuit;
	}


	public JTable getBookTable() {
		return tBooks;
	}
	
	public JTable getFileTable() {
		return tFiles;
	}
	
}
