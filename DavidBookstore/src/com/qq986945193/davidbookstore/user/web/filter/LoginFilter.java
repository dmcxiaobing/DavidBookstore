package com.qq986945193.davidbookstore.user.web.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.qq986945193.davidbookstore.user.domain.User;

/**
 * 拦截用户直接访问订单和购物车模块
 */
public class LoginFilter implements Filter {
	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		//从session域中获取用户信息，如果session中存在用户信息，则放行。否则转发
		User user = (User) httpServletRequest.getSession().getAttribute("session_user");
		//如果没有登陆直接访问购物车和订单，则直接转发到登录界面
		if (user == null) {
			httpServletRequest.setAttribute("msg", "您还没有登陆哦！");
			httpServletRequest.getRequestDispatcher("/jsps/user/login.jsp").forward(httpServletRequest, response);
			return;
		}else {
			chain.doFilter(request, response);
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
