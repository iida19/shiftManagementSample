package shiftManagementSample;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebFilter("/*")
public class SMSFilter extends HttpFilter {
	
	@Override
	protected void doFilter( HttpServletRequest request, HttpServletResponse response, FilterChain chain )
			throws IOException, ServletException {
		
		String uri = request.getRequestURI();

		if ( uri.endsWith( "/login.jsp" ) || uri.endsWith( "/LoginServlet" )
				|| uri.contains( "/css/" ) || uri.contains( "/images/" ) ) {
		    chain.doFilter( request, response );
		    return;
		}
		
		HttpSession session = request.getSession( false );
		
		UserBean user = null;

		if ( session != null ) {
		    user = ( UserBean )session.getAttribute( "user" );
		}
		
		if ( user == null ) {
		    response.sendRedirect( request.getContextPath() + "/login.jsp" );
		    return;
		}
		
		List<PostBean> importantPostList = PostDAO.findImportantPost();
		
		request.setAttribute( "importantPostList", importantPostList );
		
		chain.doFilter( request, response );
		
	}

}
