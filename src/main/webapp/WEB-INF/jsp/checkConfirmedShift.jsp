<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

	
<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<title>確定シフト一覧</title>
		<link href="${ pageContext.request.contextPath }/css/style.css" rel="stylesheet" type="text/css">
	</head>

	<body>
		
		<c:choose>
			<c:when test="${ sessionScope.user.role eq '0' }">
				<jsp:include page="staffHeader.jsp"></jsp:include>
			</c:when>
			<c:otherwise>
				<jsp:include page="managerHeader.jsp"></jsp:include>
			</c:otherwise>
		</c:choose>
		
		<div style="display: flex; gap: 30px; align-items: flex-start;">
		
			<form action="${ pageContext.request.contextPath }/ManagerServlet" method="get">
		
				<select name="confirmedPeriod">
					<c:forEach var="confirmedPeriod" items="${ confirmedPeriodList }">
						<c:choose>
							<c:when test="${ confirmedPeriod eq targetDay }">
								<option value="${ confirmedPeriod }" selected>${ confirmedPeriod.year }年${ confirmedPeriod.monthValue }月</option>
							</c:when>
							<c:otherwise>
								<option value="${ confirmedPeriod }">${ confirmedPeriod.year }年${ confirmedPeriod.monthValue }月</option>
							</c:otherwise>
						</c:choose>	
					</c:forEach>
				</select>
				
				<button type="submit" name="action" value="checkOtherPeriod">閲覧</button>
				
			</form>
			
			<c:if test="${ sessionScope.user.role eq '1' }">
				<form action="${ pageContext.request.contextPath }/ManagerServlet" method="get">
					<input type="hidden" name="action" value="retouchShift">
					<input type="submit" value="シフト修整">
				</form>
			</c:if>
			
		</div>
		
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
							<td colspan="${ fn:length( displayingHours ) +1 }">確定済みシフトなし</td>
						</tr>
					</c:otherwise>
					
				</c:choose>

			</c:forEach>
		
		</table>
		
		<jsp:include page="managerFooter.jsp"></jsp:include>

	</body>

</html>