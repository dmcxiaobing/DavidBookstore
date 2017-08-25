package com.qq986945193.davidbookstore.admin.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.david.webtools.common.base.BaseServlet;
import com.qq986945193.davidbookstore.book.domain.Book;
import com.qq986945193.davidbookstore.book.service.BookService;
/**
 * 管理员后台管理页面图书的servlet
 */
public class AdminBookServlet extends BaseServlet {
	private BookService bookService = new BookService();
	/**
	 * 查看所有图书
	 */
	public String findAll(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
		List<Book> bookList = bookService.findAllBook();
		request.setAttribute("bookList", bookList);
		return "f:/adminjsps/admin/book/list.jsp";
	}
}
