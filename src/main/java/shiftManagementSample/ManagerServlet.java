package shiftManagementSample;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@WebServlet("/ManagerServlet")
public class ManagerServlet extends HttpServlet {
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
			RequestDispatcher rd = request.getRequestDispatcher( "/WEB-INF/jsp/managerHome.jsp" );
			rd.forward( request, response );
			return;
				
				
		// シフト希望確認
		} else if ( ( "checkRequestShift" ).equals( action ) ) {
			
			String[] nextMonth = ShiftLogic.nextMonthPeriod( today );
			List<ShiftBean> requestShiftList = ShiftLogic.getShiftOfPeriod( "requestShift", nextMonth );
			
			List<LocalDate> periodDateList = ShiftLogic.createDateList( today );
			Map<LocalDate, List<ShiftBean>> shiftMap = ShiftLogic.makeShiftMap( requestShiftList, periodDateList );
			
			session.setAttribute( "periodDateList", periodDateList );
			session.setAttribute( "shiftMap", shiftMap );
			RequestDispatcher rd = request.getRequestDispatcher( "/WEB-INF/jsp/checkRequestShift.jsp" );
			rd.forward( request, response );
			return;
		
			
		// シフト確定
		} else if ( ( "confirmShift" ).equals( action ) ) {
						
			List<LocalTime> openingHours = ShiftLogic.getOpeningHours();
						
			session.setAttribute( "openingHours", openingHours );
			RequestDispatcher rd = request.getRequestDispatcher( "/WEB-INF/jsp/confirmShift.jsp" );
			rd.forward( request, response );
			return;
			
			
		// 確定したシフトの確認
		} else if ( ( "checkConfirmedShift" ).equals( action ) ) {
									
			String[] nextMonth = ShiftLogic.nextMonthPeriod( today );
			List<ShiftBean> confirmedShiftList = ShiftLogic.getShiftOfPeriod( "confirmedShift", nextMonth );
			
			List<LocalDate> periodDateList = ShiftLogic.createDateList( today );
			Map<LocalDate, List<ShiftBean>> shiftMap = ShiftLogic.makeShiftMap( confirmedShiftList, periodDateList );
			
			session.setAttribute( "periodDateList", periodDateList );
			session.setAttribute( "shiftMap", shiftMap );
			RequestDispatcher rd = request.getRequestDispatcher( "/WEB-INF/jsp/checkConfirmedShift.jsp" );
			rd.forward( request, response );
			return;
		
			
		// ログアウト
		} else if ( ( "logout" ).equals( action ) ) {
			
			session.invalidate();
			
			String message = "ログアウトしました";
			String redirectPath = "/ManagerServlet";
			
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
		if ( ( "confirm" ).equals( action ) ) {
			
			List<ShiftBean> confirmedShiftList = null;
			
			try {
				
				confirmedShiftList = createShiftList( request );
				
				String db = "confirmedShift";
				ShiftLogic.registerShiftList( confirmedShiftList, db );
				
				String message = "シフトを登録しました";
				String redirectPath = "/ManagerServlet";
				
				request.setAttribute( "message", message );
				request.setAttribute( "redirectPath", redirectPath );
				RequestDispatcher rd = request.getRequestDispatcher( "/WEB-INF/jsp/message.jsp" );
				rd.forward( request, response );
				return;
				
			} catch( IllegalArgumentException e ) {
				
				e.printStackTrace( System.out );
				
				String redirectPath = "/ManagerServlet";
				
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
		
		List<ShiftBean> confirmedShiftList = new ArrayList<ShiftBean>();
		
		HttpSession session = request.getSession();
		List<LocalTime> openingHours = ( List<LocalTime> )session.getAttribute( "openingHours" );
		
		if ( openingHours == null || openingHours.isEmpty() ) {
		    throw new IllegalArgumentException( "画面情報を取得できませんでした。" );
		}
		
		String[] userIdList = request.getParameterValues( "userId" );
		String[] shiftDateList = request.getParameterValues( "shiftDate" );
		String[] startTimeList = request.getParameterValues( "startTime" );
		String[] endTimeList = request.getParameterValues( "endTime" );
		
		if ( userIdList == null || shiftDateList == null || startTimeList == null || endTimeList == null
				|| shiftDateList.length == 0
				|| shiftDateList.length != userIdList.length
				|| shiftDateList.length != startTimeList.length || shiftDateList.length != endTimeList.length ) {
		    throw new IllegalArgumentException( "不正なリクエストです。" );
		}
		
		for ( int i = 0; i < shiftDateList.length; i ++ ) {
			
			LocalDate shiftDate = LocalDate.parse( shiftDateList[i] );
			
			String allDay = request.getParameter( "allDay_" + shiftDate + "_" + userIdList[i] );
			String dayOff = request.getParameter( "dayOff_" + shiftDate + "_" + userIdList[i] );
			
			String startTimeValue = startTimeList[i];
			String endTimeValue = endTimeList[i];
			
			boolean startBlank = startTimeValue == null || startTimeValue.isBlank();

			boolean endBlank = endTimeValue == null || endTimeValue.isBlank();
			
			if ( startBlank && endBlank && allDay == null && dayOff == null ) {
				continue;
				
			} else if ( startBlank != endBlank ) {
				throw new IllegalArgumentException( shiftDate + "の時刻が正しく選択されていません" );
				
			} else {
			
				ShiftBean sb = new ShiftBean();
				sb.setUserId( userIdList[i] );
				sb.setShiftDate( shiftDate );
			
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

					LocalTime startTime = LocalTime.parse( startTimeValue );
					LocalTime endTime = LocalTime.parse( endTimeValue );

					if ( !endTime.isAfter( startTime ) ) {
						throw new IllegalArgumentException( shiftDate + "の終了時刻は開始時刻より後にしてください" );
					}
				
					sb.setStartTime( startTime );
					sb.setEndTime( endTime );
				
					sb.setDayOff( false );
				
				}
			
				confirmedShiftList.add( sb );
			}
		}
		return confirmedShiftList;
	}

}
