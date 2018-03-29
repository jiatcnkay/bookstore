package idv.david.book.controller;

import idv.david.book.model.Book;
import idv.david.book.model.BookDAO;
import idv.david.book.model.BookDAOImpl;
import idv.david.main.ImageUtil;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

public class BookServlet extends HttpServlet {
	//圖片要用BASE64處理，VO為了方便要加上byte[]屬性
	//請求比對要寫成getAll.equals("action")比較好
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		ServletContext context = getServletContext();
		String contentType = context.getInitParameter("contentType");
		String outStr = "";

		
		BookDAO dao = new BookDAOImpl();
		Gson gson = new Gson();

		String action = req.getParameter("action");
		System.out.println(action);
		int imageSize = Integer.parseInt(req.getParameter("imageSize"));
		if (action.equals("getAll")) {
			List<Book> bookList = dao.getAll();
			
			for (int i = 0; i < bookList.size(); i++) {
				Book book = bookList.get(i);
				book.setPicture(ImageUtil.shrink(book.getPicture(), imageSize));
			}
			outStr = gson.toJson(bookList);
		} else if (action.equals("findById")) {
			String isbn = req.getParameter("isbn");
			Book book = dao.findByISBN(isbn);
			book.setPicture(ImageUtil.shrink(book.getPicture(), imageSize));
			outStr = gson.toJson(book);
		} else if (action.equals("findByCategory")) {
			int cid = Integer.parseInt(req.getParameter("cid"));
			List<Book> bookList = dao.findByCategory(cid);
			for (int i = 0; i < bookList.size(); i++) {
				Book book = bookList.get(i);
				book.setPicture(ImageUtil.shrink(book.getPicture(), imageSize));
			}
			outStr = gson.toJson(bookList);
		}
		
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
