<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

	
<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<title>シフト希望提出</title>
	</head>

	<body>
		
		<jsp:include page="staffHeader.jsp"></jsp:include>
	
		<form action="${ pageContext.request.contextPath }/StaffServlet" method="post">
		
			<table>	
		
				<tr>
					<th>${ periodDateList[0].monthValue }月</th>
				</tr>
				
				<c:set var="last" value="${ fn:length( openingHours ) -1 }"></c:set>
			
				<c:forEach var="date" items="${ periodDateList }">
					<c:set var="shift" value="${ shiftMap[date] }"></c:set>
					<c:set var="isAllDay" value="${ openingHours[0] eq shift.startTime and openingHours[last] eq shift.endTime }"></c:set>
					
					<tr>
						<td>${ date.dayOfMonth }日</td>
						<td>
							<input type="hidden" name="shiftDate" value="${ date }">
							<select name="startTime">
								<option value="">--選択--</option>
								<c:forEach var="hour" items="${ openingHours }">
									<c:choose>
										<c:when test="${ hour eq shift.startTime }">
											<option value="${ hour }" selected>${ hour }</option>
										</c:when>
										<c:otherwise>
											<option value="${ hour }">${ hour }</option>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</select>
							〜
							<select name="endTime">
								<option value="">--選択--</option>
								<c:forEach var="hour" items="${ openingHours }">
									<c:choose>
										<c:when test="${ hour eq shift.endTime }">
											<option value="${ hour }" selected>${ hour }</option>
										</c:when>
										<c:otherwise>
											<option value="${ hour }">${ hour }</option>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</select>
						</td>
						<td>
							<c:choose>
								<c:when test="${ isAllDay }">
									<input type="checkbox" name="allDay_${ date }" value="true" checked>終日OK
								</c:when>
								<c:otherwise>
									<input type="checkbox" name="allDay_${ date }" value="true">終日OK
								</c:otherwise>
							</c:choose>
						</td>
						<td>
							<c:choose>
								<c:when test="${ shift.dayOff }">
									<input type="checkbox" name="dayOff_${ date }" value="true" checked>休み希望
								</c:when>
								<c:otherwise>
									<input type="checkbox" name="dayOff_${ date }" value="true">休み希望
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
				</c:forEach>
		
			</table>
			
			<div style="display: flex; gap: 30px; align-items: flex-start;">
			
				<button type="submit" name="action" value="temporarilySave">一時保存</button>
				<button type="submit" name="action" value="request">送信</button>
			
			</div>
		
		</form>
		
		<form action="${ pageContext.request.contextPath }/StaffServlet" method="get">
			<button type="submit" name="action" value="logout">ログアウト</button>
		</form>

	</body>

</html>