<%-- 
    Name: Soe Zaw Aung, Scott
    Class: DIT/FT/2B/01
    Admin No: p2340474
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html data-theme="dark">
<head>
<meta charset="UTF-8">
<title>404 - Page Not Found</title>
<link href="https://cdn.jsdelivr.net/npm/daisyui@3.9.4/dist/full.css"
	rel="stylesheet">
<script src="https://cdn.tailwindcss.com"></script>
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@24,400,0,0" />
</head>
<body class="min-h-screen bg-base-100 flex items-center justify-center">
	<div
		class="max-w-2xl mx-auto px-4 flex flex-col items-center text-center">
		<!-- 404 Number -->
		<div
			class="text-[150px] font-bold bg-gradient-to-r from-primary to-secondary bg-clip-text text-transparent leading-none">
			404</div>

		<!-- Icon -->
		<span
			class="material-symbols-outlined text-8xl text-base-content/20 mb-8 animate-bounce">
			cleaning_services </span>

		<!-- Message -->
		<h1 class="text-4xl font-bold mb-4">Oops! Page Not Found</h1>
		<p class="text-lg text-base-content/70 mb-8">Looks like we're
			having trouble finding what you're looking for. The page you
			requested could not be found.</p>

		<!-- Buttons -->
		<div class="flex gap-4">
			<button onclick="history.back()" class="btn btn-outline">
				<span class="material-symbols-outlined">arrow_back</span> Go Back
			</button>
			<a href="${pageContext.request.contextPath}/" class="btn btn-primary">
				<span class="material-symbols-outlined">home</span> Home Page
			</a>
		</div>


		<p class="mt-12 text-base-content/50 italic">"Even our best
			cleaners couldn't find this page!"</p>
		<p class="text-base-content/30 text-md mt-2">- Soe Zaw Aung
			(Scott), CEO</p>
	</div>



</body>
</html>