<%-- 
    Name: Soe Zaw Aung, Scott
    Class: DIT/FT/2B/01
    Admin No: p2340474
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="models.bundle.Bundle"%>
<%@ page import="models.service.Service"%>
<%@ page import="java.util.ArrayList"%>
<!DOCTYPE html>
<html data-theme="dark">
<head>
<meta charset="UTF-8">
<title>Service Bundles</title>
<link href="https://cdn.jsdelivr.net/npm/daisyui@3.9.4/dist/full.css"
	rel="stylesheet">
<script src="https://cdn.tailwindcss.com"></script>
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@24,400,0,0" />
</head>
<body class="min-h-screen bg-base-100">
	<div class="container mx-auto px-4 py-8">
		<!-- Back button -->
		<div class="mb-8">
			<a href="${pageContext.request.contextPath}/categories"
				class="btn btn-ghost gap-2 mb-4"> <span
				class="material-symbols-outlined">arrow_back</span> Back to
				Categories
			</a>
		</div>

		<div class="text-center mb-16">
			<h1
				class="text-5xl font-bold bg-gradient-to-r from-primary to-secondary inline-block text-transparent bg-clip-text">
				Service Bundles</h1>
			<p class="text-base-content/70 mt-4">Save more with our specially
				curated service bundles</p>
		</div>

		<div
			class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8 max-w-7xl mx-auto">
			<%
			ArrayList<Bundle> bundles = (ArrayList<Bundle>) request.getAttribute("bundles");
			if (bundles != null && !bundles.isEmpty()) {
				for (Bundle bundle : bundles) {
			%>
			<div
				class="card bg-base-200/50 hover:bg-base-200 transition-all duration-300 shadow-lg hover:shadow-primary/20">
				<figure class="px-6 pt-6">
					<img
						src="<%=bundle.getImageUrl() != null ? bundle.getImageUrl() : "https://placehold.co/600x400"%>"
						alt="<%=bundle.getBundleName()%>"
						class="rounded-xl aspect-video object-cover w-full" />
				</figure>
				<div class="card-body">
					<div class="flex justify-between items-start">
						<h2 class="card-title text-2xl font-bold text-white"><%=bundle.getBundleName()%></h2>
						<div class="badge badge-secondary"><%=bundle.getDiscountPercent()%>%
							OFF
						</div>
					</div>

					<!-- Price Section -->
					<div class="flex items-baseline gap-2 mt-2">
						<span class="text-2xl font-bold text-primary"> $<%=String.format("%.2f", bundle.getDiscountedPrice())%>
						</span> <span class="text-base-content/50 line-through"> $<%=String.format("%.2f", bundle.getOriginalPrice())%>
						</span>
					</div>

					<!-- Included Services -->
					<div class="mt-4">
						<h3 class="font-semibold mb-2 text-base-content/80">Included
							Services:</h3>
						<div class="space-y-1">
							<%
							for (Service service : bundle.getServices()) {
							%>
							<div class="flex items-center gap-2 text-base-content/70">
								<div class="w-2 h-2 rounded-full bg-primary"></div>
								<span><%=service.getServiceName()%></span>
							</div>
							<%
							}
							%>
						</div>
					</div>
					<!-- Action Button -->
					<%
					Object userId = session.getAttribute("userId");
					if (userId != null) {
					%>
					<button class="btn btn-primary mt-6"
						onclick="window.location.href='${pageContext.request.contextPath}/book?bundle=<%= bundle.getBundleId() %>'">
						<span class="material-symbols-outlined">shopping_cart</span> Book
						Now
					</button>
					<%
					} else {
					%>
					<button class="btn btn-primary mt-6"
						onclick="window.location.href='${pageContext.request.contextPath}/login?redirect=bundles'">
						<span class="material-symbols-outlined">login</span> Login to Book
					</button>
					<%
					}
					%>
				</div>
			</div>
			<%
			}
			} else {
			%>
			<div class="col-span-full text-center py-16">
				<span
					class="material-symbols-outlined text-6xl text-base-content/20 mb-4">inventory_2</span>
				<h2 class="text-2xl font-bold text-base-content/50">No Bundles
					Available</h2>
				<p class="text-base-content/50 mt-2">Please check back later for
					our special bundle offers.</p>
			</div>
			<%
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