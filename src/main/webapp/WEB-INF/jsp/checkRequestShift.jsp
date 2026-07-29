<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

	
<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<title>シフト希望一覧</title>
		<link href="${ pageContext.request.contextPath }/css/style.css" rel="stylesheet" type="text/css">
	</head>

	<body>
		
		<jsp:include page="managerHeader.jsp"></jsp:include>
		
		<form action="${pageContext.request.contextPath}/ManagerServlet" method="get">
			<input type="hidden" name="action" value="confirmShift">
			<input type="submit" value="シフト決定メニューへ">
		</form>
		
		<table class="shiftChart">	
		
			<tr>
				<th colspan="${ fn:length( displayingHours ) +2 }">${ periodDateList[0].monthValue }月</th>
			</tr>
			
			<tr>
				<th>日付</th>
				<th>名前</th>
				<c:forEach var="hour" items="${ displayingHours }">
					<td>${ hour.hour }</td>
				</c:forEach>
			</tr>
			
			<c:forEach var="date" items="${ periodDateList }">
				<c:set var="shiftList" value="${ shiftMap[date] }"></c:set>
			
				<c:choose>
					
					<c:when test="${ not empty shiftList }">
						<c:forEach var="shift" items="${ shiftList }" varStatus="status">
							<tr>
								
								<c:if test="${ status.first }">
									<th rowspan="${ fn:length( shiftList ) }">${ date.dayOfMonth }日</th>
								</c:if>
								
								<td>${ shift.userName }</td>
									<c:choose>
										<c:when test = "${ not shift.dayOff }">
											<c:forEach var="hour" items="${ displayingHours }">											
												<c:choose>
													<c:when test="${ not hour.isBefore( shift.startTime ) and hour.isBefore( shift.endTime ) }">
														<td class="working"></td>
													</c:when>
													<c:otherwise>
														<td></td>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</c:when>
										<c:otherwise>
											<td colspan="${ fn:length( displayingHours ) }">休み</td>
										</c:otherwise>
									</c:choose>
								
							</tr>
						</c:forEach>
					</c:when>
					
					<c:otherwise>
						<tr>
							<th>${ date.dayOfMonth }日</th>
							<td colspan="2">希望シフトなし</td>
						</tr>
					</c:otherwise>
					
				</c:choose>

			</c:forEach>
		
		</table>
		
		<form action="${pageContext.request.contextPath}/ManagerServlet" method="get">
			<input type="hidden" name="action" value="confirmShift">
			<input type="submit" value="シフト決定メニューへ">
		</form>
		
		<jsp:include page="managerFooter.jsp"></jsp:include>

	</body>

</html>