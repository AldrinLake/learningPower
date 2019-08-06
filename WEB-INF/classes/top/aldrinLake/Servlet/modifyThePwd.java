package top.aldrinLake.Servlet;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import top.aldrinLake.Model.user;
import top.aldrinLake.Service.Impl.UserServeImpl;

/**
 * Servlet implementation class modifyThePwd
 * 修改密码
 */
@WebServlet("/modifyThePwd")
public class modifyThePwd extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public modifyThePwd() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=UTF-8");//防止浏览器无法显示中文
		Writer out = response.getWriter(); 
		int userId = Integer.parseInt(request.getParameter("userId"));
		String newPwd = request.getParameter("newPwd");
		String oldPwd = request.getParameter("oldPwd");
		
		user u = new user(userId);
		UserServeImpl usi = new UserServeImpl();
		boolean str = usi.modifyThePwd(u, oldPwd, newPwd);
		if(str) {
			out.write("true");
		}else {
			out.write("false");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
