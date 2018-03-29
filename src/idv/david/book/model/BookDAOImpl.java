package idv.david.book.model;

import idv.david.main.MyData;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDAOImpl implements BookDAO {

	private static final String INSERT_STMT = "INSERT INTO book(isbn, name, author, price, picture, categoryid) VALUES(?, ?, ?, ?, ?, ?)";
	private static final String UPDATE_STMT = "UPDATE book SET name = ?, author = ?, price = ?, picture = ?, categoryid = ? WHERE isbn = ?";
	private static final String DELETE_STMT = "DELETE FROM book WHERE isbn = ?";
	private static final String FIND_BY_ISBN = "SELECT * FROM book WHERE isbn = ?";
	private static final String GET_ALL = "SELECT * FROM book";
	private static final String FIND_BY_CATEGORY = "SELECT * FROM book WHERE categoryid = ?";

	public BookDAOImpl() {
		super();
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void add(Book book) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DriverManager.getConnection(MyData.URL, MyData.USER,
					MyData.PASSWORD);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, book.getIsbn());
			pstmt.setString(2, book.getName());
			pstmt.setString(3, book.getAuthor());
			pstmt.setInt(4, book.getPrice());
			pstmt.setBytes(5, book.getPicture());
			pstmt.setInt(6, book.getCategoryid());

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}

	@Override
	public void update(Book book) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DriverManager.getConnection(MyData.URL, MyData.USER,
					MyData.PASSWORD);
			pstmt = con.prepareStatement(UPDATE_STMT);

			pstmt.setString(1, book.getName());
			pstmt.setString(2, book.getAuthor());
			pstmt.setInt(3, book.getPrice());
			pstmt.setBytes(4, book.getPicture());
			pstmt.setInt(5, book.getCategoryid());
			pstmt.setString(6, book.getIsbn());

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}

	@Override
	public void delete(String isbn) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DriverManager.getConnection(MyData.URL, MyData.USER,
					MyData.PASSWORD);
			pstmt = con.prepareStatement(DELETE_STMT);

			pstmt.setString(1, isbn);

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}

	@Override
	public Book findByISBN(String isbn) {
		Book book = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = DriverManager.getConnection(MyData.URL, MyData.USER,
					MyData.PASSWORD);
			pstmt = con.prepareStatement(FIND_BY_ISBN);

			pstmt.setString(1, isbn);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				book = new Book();
				book.setIsbn(rs.getString(1));
				book.setName(rs.getString(2));
				book.setAuthor(rs.getString(3));
				book.setPrice(rs.getInt(4));
				book.setPicture(rs.getBytes(5));
				book.setCategoryid(rs.getInt(6));
			}
			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return book;
	}

	@Override
	public List<Book> getAll() {
		List<Book> bookList = new ArrayList<>();
		Book book = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = DriverManager.getConnection(MyData.URL, MyData.USER,
					MyData.PASSWORD);
			pstmt = con.prepareStatement(GET_ALL);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				book = new Book();
				book.setIsbn(rs.getString(1));
				book.setName(rs.getString(2));
				book.setAuthor(rs.getString(3));
				book.setPrice(rs.getInt(4));
				book.setPicture(rs.getBytes(5));
				book.setCategoryid(rs.getInt(6));
				bookList.add(book);
			}
			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return bookList;
	}

	@Override
	public List<Book> findByCategory(int cid) {
		List<Book> bookList = new ArrayList<>();
		Book book = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = DriverManager.getConnection(MyData.URL, MyData.USER,
					MyData.PASSWORD);
			pstmt = con.prepareStatement(FIND_BY_CATEGORY);
			pstmt.setInt(1, cid);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				book = new Book();
				book.setIsbn(rs.getString(1));
				book.setName(rs.getString(2));
				book.setAuthor(rs.getString(3));
				book.setPrice(rs.getInt(4));
				book.setPicture(rs.getBytes(5));
				book.setCategoryid(rs.getInt(6));
				bookList.add(book);
			}
			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return bookList;
	}

}
