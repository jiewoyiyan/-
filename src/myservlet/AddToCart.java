package myservlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mydb.jdbcUtils;

/**
 * Servlet implementation class AddToCart
 */
@WebServlet(name="AddToCart",urlPatterns= {"/favorite"})
public class AddToCart extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddToCart() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		String sql="insert into shoppingcart(name,price,num,imgurl) values(?,?,1,?)";
		String name=request.getParameter("description");
		String price=request.getParameter("price");
//		int num=Integer.valueOf(request.getParameter("num"));
		String imgurl=request.getParameter("imgurl");
		System.out.println("ÃÌº”–≈œ¢"+name+price+imgurl);
		try(Connection conn=jdbcUtils.getConnection();PreparedStatement ps=conn.prepareStatement(sql))
		{
			ps.setString(1, name);
			ps.setString(2, price);
//			ps.setInt(3, num);
			ps.setString(3, imgurl);
			ps.executeUpdate();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		request.getRequestDispatcher("main.html").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
