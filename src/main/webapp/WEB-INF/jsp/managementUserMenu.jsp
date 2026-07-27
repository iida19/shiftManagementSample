<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<title>従業員管理メニュー</title>
	</head>

	<body>
		
		<jsp:include page="managerHeader.jsp"></jsp:include>
		
		<div style="display: flex; gap: 30px; align-items: flex-start;">
		
			<form action="${ pageContext.request.contextPath }/ManagerServlet" method="get">
				<input type="hidden" name="action" value="registerUser">
				<input type="submit" value="新規従業員登録">
			</form>
		
			<form action="${ pageContext.request.contextPath }/ManagerServlet" method="get">
				<input type="hidden" name="action" value="deleteUser">
				<input type="submit" value="従業員削除">
			</form>
			
		</div>
		
		<jsp:include page="managerFooter.jsp"></jsp:include>

	</body>
	
</html>