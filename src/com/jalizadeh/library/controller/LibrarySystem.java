package com.jalizadeh.library.controller;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.jalizadeh.library.model.Book;
import com.jalizadeh.library.model.Library;
import com.jalizadeh.library.model.MyTableModel;
import com.jalizadeh.library.model.VIM;
import com.jalizadeh.library.view.AddBookPanel;
import com.jalizadeh.library.view.BrowseLibraryPanel;
import com.jalizadeh.library.view.LibraryInterface;
import com.jalizadeh.library.view.LoadScreen;

public class LibrarySystem implements ChangeListener, ActionListener{
	private LibraryInterface screen;
	private AddBookPanel abp;
	private BrowseLibraryPanel blp;
	private LoadScreen ls;
	
	private Library lib;
	private List<VIM> vimCache;
	//will be used to check if user changed the textfiled and if the ext. is valid?
	private String[] validFileTypes = {".wav", ".mp3", ".avi", ".mp4", ".png", ".jpg"};
	private String _validFileTypes = "Valid types end with: .wav, .mp3, .avi, .png, .jpg";
	private FileInputStream fis;
	
	private JFileChooser chooser;
	private FileFilter filterLoadVIM, filterLoadLibrary;
	private File vimFile, saveFile, libraryFile;
	
	private String[][] dataBook, dataFile;
	
	private String fileName;
	private FileOutputStream fos;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	
	private Book openedBook; //it has to be here, to be used again in openfile()
	
	//used in Save&Quit 
	private boolean quit = false;
	
	public LibrarySystem() {
		initEventAttributes();
		
		screen = new LibraryInterface("Library System - jalizadeh@github");
		abp = screen.getAddBookPanel();
		blp = screen.getBrowseLibraryPanel();
		
		
		screen.getJTabbedPane().addChangeListener(this);
		abp.addActionListener(this);
		blp.addActionListener(this);
		
		//screen.setVisible(true); //it will show screen
		//but I want to show, at first, LoadScreen
		ls = new LoadScreen("Library System - jalizadeh@github");
		ls.addActionListener(this);
		ls.setVisible(true);
		
	}

	private void initEventAttributes() {
		chooser = new JFileChooser();
		filterLoadVIM = new FileNameExtensionFilter("Video / Image / Music", "wav", 
				"avi", "jpg", "mp3", "mp4", "png", "jpeg");
		filterLoadLibrary = new FileNameExtensionFilter("Load Library (.ser)", "ser");
		chooser.addChoosableFileFilter(filterLoadVIM);
		chooser.addChoosableFileFilter(filterLoadLibrary);
		
		lib = new Library();
		vimCache = new ArrayList<VIM>(); //it will store selected files temporary
		vimFile = null;
	}

	/*
	 * called when a tab changes
	 * the "Browse Library" tab has elements wider than the first tab,
	 * so I change the size when it is selected
	 * 
	 * or you can do everything else needed
	 */
	@Override 
	public void stateChanged(ChangeEvent arg0) {
		if(screen.getJTabbedPane().getSelectedIndex() == 1) {
			//screen.getJTabbedPane().setTitleAt(1, "new name"); //but I wont change the name
			screen.setSize(360, 480);
		} else {
			screen.setSize(320, 460);
		}
	}

