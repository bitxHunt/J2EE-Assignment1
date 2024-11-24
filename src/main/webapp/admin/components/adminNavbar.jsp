<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Admin Nav Bar</title>
<link href="https://cdn.jsdelivr.net/npm/daisyui@3.9.4/dist/full.css"
	rel="stylesheet">
<script src="https://cdn.tailwindcss.com"></script>
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@24,400,0,0&icon_names=vacuum" />
</head>
<body>
	<!-- Top Navigation Bar -->
	<div class="navbar bg-base-200 flex items-center h-16 mb-8">
		<!-- Left side -->
		<div class="flex-1 flex items-center gap-4">
			<a href="${pageContext.request.contextPath}/admin"
				class="btn btn-ghost"> <span
				class="material-symbols-outlined text-blue-500 text-4xl">vacuum</span>
			</a>
			<p class="text-xl">Admin View</p>
		</div>

		<!-- Right side -->
		<div class="flex items-center gap-4">
			<a href="${pageContext.request.contextPath}/admin"
				class="btn btn-ghost"> Home </a> <a
				href="${pageContext.request.contextPath}/admin/manage-users"
				class="btn btn-ghost"> HR Management </a>
			<button
				onclick="window.location='${pageContext.request.contextPath}/admin/createForm'"
				class="btn btn-primary">Create New</button>
		</div>
	</div>
</body>
</html>