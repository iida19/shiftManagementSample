<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
	
<!DOCTYPE html>

<html>
	<head>
		<meta charset="UTF-8">
		<title>メッセージ</title>
	</head>

	<body>
		
		<c:choose>
			<c:when test="${ empty sessionScope.user }">
				<!-- ヘッダーなし -->
			</c:when>
			<c:when test="${ sessionScope.user.role eq '0' }">
				<jsp:include page="staffHeader.jsp"></jsp:include>
			</c:when>
			<c:otherwise>
				<jsp:include page="managerHeader.jsp"></jsp:include>
			</c:otherwise>
		</c:choose>
	
		${ message }
		
		<form action="${ pageContext.request.contextPath }${ redirectPath }" method="get">
			<input type="submit" value="ホームへ">
		</form>

	</body>

</html>