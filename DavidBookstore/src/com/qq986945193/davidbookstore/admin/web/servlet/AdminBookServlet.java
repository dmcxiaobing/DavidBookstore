package com.qq986945193.davidbookstore.admin.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.david.webtools.common.base.BaseServlet;
import com.qq986945193.davidbookstore.book.domain.Book;
import com.qq986945193.davidbookstore.book.service.BookService;
import com.qq986945193.davidbookstore.category.service.CategoryService;
/**
 * 管理员后台管理页面图书的servlet
 */
public class AdminBookServlet extends BaseServlet {
	private BookService bookService = new BookService();
	private CategoryService categoryService = new CategoryService();
	/**
	 * 查看所有图书
	 */
	public String findAll(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
		List<Book> bookList = bookService.findAllBook();
		request.setAttribute("bookList", bookList);
		return "f:/adminjsps/admin/book/list.jsp";
	}
	/**
	 * 根据bid查询图书详情 并获取到所有分类
	 */
	public String findBookByBid(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
		Book book = bookService.loadByBid(request.getParameter("bid"));
		request.setAttribute("book", book);
		request.setAttribute("categoryList", categoryService.findAll());
		return "f:/adminjsps/admin/book/desc.jsp";
	}
	/**
	 * 根据bid修改图书信息
	 */
	public String modByBid(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println(request.getParameter("cid"));
		System.out.println(request.getParameter("bid"));

		return findAll(request, resp);
	}
}
