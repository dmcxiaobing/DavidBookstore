<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>My JSP 'bookdesc.jsp' starting page</title>

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
body {
	font-size: 10pt;
	background: rgb(254, 238, 189);
}

div {
	margin: 20px;
	border: solid 2px gray;
	width: 150px;
	height: 150px;
	text-align: center;
}

li {
	margin: 10px;
}
</style>
<script type="text/javascript" src="<c:url value = '/menu/ajaxutils.js'/>"></script>
<script type="text/javascript" src="<c:url value = '/menu/jquery-1.5.1.js'/>"></script>
<!-- 获取option的值 -->
<script type="text/javascript">

  $(document).ready(function () {
             $("#selectOption").change(function () {
               //change事件
               $.post("<c:url value='/servlet/AdminBookServlet?method=getCid'/>", {"cid":cid});
             });
         });

	var cid;
	function show_sub(v) {
		cid = v;
	//	alert(cid)
		/* 这里使用jQuery将option提交到后台 */
		$.post("<c:url value='/servlet/AdminBookServlet?method=getCid'/>", {"cid":cid});
	/* 	ajax(
 			{
 				url:"<c:url value='/servlet/AdminBookServlet?method=getCid'/>",
 				type:"json",
 				callback:function(data){
					alert(data)
 				}
			 			
 			
 			} 
 		
 		
 		);*/
	}

</script>

</script>
</head>

<body>
		<div>
		<img src="<c:url value='/${book.image }'/>" border="0" />
		</div>
	<form style="margin:20px;" id="form"
		action="<c:url value='/servlet/AdminBookServlet?method=modByBid&bid=${book.bid }&cid'/>"
		method="post">
		<input type="hidden" name="image" value="${book.image }"/>
		图书名称：<input
			type="text" name="bname" value="${book.bname }" /><br /> 图书单价：<input
			type="text" name="price" value="${book.price }" /><br /> 图书作者：<input
			type="text" name="author" value="${book.author }" /><br /> 
			
			图书分类：<select
			style="width: 150px; height: 20px;" name="cid" id="selectOption"
			onchange="show_sub(this.options[this.options.selectedIndex].value)"
			onblur="show_sub(this.options[this.options.selectedIndex].value)"> -->
			<c:forEach items="${categoryList }" var="category">
				<!-- 循环遍历所有的图书名称 -->
				<option value="${category.cid }"  <c:if test="${category.cid eq book.category.cid }">selected="selected"</c:if>  >${category.cname }</option>
				  				<%-- <option id="${category.cid }" value="${category.cname }">${category.cname }</option> --%>
			</c:forEach> 

			<!-- 	<!-- 
			<option value="">JavaScript</option>
			<option value="">Hibernate</option>
			<option value="">Struts</option> 
			-->

			<!-- <option value="" selected='selected'>Spring</option> -->
		</select><br />
		<%-- 	<input type="submit" name="method" value="<c:url value='/servlet/AdminBookServlet?method=deleteByBid'/>" onclick="return confirm('是否真要删除该图书？');"/> --%>
		<input type="submit" name="method" value="修改图书信息" onclick="modByBid()" />
	</form>

	<!-- <select name="type"
		onchange="show_sub(this.options[this.options.selectedIndex].value)">
		<option value="0">请选择主类别</option>
		<option value="1">1</option>
		<option value="2">2</option>
	</select> -->
</body>
</html>
