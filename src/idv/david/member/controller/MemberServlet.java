package idv.david.member.controller;

import idv.david.member.model.Member;
import idv.david.member.model.MemberDAO;
import idv.david.member.model.MemberDAOImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

public class MemberServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		ServletContext context = getServletContext();
		String contentType = context.getInitParameter("contentType");
		req.setCharacterEncoding("UTF-8");
		MemberDAO memberDao = new MemberDAOImpl();
		Gson gson = new Gson();
		String outStr = "";

		String action = req.getParameter("action");
		System.out.println(action);
		
		if (action.equals("isMember")) {
			String userId = req.getParameter("userId");
			String password = req.getParameter("password");
			outStr = String.valueOf(memberDao.isMember(userId, password));
		} else if (action.equals("isUserIdExist")) {
			String userId = req.getParameter("userId");
			outStr = String.valueOf(memberDao.isUserIdExist(userId));
		} else if (action.equals("add")) {
			String memberJson = req.getParameter("member");
			System.out.println(memberJson);
			Member member = gson.fromJson(memberJson, Member.class);
			outStr = String.valueOf(memberDao.add(member));
		} else if (action.equals("findById")) {
			String userId = req.getParameter("userId");
			Member member = memberDao.findById(userId);
			outStr = member == null ? "" : gson.toJson(member);
		} else if (action.equals("update")) {
			Member member = gson.fromJson(req.getParameter("member"),
					Member.class);
			outStr = String.valueOf(memberDao.update(member));
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
