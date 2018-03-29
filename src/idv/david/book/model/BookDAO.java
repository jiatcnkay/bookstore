package idv.david.book.model;

import java.util.List;

public interface BookDAO {
	void add(Book book);
	void update(Book book);
	void delete(String isbn);
	Book findByISBN(String isbn);
	List<Book> findByCategory(int cid);
	List<Book> getAll();
}
