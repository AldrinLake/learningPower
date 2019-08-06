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
import top.aldrinLake.Service.Impl.UserServeImpl;

/**
 * Servlet implementation class uploadGrades
 */
@WebServlet("/uploadGrades")
public class uploadGradesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public uploadGradesServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);
		response.setContentType("text/html;charset=UTF-8");//防止浏览器无法显示中文
		Writer out = response.getWriter(); 
		int idUser = Integer.parseInt(request.getParameter("userId"));
		int grades = Integer.parseInt(request.getParameter("grades"));
		System.out.println("获取："+idUser+","+grades);
		user u = new user(idUser);
		u.setGrades(grades);
		UserServeImpl usi = new UserServeImpl();
		usi.setScore(u);//上传成绩
		
		/*返回成绩上传之后用户的状态*/
		user u2 = new user(idUser);
		u2 = usi.selectUserInfo(u2);
		List<user> users= new ArrayList<user>();
		users.add(u2);
		//创建JSON集合
	    JSONArray jsonarray = JSONArray.fromObject(users);
	    //把JSON集合转出String字符串输出
	    String str = jsonarray.toString();
	    System.out.println("返回数据："+str);
    	out.write(str);
		//System.out.println(u.toString());
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
    	
	}

}
