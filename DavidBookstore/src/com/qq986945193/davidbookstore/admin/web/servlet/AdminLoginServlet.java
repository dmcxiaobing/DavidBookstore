package com.qq986945193.davidbookstore.admin.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.david.webtools.common.base.BaseServlet;
import com.qq986945193.davidbookstore.book.domain.Book;
import com.qq986945193.davidbookstore.cart.domain.Cart;
import com.qq986945193.davidbookstore.cart.domain.CartItem;

/**
 * 后台管理页面的登陆servlet
 */
public class AdminLoginServlet extends BaseServlet {
	/**
	 * 管理员登陆，这里设置管理员为admin
	 */
	public String login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String adminname = request.getParameter("adminname");
		String password = request.getParameter("password");
		if (adminname == null || adminname.trim().isEmpty() || password == null || password.trim().isEmpty()) {
			request.setAttribute("msg", "用户名或密码不能为空");
			return "f:/adminjsps/login.jsp";
		}
		if (adminname.equals("admin") && password.equals("admin")) {
			return "f:/adminjsps/admin/index.jsp";
		} else {
			request.setAttribute("msg", "用户名或密码不正确");
			return "f:/adminjsps/login.jsp";
		}

	}
}
