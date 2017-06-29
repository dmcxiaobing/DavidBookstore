package com.qq986945193.davidbookstore.cart.domain;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 购物车javabean
 */
public class Cart {
	private Map<String, CartItem> map = new LinkedHashMap<String, CartItem>();

	/**
	 * 计算合计 总价格
	 */
	public double getTotal() {
		// 合计=所有条目的小计之和
		BigDecimal total = new BigDecimal("0");
		for (CartItem cartItem : map.values()) {
			BigDecimal subtotal = new BigDecimal("" + cartItem.getSubtotal());
			total = total.add(subtotal);
		}
		return total.doubleValue();
	}

	/**
	 * 添加条目到购物车中
	 */
	public void add(CartItem cartItem) {
		if (map.containsKey(cartItem.getBook().getBid())) {// 判断原来购物车中是否存在该条目
			// 返回已经存在的条目
			CartItem cartitem = map.get(cartItem.getBook().getBid());
			// 设置老条目的数量=自己数量+新条目的数量
			cartitem.setCount(cartItem.getCount() + cartitem.getCount());
			map.put(cartItem.getBook().getBid(), cartitem);

		} else {
			map.put(cartItem.getBook().getBid(), cartItem);
		}
	}

	/**
	 * 清空所有条目数
	 */
	public void clear() {
		map.clear();
	}

	/**
	 * 删除指定的条目
	 */
	public void delete(String bid) {
		map.remove(bid);
	}

	/**
	 * 获取所有条目
	 */
	public Collection<CartItem> getCartItems() {
		return map.values();
	}

}
