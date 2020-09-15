package myservlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import mybean.Shopping;
import mydb.jdbcUtils;

/**
 * Servlet implementation class GetShoppingServlet
 */
@WebServlet(name="GetShoppingServlet",urlPatterns= {"/gshopping"})
public class GetShoppingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetShoppingServlet() {
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
		String sql="select * from shopping ";
		try(Connection conn=jdbcUtils.getConnection();PreparedStatement ps=conn.prepareStatement(sql);
				PrintWriter out=response.getWriter();)
		{
			List<Shopping> list=new ArrayList<>();
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				Shopping s=new Shopping();
				s.setName(rs.getString("name"));
				s.setImgurl(rs.getString("imgurl"));
				s.setDescription(rs.getString("description"));
				s.setPrice(rs.getString("price"));
				list.add(s);
			}
			Gson gson=new Gson();
			String json=gson.toJson(list);
			System.out.println(json);
			out.print(json);
		}catch(Exception e)
		{
			e.printStackTrace();
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
