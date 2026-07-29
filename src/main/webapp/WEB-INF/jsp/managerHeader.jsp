<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<div class="systemHeader">
	
	<div class="header-body">
	
		${ user.userName }さん、お疲れさまです
		
		<div class="header-message">
			
			<c:forEach var="importantPost" items="${ requestScope.importantPostList }">
				${ importantPost.body }<br>
			</c:forEach>
		
		</div>
		
		<div class="header-menu">
			
			<form action="${ pageContext.request.contextPath }/ManagerServlet" method="get">
				<input class="header-button ${currentMenu == 'home' ? 'active' : ''}" type="submit" value="ホームへ">
			</form>
		
			<form action="${ pageContext.request.contextPath }/ManagerServlet" method="get">
				<input type="hidden" name="action" value="checkRequestShift">
				<input class="header-button ${currentMenu == 'checkRequest' ? 'active' : ''}" type="submit" value="シフト希望一覧">
			</form>
		
			<form action="${ pageContext.request.contextPath }/ManagerServlet" method="get">
				<input type="hidden" name="action" value="checkConfirmedShift">
				<input class="header-button ${currentMenu == 'checkConfirm' ? 'active' : ''}" type="submit" value="確定シフト一覧">
			</form>
			
			<form action="${ pageContext.request.contextPath }/ManagerServlet" method="get">
				<input type="hidden" name="action" value="managementUser">
				<input class="header-button ${currentMenu == 'management' ? 'active' : ''}" type="submit" value="従業員管理">
			</form>
			
			<form action="${ pageContext.request.contextPath }/ForumServlet" method="get">
				<input class="header-button ${currentMenu == 'forum' ? 'active' : ''}" type="submit" value="掲示板">
			</form>
			
		</div>
		
	</div>

</div>