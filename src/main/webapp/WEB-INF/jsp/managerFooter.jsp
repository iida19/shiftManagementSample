<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>管理者フッター</title>
	</head>

	<body>
	
		<form action="${ pageContext.request.contextPath }/ManagerServlet" method="get">
			<button type="submit" name="action" value="logout">ログアウト</button>
		</form>

	</body>

</html>