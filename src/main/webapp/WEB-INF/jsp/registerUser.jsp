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
		<title>新規従業員登録</title>
		<link href="${ pageContext.request.contextPath }/css/style.css" rel="stylesheet" type="text/css">
	</head>

	<body>
		
		<jsp:include page="managerHeader.jsp"></jsp:include>
	
		<div class="title">新規従業員登録</div>
		
			<form action="${ pageContext.request.contextPath }/ManagerServlet" method="post">
				<input type="hidden" name="action" value="register">
				
					<table>
						<tr>
							<td>
								ユーザーID：
							</td>
							<td>
								<input type="text" name="userId" value="${ userId }">
							</td>
						</tr>
						<tr>	
							<td>
								従業員名：
							</td>
							<td>
								<input type="text" name="userName" value="${ userName }">
							</td>
						</tr>
						<tr>	
							<td>
								区分：
							</td>
							<td>
								<input type="radio" id="0" name="role" value="0" checked>
									<label for="0">従業員</label>
								<input type="radio" id="1" name="role" value="1">
									<label for="1">管理者</label>
							</td>
						</tr>
					</table>

				<input type="submit" value="登録">
			</form>
		
		<div class="em">
			<%=em %>
		</div>
		
		<jsp:include page="managerFooter.jsp"></jsp:include>
		
	</body>
	
</html>