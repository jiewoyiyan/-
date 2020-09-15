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
 * Servlet implementation class ReduceFromCart
 */
@WebServlet(name="UpdateNumFromCart",urlPatterns={"/update"})
public class UpdateNumFromCart extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateNumFromCart() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		int id=Integer.valueOf(request.getParameter("id"));
		String num=request.getParameter("num");
		String sql="update shoppingcart set num=? where id=?";
		try(Connection conn=jdbcUtils.getConnection();PreparedStatement ps=conn.prepareStatement(sql))
		{
			ps.setInt(2, id);
			ps.setString(1, num);
			ps.executeUpdate();
			System.out.println("购物车商品数量更新成功");
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
