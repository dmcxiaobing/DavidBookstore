package com.qq986945193.davidbookstore.admin.web.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.david.webtools.common.base.BaseServlet;
import com.david.webtools.common.utils.CommonUtils;
import com.qq986945193.davidbookstore.book.domain.Book;
import com.qq986945193.davidbookstore.book.service.BookService;
import com.qq986945193.davidbookstore.category.service.CategoryService;
import com.qq986945193.davidbookstore.common.constants.Api;

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
	public String findBookByBid(HttpServletRequest request, HttpServletResponse resp)
			throws ServletException, IOException {
		Book book = bookService.loadByBid(request.getParameter("bid"));
		request.setAttribute("book", book);
		request.setAttribute("categoryList", categoryService.findAll());
		return "f:/adminjsps/admin/book/desc.jsp";
	}

	/**
	 * 根据bid修改图书信息
	 * @throws SQLException 
	 */
	public String modByBid(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException, SQLException {
		String bid = request.getParameter("bid");
		Book book = CommonUtils.toBean(request.getParameterMap(), Book.class);
		//修改图书信息
		bookService.updateBook(book);
		return findAll(request, resp);
	}


	/**
	 * 修改图书信息时获取到分类ID
	 */
	public String getCid(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
		String cid = request.getParameter("cid");
		System.out.println(cid);
		request.getSession().setAttribute(Api.SESSION_CID_ADMIN, cid);
		return null;
	}
}
