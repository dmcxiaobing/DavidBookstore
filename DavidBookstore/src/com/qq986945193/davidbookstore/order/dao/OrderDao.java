package com.qq986945193.davidbookstore.order.dao;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

import com.david.webtools.common.jdbc.TxQueryRunner;
import com.david.webtools.common.utils.CommonUtils;
import com.qq986945193.davidbookstore.book.domain.Book;
import com.qq986945193.davidbookstore.order.domain.Order;
import com.qq986945193.davidbookstore.order.domain.OrderItem;
import com.qq986945193.davidbookstore.user.domain.User;

/**
 * 订单的持久层
 */
public class OrderDao {
	private QueryRunner queryRunner = new TxQueryRunner();
	/**
	 * 添加订单
	 * @param order
	 */
	public void addOrder(Order order) {
		try {
			String sql = "insert into orders values(?,?,?,?,?,?)";
			/**
			 * 处理util中的Date转换成sql中的timestamp
			 */
			Timestamp timestamp = new Timestamp(order.getOrdertime().getTime());
			System.out.println(timestamp);
			Object[] params = {order.getOid(),
					timestamp,
					order.getTotal()
					,order.getState()
					,order.getOwner().getUid()
					,order.getAddress()};
			queryRunner.update(sql,params);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 插入订单条目 不然只能看到订单，而看不到买的什么东西
	 */
	public void addOrderItemList(List<OrderItem> orderItemList) {
		/**
		 * QueryRunner类的batch（String sql,Object[][] params）
		 * 其中params是多个一维数组。每个一维数组斗鱼sql执行一次，多个就执行多次
		 * 把一个OrderItem对象转换成一个一维数组
		 */
		try {
			//因为这个订单条目中 有多个条目，而一个条目又有多重属性，对应数据库，所以要分开二维数组
			String sql = "insert into orderitem values(?,?,?,?,?)";
			/**
			 * 把orderItemList转换成二维数组。把一个orderItem对象转换成一个一维数组
			 */
			Object[][] params = new Object[orderItemList.size()][];
			//循环遍历orderItem，使用每个orderItem对象给骗人爱马仕中每个一维数组赋值
			for (int i = 0; i <orderItemList.size(); i++) {
				OrderItem item = orderItemList.get(i);
				params[i] = new Object[]{
						item.getIid()
						,item.getCount()
						,item.getSubtotal()
						,item.getOrder().getOid()
						,item.getBook().getBid()
				};
			}
			queryRunner.batch(sql, params);//执行批处理
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 我的订单，根据用户查找订单
	 */
	public List<Order> findAllOrders(User user) {
		try {
			//通过uid查询当前用户所有的order。然后循环遍历每一个order，并为其加载订单的条目OrderItem
			String sql = "select * from orders where uid = ?";
			//得到所有的订单
			List<Order> orders = queryRunner.query(sql, new BeanListHandler<Order>(Order.class),user.getUid());
			//循环遍历每个订单order，并未其加载订单条目
			for (Order order : orders) {
				loadOrderItems(order);//为每一个order订单添加它所有的订单条目
			}
			//返回订单列表
			return orders;
		
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 根据订单id加载指定的订单里面的所有订单条目
	 */
	private void loadOrderItems(Order order) throws SQLException {
		//查询两张表，订单条目和图书表 、、、订单条目中包含价格，数量， 图书表中包含书的名称与单价
		String sql = "select * from orderitem i,book b where i.bid=b.bid and oid = ?";
		/**
		 * 因为一行结果集对应的不再是一个Javabean，所以不能再使用BeanListHander,而是MapListHandler
		 */
		List<Map<String, Object>> mapList = queryRunner.query(sql, new MapListHandler(),order.getOid());
		/**
		 * mapList是多个map，每个map对应一行结果集
		 * 一行：
		 * {iid=C7AD5492F27D492189105FB50E55CBB6, count=2, subtotal=60.0, oid=1AE8A70354C947F8B81B80ADA6783155, bid=7, bname=精通Hibernate,price=30.0, author=Davidbook, image=book_img/8991366-1_l.jpg, cid=2}
		 * 
		 * 我们需要使用一个Map生成两个对象：orderItem和book.然后在建立两者的对应关系。把book设置为orderitem
		 * 
		 * 
		 */	
		/**
		 * 循环遍历每个map，使用map生成两个对象，然后建立关系。（最终结果一个OrderItem），把OrderItem保存起来
		 */
		List<OrderItem> orderItemList = toOrderItemList(mapList);
		order.setOrderItemList(orderItemList);//将订单条目设置到order中
	}
	/**
	 * 把MapList中每个Map转换成两个对象，并建立关系
	 */
	private List<OrderItem> toOrderItemList(List<Map<String, Object>> mapList) {
		List<OrderItem> orderItemList = new ArrayList<OrderItem>();
		for (Map<String, Object> map : mapList) {
			//循环给每一个OrderItem赋值
			OrderItem orderItem = toOrderItem(map);
			orderItemList.add(orderItem);
		}
			
		return orderItemList;
	}
	/**
	 * 把每一个map转换成一个OrderItem对象
	 */
	private OrderItem toOrderItem(Map<String, Object> map) {
		OrderItem orderItem = CommonUtils.toBean(map, OrderItem.class);
		Book book = CommonUtils.toBean(map, Book.class);
		orderItem.setBook(book);
		return orderItem;
	}

}
