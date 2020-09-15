package myfilter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class CartFilter
 */
@WebFilter(filterName="CartFilter",urlPatterns= {"/shopping-cart.html"})
public class CartFilter implements Filter {

    /**
     * Default constructor. 
     */
    public CartFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here

		System.out.println("cart过滤器发挥了作用");
        HttpServletRequest httpRequest=(HttpServletRequest)request;
        HttpServletResponse httpResponse=(HttpServletResponse)response;
      
        HttpSession session=httpRequest.getSession(false);//当session不存在时不创建
        if(session!=null)
        {
			/*
			 * String name=(String)session.getAttribute("name"); String
			 * password=(String)session.getAttribute("password");
			 */
        	if(session.getAttribute("name")!=null&&session.getAttribute("password")!=null)
        	{
        		// pass the request along the filter chain
        		chain.doFilter(request, response);
        	}
        	else
        	{
        		httpResponse.sendRedirect("index.html");
        	}
        }
        else
        {
        	httpResponse.sendRedirect("index.html");
        }
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
