<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
	<head>
		<meta charset="UTF-8">
		<title>スタッフヘッダー</title>
	</head>

	<body>
	
		<form action="<%=request.getContextPath() %>/StaffServlet" method="get">
			<input type="hidden" name="action" value="requestShift">
			<input type="submit" value="シフト希望提出">
		</form>

	</body>

</html>