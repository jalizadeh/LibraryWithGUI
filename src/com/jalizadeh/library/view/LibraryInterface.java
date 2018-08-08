package com.jalizadeh.library.view;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

public class LibraryInterface extends JFrame{
	private static final long serialVersionUID = 7285996181470642854L;
	
	private AddBookPanel abp;
	private BrowseLibraryPanel blp;
	
	private JTabbedPane jtp;
	private String filler;
	
	public LibraryInterface(String title) {
		super(title);
		
		//filler = "       "; //7 spaces
		jtp = new JTabbedPane();
		abp = new AddBookPanel();
		blp = new BrowseLibraryPanel();
		
		jtp.addTab("Add Book",	abp);
		jtp.addTab("Browse Library", blp);
		
		add(jtp);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setLocationRelativeTo(null);	//null => show in the center
		setLocation(0, 0);
		setSize(320, 460);
		//pack();
		setResizable(false);
	}
	
	public AddBookPanel getAddBookPanel() {
		return abp;
	}
	
	public BrowseLibraryPanel getBrowseLibraryPanel() {
		return blp;
	}
	
	public JTabbedPane getJTabbedPane() {
		return jtp;
	}
	
	public String getFiller() {
		return filler;
	}
}
