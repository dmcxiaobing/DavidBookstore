package com.qq986945193.davidbookstore.admin.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.david.webtools.common.base.BaseServlet;
import com.david.webtools.common.utils.CommonUtils;
import com.qq986945193.davidbookstore.admin.service.AdminCategoryService;
import com.qq986945193.davidbookstore.category.domain.Category;
import com.qq986945193.davidbookstore.category.service.CategoryService;

/**
 * 管理员后台的分类管理
 */
public class AdminCategoryServlet extends BaseServlet {
	private CategoryService categoryService = new CategoryService();
	private AdminCategoryService adminCategoryService = new AdminCategoryService();

	/**
	 * 查看分类
	 */
	public String findAll(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/**
		 * 调用service方法，得到所有分类。然后转发到相应的界面
		 */
		List<Category> categoryList = categoryService.findAll();
		request.setAttribute("categoryList", categoryList);
		return "f:/adminjsps/admin/category/list.jsp";
	}

	/**
	 * 根据分类ID删除分类
	 */

	public String deleteByCid(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String cid = request.getParameter("cid");
		try {
			adminCategoryService.deleteByCid(cid);
			// request.setAttribute("msg", "删除成功");
			return findAll(request, response);
		} catch (Exception e) {
			request.setAttribute("msg", e.getMessage());
			return "f:/adminjsps/msg.jsp";
		}

	}

	/**
	 * 添加分类add
	 */
	public String add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/**
		 * 获取到输入的内容，然后添加到数据库。返回到列表页面
		 */
		Category category = CommonUtils.toBean(request.getParameterMap(), Category.class);
		category.setCid(CommonUtils.getUUIDRandomNum());
		adminCategoryService.add(category);
		return findAll(request, response);

	}
	
	/**
	 * 修改分类之前的获取到数据，并转发到jsp
	 */
	public String editPre(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cid = request.getParameter("cid");
		request.setAttribute("category", adminCategoryService.loadByCid(cid));
		return "f:/adminjsps/admin/category/mod.jsp";
	}
	
	/**
	 * 修改分类 确认
	 */
	public String edit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Category category = CommonUtils.toBean(request.getParameterMap(), Category.class);
		adminCategoryService.edit(category);
		return findAll(request, response);
	}
}
