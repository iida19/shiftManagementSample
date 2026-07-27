<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>スタッフフッター</title>
	</head>

	<body>

		<form action="${ pageContext.request.contextPath }/StaffServlet" method="get">
			<button type="submit" name="action" value="logout">ログアウト</button>
		</form>

	</body>

</html>