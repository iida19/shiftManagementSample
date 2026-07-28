<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
    
<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<title>掲示板</title>
		<link href="${ pageContext.request.contextPath }/css/style.css" rel="stylesheet" type="text/css">
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
	
		<form action="${ pageContext.request.contextPath }/ForumServlet" method="post">
			<input type="text" name="body">
			
				<c:if test="${ sessionScope.user.role eq '1' }">
					<input type="checkbox" name="important" value="true">
				</c:if>
			
			<button type="submit" name="action" value="post">投稿</button>		
		</form>
		
		${ em }
		
		<c:choose>
		
			<c:when test="${ empty postList }">
				<c:if test="${ empty em }">
					まだ投稿はありません。何か投稿してみましょう！
				</c:if>
			</c:when>
			
			<c:otherwise>
			
				<form action="${ pageContext.request.contextPath }/ForumServlet" method="post">
				
					<button type="submit" name="action" value="delete">削除</button>
			
					<table>
						<c:forEach var="post" items="${ postList }">
						
							<c:if test="${ not empty post }">
								
								<c:choose>
									<c:when test="${ post.important }">
										<tr class="importantMessage">
									</c:when>
									<c:otherwise>
										<tr>
									</c:otherwise>
								</c:choose>
								
									<td>
										${ post.userName }
									</td>
									<td>
										${ post.body }
									</td>
									<td>
										${ post.formattedPostdate }
									</td>
									<td>
										<c:choose>
											<c:when test="${ post.userId eq user.userId or user.role eq '1' }">
												<input type="checkbox" name="delete" value="${ post.id }">
											</c:when>
											<c:otherwise>
												<input type="checkbox" disabled>
											</c:otherwise>
										</c:choose>
									</td>	
								</tr>
								
							</c:if>
						
						</c:forEach>
					</table>
				
				</form>
			
			</c:otherwise>
		
		</c:choose>		

	</body>

</html>