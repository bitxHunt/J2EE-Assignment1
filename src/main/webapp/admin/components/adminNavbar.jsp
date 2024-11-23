<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Admin Nav Bar</title>
</head>
<body>
	<!-- Top Navigation Bar -->
	<div class="navbar bg-base-200 rounded-box mb-8">
		<div class="flex-1">
			<span class="material-symbols-outlined text-blue-500 text-5xl">
				vacuum </span> <a class="btn btn-ghost text-xl">Admin View</a>
		</div>
		<div class="flex-none">
			<button
				onclick="window.location='${pageContext.request.contextPath}/admin/createForm'"
				class="btn btn-primary">Create New</button>
		</div>
	</div>
</body>
</html>