package com.qq986945193.davidbookstore.admin.dao;

import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.david.webtools.common.jdbc.TxQueryRunner;
import com.qq986945193.davidbookstore.order.dao.OrderDao;
import com.qq986945193.davidbookstore.order.domain.Order;

/**
 * 管理员订单类
 * @Author ：程序员小冰
 * @新浪微博 ：http://weibo.com/mcxiaobing
 * @GitHub: https://github.com/QQ986945193
 */
public class AdminOrderDao {
	private QueryRunner queryRunner = new TxQueryRunner();
	private OrderDao orderDao = new OrderDao();
	
	/**
	 * 查询所有订单
	 */
	public List<Order> findAll() {
		try {
			//查询所有订单，然后循环遍历每个订单order，并为其加载订单条目OrderItem
			String sql = "select * from orders";
			List<Order> orderList = queryRunner.query(sql, new BeanListHandler<Order>(Order.class));
			//循环遍历每个订单Order,并为其加载订单条目
			for (Order order : orderList) {
				orderDao.loadOrderItems(order);//为其每一个订单order添加它所有的订单条目
			}
			//返回订单列表
			return orderList;
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		
	}
	/**
	 * 根据订单状态查询订单
	 */
	public List<Order> findByState(String state) {
		try {
			//查询所有订单，然后循环遍历每个订单order，并为其加载订单条目OrderItem
			String sql = "select * from orders where state = ?";
			List<Order> orderList = queryRunner.query(sql, new BeanListHandler<Order>(Order.class),state);
			//循环遍历每个订单Order,并为其加载订单条目
			for (Order order : orderList) {
				orderDao.loadOrderItems(order);//为其每一个订单order添加它所有的订单条目
			}
			//返回订单列表
			return orderList;
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

}
