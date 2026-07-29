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
		String currentMenu;
				
				
		// ログイン直後
		if ( action == null ) {
				
			List<ShiftBean> todaysShift = ShiftLogic.getTodaysShift( today );		
			List<LocalTime> inputingHours = ShiftLogic.getInputingHours();
			List<LocalTime> displayingHours = ShiftLogic.getDisplayingHours();
			currentMenu = "home";
			
			request.setAttribute( "currentMenu", currentMenu );
			session.setAttribute( "today", today );
			session.setAttribute( "todaysShift", todaysShift );
			session.setAttribute( "inputingHours", inputingHours );
			session.setAttribute( "displayingHours", displayingHours );
			RequestDispatcher rd = request.getRequestDispatcher( "/WEB-INF/jsp/managerHome.jsp" );
			rd.forward( request, response );
			return;
				
				
		// シフト希望確認
		} else if ( ( "checkRequestShift" ).equals( action ) ) {
			
			LocalDate targetDay = today.plusMonths( 1 );
			List<ShiftBean> requestShiftList = ShiftLogic.getShiftOfPeriod( "requestShift", targetDay );
			List<LocalDate> periodDateList = ShiftLogic.createDateList( targetDay );
			Map<LocalDate, List<ShiftBean>> shiftMap = ShiftLogic.makeShiftMap( requestShiftList, periodDateList );
			currentMenu = "checkRequest";
			
			request.setAttribute( "currentMenu", currentMenu );
			session.setAttribute( "periodDateList", periodDateList );
			session.setAttribute( "shiftMap", shiftMap );
			RequestDispatcher rd = request.getRequestDispatcher( "/WEB-INF/jsp/checkRequestShift.jsp" );
			rd.forward( request, response );
			return;
		
			
		// シフト確定
		} else if ( ( "confirmShift" ).equals( action ) ) {
			
			currentMenu = "confirm";
			
			request.setAttribute( "currentMenu", currentMenu );
			RequestDispatcher rd = request.getRequestDispatcher( "/WEB-INF/jsp/confirmShift.jsp" );
			rd.forward( request, response );
			return;
			
			
		// 確定したシフトの確認
		} else if ( ( "checkConfirmedShift" ).equals( action ) ) {
									
			LocalDate targetDay = today.plusMonths( 1 );
			List<ShiftBean> confirmedShiftList = ShiftLogic.getShiftOfPeriod( "confirmedShift", targetDay );
			List<LocalDate> periodDateList = ShiftLogic.createDateList( targetDay );
			Map<LocalDate, List<ShiftBean>> shiftMap = ShiftLogic.makeShiftMap( confirmedShiftList, periodDateList );
			List<LocalDate> confirmedPeriodList = ShiftLogic.findConfirmedPeriod( targetDay );
			currentMenu = "checkConfirm";
			
			request.setAttribute( "currentMenu", currentMenu );
			session.setAttribute( "periodDateList", periodDateList );
			session.setAttribute( "targetDay", targetDay );
			session.setAttribute( "shiftMap", shiftMap );
			session.setAttribute( "confirmedPeriodList", confirmedPeriodList );
			RequestDispatcher rd = request.getRequestDispatcher( "/WEB-INF/jsp/checkConfirmedShift.jsp" );
			rd.forward( request, response );
			return;
			
			
		// 確定済みの他の月のシフトを見る
		} else if ( ( "checkOtherPeriod" ).equals( action ) ) {
			
			String confirmedPeriodValue = request.getParameter( "confirmedPeriod" );
			LocalDate targetDay = ShiftLogic.getTargetDay( confirmedPeriodValue );
			List<ShiftBean> confirmedShiftList = ShiftLogic.getShiftOfPeriod( "confirmedShift", targetDay );
			List<LocalDate> periodDateList = ShiftLogic.createDateList( targetDay );
			Map<LocalDate, List<ShiftBean>> shiftMap = ShiftLogic.makeShiftMap( confirmedShiftList, periodDateList );
			currentMenu = "checkConfirm";
			
			request.setAttribute( "currentMenu", currentMenu );
			session.setAttribute( "periodDateList", periodDateList );
			session.setAttribute( "targetDay", targetDay );
			session.setAttribute( "shiftMap", shiftMap );
			RequestDispatcher rd = request.getRequestDispatcher( "/WEB-INF/jsp/checkConfirmedShift.jsp" );
			rd.forward( request, response );
			return;
			
		
		// 確定済みシフトの修整
		} else if ( ( "retouchShift" ).equals( action ) ) {
			
			currentMenu = "retouch";
			
			request.setAttribute( "currentMenu", currentMenu );
			RequestDispatcher rd = request.getRequestDispatcher( "/WEB-INF/jsp/retouchShift.jsp" );
			rd.forward( request, response );
			return;
			
		
		// 従業員管理
		} else if ( ( "managementUser" ).equals( action ) ) {
			
			currentMenu = "management";
			
			request.setAttribute( "currentMenu", currentMenu );
			RequestDispatcher rd = request.getRequestDispatcher( "/WEB-INF/jsp/managementUserMenu.jsp" );
			rd.forward( request, response );
			return;
			
			
		// 新規従業員登録
		} else if ( ( "registerUser" ).equals( action ) ) {
			
			currentMenu = "management";
			
			request.setAttribute( "currentMenu", currentMenu );
			RequestDispatcher rd = request.getRequestDispatcher( "/WEB-INF/jsp/registerUser.jsp" );
			rd.forward( request, response );
			return;
			
			
		// 従業員削除
		} else if ( ( "deleteUser" ).equals( action ) ) {
			
			List<UserBean> userList = UserDAO.findAll();
			currentMenu = "checkConfirm";
			
			request.setAttribute( "currentMenu", currentMenu );
			request.setAttribute( "userList", userList );
			RequestDispatcher rd = request.getRequestDispatcher( "/WEB-INF/jsp/deleteUser.jsp" );
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
		String currentMenu = "";
		
		
		// シフトを確定する
		if ( ( "confirm" ).equals( action ) ) {
			
			List<ShiftBean> confirmedShiftList = null;
			
			try {
				
				confirmedShiftList = createShiftList( request );
				
				String db = "confirmedShift";
				ShiftLogic.registerShiftList( confirmedShiftList, db );
				
				String message = "シフトを登録しました";
				String redirectPath = "/ManagerServlet";
				
				currentMenu = "confirm";
				
				request.setAttribute( "currentMenu", currentMenu );
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
			
			
		// シフト修整
		} else if ( ( "retouch" ).equals( action ) ) {
			
			List<ShiftBean> retouchShiftList = null;
			
			try {
				
				retouchShiftList = createShiftList( request );
				
				String db = "confirmedShift";
				ShiftLogic.registerShiftList( retouchShiftList, db );
				
				String message = "シフトを修整しました";
				String redirectPath = "/ManagerServlet";
				
				currentMenu = "retouch";
				
				request.setAttribute( "currentMenu", currentMenu );
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
			
		
		// 新規従業員登録
		} else if ( ( "register" ).equals( action ) ) {
			
			String userId = request.getParameter( "userId" );
			String userName = request.getParameter( "userName" );
			String role = request.getParameter( "role" );
			
			currentMenu = "management";
		
			UserBean u = new UserBean( userId, userName, role );
			int status = UserLogic.registerUser( u );
					// 0で登録成功、1はユーザー名重複、2は空欄あり
			
			
			if ( status == 0 ) {
				
				String message = "登録しました。";
				String redirectPath = "/ManagerServlet";
				
				request.setAttribute( "message", message );
				request.setAttribute( "currentMenu", currentMenu );
				request.setAttribute( "redirectPath", redirectPath );
				RequestDispatcher rd = request.getRequestDispatcher( "/WEB-INF/jsp/message.jsp" );
				rd.forward( request, response );
				return;
				
			} else if ( status == 1 ) {
				
				String errorMessage = "ユーザーID " + u.getUserId() + " が重複しています";
				request.setAttribute( "em", errorMessage );
				request.setAttribute( "currentMenu", currentMenu );
				request.setAttribute( "userId", u.getUserId() );
				request.setAttribute( "userName", u.getUserName() );
				RequestDispatcher rd = request.getRequestDispatcher( "/WEB-INF/jsp/registerUser.jsp" );
				rd.forward( request, response );
				return;
				
			} else if ( status == 2 ) {
						
				String errorMessage = "入力されていない項目があるようです";
				request.setAttribute( "em", errorMessage );
				request.setAttribute( "currentMenu", currentMenu );
				request.setAttribute( "userName", u.getUserName() );
				RequestDispatcher rd = request.getRequestDispatcher( "/WEB-INF/jsp/registerUser.jsp" );
				rd.forward( request, response );
				return;
			
			} else if ( status == -1 ) {
				
				System.out.println( "register statusが未判定です" );
				String errorMessage = "処理に問題が発生しました。すみませんがもう一度お試しください。";
				request.setAttribute( "em", errorMessage );
				request.setAttribute( "currentMenu", currentMenu );
				request.setAttribute( "userName", u.getUserName() );
				RequestDispatcher rd = request.getRequestDispatcher( "/WEB-INF/jsp/registerUser.jsp" );
				rd.forward( request, response );
				return;
				
			}
			
			
		// 従業員削除
		} else if ( ( "delete" ).equals( action ) ) {
			
			String[] deleteId = request.getParameterValues( "deleteId" );
			UserLogic.deleteUser( deleteId );
			
			String message = "削除しました。";
			String redirectPath = "/ManagerServlet";
			currentMenu = "management";
			
			request.setAttribute( "message", message );
			request.setAttribute( "currentMenu", currentMenu );
			request.setAttribute( "redirectPath", redirectPath );
			RequestDispatcher rd = request.getRequestDispatcher( "/WEB-INF/jsp/message.jsp" );
			rd.forward( request, response );
			return;
			
			
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public static List<ShiftBean> createShiftList( HttpServletRequest request ) {
		
		List<ShiftBean> confirmedShiftList = new ArrayList<ShiftBean>();
		
		HttpSession session = request.getSession();
		List<LocalTime> inputingHours = ( List<LocalTime> )session.getAttribute( "inputingHours" );
		
		if ( inputingHours == null || inputingHours.isEmpty() ) {
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
			
			boolean startBlank = ( startTimeValue == null || startTimeValue.isBlank() );
			boolean endBlank = ( endTimeValue == null || endTimeValue.isBlank() );
			
			if ( startBlank != endBlank ) {
				throw new IllegalArgumentException( shiftDate + "の時刻が正しく選択されていません" );
				
			} else {
			
				ShiftBean sb = new ShiftBean();
				sb.setUserId( userIdList[i] );
				sb.setShiftDate( shiftDate );
			
				// 休み
				if (	dayOff != null ||
						( startBlank && endBlank && allDay == null && dayOff == null ) ) {
				
					sb.setDayOff( true );
			
				// 終日
				} else if ( allDay != null ) {
				
					sb.setStartTime( inputingHours.getFirst() );
					sb.setEndTime( inputingHours.getLast() );
				
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
