package top.aldrinLake.Servlet;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import top.aldrinLake.Model.user;
import top.aldrinLake.Service.Impl.*;
/**
 * Servlet implementation class loginServlet
 */
@WebServlet("/loginServlet")
public class loginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public loginServlet() {
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
		UserServeImpl usi = new UserServeImpl();
		user u = new user();
		u.setName(request.getParameter("username"));
		u.setPwd(request.getParameter("password"));
		u = usi.checkLogin(u);
		if(u.getIduser()==0){
			System.out.println("false");
		}else if(u.getIduser()!=0) {
			System.out.println("success");
		}
		List<user> users= new ArrayList<user>();
		users.add(u);
		//创建JSON集合
	    JSONArray jsonarray = JSONArray.fromObject(users);
	    //把JSON集合转出String字符串输出
	    String str = jsonarray.toString();
    	out.write(str);
		System.out.println(u.toString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
