package idv.david.orders.model;

import idv.david.main.MyData;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class OrderMasterDAOImpl implements OrderMasterDAO {
	private static final String FIND_BY_ID = "SELECT * FROM ordermaster WHERE orderid = ?";
	private static final String GET_ALL = "SELECT * FROM ordermaster";
	private static final String INSERT_STMT = "INSERT INTO ordermaster(userid, amount) VALUES(?, ?)";
	private static final String GET_BY_DATE = "SELECT * FROM ordermaster WHERE userid = ? "
			+ "AND timestamp BETWEEN ? AND ? ORDER BY timestamp DESC";

	public OrderMasterDAOImpl() {
		super();
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public OrderMaster findById(int orderid) {
		OrderMaster orderMaster = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = DriverManager.getConnection(MyData.URL, MyData.USER,
					MyData.PASSWORD);

			pstmt = con.prepareStatement(FIND_BY_ID);

			pstmt.setInt(1, orderid);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				orderMaster = new OrderMaster();
				orderMaster.setOrderid(rs.getInt(1));
				orderMaster.setUserid(rs.getString(2));
				orderMaster.setAmount(rs.getInt(3));
				orderMaster.setTimestamp(rs.getTimestamp(4));
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
		return orderMaster;

	}

	@Override
	public List<OrderMaster> getAll(String userId, String start, String end) {
		List<OrderMaster> orderList = new ArrayList<>();
		OrderMaster orderMaster = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = DriverManager.getConnection(MyData.URL, MyData.USER,
					MyData.PASSWORD);
			if (start == null || end == null || start.isEmpty()
					|| end.isEmpty()) {
				pstmt = con.prepareStatement(GET_ALL);
			} else {
				pstmt = con.prepareStatement(GET_BY_DATE);
				pstmt.setString(1, userId);
				pstmt.setString(2, start);
				// include the whole day!
				pstmt.setString(3, end + " 23:59:59");
			}
			rs = pstmt.executeQuery();

			while (rs.next()) {
				orderMaster = new OrderMaster();
				orderMaster.setOrderid(rs.getInt(1));
				orderMaster.setUserid(rs.getString(2));
				orderMaster.setAmount(rs.getInt(3));
				orderMaster.setTimestamp(rs.getTimestamp(4));
				orderList.add(orderMaster);
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
		return orderList;
	}

	@Override
	public int addWithOrderItem(OrderMaster orderMaster,
			List<OrderItem> orderItemList) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String next_orderid = null;
		try {

			con = DriverManager.getConnection(MyData.URL, MyData.USER,
					MyData.PASSWORD);

			// 1.設定於pstmt.executeUpdate()之前
			con.setAutoCommit(false);

			// 先新增訂單
			String cols[] = { "orderid" };
			pstmt = con.prepareStatement(INSERT_STMT, cols);
			pstmt.setString(1, orderMaster.getUserid());
			pstmt.setInt(2, orderMaster.getAmount());
			pstmt.executeUpdate();

			// 取得對應的自增主鍵值
			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				next_orderid = rs.getString(1);
				System.out.println("自增主鍵值= " + next_orderid + "(剛新增成功的訂單編號)");
			} else {
				System.out.println("未取得自增主鍵值");
			}
			rs.close();
			// 再同時新增訂單明細內容
			OrderItemDAO dao = new OrderItemDAOImpl();
			System.out.println("orderItemList.size()-A= "
					+ orderItemList.size());
			for (OrderItem orderItem : orderItemList) {
				orderItem.setOrderid(new Integer(next_orderid));
				dao.addWithOrderMaster(orderItem, con);
			}

			// 2.設定於pstmt.executeUpdate()之後
			con.commit();
			con.setAutoCommit(true);
			System.out.println("orderItemList.size()-B= "
					+ orderItemList.size());
			System.out.println("新增訂單編號" + next_orderid + "時，共有明細"
					+ orderItemList.size() + "筆同時被新增");

			// Handle any driver errors
		} catch (SQLException se) {
			if (con != null) {
				try {
					// 3.設定於當有exception發生時之catch區塊內
					System.err.print("Transaction is being ");
					System.err.println("rolled back-由-OrderItem");
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
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return new Integer(next_orderid);
	}
}
