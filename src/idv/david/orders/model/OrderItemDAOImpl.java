package idv.david.orders.model;

import idv.david.main.MyData;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderItemDAOImpl implements OrderItemDAO {
	private static final String INSERT_STMT = "INSERT INTO orderitem(orderid, isbn, quantity) VALUES(?, ?, ?)";
	private static final String FIND_BY_ORDER = "SELECT * FROM orderitem WHERE orderid = ?";
	
	
	public OrderItemDAOImpl() {
		super();
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void add(OrderItem orderItem) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(OrderItem orderItem) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(int orderid) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<OrderItem> findByOrderId(int orderid) {
		List<OrderItem> oitList = new ArrayList<>();
		OrderItem oit = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = DriverManager.getConnection(MyData.URL, MyData.USER,
					MyData.PASSWORD);
			pstmt = con.prepareStatement(FIND_BY_ORDER);
			pstmt.setInt(1, orderid);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				oit = new OrderItem();
				oit.setOrderid(orderid);
				oit.setIsbn(rs.getString(2));
				oit.setQuantity(rs.getInt(3));
				oitList.add(oit);
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
		return oitList;
	}

	@Override
	public void addWithOrderMaster(OrderItem orderItem, Connection con) {
		PreparedStatement pstmt = null;

		try {

			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setInt(1, orderItem.getOrderid());
			pstmt.setString(2, orderItem.getIsbn());
			pstmt.setInt(3, orderItem.getQuantity());

			pstmt.executeUpdate();

			// Handle any SQL errors
		} catch (SQLException se) {
			if (con != null) {
				try {
					// 3.設定於當有exception發生時之catch區塊內
					System.err.print("Transaction is being ");
					System.err.println("rolled back-由-OrderMaster");
					con.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException("rollback error occured. "
							+ excep.getMessage());
				}
			}
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
		}
	}

}
