package com.qq986945193.davidbookstore.order.web.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.david.webtools.common.base.BaseServlet;
import com.david.webtools.common.utils.CommonUtils;
import com.qq986945193.davidbookstore.cart.domain.Cart;
import com.qq986945193.davidbookstore.cart.domain.CartItem;
import com.qq986945193.davidbookstore.order.domain.Order;
import com.qq986945193.davidbookstore.order.domain.OrderItem;
import com.qq986945193.davidbookstore.order.service.OrderService;
import com.qq986945193.davidbookstore.user.domain.User;

/**
 * 订单的控制器
 */
public class OrderServlet extends BaseServlet {

	private OrderService orderService = new OrderService();

	/**
	 * 我的订单
	 */
	public String myOrders(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = (User) request.getSession().getAttribute("session_user");
		//根据用户去查找订单
		List<Order> orderList = orderService.findAllOrders(user);
		request.setAttribute("orderList", orderList);
		return "f:/jsps/order/list.jsp";
	}

	/**
	 * 添加订单。把session中的购物车来生成order对象
	 */
	public String addOrder(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/**
		 * 1，从session中取出cart，使cart生成order对象。 调用service非农股份完成天假订单。
		 * 2，保存order到request域中，转发到/jsps/order/desc.jsp
		 */
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		// 把cart转换成order对象
		/**
		 * 创建order对象，并设置属性
		 */
		Order order = new Order();
		// 设置订单号
		order.setOid(CommonUtils.getUUIDRandomNum());
		User user = (User) request.getSession().getAttribute("session_user");
		order.setOwner(user);// 设置订单所有者
		order.setOrdertime(new Date());// 设置下单时间
		order.setState(1);// 设置订单状态，订单状态为1，表示未付款
		order.setTotal(cart.getTotal());// 设置订单的合计，从cart中获取合计 总价格
		/**
		 * 创建订单条目集合
		 * 
		 * cartItemList --- orderItemList
		 * 
		 */
		List<OrderItem> orderItems = new ArrayList<OrderItem>();
		// 循环遍历cart中所有的cartItem，使用每一个CartItem对象创建OrderItem对象并添加到订单条目中
		for (CartItem cartItem : cart.getCartItems()) {
			// 创建订单条目
			OrderItem orderItem = new OrderItem();
			// 设置条目的图书
			orderItem.setBook(cartItem.getBook());
			orderItem.setCount(cartItem.getCount());// 设置条目的数量
			// 设置条目的id
			orderItem.setIid(CommonUtils.getUUIDRandomNum());
			// 设置条目的小计
			orderItem.setSubtotal(cartItem.getSubtotal());
			// 所有订单条目均属于一个订单
			orderItem.setOrder(order);

			// 把订单条目添加到集合中
			orderItems.add(orderItem);
		}
		// 把所有的订单条目添加到订单中
		order.setOrderItemList(orderItems);
		// 清空购物车，生成订单了所有清空
		cart.clear();
		/**
		 * 调用orderService添加订单
		 */
		orderService.add(order);
		// 保存order到request域中，转发
		request.setAttribute("order", order);
		return "f:/jsps/order/desc.jsp";
	}

	/**
	 * 支付去银行
	 */
	public String zhiFu(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		return "f:/jsps/order/desc.jsp";
	}

}
