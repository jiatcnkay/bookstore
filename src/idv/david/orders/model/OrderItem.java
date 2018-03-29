package idv.david.orders.model;

import idv.david.book.model.Book;

import java.io.Serializable;

public class OrderItem implements Serializable{
	private int orderid;
    private String isbn;
    private int quantity;

    public OrderItem() {
    }

    public OrderItem(int orderid, String isbn, int quantity) {
        this.orderid = orderid;
        this.isbn = isbn;
        this.quantity = quantity;
    }

    public int getOrderid() {
        return orderid;
    }

    public void setOrderid(int orderid) {
        this.orderid = orderid;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
