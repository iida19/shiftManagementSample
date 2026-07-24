package shiftManagementSample;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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
		
		// ログイン中か？
		UserBean ub = ( UserBean )session.getAttribute( "user" );
		
		
		// 今来たところ。これからログイン
		if ( ub == null ) {
			
			
				String uId = request.getParameter( "userId" );
				String pw = request.getParameter( "password" );
			
				int status = UserLogic.loginCheck( uId, pw );
						// 0でログイン成功、1は入力内容違い、2は空欄あり
			
			
				if ( status == 0 ) {
					
					UserBean u = UserDAO.select( uId, pw );
					u.setPassword( null );
					String redirectPath;
					
					if ( ( "0" ).equals( u.getRole() ) ) {
						redirectPath = "/ManagerServlet";
					} else {
						redirectPath = "/StaffServlet";
					}
					
					session.setAttribute( "user", u );
					response.sendRedirect( request.getContextPath() + redirectPath );
					return;
				
				} else if ( status == 1 ) {
				
					String errorMessage = "ユーザー名かパスワードが間違っています";
					request.setAttribute( "em", errorMessage );
					request.setAttribute( "userId", uId );
					RequestDispatcher rd = request.getRequestDispatcher( "/login.jsp" );
					rd.forward( request, response );
					return;
			
				} else if ( status == 2 ) {
				
					String errorMessage = "入力されていない項目があるようです";
					request.setAttribute( "em", errorMessage );
					request.setAttribute( "userId", uId );
					RequestDispatcher rd = request.getRequestDispatcher( "/login.jsp" );
					rd.forward( request, response );
					return;
		
				} else if ( status == -1 ) {
					
					System.out.println( "login statusが未判定です" );
					String errorMessage = "処理に問題が発生しました。すみませんがもう一度お試しください。";
					request.setAttribute( "em", errorMessage );
					request.setAttribute( "userId", uId );
					RequestDispatcher rd = request.getRequestDispatcher( "/login.jsp" );
					rd.forward( request, response );
					return;
					
				}
		
		// ログイン中
		} else {
			
			String redirectPath;
			
			if ( ( "0" ).equals( ub.getRole() ) ) {
				redirectPath = "/ManagerServlet";
			} else {
				redirectPath = "/StaffServlet";
			}
			
			session.setAttribute( "user", ub );
			response.sendRedirect( request.getContextPath() + redirectPath );
			return;
			
		}
		

	}

}