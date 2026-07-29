package shiftManagementSample;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@WebServlet("/ForumServlet")
public class ForumServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		//====================================
		//キャッシュを禁止する
		//→ログアウト後に「戻る」ボタンが押されても再表示されない対策
		//====================================
		response.setHeader( "Cache-Control","no-cache, no-store, must-revalidate" );
		response.setHeader( "Pragma", "no-cache" );
		response.setDateHeader( "Expires", 0 );
								
				
		request.setCharacterEncoding( "UTF-8" );
		HttpSession session = request.getSession();
		
		List<PostBean> postList = PostDAO.findAll();
		
		request.setAttribute( "postList", postList );
		RequestDispatcher rd = request.getRequestDispatcher( "/WEB-INF/jsp/forum.jsp" );
		rd.forward( request, response );
		return;

	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		//====================================
		//キャッシュを禁止する
		//→ログアウト後に「戻る」ボタンが押されても再表示されない対策
		//====================================
		response.setHeader( "Cache-Control","no-cache, no-store, must-revalidate" );
		response.setHeader( "Pragma", "no-cache" );
		response.setDateHeader( "Expires", 0 );
								
				
		request.setCharacterEncoding( "UTF-8" );
		HttpSession session = request.getSession();
				
		String action = request.getParameter( "action" );
		
		
		// 書き込み
		if ( ( "post" ).equals( action ) ) {
			
			UserBean u = ( UserBean )session.getAttribute( "user" );
			String body = request.getParameter( "body" );
			String important = request.getParameter( "important" );
			
			System.out.print( u.getUserName() + " " );
			System.out.print( body + " " );
			System.out.print( important );
			
			PostLogic.postToForum( u.getUserId(), body, important );
			List<PostBean> postList = PostDAO.findAll();
			
			for ( PostBean p : postList ) {
				System.out.println( p.getBody() );
			}
			
			request.setAttribute( "postList", postList );
			RequestDispatcher rd = request.getRequestDispatcher( "/WEB-INF/jsp/forum.jsp" );
			rd.forward( request, response );
			return;
			
			
		// 削除
		} else if ( ( "delete" ).equals( action ) ) {
			
			String[] deleteIdValue = request.getParameterValues( "deleteId" );
			
			if ( deleteIdValue == null ) {
				
				String errorMessage = "削除するつぶやきが選択されていません！";
				request.setAttribute( "em", errorMessage );
				RequestDispatcher rd = request.getRequestDispatcher( "/WEB-INF/jsp/forum.jsp" );
				rd.forward( request, response );
				return;
				
			}
			
			PostLogic.deletePosts( deleteIdValue );
			List<PostBean> postList = PostDAO.findAll();
			request.setAttribute( "postList", postList );
			RequestDispatcher rd = request.getRequestDispatcher( "/WEB-INF/jsp/forum.jsp" );
			rd.forward( request, response );
			return;
			
			
		}
		

	}

}
