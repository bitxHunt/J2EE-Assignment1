<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html data-theme="dark">
<head>
<meta charset="UTF-8">
<title>Create Service/Bundle</title>
<link href="https://cdn.jsdelivr.net/npm/daisyui@3.9.4/dist/full.css"
	rel="stylesheet">
<script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="min-h-screen bg-base-100 p-8">
	<button
		onclick="window.location='${pageContext.request.contextPath}/admin/createForm.jsp'"
		class="btn btn-primary mb-4">Create New</button>
</body>
</html>