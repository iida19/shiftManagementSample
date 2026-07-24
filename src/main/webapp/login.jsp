<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String em = ( String )request.getAttribute( "em" );
	if ( em == null ) {
		em = "";
	}
%>
    
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>ログイン</title>
		<link href="<%=request.getContextPath() %>/css/style.css" rel="stylesheet" type="text/css">
	</head>

	<body>
	
		<div class="title">シフト管理アプリ</div>
		
		<div class="lg-area">
			<form action="<%=request.getContextPath() %>/LoginServlet" method="post">
				<div class="lg-tbox">
					ユーザー名：<input type="text" name="userId" value="${ user.userId }">
				</div>
				<div class="lg-tbox">	
					パスワード：<input type="password" name="password">
				</div>	
				<input type="submit" value="ログイン">
			</form>
		</div>
		
		<div class="em">
			<%=em %>
		</div>	
		
	</body>
	
</html>