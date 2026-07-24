package shiftManagementSample;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@WebServlet("/StaffServlet")
public class StaffServlet extends HttpServlet {
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
		
		String action = request.getParameter( "action" );
		LocalDate today = LocalDate.now();
		
		
		// ログイン直後
		if ( action == null ) {
		
			List<ShiftBean> todaysShift = ShiftLogic.getTodaysShift( today );
			
			session.setAttribute( "today", today );
			session.setAttribute( "todaysShift", todaysShift );
			RequestDispatcher rd = request.getRequestDispatcher( "/WEB-INF/jsp/staffHome.jsp" );
			rd.forward( request, response );
		
		
		// シフト希望登録
		} else if ( ( "requestShift" ).equals( action ) ) {
			
			List<LocalDate> periodDateList = ShiftLogic.createDateList( today );
			List<LocalTime> openingHours = ShiftLogic.getOpeningHours();
			
			request.setAttribute( "periodDateList", periodDateList );
			request.setAttribute( "openingHours", openingHours );
			RequestDispatcher rd = request.getRequestDispatcher( "/WEB-INF/jsp/requestShift.jsp" );
			rd.forward( request, response );
			
		}
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
