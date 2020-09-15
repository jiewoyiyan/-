package myservlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import mybean.User;
import mydb.jdbcUtils;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet(name="LoginServlet",urlPatterns= {"/login"})
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		response.setContentType("application/json;charset=UTF-8");
		String name=request.getParameter("name");
		String password=request.getParameter("password");
		System.out.print("执行了");
		System.out.print(name+password);
		if(name!=null&&password!=null)
		{
			String sql="select * from users where name=? and password=?";
			try(Connection conn=jdbcUtils.getConnection();PreparedStatement pstat=conn.prepareStatement(sql);
					PrintWriter out=response.getWriter();)
			{
				pstat.setString(1,name);
				pstat.setString(2,password);
				if(pstat.execute())
				{
//					List<User> list=new ArrayList<User>(); 
					User user=new User();
					ResultSet rs=pstat.getResultSet();
					if(rs.next())
					{
						user.setName(rs.getString("name"));
						user.setPassword(rs.getString("password"));
						user.setSex(rs.getString("sex"));
						user.setAge(rs.getString("age"));
						user.setBirthday(rs.getString("birthday"));
						user.setEmail(rs.getString("email"));
					}
//					list.add(user);
					Gson gson=new Gson();
					String json=gson.toJson(user);
					out.write(json);
					/*
					 * Cookie cookie=new
					 * Cookie("lastAccessDate",String.valueOf(System.currentTimeMillis())); Cookie
					 * cookie1=new Cookie("name",name); Cookie cookie2=new
					 * Cookie("password",password); cookie.setMaxAge(60*60*24);
					 * cookie1.setMaxAge(60*60*24); cookie2.setMaxAge(60*60*24);
					 * response.addCookie(cookie); response.addCookie(cookie1);
					 * response.addCookie(cookie2);
					 */
					HttpSession session=request.getSession();//此方法被调用时session才创建
					session.setAttribute("name", name);
					session.setAttribute("password", password);
					session.setMaxInactiveInterval(60*60*24);
					System.out.println(json);
				}
			}catch(Exception e)
			{
				e.printStackTrace();
			}
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
