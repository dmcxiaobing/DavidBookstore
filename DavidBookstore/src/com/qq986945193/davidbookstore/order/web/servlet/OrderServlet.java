package com.qq986945193.davidbookstore.order.web.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.david.webtools.common.base.BaseServlet;
import com.david.webtools.common.utils.CommonUtils;
import com.qq986945193.davidbookstore.cart.domain.Cart;
import com.qq986945193.davidbookstore.cart.domain.CartItem;
import com.qq986945193.davidbookstore.common.utils.PaymentUtils;
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
	 * 加载订单。根据oid加载详情
	 */
	public String load(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String oid = request.getParameter("oid");
		Order order = orderService.findOrderByOid(oid);
		request.setAttribute("order", order);
		return "f:/jsps/order/desc.jsp";
	}

	/**
	 * 我的订单
	 */
	public String myOrders(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		User user = (User) request.getSession().getAttribute("session_user");
		// 根据用户去查找订单
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
	public String pay(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Properties props = new Properties();
		InputStream input = this.getClass().getClassLoader().getResourceAsStream("merchantInfo.properties");
		props.load(input);
		/*
		 * 准备13参数
		 */
		String p0_Cmd = "Buy";
		String p1_MerId = props.getProperty("p1_MerId");
		String p2_Order = request.getParameter("oid");
		String p3_Amt = "0.01";
		String p4_Cur = "CNY";
		String p5_Pid = "";
		String p6_Pcat = "";
		String p7_Pdesc = "";
		String p8_Url = props.getProperty("p8_Url");
		String p9_SAF = "";
		String pa_MP = "";
		String pd_FrpId = request.getParameter("pd_FrpId");
		String pr_NeedResponse = "1";

		/*
		 * 计算hmac
		 */
		String keyValue = props.getProperty("keyValue");
		String hmac = PaymentUtils.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt, p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc,
				p8_Url, p9_SAF, pa_MP, pd_FrpId, pr_NeedResponse, keyValue);

		/*
		 * 连接易宝的网址和13+1个参数
		 */
		StringBuilder url = new StringBuilder(props.getProperty("url"));
		url.append("?p0_Cmd=").append(p0_Cmd);
		url.append("&p1_MerId=").append(p1_MerId);
		url.append("&p2_Order=").append(p2_Order);
		url.append("&p3_Amt=").append(p3_Amt);
		url.append("&p4_Cur=").append(p4_Cur);
		url.append("&p5_Pid=").append(p5_Pid);
		url.append("&p6_Pcat=").append(p6_Pcat);
		url.append("&p7_Pdesc=").append(p7_Pdesc);
		url.append("&p8_Url=").append(p8_Url);
		url.append("&p9_SAF=").append(p9_SAF);
		url.append("&pa_MP=").append(pa_MP);
		url.append("&pd_FrpId=").append(pd_FrpId);
		url.append("&pr_NeedResponse=").append(pr_NeedResponse);
		url.append("&hmac=").append(hmac);

		System.out.println(url);

		/*
		 * 重定向到易宝
		 */
		response.sendRedirect(url.toString());
		return null;
	}

	/**
	 * 这个方法是易宝回调方法 我们必须要判断调用本方法的是不是易宝！
	 */
	public String back(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		/*
		 * 1. 获取11 + 1
		 */
		String p1_MerId = request.getParameter("p1_MerId");
		String r0_Cmd = request.getParameter("r0_Cmd");
		String r1_Code = request.getParameter("r1_Code");
		String r2_TrxId = request.getParameter("r2_TrxId");
		String r3_Amt = request.getParameter("r3_Amt");
		String r4_Cur = request.getParameter("r4_Cur");
		String r5_Pid = request.getParameter("r5_Pid");
		String r6_Order = request.getParameter("r6_Order");
		String r7_Uid = request.getParameter("r7_Uid");
		String r8_MP = request.getParameter("r8_MP");
		String r9_BType = request.getParameter("r9_BType");

		String hmac = request.getParameter("hmac");

		/*
		 * 2. 校验访问者是否为易宝！
		 */
		Properties props = new Properties();
		InputStream input = this.getClass().getClassLoader().getResourceAsStream("merchantInfo.properties");
		props.load(input);
		String keyValue = props.getProperty("keyValue");

		boolean bool = PaymentUtils.verifyCallback(hmac, p1_MerId, r0_Cmd, r1_Code, r2_TrxId, r3_Amt, r4_Cur, r5_Pid,
				r6_Order, r7_Uid, r8_MP, r9_BType, keyValue);

		if (!bool) {// 如果校验失败
			request.setAttribute("msg", "您不是什么好东西！");
			return "f:/jsps/msg.jsp";
		}

		/*
		 * 3. 获取状态订单，确定是否要修改订单状态，以及添加积分等业务操作
		 */
		orderService.setOrderStatus(r6_Order);// 有可能对数据库进行操作，也可能不操作！

		/*
		 * 4. 判断当前回调方式 为“1”: 浏览器重定向; 为“2”: 服务器点对点通讯. 如果为点对点，需要回馈以success开头的字符串
		 */
		if (r9_BType.equals("2")) {
			response.getWriter().print("success");
		}
		/*
		 * 5. 保存成功信息，转发到msg.jsp
		 */
		request.setAttribute("msg", "支付成功！等待卖家发货！你慢慢等~");
		return "f:/jsps/msg.jsp";
	}

	/**
	 * 确认收货
	 */
	public String confirm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		String oid = request.getParameter("oid");// 得到订单id，然后修改状态
		try {
			orderService.confirm(oid);
			request.setAttribute("msg", "确认收货成功");
		} catch (Exception e) {
			request.setAttribute("msg", e.getMessage());
		}
		return "f:/jsps/msg.jsp";

	}

}
