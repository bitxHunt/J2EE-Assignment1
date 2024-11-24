<%-- 
    Name: Soe Zaw Aung, Scott
    Class: DIT/FT/2B/01
    Admin No: p2340474
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="models.category.Category"%>
<%@ page import="java.util.ArrayList"%>
<!DOCTYPE html>
<html data-theme="dark">
<head>
<meta charset="UTF-8">
<title>Service Categories</title>
<link href="https://cdn.jsdelivr.net/npm/daisyui@3.9.4/dist/full.css"
	rel="stylesheet">
<script src="https://cdn.tailwindcss.com"></script>
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@24,400,0,0" />
</head>
<body class="min-h-screen bg-base-100">
	<!-- Header -->
	<%
	Integer userId = (Integer) session.getAttribute("userId");
	if (userId != null) {
	%>
	<%@ include file="../../user/components/header.jsp"%>
	<%
	} else {
	%>
	<%@ include file="header.jsp"%>
	<%
	}
	%>
	<div class="container mx-auto px-4 py-8">

		<div class="text-center mb-16">
			<h1
				class="text-5xl font-bold bg-gradient-to-r from-primary to-secondary inline-block text-transparent bg-clip-text">
				Service Categories</h1>
		</div>

		<!-- Back button -->
		<div class="mb-8">
			<%
			String backRoute = (userId != null) ? "/profile" : "/";
			%>
			<a href="<%=request.getContextPath() + backRoute%>"
				class="btn btn-ghost gap-2"> <span
				class="material-symbols-outlined">arrow_back</span> Back to <%=userId != null ? "Profile" : "Home"%>
			</a>
		</div>

		<!-- Announcement Card for Bundles -->
		<div class="max-w-6xl mx-auto mb-8">
			<div class="alert shadow-lg bg-base-200 text-base-content">
				<span class="material-symbols-outlined text-4xl">package</span>
				<div class="flex flex-col">
					<h3 class="font-bold text-lg">Looking for better deals?</h3>
					<div class="text-base-content/80">Check out our service
						bundles and save up to 70% on your bookings!</div>
				</div>
				<div>
					<button class="btn btn-primary"
						onclick="window.location.href='${pageContext.request.contextPath}/bundles'">
						<span class="material-symbols-outlined">local_offer</span> View
						Bundles
					</button>
				</div>
			</div>
		</div>


		<div class="grid grid-cols-1 md:grid-cols-2 gap-8 max-w-6xl mx-auto">
			<%
			ArrayList<Category> categories = (ArrayList<Category>) request.getAttribute("categories");
			if (categories != null) {
				for (Category category : categories) {
			%>
			<div
				class="card bg-base-200/50 hover:bg-base-200 transition-all duration-300 shadow-lg hover:shadow-primary/20">
				<div class="card-body p-8">
					<!-- Category Header -->
					<div class="flex items-center gap-4 mb-6">
						<span class="material-symbols-outlined text-4xl text-white">cleaning_services</span>
						<div>
							<h2 class="card-title text-2xl font-bold text-white mb-2"><%=category.getCategoryName()%></h2>
							<div class="badge badge-secondary">
								<%=category.getServiceCount()%>
								Services Available
							</div>
						</div>
					</div>

					<!-- Services List -->
					<div class="mb-6">
						<h3 class="text-lg font-semibold mb-3 text-base-content/80">Available
							Services:</h3>
						<div class="space-y-2">
							<%
							if (category.getServices() != null && !category.getServices().isEmpty()) {
								for (String service : category.getServices()) {
							%>
							<div class="flex items-center gap-2 text-base-content/70">
								<div class="w-2 h-2 rounded-full bg-primary"></div>
								<span><%=service%></span>
							</div>
							<%
							}
							} else {
							%>
							<div class="flex items-center gap-2 text-base-content/50 italic">
								<div class="w-2 h-2 rounded-full bg-base-content/20"></div>
								<span>No services available</span>
							</div>
							<%
							}
							%>
						</div>
					</div>

					<!-- Action Button -->
					<div class="card-actions">
						<button class="btn btn-primary btn-block"
							onclick="window.location.href='${pageContext.request.contextPath}/services?category=<%= category.getCategoryId() %>'">
							<span class="material-symbols-outlined">visibility</span> View
							Services
						</button>
					</div>
				</div>
			</div>
			<%
			}
			}
			%>
		</div>
	</div>

	<!-- gradient background effect -->
	<div class="fixed inset-0 -z-10 bg-base-100">
		<div
			class="absolute inset-0 bg-gradient-to-br from-primary/5 via-transparent to-secondary/5"></div>
	</div>
</body>
</html>