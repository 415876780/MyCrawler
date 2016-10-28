package ccnu.computer.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.util.SystemPropertyUtils;

import ccnu.computer.model.SystemContext;

public class SystemContextFilter implements Filter{

	
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		int offset = 0;
		System.out.println("----------------------");
		
		try {
			offset = Integer.parseInt(req.getParameter("pager.offset"));
		} catch (NumberFormatException e) {
		}
		try {
			SystemContext.setOffset(offset);
			SystemContext.setSize(15);
			chain.doFilter(req, resp);
		}finally {
			SystemContext.removeOffset();
			SystemContext.removeSize();
		}
	}

	
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
