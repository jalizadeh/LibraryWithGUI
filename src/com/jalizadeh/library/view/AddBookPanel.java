package com.jalizadeh.library.view;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.jalizadeh.library.controller.LibrarySystem;

public class AddBookPanel extends JPanel {
	private static final long serialVersionUID = 1594247774468435084L;
	
	private Box mainBox,
				hBox1, hBox2, hBox3, hBox4, 
				hBox5, hBox6, hBox7, hBox8;
	
	private JLabel jlISBN, jlTitle, jlAuthor, jlPrice, jlFile, jlLogDog;
	private JTextField jtISBN, jtTitle, jtAuthor, jtPrice, jtFile;
	private JButton jbBrowse, jbAddFile, jbAddBook, jbSave, jbSaveAndQuit, jbListAllBooks;
	private JTextArea jtaLog;
	private JScrollPane scrollPane;
	
	public AddBookPanel() {
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
		hBox6 = Box.createHorizontalBox();
		hBox7 = Box.createHorizontalBox();
		hBox8 = Box.createHorizontalBox();

		jlISBN 		= new JLabel("Enter ISBN        ");
		jlTitle 	= new JLabel("Enter Title         ");
		jlAuthor 	= new JLabel("Enter Author     ");
		jlPrice 	= new JLabel("Enter Price       ");
		jlFile 		= new JLabel("Enter File           ");
		jlLogDog 	= new JLabel("LogDog");
		
		
		jtISBN = new JTextField(19);
		jtTitle = new JTextField(19);
		jtAuthor = new JTextField(19);
		jtPrice = new JTextField(19);
		jtFile = new JTextField(19);
		jtFile.setText("*Optional");
		
		/*
		 * change text direction from right to left
		 * 
		jtISBN.setHorizontalAlignment(JTextField.RIGHT);
		jtTitle.setHorizontalAlignment(JTextField.RIGHT);
		jtAuthor.setHorizontalAlignment(JTextField.RIGHT);
		jtPrice.setHorizontalAlignment(JTextField.RIGHT);
		jtFile.setHorizontalAlignment(JTextField.RIGHT);
		*/
		
		jbBrowse = new JButton("Browse");
		jbAddFile = new JButton("Add File to Book");
		jbAddBook = new JButton("Add Book");
		jbSave = new JButton("Save");
		jbSaveAndQuit = new JButton("Save & Quit");
		jbListAllBooks = new JButton("List All Books");
		
		jtaLog = new JTextArea(10, 24);
		jtaLog.setEditable(false);
		
		scrollPane = new JScrollPane(jtaLog);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	}

	private void addWidgets() {
		hBox1.add(jlISBN);
		hBox1.add(jtISBN);
		
		hBox2.add(jlTitle);
		hBox2.add(jtTitle);
		
		hBox3.add(jlAuthor);
		hBox3.add(jtAuthor);
		
		hBox4.add(jlPrice);
		hBox4.add(jtPrice);
		
		hBox5.add(jlFile);
		hBox5.add(jtFile);
		
		hBox6.add(Box.createHorizontalStrut(90));
		hBox6.add(jbBrowse);
		hBox6.add(Box.createHorizontalStrut(5));
		hBox6.add(jbAddFile);
		
		hBox7.add(jlLogDog);
		hBox7.add(Box.createHorizontalStrut(165));
		hBox7.add(jbAddBook);
		
		hBox8.add(jbListAllBooks);
		hBox8.add(Box.createHorizontalStrut(20));
		hBox8.add(jbSave);
		hBox8.add(Box.createHorizontalStrut(5));
		hBox8.add(jbSaveAndQuit);
		
		mainBox.add(hBox1);
		mainBox.add(Box.createVerticalStrut(5));
		mainBox.add(hBox2);
		mainBox.add(Box.createVerticalStrut(5));
		mainBox.add(hBox3);
		mainBox.add(Box.createVerticalStrut(5));
		mainBox.add(hBox4);
		mainBox.add(Box.createVerticalStrut(5));
		mainBox.add(hBox5);
		mainBox.add(Box.createVerticalStrut(5));
		mainBox.add(hBox6);
		mainBox.add(Box.createVerticalStrut(5));
		mainBox.add(hBox7);
		mainBox.add(Box.createVerticalStrut(5));
		mainBox.add(scrollPane);
		mainBox.add(Box.createVerticalStrut(5));
		mainBox.add(hBox8);
		
		add(mainBox);
	}

	public void addActionListener(ActionListener a) {
		jbBrowse.addActionListener(a);
		jbAddBook.addActionListener(a);
		jbAddFile.addActionListener(a);
		jbSave.addActionListener(a);
		jbSaveAndQuit.addActionListener(a);
		jbListAllBooks.addActionListener(a);
	}

	public JButton getButtonBrowse() {
		return jbBrowse;
	}
	
	public JButton getButtonAddBook() {
		return jbAddBook;
	}
	
	public JButton getButtonAddFile() {
		return jbAddFile;
	}
	
	public JButton getButtonSave() {
		return jbSave;
	}
	
	public JButton getButtonSaveAndQuit() {
		return jbSaveAndQuit;
	}
	
	public JButton getButtonListAllBooks() {
		return jbListAllBooks;
	}
	
	public JTextField getTextFieldISBN() {
		return jtISBN;
	}
	
	public JTextField getTextFieldTitle() {
		return jtTitle;
	}
	
	public JTextField getTextFieldAuthor() {
		return jtAuthor;
	}
	
	public JTextField getTextFieldPrice() {
		return jtPrice;
	}
	
	public JTextField getTextFieldFile() {
		return jtFile;
	}

	public JTextArea getTextAreaLog() {
		return jtaLog;
	}
	
}
