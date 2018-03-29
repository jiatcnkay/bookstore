package idv.david.member.model;

import idv.david.book.model.Book;
import idv.david.main.MyData;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MemberDAOImpl implements MemberDAO {
	private static final String INSERT_STMT = "INSERT INTO member(userid, password, name, email) VALUES(?, ?, ?, ?)";
	private static final String UPDATE_STMT = "UPDATE member SET password = ?, name = ?, email = ? WHERE userid = ?";
	private static final String FIND_BY_ID_PASWD = "SELECT * FROM member WHERE userid = ? AND password = ?";
	private static final String FIND_BY_ID = "SELECT * FROM member WHERE userid = ?";
	private static final String CHECK_ID_EXIST = "SELECT userid FROM member WHERE userid = ?";
	

	public MemberDAOImpl() {
		super();
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean add(Member member) {
		Connection con = null;
		PreparedStatement pstmt = null;
		boolean isAdded = false;

		try {
			con = DriverManager.getConnection(MyData.URL, MyData.USER,
					MyData.PASSWORD);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, member.getUserid());
			pstmt.setString(2, member.getPassword());
			pstmt.setString(3, member.getName());
			pstmt.setString(4, member.getEmail());

			int count = pstmt.executeUpdate();
			isAdded = count > 0 ? true : false;

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
		return isAdded;
	}

	@Override
	public boolean update(Member member) {
		Connection con = null;
		PreparedStatement pstmt = null;
		boolean isUpdated = false;
		
		try {
			con = DriverManager.getConnection(MyData.URL, MyData.USER,
					MyData.PASSWORD);
			pstmt = con.prepareStatement(UPDATE_STMT);

			pstmt.setString(1, member.getPassword());
			pstmt.setString(2, member.getName());
			pstmt.setString(3, member.getEmail());
			pstmt.setString(4, member.getUserid());

			int count = pstmt.executeUpdate();
			isUpdated = count > 0 ? true : false;

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
		return isUpdated;
	}

	@Override
	public boolean delete(String userid) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Member findById(String userid) {
		Member member = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = DriverManager.getConnection(MyData.URL, MyData.USER,
					MyData.PASSWORD);
			pstmt = con.prepareStatement(FIND_BY_ID);

			pstmt.setString(1, userid);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				member = new Member();
				member.setUserid(rs.getString(1));
				member.setPassword(rs.getString(2));
				member.setName(rs.getString(3));
				member.setEmail(rs.getString(4));
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
		return member;
	}

	@Override
	public List<Member> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isMember(String userId, String password) {
		Connection conn = null;
		PreparedStatement ps = null;
		boolean isMember = false;
		try {
			conn = DriverManager.getConnection(MyData.URL, MyData.USER,
					MyData.PASSWORD);
			ps = conn.prepareStatement(FIND_BY_ID_PASWD);
			ps.setString(1, userId);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			isMember = rs.next();
			return isMember;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return isMember;
	}

	@Override
	public boolean isUserIdExist(String userId) {
		Connection conn = null;
		PreparedStatement ps = null;
		boolean isUserIdExist = false;
		try {
			conn = DriverManager.getConnection(MyData.URL, MyData.USER,
					MyData.PASSWORD);
			ps = conn.prepareStatement(CHECK_ID_EXIST);
			ps.setString(1, userId);
			ResultSet rs = ps.executeQuery();
			isUserIdExist = rs.next();
			return isUserIdExist;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return isUserIdExist;
	}

}
