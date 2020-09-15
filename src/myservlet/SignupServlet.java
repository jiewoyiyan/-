package myservlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import mybean.User;
import mydb.jdbcUtils;

/**
 * Servlet implementation class SignupServlet
 */
@WebServlet(name="SignupServlet",urlPatterns= {"/signup"})
public class SignupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	String nameReg="^[\\u4e00-\\u9fa5]{1,6}$|^[\\dA-Za-z_]{1,12}$";
	String passwordReg="^[\\dA-Za-z_]{6,12}$";
	String emailReg="^[\\w-]+(\\.[w-]+)*@[\\w-]+(\\.[\\w-]+)+$";
	String numReg="^\\d+$";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignupServlet() {
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
		PrintWriter out=response.getWriter();
		String registerJsonInfo=request.getParameter("jsonInfo");
		System.out.println(registerJsonInfo);
		Gson gson=new Gson();
		User user=gson.fromJson(registerJsonInfo, User.class);
		if(user==null)
		{
			out.write("未获取到信息");
			out.close();
			return;
		}
		if(Pattern.matches(nameReg, user.getName())&&Pattern.matches(passwordReg, user.getPassword())&&
				Pattern.matches(emailReg, user.getEmail())&&Pattern.matches(numReg, user.getAge()))
		{
			System.out.println("输入合法"+user.getEmail());
			String sql="select * from users where name=?";
			String sql2="insert into users values(?,?,?,?,?,?)";
			try(Connection conn=jdbcUtils.getConnection();PreparedStatement ps=conn.prepareStatement(sql);
					PreparedStatement ps2=conn.prepareStatement(sql2);)
			{
				ps.setString(1, user.getName());
				//ps.setString(2, user.getPassword());
				System.out.println(ps.executeQuery().getRow());
				ResultSet rs=ps.executeQuery();
				rs.last();//移动到最后一行
				if(rs.getRow()>0)//获取当前行号
				{
					out.print("此用户已经存在");
					out.close();
					return;
				}
				ps2.setString(1, user.getName());
				ps2.setString(2, user.getPassword());
				ps2.setString(3, user.getSex());
				ps2.setString(4, user.getAge());
				ps2.setString(5, user.getBirthday());
				ps2.setString(6, user.getEmail());
				System.out.println("执行了");
				if(ps2.executeUpdate()>0)
				{
					out.print("注册成功");
					out.close();
					return;
				}
				out.print("注册失败");
				out.close();
			}
			catch(Exception e)
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
