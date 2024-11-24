<%-- 
    Name: Soe Zaw Aung, Scott
    Class: DIT/FT/2B/01
    Admin No: p2340474
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="models.category.Category"%>
<%@ page import="models.service.Service"%>
<%@ page import="java.util.ArrayList"%>
<!DOCTYPE html>
<html data-theme="dark">
<head>
<meta charset="UTF-8">
<title>Services</title>
<link href="https://cdn.jsdelivr.net/npm/daisyui@3.9.4/dist/full.css"
	rel="stylesheet">
<script src="https://cdn.tailwindcss.com"></script>
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@24,400,0,0" />
</head>
<body class="min-h-screen bg-base-100">
	

	<div class="container mx-auto px-4 py-8">
		<%
		Category category = (Category) request.getAttribute("category");
		ArrayList<Service> services = (ArrayList<Service>) request.getAttribute("services");
		%>

		<!-- Back button and Category Title -->
		<div class="mb-8">
			<a href="${pageContext.request.contextPath}/categories"
				class="btn btn-ghost gap-2 mb-4"> <span
				class="material-symbols-outlined">arrow_back</span> Back to
				Categories
			</a>
			<h1 class="text-4xl font-bold text-white">
				<%=category.getCategoryName()%>
				Services
			</h1>
		</div>

		<!-- Services Grid -->
		<div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
			<%
			if (services != null && !services.isEmpty()) {
				for (Service service : services) {
			%>
			<div
				class="card bg-base-200 shadow-xl hover:shadow-2xl transition-all duration-300">
				<figure class="px-6 pt-6">
					<img
						src="<%=service.getImageUrl() != null ? service.getImageUrl() : "https://placehold.co/600x400"%>"
						alt="<%=service.getServiceName()%>"
						class="rounded-xl object-cover w-full h-48" />
				</figure>
				<div class="card-body">
					<h2 class="card-title text-white"><%=service.getServiceName()%></h2>
					<p class="text-base-content/70"><%=service.getServiceDescription()%></p>
					<div class="flex justify-between items-center mt-4">
						<span class="text-2xl font-bold text-primary">$<%=String.format("%.2f", service.getPrice())%></span>
						<%
						// Check if user is logged in
						Object userId = session.getAttribute("userId");
						if (userId != null) {
						%>
						<button class="btn btn-primary"
							onclick="window.location.href='${pageContext.request.contextPath}/book'">
							Book Now</button>
						<%
						} else {
						%>
						<button class="btn btn-primary"
							onclick="window.location.href='${pageContext.request.contextPath}/login'">
							Login to Book</button>
						<%
						}
						%>
					</div>
				</div>
			</div>
			<%
			}
			} else {
			%>
			<div class="col-span-full text-center py-16">
				<span
					class="material-symbols-outlined text-6xl text-base-content/20 mb-4">warning</span>
				<h2 class="text-2xl font-bold text-base-content/50">No Services
					Available</h2>
				<p class="text-base-content/50 mt-2">Please check back later for
					updates.</p>
			</div>
			<%
			}
			%>
		</div>
	</div>

	<!-- Optional: Add a nice gradient background effect -->
	<div class="fixed inset-0 -z-10 bg-base-100">
		<div
			class="absolute inset-0 bg-gradient-to-br from-base-200/50 via-transparent to-base-300/50"></div>
	</div>
</body>
</html>