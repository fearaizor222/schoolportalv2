<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Index</title>
</head>
<body>
	<table class="table table-hover">
		<tr>
			<th>Username</th>
			<th>Password</th>
			<th>Fullname</th>
			<th></th>
			<th></th>
		</tr>
		<c:forEach var="u" items="${users }">
			<tr>
				<td>${u.username }</td>
				<td>${u.password }</td>
				<td>${u.fullname }</td>
				<td><a href="update.htm?userId=${u.username}">update</a></td>
				<td><a href="delete/${u.username}.htm">Delete</a></td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>