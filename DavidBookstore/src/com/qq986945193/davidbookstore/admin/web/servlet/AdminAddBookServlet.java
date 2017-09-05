package com.qq986945193.davidbookstore.admin.web.servlet;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.ImageIcon;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.david.webtools.common.utils.CommonUtils;
import com.qq986945193.davidbookstore.book.domain.Book;
import com.qq986945193.davidbookstore.book.service.BookService;
import com.qq986945193.davidbookstore.category.domain.Category;
import com.qq986945193.davidbookstore.category.service.CategoryService;
import com.sun.prism.Image;


/**
 * 添加图书。上传图书
 * 
 * @Author ：程序员小冰
 * @新浪微博 ：http://weibo.com/mcxiaobing
 * @GitHub: https://github.com/QQ986945193
 */
public class AdminAddBookServlet extends HttpServlet {
	private BookService bookService = new BookService();
	private CategoryService cateGoryService = new CategoryService();
	/**
	 * 上传图书
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//处理post乱码问题
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		/**
		 * 把表单数据封装到Book对象中
		 */
		//上传三步，创建工厂，得到解析器。使用解析器去解析对象。得到List<FileItem>
		//设置临时目录15KB，
		DiskFileItemFactory factory = new DiskFileItemFactory(15 * 1024, new File("D:/Users"));
		//得到解析器
		ServletFileUpload servletFileUpload = new ServletFileUpload(factory);
		//设置单个文件大小为15KB
		servletFileUpload.setFileSizeMax(20*1024);
		//解析
		try {
			List<FileItem> fileItems = servletFileUpload.parseRequest(request);
			/**
			 * 把FileItemList中的数据封装到Book对象中。再把所有的普通表单字段数据先封装到map中
			 * 最后把map数据封装到book对象中
			 */
			Map<String, String> map = new HashMap<String,String>();
			for (FileItem fileItem : fileItems) {
				if (fileItem.isFormField()) {
					//如果是正常的表单数据。进行封装到map
					map.put(fileItem.getFieldName(), fileItem.getString("utf-8"));
				}
			}
			Book book = CommonUtils.toBean(map, Book.class);
			//为book设置ID
			book.setBid(CommonUtils.getUUIDRandomNum());
			//需要把map中的cid值封装到Category对象中，然后再和book结合。
			Category category = CommonUtils.toBean(map, Category.class);
			book.setCategory(category);
			/**
			 * 保存上传的文件。保存的目录和文件名称
			 */
			//得到保存的目录。。D:\KaiFaGongJu\apache-tomcat-8.5.12\webapps\DavidBookstore\book_img
			String savePath = this.getServletContext().getRealPath("/book_img");
			System.out.println("savePath="+savePath);
			//得到文件名称，给原来文件名称添加UUID，防止文件名冲突
			String fileName = CommonUtils.getUUIDRandomNum()+"_"+fileItems.get(1).getName();//这里直接写1，下标第二个
		
			/** 
			 * 校验扩展名 转换为小写a.jpg b.png
			 * false
			 */
			if (!fileName.toLowerCase().endsWith("jpg")&&!fileName.toLowerCase().endsWith("png")) {
				request.setAttribute("msg", "你上传的图片不是jpg.png扩展名");
				request.setAttribute("categoryList", cateGoryService.findAll());
				request.getRequestDispatcher("/adminjsps/admin/book/add.jsp").forward(request, response);
				return;
			}
			
			
			//使用目录和文件名称，创建目标文件
			File destFile = new File(savePath,fileName);
			//保存文件到目标文件位置
			fileItems.get(1).write(destFile);
			
			
			
			/**
			 * 校验图片的尺寸
			 */
			java.awt.Image image = new ImageIcon(destFile.getAbsolutePath()).getImage();
			if (image.getWidth(null)>200||image.getHeight(null)>200) {
				//删除文件，并提示
				destFile.delete();//删除这个文件
				request.setAttribute("msg", "您上传的文件尺寸超过了200*200");
				request.setAttribute("categoryList", cateGoryService.findAll());
				request.getRequestDispatcher("/adminjsps/admin/book/add.jsp").forward(request, response);
				return;
			}
			
			
			/**
			 * 设置Book对象的img，即将图片路径存储到数据库
			 * 
			 */
			book.setImage("book_img/"+fileName);
			/**
			 * 使用BookService完成保存
			 */
			bookService.add(book);
			
			/*
			 * 5. 返回到图书列表
			 */
			request.getRequestDispatcher("/servlet/AdminBookServlet?method=findAll")
					.forward(request, response);
		} catch (Exception e) {
			if (e instanceof FileUploadBase.FileSizeLimitExceededException) {
				request.setAttribute("msg", "你上传的文件超过了15KB");
				request.setAttribute("categoryList", cateGoryService.findAll());
				request.getRequestDispatcher("/adminjsps/admin/book/add.jsp").forward(request, response);
			}
			e.printStackTrace();
		}
		
	}
}
