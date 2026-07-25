<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>


<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>管理者トップ</title>
	</head>

	<body>
		
		<jsp:include page="managerHeader.jsp"></jsp:include>
		
		<table>
			
			<c:choose>
				<c:when test = "${ not empty todaysShift }">
					<c:forEach var="s" items="${ todaysShift }">
						<tr>
							<td>${ s.userName }</td>
								<c:choose>
									<c:when test = "${ not s.dayOff }">
										<td>${ s.startTime } ～ ${ s.endTime }</td>
									</c:when>
									<c:otherwise>
										<td>休み</td>
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
		
		<form action="${ pageContext.request.contextPath }/ManagerServlet" method="get">
			<button type="submit" name="action" value="logout">ログアウト</button>
		</form>
		
	</body>

</html>