<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

	
<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<title>シフト希望一覧</title>
	</head>

	<body>
		
		<jsp:include page="managerHeader.jsp"></jsp:include>
		
		<table border="1">	
		
			<tr>
				<th colspan="3">${ periodDateList[0].monthValue }月</th>
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
								<td>
									<c:choose>
										<c:when test="${ shift.dayOff }">
											休み
										</c:when>
										<c:otherwise>
											${ shift.startTime }〜${ shift.endTime }
										</c:otherwise>
									</c:choose>
								</td>
								
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