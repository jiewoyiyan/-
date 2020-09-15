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

import mybean.Favorite;
import mydb.jdbcUtils;

/**
 * Servlet implementation class GetMyCart
 */
@WebServlet(name="GetMyCart",urlPatterns= {"/reachcart"})
public class GetMyCart extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetMyCart() {
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
		String sql="select * from shoppingcart";
		try(Connection conn=jdbcUtils.getConnection();PreparedStatement ps=conn.prepareStatement(sql);
				PrintWriter out=response.getWriter();)
		{
			ResultSet rs=ps.executeQuery();
			List<Favorite> list=new ArrayList<Favorite>();
			while(rs.next())
			{
				Favorite f=new Favorite();
				f.setId(rs.getInt("id"));
				f.setName(rs.getString("name"));
				f.setPrice(rs.getString("price"));
				f.setNum(rs.getInt("num"));
				f.setSrc(rs.getString("imgurl"));
				list.add(f);
			}
			Gson gson=new Gson();
			String json=gson.toJson(list);
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
