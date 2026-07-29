<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>スタッフトップ</title>
		<link href="${ pageContext.request.contextPath }/css/style.css" rel="stylesheet" type="text/css">
		<link href="${ pageContext.request.contextPath }/css/header.css" rel="stylesheet" type="text/css">
	</head>

	<body>
	
		<jsp:include page="staffHeader.jsp"></jsp:include>
		
		<div style="display: flex; gap: 30px; align-items: flex-start;">
			
			<table border="1">
				
				<tr>
					<th colspan="7">${ periodDateList[0].monthValue }</th>
				</tr>
				
				<tr>
					
					<c:set var="firstDate" value="${ periodDateList[0] }"></c:set>
					<c:set var="emptyCount" value="${ firstDate.dayOfWeek.value mod 7 }"></c:set>
					
					<c:if test="${ emptyCount > 0  }">
						<c:forEach begin="1" end="${ emptyCount }">
							<td></td>
						</c:forEach>
					</c:if>
				
					<c:forEach var="date" items="${ periodDateList }">
						<c:set var="shift" value="${ shiftMap[date] }"></c:set>
					
						<td>
							${ date.dayOfMonth }
							
							<c:choose>
								<c:when test="${ not empty shift and shift.dayOff }"></c:when>
								<c:when test="${ not empty shift }">
									<br>
									${ shift.startTime }
									<br>〜<br>
									${ shift.endTime }
								</c:when>
							</c:choose>
							
						</td>
						<c:if test="${ 'SATURDAY' eq date.dayOfWeek }">
							</tr>
							<tr>
						</c:if>
					</c:forEach>
					
					<c:set var="lastDate" value="${ periodDateList[ fn:length( periodDateList ) -1 ] }"></c:set>
					<c:set var="lastEmptyCount" value="${ 6- ( lastDate.dayOfWeek.value mod 7 ) }"></c:set>
					
					<c:if test="${ lastEmptyCount > 0 }">
						<c:forEach begin="1" end="${ lastEmptyCount }">
							<td></td>
						</c:forEach>
					</c:if>
					
				</tr>
				
			</table>
		
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
		
		</div>
		
		<jsp:include page="staffFooter.jsp"></jsp:include>
		
	</body>

</html>