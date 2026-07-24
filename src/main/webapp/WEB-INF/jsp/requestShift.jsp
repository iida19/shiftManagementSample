<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<%@ page import="shiftManagementSample.UserBean" %>
<%
	UserBean u = ( UserBean )session.getAttribute( "user" );
	if ( u == null ) {
		response.sendRedirect( request.getContextPath() + "/login.jsp" );
		return;
	}
%>
	
<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<title>シフト希望提出</title>
	</head>

	<body>
	
		<form action="${ pageContext.request.contextPath }/RequestShiftServlet" method="post">
		
			<table>	
		
				<tr>
					<th>${  ( periodDateList[0] ).monthValue }月</th>
				</tr>
			
				<c:forEach var="date" items="${ periodDateList }">
					<tr>
						<td>${ date.dayOfMonth }日</td>
						<td>
							<input type="hidden" name="shiftDate" value="${ date }">
							<select name="startTime">
								<c:forEach var="hour" items="${ openingHours }">
									<option value="${ hour }">${ hour }</option>
								</c:forEach>
							</select>
							〜
							<select name="endTime">
								<c:forEach var="hour" items="${ openingHours }">
									<option value="${ hour }">${ hour }</option>
								</c:forEach>
							</select>
						</td>
						<td>
							<input type="checkbox" name="allDay_${ date }" value="true">終日OK
						</td>
						<td>
							<input type="checkbox" name="dayOff_${ date }" value="true">休み希望
						</td>
					</tr>
				</c:forEach>
		
			</table>
			
			<button type="submit" name="action" value="request">送信</button>
		
		</form>

	</body>

</html>