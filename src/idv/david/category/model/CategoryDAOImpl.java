package idv.david.category.model;

import idv.david.main.MyData;
import idv.david.member.model.Member;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAOImpl implements CategoryDAO {
	private static final String GET_ALL = "SELECT * FROM category";

	public CategoryDAOImpl() {
		super();
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void add(Category category) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Category category) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(int cid) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Category findById(int cid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Category> getAll() {
		List<Category> categoryList = new ArrayList<>();
		Category category = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = DriverManager.getConnection(MyData.URL, MyData.USER,
					MyData.PASSWORD);
			pstmt = con.prepareStatement(GET_ALL);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				category = new Category();
				category.setCid(rs.getInt(1));
				category.setCname(rs.getString(2));
				categoryList.add(category);
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
		return categoryList;
	}

}
