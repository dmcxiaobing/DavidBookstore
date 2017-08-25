package com.qq986945193.davidbookstore.admin.dao;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.david.webtools.common.jdbc.TxQueryRunner;
import com.qq986945193.davidbookstore.category.domain.Category;

/**
 * 管理员后台的分类管理
 */
public class AdminCategoryDao {
	private QueryRunner queryRunner = new TxQueryRunner();

	/**
	 * 根据Cid删除分类
	 */
	public void deleteByCid(String cid) throws SQLException {
		String sql = "delete from category where cid = ?";
		queryRunner.update(sql, cid);
	}

	/**
	 * 添加分类
	 */
	public void add(Category category) {
		String sql = "insert into category values(?,?)";
		try {
			queryRunner.update(sql, category.getCid(), category.getCname());

		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	/**
	 * 根据分类ID获取详情
	 * @param cid
	 * @return
	 */
	public Category loadByCid(String cid) {
		
		try {
			String sql = "select * from category where cid = ?";
			return queryRunner.query(sql, new BeanHandler<Category>(Category.class),cid);
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	/**
	 * 编辑修改分类
	 */
	public void edit(Category category) {
		
		try {
			String sql = "update category set cname = ? where cid = ?";
			queryRunner.update(sql, category.getCname(),category.getCid());
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

}
