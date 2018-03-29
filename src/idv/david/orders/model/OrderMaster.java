package idv.david.orders.model;

import idv.david.book.model.Book;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

public class OrderMaster implements Serializable {
	private int orderid;
	private String userid;
	private int amount;
	private Timestamp timestamp;
	private List<Book> orderBookList;

	public OrderMaster() {
		super();
	}

	public OrderMaster(int orderid, String userid, int amount,
			Timestamp timestamp) {
		super();
		this.orderid = orderid;
		this.userid = userid;
		this.amount = amount;
		this.timestamp = timestamp;
	}

	public int getOrderid() {
		return orderid;
	}

	public void setOrderid(int orderid) {
		this.orderid = orderid;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public List<Book> getOrderBookList() {
		return orderBookList;
	}

	public void setOrderBookList(List<Book> orderBookList) {
		this.orderBookList = orderBookList;
	}
}
