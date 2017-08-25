package com.qq986945193.davidbookstore.order.service;

import java.sql.SQLException;
import java.util.List;

import com.david.webtools.common.jdbc.JdbcUtils;
import com.qq986945193.davidbookstore.order.dao.OrderDao;
import com.qq986945193.davidbookstore.order.domain.Order;
import com.qq986945193.davidbookstore.user.domain.User;

/**
 * 订单的业务层
 */
public class OrderService {
	private OrderDao orderDao = new OrderDao();
	/**
	 * 添加订单到数据库  这个需要处理事务
	 * @param order
	 */
	public void add(Order order) {
		try {
			//开启事务
			JdbcUtils.beginTransaction();
			orderDao.addOrder(order);//插入订单
			orderDao.addOrderItemList(order.getOrderItemList());//插入订单中的所有条目
			//提交事务
			JdbcUtils.commitTransaction();
		} catch (Exception e) {
			//回滚事务
			try {
				JdbcUtils.rollbackTransaction();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	/**
	 * 我的订单，根据用户去查找订单
	 */
	public List<Order> findAllOrders(User user) {
		return orderDao.findAllOrders(user);
	}
	/**
	 * 根据oid查询到订单的详情
	 */
	public Order findOrderByOid(String oid) {
		return orderDao.findOrderByOid(oid);
	}
	/**
	 * 设置订单状态。根据订单id
	 */
	public void setOrderStatus(String r6_Order) throws SQLException {
		/**
		 * 1,获取订单的状态，如果订单状态未付款则修改，否则不再修改
		 */
		int state = orderDao.getStateByOid(r6_Order);
		if (state == 1) {
			//修改订单状态为2
			orderDao.updateOrderState(r6_Order,2);
		}
	}
	/**
	 * 确认收货
	 * @throws Exception 
	 */
	public void confirm(String oid) throws Exception {
		/*
		 * 1,获取订单的状态，如果订单状态是已付款则修改，否则不再修改
		 */
		int state = orderDao.getStateByOid(oid);
		if (state != 3) {
			throw new RuntimeException("不是付款状态，请不要操作");
		}
		/**
		 * 2，修改订单状态为4，表示交易成功
		 */
		orderDao.updateOrderState(oid, 4);
	}

}
