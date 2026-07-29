<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<title>シフト修整</title>
		<link href="${ pageContext.request.contextPath }/css/style.css" rel="stylesheet" type="text/css">
	</head>

	<body>
	
		<jsp:include page="managerHeader.jsp"></jsp:include>
		
		<div style="display: flex; gap: 30px; align-items: flex-start;">
		
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


			<form action="${ pageContext.request.contextPath }/ManagerServlet" method="post">
						
				<table class="shiftForm">	
					
					<tr>
						<th colspan="5">${ periodDateList[0].monthValue }月</th>
					</tr>
					
					<tr>
						<th>日付</th>
						<th>名前</th>
						<th>開始～終了</th>
						<th>終日</th>
						<th>休み</th>
					</tr>
					
					<c:set var="last" value="${ fn:length( inputingHours ) -1 }"></c:set>
					
					<c:forEach var="date" items="${ periodDateList }">
						<c:set var="shiftList" value="${ shiftMap[date] }"></c:set>
								
							<c:choose>
										
								<c:when test="${ not empty shiftList }">
									<c:forEach var="shift" items="${ shiftList }" varStatus="status">
										<c:set var="isAllDay" value="${ inputingHours[0] eq shift.startTime and inputingHours[last] eq shift.endTime }"></c:set>
										
											<tr>
														
												<c:if test="${ status.first }">
													<th rowspan="${ fn:length( shiftList ) }">${ date.dayOfMonth }日</th>
												</c:if>
														
												<td>${ shift.userName }</td>
												<td>
													
													<input type="hidden" name="userId" value="${ shift.userId }">
													<input type="hidden" name="userName" value="${ shift.userName }">
													<input type="hidden" name="shiftDate" value="${ date }">
													
													<select name="startTime">
														<option value="">--選択--</option>
														<c:forEach var="hour" items="${ inputingHours }">
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
														<c:forEach var="hour" items="${ inputingHours }">
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
															<input type="checkbox" name="allDay_${ date }_${ shift.userId }" value="true" checked>終日
														</c:when>
														<c:otherwise>
															<input type="checkbox" name="allDay_${ date }_${ shift.userId }" value="true">終日
														</c:otherwise>
													</c:choose>
												</td>
												<td>
													<c:choose>
														<c:when test="${ shift.dayOff }">
															<input type="checkbox" name="dayOff_${ date }_${ shift.userId }" value="true" checked>休み
														</c:when>
														<c:otherwise>
															<input type="checkbox" name="dayOff_${ date }_${ shift.userId }" value="true">休み
														</c:otherwise>
													</c:choose>
												</td>
														
											</tr>
											
									</c:forEach>
								</c:when>
										
								<c:otherwise>
									<tr>
										<th>${ date.dayOfMonth }日</th>
										<td colspan="4">確定済みシフトなし</td>
									</tr>
								</c:otherwise>
										
							</c:choose>
		
					</c:forEach>
						
				</table>
							
				<button type="submit" name="action" value="retouch">送信</button>
						
			</form>
				
		</div>

	</body>

</html>