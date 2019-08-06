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
 * Servlet implementation class getMyUnQualifiedList
 */
@WebServlet("/getMyUnQualifiedList")
public class getMyUnQualifiedList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public getMyUnQualifiedList() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=UTF-8");//��ֹ������޷���ʾ����
		Writer out = response.getWriter(); 
		int page = Integer.parseInt(request.getParameter("page"));
		int userId = Integer.parseInt(request.getParameter("userId"));
		user u =new user(userId);
		System.out.println("��"+page);
		UserServeImpl usi = new UserServeImpl();
		String str = usi.getMyUnQualifiedList(u, page);
		out.write(str);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
