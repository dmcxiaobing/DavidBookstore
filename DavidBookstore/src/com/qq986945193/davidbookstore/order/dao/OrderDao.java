package com.qq986945193.davidbookstore.order.dao;

import java.sql.Timestamp;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;

import com.david.webtools.common.jdbc.TxQueryRunner;
import com.qq986945193.davidbookstore.order.domain.Order;
import com.qq986945193.davidbookstore.order.domain.OrderItem;

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

}
