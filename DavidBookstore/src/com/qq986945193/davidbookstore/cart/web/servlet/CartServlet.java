package com.qq986945193.davidbookstore.cart.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.david.webtools.common.base.BaseServlet;
import com.qq986945193.davidbookstore.book.domain.Book;
import com.qq986945193.davidbookstore.book.service.BookService;
import com.qq986945193.davidbookstore.cart.domain.Cart;
import com.qq986945193.davidbookstore.cart.domain.CartItem;

/**
 * 购物车的servlet
 */
public class CartServlet extends BaseServlet {
	private BookService bookService = new BookService();
	/**
	 * 添加进购物车
	 */
	public String add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 登陆成功后，给用户一个购物车，如果购物车为空，则说明没有登陆，提示用户登陆
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		//得到图书的id
		String bid = request.getParameter("bid");
		//得到数量
		int count = Integer.parseInt(request.getParameter("count"));
		//根据bid查询图书
		Book book = bookService.loadByBid(bid);
		//根据数量和图书设置 条目
		CartItem cartItem = new CartItem();
		cartItem.setBook(book);
		cartItem.setCount(count);
		//将条目添加到购物车
		cart.add(cartItem);
		return "f:/jsps/cart/list.jsp";
	}

	
	/**
	 * 清空购物车
	 * @return
	 */
	public String clear(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//得到购物车
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		cart.clear();
		return "f:/jsps/cart/list.jsp";
	}
	/**
	 * 删除某个条目 根据bid
	 * @return
	 */
	public String delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//得到购物车和图书id
		String bid = request.getParameter("bid");
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		cart.delete(bid);
		return "f:/jsps/cart/list.jsp";
	}

}
