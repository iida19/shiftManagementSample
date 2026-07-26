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
		<link href="${ pageContext.request.contextPath }/css/style.css" rel="stylesheet" type="text/css">
	</head>

	<body>
	
		<div class="title">シフト管理アプリ</div>
		
		<div class="lg-area">
			<form action="${ pageContext.request.contextPath }/LoginServlet" method="post">
				
					<table>
						<tr>
							<div class="lg-tbox">
								<td>
									ユーザーID：
								</td>
								<td>
									<input type="text" name="userId" value="${ user.userId }">
								</td>
							</div>
						</tr>
						<tr>
							<div class="lg-tbox">	
								<td>
									パスワード：
								</td>
								<td>
									<input type="password" name="password">
								</td>
							</div>
						</tr>
					</table>

				<input type="submit" value="ログイン">
			</form>
		</div>
		
		<div class="em">
			<%=em %>
		</div>	
		
	</body>
	
</html>