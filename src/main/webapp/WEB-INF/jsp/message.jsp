<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
	<head>
		<meta charset="UTF-8">
		<title>メッセージ</title>
	</head>

	<body>
	
		${ message }
		
		<form action="${ pageContext.request.contextPath }${ redirectPath }" method="get">
			<input type="submit" value="ホームへ">
		</form>

	</body>

</html>