<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>管理员登录页面</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
     <script type="text/javascript">  
        function checkUserName(obj){  
            var username = obj;  
            var checkUserNameResult = document.getElementById("checkUserNameResult");    
            if(username.trim().length==0){  
                  checkUserNameResult.innerHTML = "用户名不能为空";    
                  obj.focus();  
            }else{  
                 checkUserNameResult.innerHTML = "";    
            }  
        }  
    </script>  
  
    <script type="text/javascript">  
        function checkPassword(obj){  
            var password = obj;  
            var checkPasswordResult = document.getElementById("checkPasswordResult");   
            if(password.trim().length==0){  
                checkPasswordResult.innerHTML = "密码不能为空";    
                obj.focus();  
            }else{  
                 checkPasswordResult.innerHTML = "";    
            }  
        }  
  
    </script> 
  </head>
  
  <body>
<h1>管理员登录页面</h1>
<hr/>

  <p style="font-weight: 900; color: red">${msg }</p>
<form action="<c:url value='/servlet/AdminLoginServlet'/>" method="post">
	<input type = "hidden" name="method" value = "login"/>
	管理员账户：<input type="text" name="adminname" value="" id = "adminname" onblur="checkUserName(this.value);"><span id="checkUserNameResult"></span><br/>
	密　　　码：<input type="password" name="password" id = "password" onblur="checkPassword(this.value);"/><span id="checkPasswordResult"></span><br/>
	<input type="submit" value="进入后台"/>
</form>
  </body>
</html>
