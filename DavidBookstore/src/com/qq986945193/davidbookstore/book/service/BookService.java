package com.qq986945193.davidbookstore.book.service;

import java.sql.SQLException;
import java.util.List;

import com.qq986945193.davidbookstore.book.dao.BookDao;
import com.qq986945193.davidbookstore.book.domain.Book;

/**
 * 书籍的业务层
 */
public class BookService {

	public BookDao bookDao = new BookDao();

	/**
	 * 根据cid查询图书
	 * 
	 * @param cid
	 * @return
	 */
	public List<Book> findBookByCid(String cid) {
		// TODO Auto-generated method stub
		return bookDao.findBookByCid(cid);
	}

	/**
	 * 查询所有图书
	 */
	public List<Book> findAllBook() {

		return bookDao.findAllBook();
	}

	/**
	 * 根据bid查询book的详情
	 */
	public Book loadByBid(String bid) {
		return bookDao.loadByBid(bid);
	}

	/**
	 * 修改图书的信息
	 * @throws SQLException 
	 */
	public void updateBook(Book book) throws SQLException {
		bookDao.updateBook(book);
	}
	/**
	 * 添加图书
	 */
	public void add(Book book) {
		bookDao.add(book);
	}
	/**
	 * 删除图书
	 */
	public void deleteById(String bid) {
		bookDao.deleteById(bid);
	}
	/**
	 * 编辑图书，修改图书信息
	 * @param formBook 
	 */
	public void editBook(String bid, Book formBook) {
		bookDao.edit(bid,formBook);
	}
}
