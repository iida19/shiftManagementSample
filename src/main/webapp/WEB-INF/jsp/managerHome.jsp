<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>管理者トップ</title>
		<link href="${ pageContext.request.contextPath }/css/style.css" rel="stylesheet" type="text/css">
		<link href="${ pageContext.request.contextPath }/css/header.css" rel="stylesheet" type="text/css">
	</head>

	<body>

		<jsp:include page="/WEB-INF/jsp/managerHeader.jsp" />

		本日は${ today }です
		
		<table class="shiftChart">
			
			<c:choose>
				<c:when test = "${ not empty todaysShift }">
				
					<tr>
						<th colspan="${ fn:length( displayingHours ) +1 }">${ today }の出勤予定</th>
					</tr>
					
					<tr>
						<th>名前</th>
						<c:forEach var="hour" items="${ displayingHours }">
							<td>${ hour.hour }</td>
						</c:forEach>
					</tr>
					
					<c:forEach var="s" items="${ todaysShift }">

						<tr>
						
							<td>${ s.userName }</td>
								<c:choose>
									<c:when test = "${ not s.dayOff }">
										<c:forEach var="hour" items="${ displayingHours }">											
											<c:choose>
												<c:when test="${ not hour.isBefore( s.startTime ) and hour.isBefore( s.endTime ) }">
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
					今日の出勤予定はありません
				</c:otherwise>
			</c:choose>
		
		</table>
		
		<jsp:include page="managerFooter.jsp"></jsp:include>
		
	</body>

</html>