	/*
	 * which button is clicked?
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		if(event.getSource() == abp.getButtonBrowse()) {
			openChooserAndSetTheVIMFile();
		} else if(event.getSource() == abp.getButtonAddFile()) {
			addVIMFileTovimCache();
		} else if(event.getSource() == abp.getButtonAddBook()) {
			addVIMFileInvimCacheToBookAndAddBookToLibrary();
			reloadDataBook(); // when a new book added, update the table
			reloadDataFile();
		} else if(event.getSource() == abp.getButtonListAllBooks()) {
			listAllBooksInLibrary();
		} else if(event.getSource() == abp.getButtonSave()) {
			save();
		} else if(event.getSource() == abp.getButtonSaveAndQuit()) {
			saveAndQuit();
		} else if(event.getSource() == blp.getButtonOpenBook()) {
		 	openBook();
		} else if(event.getSource() == blp.getButtonDeleteBook()) {
			deleteBook();
		} else if(event.getSource() == blp.getButtonOpenFile()) {
			openFile();
		} else if(event.getSource() == blp.getButtonDeleteFile()) {
			
		} else if(event.getSource() == blp.getButtonSave()) {
			save();
		} else if (event.getSource() == blp.getButtonSaveAndQuit()) {
			saveAndQuit();
		} else if (event.getSource() == ls.getButtonNew()) {
			ls.dispose();
			screen.setVisible(true);
		} else if (event.getSource() == ls.getButtonLoad()) {
			reloadDataBook();
			reloadDataFile();
			loadLibrary();
		} else if (event.getSource() == ls.getButtonExit()) {
			System.exit(0);
		}
		
	}

	


	private void deleteBook() {
		int row;
		
		row = ((JTable) blp.getBookTable()).getSelectedRow();
		//((JTable)blp.getBookTable().getModel()).removeRow();
		
		reloadDataBook();
	}

	private void openFile() {
		int row, column;
		String fileName;
		VIM v = null;
		File file = null;
		
		row = ((JTable) blp.getFileTable()).getSelectedRow();
		column = ((JTable) blp.getFileTable()).getSelectedColumn();
		fileName = ((JTable) blp.getFileTable()).getValueAt(row, column).toString();
		
		if(fileName != null) { //prevent user from selecting empty cell
			JOptionPane.showMessageDialog(screen,row + " " + column + "\nfilename: " + fileName 
					+ "\nisbn: " + openedBook.getISBNforVector());
			v = openedBook.getVIMByName(fileName);
			if(v != null) {
				try {
					file = new File(v.getName());
					fos = new FileOutputStream(file);
					fos.write(v.getData());
					fos.close();
					Desktop.getDesktop().open(file);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				JOptionPane.showMessageDialog(screen,"vim problem");
			}
		} else {
			JOptionPane.showMessageDialog(screen,"Please select a valid cell.");
		}
	}

	
	private void openBook() {
		int row, column;
		String isbn;
		
		row = ((JTable) blp.getBookTable()).getSelectedRow();
		column = 3; //it is ISBN column
		isbn = ((JTable) blp.getBookTable()).getValueAt(row, column).toString();
		//JOptionPane.showMessageDialog(screen,row + " " + column + " " + isbn);
		
		openedBook = lib.getBookByISBN(isbn);
		
		dataFile = openedBook.toStringVectorFile();
		reloadDataFile();
		
	}

	private void loadLibrary() {
		chooser.setFileFilter(filterLoadLibrary); //selected filter when chooser pops up
		
		// the parent is "screen", so the window will 
		// pop up in the center of application`s window
		int resultCode = chooser.showOpenDialog(ls);
		if(resultCode == JFileChooser.APPROVE_OPTION) {
			libraryFile = chooser.getSelectedFile();
			try {
				fis = new FileInputStream(libraryFile);
				ois = new ObjectInputStream(fis);
				lib = (Library) ois.readObject();
				ois.close();
				
				ls.dispose();
				
				//Load Books
				reloadDataBook();
				
				screen.setVisible(true);
				
			} catch (IOException e) {
				//raised when I/O got a problem
				//e.printStackTrace();
				JOptionPane.showMessageDialog(ls, "An error happened during reading file.");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			
			
		}
	}

	private void reloadDataBook() {
		
		dataBook = lib.toStringVector();
		
		while (((MyTableModel) blp.getBookTable().getModel()).getRowCount() > 0) {
			((MyTableModel) blp.getBookTable().getModel()).removeRow(0);
		}
		
		for (int i = 0; i < dataBook.length; i++) {
			((MyTableModel) blp.getBookTable().getModel()).addRow(dataBook[i]);
		}
	}

	
	private void reloadDataFile() {	
		while (((MyTableModel) blp.getFileTable().getModel()).getRowCount() > 0) {
			((MyTableModel) blp.getFileTable().getModel()).removeRow(0);
		}
		
		if(dataFile != null) {
			for (int i = 0; i < dataFile.length; i++) {
				((MyTableModel) blp.getFileTable().getModel()).addRow(dataFile[i]);
			}
		}
	}
	
	
	/*
	 * 1. check if fileName is set
	 * 2. check if fileName is not ""
	 * if both OK, save & exit
	 */
	private void saveAndQuit() {
		save();
		if(quit)
			System.exit(0);
	}

	
	/*
	 * 1. check if fileName is set
	 * 2. check if fileName is not ""
	 */
	private void save() {
		fileName = JOptionPane.showInputDialog(screen, "Enter file name to save as...",
									"Save", JOptionPane.INFORMATION_MESSAGE);
		if(fileName != null) {
			if(!fileName.trim().contentEquals("")) {
				FileOutputStream  fos = null;
				ObjectOutputStream oos = null;
				try {
					saveFile = new File(fileName.trim() + ".ser");
					
					//check for overwrite
					if(!saveFile.exists()) {
						fos = new FileOutputStream(saveFile);
						oos = new ObjectOutputStream(fos);
						oos.writeObject(lib);
						fos.close();
						oos.close();
						abp.getTextAreaLog().append("\n> Save successfully as: "+fileName+".ser");
						quit = true; //make it true for Save&Quit
					} else {
						int resultCode = JOptionPane.showConfirmDialog(screen, 
								"File with this name already exist.\nDo you wish to overwrite?"
								, "Warning"
								,JOptionPane.YES_NO_OPTION
								,JOptionPane.WARNING_MESSAGE);
						if(resultCode == JOptionPane.YES_OPTION) {
							fos = new FileOutputStream(saveFile);
							oos = new ObjectOutputStream(fos);
							oos.writeObject(lib);
							fos.close();
							oos.close();
							abp.getTextAreaLog().append("\n> Save successfully as: "+fileName+".ser");
							quit = true; //make it true for Save&Quit
						} else {
							abp.getTextAreaLog().append("\n> Save cancelled.");
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				abp.getTextAreaLog().append("\n> Save cancelled.");
			}
		} else {
			abp.getTextAreaLog().append("\n> Save cancelled.");
		}
	}

	private void listAllBooksInLibrary() {
		abp.getTextAreaLog().setText("> Listing all books in library:");
		abp.getTextAreaLog().append(" " + lib.toString());
	}

	private void addVIMFileInvimCacheToBookAndAddBookToLibrary() {
		boolean doesISBNAlreadyExist = false;
		int isbn = 0;
		double price = 0.0;
		Book b;
		boolean allFiledsAreFilled = false;
		
		if(!abp.getTextFieldISBN().getText().trim().contentEquals("") &&
				!abp.getTextFieldAuthor().getText().trim().contentEquals("") &&
				!abp.getTextFieldTitle().getText().trim().contentEquals("") &&
				!abp.getTextFieldPrice().getText().trim().contentEquals("")) {
			
			allFiledsAreFilled = true;
		}
		
		if(allFiledsAreFilled) {
			try {
				isbn = Integer.parseInt(abp.getTextFieldISBN().getText().trim());
				price = Double.parseDouble(abp.getTextFieldPrice().getText().trim());
				
				//if no exception raise, it will do the rest
				doesISBNAlreadyExist = lib.doesISBNAlreadyExist(isbn);
				if(doesISBNAlreadyExist) {
					JOptionPane.showMessageDialog(screen, isbn 
							+ " already exists.\nPlease check/use another ISBN.");
				} else if(isbn == 0) {
					JOptionPane.showMessageDialog(screen, "ISBN can not be 0");
				} else if (price == 0) {
					JOptionPane.showMessageDialog(screen, "Price can not be 0");
				} else {
					b = new Book(isbn,
									abp.getTextFieldTitle().getText().trim(),
									abp.getTextFieldAuthor().getText().trim(),
									price);
					
					for (int i = 0; i < vimCache.size(); i++) {
						b.addVIM(vimCache.get(i));
					}
					
					lib.addBook(b);
					
					//now do the rest -> Log & clear
					abp.getTextAreaLog().append("\nBook \""+b.getTitle()+"\" has been added to the library.");
					abp.getTextFieldISBN().setText("");
					abp.getTextFieldAuthor().setText("");
					abp.getTextFieldTitle().setText("");
					abp.getTextFieldPrice().setText("");
					
					//reinitialize vimCache, otherwise it will carry previous vim files
					vimCache = new ArrayList<VIM>();
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(screen, abp.getTextFieldISBN().getText() 
						+ " or " + abp.getTextFieldPrice().getText() + " is not valid");
			}
		
		//if allFiledsAreFilled != true
		} else {
			JOptionPane.showMessageDialog(screen,"Please fill all non-optional fields.");
		}
	}

	/*
	 * 1. check the file name inside text field for any type mistake, if user change it
	 * 2. error? show error
	 * 3. fine? 
	 * 3.1. read it
	 * 3.2. put file in vimCache list
	 * 3.3. append to LogDog
	 */
	private void addVIMFileTovimCache() {
		if(vimFile != null) {
			for (int i = 0; i < validFileTypes.length; i++) {
				//before adding the VIM, i should check if the file name is changed? fine?
				if(abp.getTextFieldFile().getText().trim().endsWith(validFileTypes[i])) {
					byte[] data = new byte[(int)vimFile.length()];
					try {
						fis = new FileInputStream(vimFile);
						fis.read(data);
						fis.close();
						
						VIM v = new VIM(abp.getTextFieldFile().getText().trim(), data);
						vimCache.add(v);
						abp.getTextAreaLog().append("\n<" + abp.getTextFieldFile().getText().trim()
								+ "> is ready to be added to book.");
						
						//clear the text field and vim file, so not to let add the same file again
						abp.getTextFieldFile().setText("*Optional");
						vimFile = null;
						
						//if everything goes fine, the method will return and end 
						return;
					} catch (FileNotFoundException e) {					
						//raises when File is not found
						//e.printStackTrace(); //i wont use it in GUI based app, but pop up
						JOptionPane.showMessageDialog(screen, "File not found!!!");
					} catch (IOException e) {
						//raised when I/O got a problem
						//e.printStackTrace();
						JOptionPane.showMessageDialog(screen, "An error happened during reading file.");
					}
				}
			}
			//but if the method is not ended until <return> above, this error will pop up
			JOptionPane.showMessageDialog(screen, "File type is not valid.\n" + _validFileTypes);
		} else {
			JOptionPane.showMessageDialog(screen, 
					"oops! Something went wrong.\nPlease click Browse again and choose file.");
		}
	}

	/*
	 * 1. open file chooser
	 * 2. put the file name in the text field to be used bye "addVIMFileToBook()"
	 */
	private void openChooserAndSetTheVIMFile() {
		chooser.setFileFilter(filterLoadVIM); //selected filter when chooser pops up
		
		// the parent is "screen", so the window will 
		// pop up in the center of application`s window
		int resultCode = chooser.showOpenDialog(screen);
		if(resultCode == JFileChooser.APPROVE_OPTION) {
			vimFile = chooser.getSelectedFile();
			abp.getTextFieldFile().setText(vimFile.getName());
		}
	}
}
