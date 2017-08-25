package com.qq986945193.davidbookstore.admin.service;

import java.sql.SQLException;

import com.qq986945193.davidbookstore.admin.dao.AdminCategoryDao;
import com.qq986945193.davidbookstore.book.dao.BookDao;
import com.qq986945193.davidbookstore.category.domain.Category;

/**
 * 管理员后台的分类管理
 */
public class AdminCategoryService {
	private AdminCategoryDao dao = new AdminCategoryDao();
	private BookDao bookDao = new BookDao();
	/**
	 * 根据cid删除分类
	 */
	public void deleteByCid(String cid) throws SQLException {
		//首先查看当前分类下面是否有图书，如果有图书，则不允许删除
		int count = bookDao.getCountByCid(cid);
		if (count>0) {
			throw new RuntimeException("该分类下还有图书，不能删除");
		}
		//如果没有图书，则可以删除
		dao.deleteByCid(cid);
	}
	/**
	 * 添加分类
	 */
	public void add(Category category) {
		dao.add(category);
	}
	
	public Category loadByCid(String cid) {
		return dao.loadByCid(cid);
	}
	/**
	 * 编辑修改分类
	 */
	public void edit(Category category) {
		dao.edit(category);
	}

}
