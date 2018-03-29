package idv.david.orders.controller;

import idv.david.book.model.Book;
import idv.david.book.model.BookDAO;
import idv.david.book.model.BookDAOImpl;
import idv.david.main.ImageUtil;
import idv.david.orders.model.OrderItem;
import idv.david.orders.model.OrderItemDAO;
import idv.david.orders.model.OrderItemDAOImpl;
import idv.david.orders.model.OrderMaster;
import idv.david.orders.model.OrderMasterDAO;
import idv.david.orders.model.OrderMasterDAOImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class OrderServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		String contentType = getServletContext().getInitParameter("contentType");
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		int imageSize = Integer.parseInt(req.getParameter("imageSize"));
		String userId = req.getParameter("userId");
		System.out.println(userId);
		String outStr = "";
		OrderMasterDAO orderDAO = new OrderMasterDAOImpl();
		Gson gson = new Gson();
		if (action.equals("add") && userId != null) {
			List<OrderItem> orderItemList = new ArrayList<>();
			String orderJson = req.getParameter("orderJson");
			OrderMaster order = gson.fromJson(orderJson, OrderMaster.class);
			System.out.println(orderJson);
			List<Book> bookList = order.getOrderBookList();
			for (Book book : bookList) {
				OrderItem oit = new OrderItem();
				oit.setIsbn(book.getIsbn());
				oit.setQuantity(book.getQuantity());
				orderItemList.add(oit);
			}
			int orderid = -1;
			orderid = orderDAO.addWithOrderItem(order, orderItemList);
			if (orderid != -1) {
				OrderMaster orderMaster = orderDAO.findById(orderid);
				orderMaster.setOrderBookList(bookList);
				outStr = gson.toJson(orderMaster);
			}

		} else if (action.equals("getAll") && userId != null) {
			OrderItemDAO orderItemDAO = new OrderItemDAOImpl();
			BookDAO bookDAO = new BookDAOImpl();
			String start = req.getParameter("start");
			String end = req.getParameter("end");
			List<OrderMaster> orderList = orderDAO.getAll(userId, start, end);
			if (orderList != null && orderList.size() > 0) {
				for (OrderMaster order : orderList) {
					List<OrderItem> oitList = orderItemDAO.findByOrderId(order.getOrderid());
					for (OrderItem oit : oitList) {
						Book book = bookDAO.findByISBN(oit.getIsbn());
						book.setPicture(ImageUtil.shrink(book.getPicture(), imageSize));
					}
				}
				outStr = gson.toJson(orderList);
			}
		} else if (action.equals("findByOrder") && userId != null) {
			OrderItemDAO orderItemDAO = new OrderItemDAOImpl();
			int orderId = Integer.parseInt(req.getParameter("orderId"));
			List<OrderItem> oitList = orderItemDAO.findByOrderId(orderId);
			outStr = gson.toJson(oitList);
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
