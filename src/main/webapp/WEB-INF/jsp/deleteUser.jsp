<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
	
<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<title>従業員削除</title>
	</head>

	<body>
		
		<jsp:include page="managerHeader.jsp"></jsp:include>
		
		<form action="${ pageContext.request.contextPath }/ManagerServlet" method="post">
			
			<table>
				
				<tr>
					<th>ユーザーID</th>
					<th>従業員名</th>
				</tr>
				
				<c:forEach var="user" items="${ userList }">
					<tr>
						<td>
							${ user.userId }
						</td>
						<td>
							${ user.userName }
						</td>
						<td>
							<input type="checkbox" name="deleteId" value="${ user.userId }">
						</td>
					</tr>
				</c:forEach>
				
			</table>
			
			<input type="hidden" name="action" value="delete">
			<input type="submit" value="削除">
		</form>
		
		<jsp:include page="managerFooter.jsp"></jsp:include>

	</body>
	
</html>