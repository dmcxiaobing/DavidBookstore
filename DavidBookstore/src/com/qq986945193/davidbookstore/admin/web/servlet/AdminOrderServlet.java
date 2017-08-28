package com.qq986945193.davidbookstore.admin.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.david.webtools.common.base.BaseServlet;
import com.qq986945193.davidbookstore.admin.service.AdminCategoryService;
import com.qq986945193.davidbookstore.admin.service.AdminOrderService;
import com.qq986945193.davidbookstore.category.domain.Category;
import com.qq986945193.davidbookstore.order.domain.Order;
/**
 * 后台管理之订单管理
 */
public class AdminOrderServlet extends BaseServlet {
	private AdminOrderService adminOrderService = new AdminOrderService();

	/**
	 * 查看所有订单
	 */
	public String findAll(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/**
		 * 调用service方法，得到所有订单。然后转发到相应的界面
		 */
		List<Order> orderList = adminOrderService.findAll();
		request.setAttribute("orderList", orderList);
		return "f:/adminjsps/admin/order/list.jsp";
	}
	
	/**
	 * 根据订单状态查询订单
	 */
	public String findByState(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String state = request.getParameter("state");
		/**
		 * 调用service方法，得到所有订单。然后转发到相应的界面
		 */
		List<Order> orderList = adminOrderService.findByState(state);
		request.setAttribute("orderList", orderList);
		for (Order order : orderList) {
			System.out.println(order.getState());
		}
		return "f:/adminjsps/admin/order/list.jsp";
	}
	/**
	 *  付款
	 */
	public String pay(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String oid = request.getParameter("oid");
		adminOrderService.pay(oid);
		return findAll(request, response);
	}
	/**
	 *  发货
	 */
	public String send(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String oid = request.getParameter("oid");
		adminOrderService.send(oid);
		return findAll(request, response);
	}
	/**
	 *  确认收货
	 */
	public String makeSureSuccess(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String oid = request.getParameter("oid");
		adminOrderService.makeSureSuccess(oid);
		return findAll(request, response);
	}

}
