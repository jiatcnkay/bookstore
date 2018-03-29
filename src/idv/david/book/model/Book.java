package idv.david.book.model;

import java.io.Serializable;

public class Book implements Serializable {
	private String isbn;
	private String name;
	private String author;
	private int price;
	private byte[] picture;
	private int categoryid;
	private int quantity;
	
	public Book() {
		super();
	}

	public Book(String isbn, String name, String author, int price,
			byte[] picture, int categoryid) {
		super();
		this.isbn = isbn;
		this.name = name;
		this.author = author;
		this.price = price;
		this.picture = picture;
		this.categoryid = categoryid;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public byte[] getPicture() {
		return picture;
	}

	public void setPicture(byte[] picture) {
		this.picture = picture;
	}

	public int getCategoryid() {
		return categoryid;
	}

	public void setCategoryid(int categoryid) {
		this.categoryid = categoryid;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
