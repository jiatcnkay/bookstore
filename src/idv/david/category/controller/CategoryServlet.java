package idv.david.category.controller;

import idv.david.category.model.Category;
import idv.david.category.model.CategoryDAO;
import idv.david.category.model.CategoryDAOImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

public class CategoryServlet extends HttpServlet {
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		ServletContext context = getServletContext();
		String contentType = context.getInitParameter("contentType");
		String outStr = "";
		CategoryDAO dao = new CategoryDAOImpl();
		List<Category> categoryList = dao.getAll();
		outStr = new Gson().toJson(categoryList);
		
		res.setContentType(contentType);
		PrintWriter out = res.getWriter();
		System.out.println(outStr);
		out.print(outStr);
		out.close();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}
}
