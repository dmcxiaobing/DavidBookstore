<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>订单列表</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="content-type" content="text/html;charset=utf-8">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<style type="text/css">
	* {
		font-size: 11pt;
	}
	div {
		border: solid 2px rgb(78,78,78);
		width: 75px;
		height: 75px;
		text-align: center;
	}
	li {
		margin: 10px;
	}
</style>
  </head>
  
  <body style="background: rgb(254,238,189);">
<h1>我的订单</h1>

<table border="1" width="100%" cellspacing="0" background="black">
	<c:forEach items="${orderList }" var="order">
	<tr bgcolor="rgb(78,78,78)" bordercolor="rgb(78,78,78)" style="color: white;">
		<td colspan="6">
			订单：${order.oid }　成交时间：${order.ordertime }　金额：<font color="red"><b>${order.total }　</b></font>	
			<c:choose> 
				<c:when test="${order.state eq 1  }"> 
				<a onclick="return confirm('您真要修改为付款状态吗?')"  href="${pageContext.request.contextPath }/servlet/AdminOrderServlet?method=pay&oid=${order.oid}">付款</a>
				</c:when>
				<c:when test="${order.state eq 2 }"> <a onclick="return confirm('您真要修改为已发货状态吗?')" href="${pageContext.request.contextPath }/servlet/AdminOrderServlet?method=send&oid=${order.oid}">发货</a>
				</c:when>
				<c:when test="${order.state eq 3 }"> <a onclick="return confirm('您真要修改为交易成功状态吗?')" href="${pageContext.request.contextPath }/servlet/AdminOrderServlet?method=makeSureSuccess&oid=${order.oid}">确认收货</a>
				</c:when>
				<c:when test="${order.state eq 4 }"> 交易成功</c:when>
			</c:choose>
		</td>
	</tr>
		<c:forEach items="${order.orderItemList }" var="orderItem">
		<tr bordercolor="rgb(78,78,78)" align="center">
			<td width="15%">
				<div><img src="<c:url value='/${orderItem.book.image }'/>" height="75"/></div>
			</td>
			<td>${orderItem.book.bname }</td>
			<td>${orderItem.book.price }</td>
			<td>${orderItem.book.author }</td>
			<td>${orderItem.count }</td>
			<td>${orderItem.subtotal }</td>
		</tr>
		</c:forEach>
	
	
	</c:forEach>
	
  
 

<%-- 
	<tr bgcolor="rgb(78,78,78)" bordercolor="rgb(78,78,78)" style="color: white;">
		<td colspan="6">
			订单：153839427aa94f359fe51932d9f9e383　成交时间：2013-06-04 15:02:31　金额：<font color="red"><b>63.2</b></font>　
				   <a href="javascript:alert('发货成功！')">发货</a>
		</td>
	</tr>
	<tr bordercolor="rgb(78,78,78)" align="center">
		<td width="15%">
			<div><img src="<c:url value='/book_img/20029394-1_l.jpg'/>" height="75"/></div>
		</td>
		<td>书名：精通Spring2.x</td>
		<td>单价：63.2元</td>
		<td>作者：陈华雄</td>
		<td>数量：1</td>
		<td>小计：63.2元</td>
	</tr>
  


 
	<tr bgcolor="rgb(78,78,78)" bordercolor="rgb(78,78,78)" style="color: white;">
		<td colspan="6">
			订单：d1b85bfc71564b18bf7802582a9fd934　成交时间：2013-06-04 15:01:01　金额：<font color="red"><b>137.0</b></font>	已收货（完成）
		</td>
	</tr>
	<tr bordercolor="rgb(78,78,78)" align="center">
		<td width="15%">
			<div><img src="<c:url value='/book_img/20285763-1_l.jpg'/>" height="75"/></div>
		</td>
		<td>书名：Java核心技术卷1</td>
		<td>单价：68.5元</td>
		<td>作者：qdmmy6</td>
		<td>数量：2</td>
		<td>小计：137.0元</td>
	</tr>
  


	<tr bgcolor="rgb(78,78,78)" bordercolor="rgb(78,78,78)" style="color: white;">
		<td colspan="6">
			订单：o1　成交时间：2013-06-04 12:47:41　金额：<font color="red"><b>100.0</b></font>　未付款
		</td>
	</tr>
	<tr bordercolor="rgb(78,78,78)" align="center">
		<td width="15%">
			<div><img src="<c:url value='/book_img/9317290-1_l.jpg'/>" height="75"/></div>
		</td>
		<td>书名：Java编程思想（第4版）</td>
		<td>单价：75.6元</td>
		<td>作者：qdmmy6</td>
		<td>数量：2</td>
		<td>小计：300.0元</td>
	</tr>
	<tr bordercolor="rgb(78,78,78)" align="center">
		<td width="15%">
			<div><img src="<c:url value='/book_img/20285763-1_l.jpg'/>" height="75"/></div>
		</td>
		<td>书名：Java核心技术卷1</td>
		<td>单价：68.5元</td>
		<td>作者：qdmmy6</td>
		<td>数量：3</td>
		<td>小计：500.0元</td>
	</tr> --%>
</table>
  </body>
</html>
