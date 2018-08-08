package com.jalizadeh.library.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Library extends Object implements Serializable{
	private static final long serialVersionUID = 2484705313817994776L;
	private List<Book> collection;
	
	public Library() {
		collection = new ArrayList<Book>();
	} 
	
	public void addBook(Book book) {
		collection.add(book);
	}
	
	public Book getBookByTitle(String title) {
		Book v = null;
		Iterator<Book> i = collection.iterator();
		if(i.hasNext()) {
			v = i.next();
			if(v.getTitle().toLowerCase().contentEquals(title.toLowerCase())) {
				return v;
			}
		}
		return null;
	}

	@Override
	public String toString() {
		String total = "\n";
		
		Iterator<Book> i = collection.iterator();
		while(i.hasNext()) {
			Book b = (Book) i.next();
			total += b.toString()+"\n";
		}
		
		//note there is one character => "\n" at least
		if(total.length() > 1)
			return total;
		else
			return "\nThere is no book to show.";
	}

	/*
	 * will check if the inserted ISBN already exist?
	 */
	public boolean doesISBNAlreadyExist(int isbn) {
		Iterator<Book> i = collection.iterator();
		while(i.hasNext()) {
			if(i.next().getISBN() == isbn)
				return true;
		}
		
		return false;
	}
	
	/*
	 * it will be used in Browse Library
	 */
	public String[][] toStringVector(){
		String[][] total = new String[collection.size()][4];
		
		for (int i = 0; i < collection.size(); i++) {
			total[i][0] = collection.get(i).getTitle();
			total[i][1] = collection.get(i).getAuthor();
			total[i][2] = collection.get(i).getPrice();
			total[i][3] = collection.get(i).getISBNforVector();
		}
		
		return total;
	}

	public Book getBookByISBN(String isbn) {
		for (int i = 0; i < collection.size(); i++) {
			if(collection.get(i).getISBNforVector().contentEquals(isbn)) {
				return collection.get(i);
			}
		}
		
		return null;
	}
	
}
