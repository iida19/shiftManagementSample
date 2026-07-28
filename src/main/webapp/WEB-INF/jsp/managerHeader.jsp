<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<title>管理者ヘッダー</title>
	</head>

	<body>
		
		<div style="display: flex; gap: 30px; align-items: flex-start;">
			
			<form action="${ pageContext.request.contextPath }/ManagerServlet" method="get">
				<input type="submit" value="ホームへ">
			</form>
		
			<form action="${ pageContext.request.contextPath }/ManagerServlet" method="get">
				<input type="hidden" name="action" value="checkRequestShift">
				<input type="submit" value="シフト希望一覧">
			</form>
		
			<form action="${ pageContext.request.contextPath }/ManagerServlet" method="get">
				<input type="hidden" name="action" value="checkConfirmedShift">
				<input type="submit" value="確定シフト一覧">
			</form>
			
			<form action="${ pageContext.request.contextPath }/ManagerServlet" method="get">
				<input type="hidden" name="action" value="managementUser">
				<input type="submit" value="従業員管理">
			</form>
			
			<form action="${ pageContext.request.contextPath }/ForumServlet" method="get">
				<input type="submit" value="掲示板">
			</form>
			
		</div>

	</body>
</html>