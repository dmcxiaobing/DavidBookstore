package com.qq986945193.davidbookstore.admin.service;

import java.sql.SQLException;
import java.util.List;

import com.qq986945193.davidbookstore.admin.dao.AdminOrderDao;
import com.qq986945193.davidbookstore.order.dao.OrderDao;
import com.qq986945193.davidbookstore.order.domain.Order;

/**
 * 业务类 后台
 */
public class AdminOrderService {
	private AdminOrderDao adminOrderDao = new AdminOrderDao();
	private OrderDao orderDao = new OrderDao();

	/**
	 * 查看所有订单
	 */
	public List<Order> findAll() {
		return adminOrderDao.findAll();
	}

	public List<Order> findByState(String state) {
		return adminOrderDao.findByState(state);
	}

	/**
	 * 付款，首先查询数据库的状态是否为未付款，如果是则将状态改为已付款的状态
	 */
	public void pay(String oid) {
		try {
			int state = orderDao.getStateByOid(oid);
			if (state == 1) {
				// 如果等于1 则是未付款可以付款 ..然后将状态改为已付款的状态
				orderDao.updateOrderState(oid, 2);
			}else {
				throw new RuntimeException("请不要乱付款");
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}

	}
	/**
	 * 发货
	 */
	public void send(String oid) {
		try {
			int state = orderDao.getStateByOid(oid);
			if (state == 2) {
				// 如果等于1 则是未付款可以付款 ..然后将状态改为已付款的状态
				orderDao.updateOrderState(oid, 3);
			}else {
				throw new RuntimeException("请不要乱发货");
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		
	}
	/**
	 *  确认收货
	 */
	public void makeSureSuccess(String oid) {
		try {
			int state = orderDao.getStateByOid(oid);
			if (state == 3) {
				// 如果等于1 则是未付款可以付款 ..然后将状态改为已付款的状态
				orderDao.updateOrderState(oid, 4);
			}else {
				throw new RuntimeException("请不要乱确认收货");
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

}
