package shiftManagementSample;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
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
			
			session.setAttribute( "periodDateList", periodDateList );
			session.setAttribute( "openingHours", openingHours );
			RequestDispatcher rd = request.getRequestDispatcher( "/WEB-INF/jsp/requestShift.jsp" );
			rd.forward( request, response );
		
			
		// ログアウト
		} else if ( ( "logout" ).equals( action ) ) {
			
			session.invalidate();
			
			String message = "ログアウトしました";
			String redirectPath = "/StaffServlet";
			
			request.setAttribute( "message", message );
			request.setAttribute( "redirectPath", redirectPath );
			RequestDispatcher rd = request.getRequestDispatcher( "/WEB-INF/jsp/message.jsp" );
			rd.forward( request, response );
			return;
			
		}
		
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
		
		
		// シフト希望リクエストを受け取る
		if ( ( "request" ).equals( action ) ) {
			
			List<ShiftBean> requestShiftList = null;
			
			try {
				
				requestShiftList = createShiftList( request );

				System.out.println("----- INSERT直前 requestShiftList -----");

				for (ShiftBean s : requestShiftList) {
				    System.out.println(
				    	s.getShiftId() + " "
				        + s.getShiftDate() + " "
				        + s.getUserId() + " "
				        + s.isDayOff() + " "
				        + s.getStartTime() + " "
				        + s.getEndTime()
				    );
				}

				ShiftLogic.registerShiftList(requestShiftList, "requestShift");
				
				String db = "requestShift";
				ShiftLogic.registerShiftList( requestShiftList, db );
				
				String message = "シフト希望を送信しました";
				String redirectPath = "/StaffServlet";
				
				request.setAttribute( "message", message );
				request.setAttribute( "redirectPath", redirectPath );
				RequestDispatcher rd = request.getRequestDispatcher( "/WEB-INF/jsp/message.jsp" );
				rd.forward( request, response );
				return;
				
			} catch( IllegalArgumentException e ) {
				
				e.printStackTrace( System.out );
				
				String redirectPath = "/StaffServlet";
				
				request.setAttribute( "message", e.getMessage() );
				request.setAttribute( "redirectPath", redirectPath );
				RequestDispatcher rd = request.getRequestDispatcher( "/WEB-INF/jsp/message.jsp" );
				rd.forward( request, response );
				return;
				
			}
			
			
		}
		
		
	}
	
	
	@SuppressWarnings("unchecked")
	public static List<ShiftBean> createShiftList( HttpServletRequest request ) {
		
		List<ShiftBean> requestShiftList = new ArrayList<ShiftBean>();
		
		HttpSession session = request.getSession();
		UserBean user = ( UserBean )session.getAttribute( "user" );
		List<LocalTime> openingHours = ( List<LocalTime> )session.getAttribute( "openingHours" );
		
		if ( user == null || openingHours == null || openingHours.isEmpty() ) {
		    throw new IllegalArgumentException( "画面情報を取得できませんでした。" );
		}
		
		String[] shiftDateList = request.getParameterValues( "shiftDate" );
		String[] startTimeList = request.getParameterValues( "startTime" );
		String[] endTimeList = request.getParameterValues( "endTime" );
		
		if ( shiftDateList == null || startTimeList == null || endTimeList == null
				|| shiftDateList.length == 0
				|| shiftDateList.length != startTimeList.length
		        || shiftDateList.length != endTimeList.length ) {
		    throw new IllegalArgumentException( "不正なリクエストです。" );
		}
		
		for ( int i = 0; i < shiftDateList.length; i ++ ) {
			
			ShiftBean sb = new ShiftBean();
			
			sb.setUserId( user.getUserId() );
			sb.setUserName( user.getUserName() );
			
			LocalDate shiftDate = LocalDate.parse( shiftDateList[i] );
			sb.setShiftDate( shiftDate );
			
			String allDay = request.getParameter( "allDay_" + shiftDate );
			String dayOff = request.getParameter( "dayOff_" + shiftDate );
			
			String startTimeValue = startTimeList[i];
			String endTimeValue = endTimeList[i];
			
			// 休み希望
			if ( dayOff != null ) {
				
				sb.setDayOff( true );
			
			// 終日OK
			} else if ( allDay != null ) {
				
				sb.setStartTime( openingHours.getFirst() );
				sb.setEndTime( openingHours.getLast() );
				
				sb.setDayOff( false );
			
			// それ以外（時間帯指定によるシフト希望）
			} else {
				
				if ( startTimeValue == null || startTimeValue.isBlank()
			            || endTimeValue == null || endTimeValue.isBlank() ) {

			        throw new IllegalArgumentException(
			                shiftDate + "の希望を入力してください" );
			    }

			    LocalTime startTime = LocalTime.parse( startTimeValue );
			    LocalTime endTime = LocalTime.parse( endTimeValue );

			    if ( !endTime.isAfter( startTime ) ) {
			        throw new IllegalArgumentException(
			                shiftDate + "の終了時刻は開始時刻より後にしてください" );
			    }
				
				sb.setStartTime( startTime );
				sb.setEndTime( endTime );
				
				sb.setDayOff( false );
				
			}
			
			requestShiftList.add( sb );
			
		}
		return requestShiftList;
	}

}
