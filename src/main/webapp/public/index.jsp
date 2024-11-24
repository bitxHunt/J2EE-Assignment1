<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="models.bundle.Bundle"%>
<%@ page import="models.category.Category"%>
<%@ page import="java.util.ArrayList"%>

<!DOCTYPE html>
<html lang="en" data-theme="dark">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>CleanX - Professional Cleaning Services</title>
<link
	href="https://cdn.jsdelivr.net/npm/daisyui@4.12.14/dist/full.min.css"
	rel="stylesheet" type="text/css" />
<script src="https://cdn.tailwindcss.com"></script>
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@24,400,0,0" />
</head>
<body>
	<!-- Header -->
	<%@ include file="components/header.jsp"%>
	<!-- Hero Section -->
	<div class="hero min-h-screen"
		style="background-image: url('https://images.unsplash.com/photo-1581578731548-c64695cc6952?ixlib=rb-4.0.3');">
		<div class="hero-overlay bg-opacity-60"></div>
		<div class="hero-content text-center text-neutral-content">
			<div class="max-w-3xl">
				<h1 class="mb-5 text-5xl font-bold">Professional Cleaning
					Services at Your Doorstep</h1>
				<p class="mb-8 text-lg">Experience the difference with our
					expert cleaning services. We bring sparkle to your space and peace
					to your mind.</p>
				<a href="${pageContext.request.contextPath}/book"
					class="btn btn-primary btn-lg">Book Now</a>
			</div>
		</div>
	</div>

	<!-- Features Section -->
	<section class="py-16 bg-base-100">
		<div class="container mx-auto px-4">
			<div class="grid grid-cols-1 md:grid-cols-3 gap-8">
				<div class="card bg-base-200">
					<div class="card-body items-center text-center">
						<span class="material-symbols-outlined text-4xl text-primary">verified</span>
						<h3 class="card-title">Professional Staff</h3>
						<p>Experienced and vetted cleaning professionals</p>
					</div>
				</div>
				<div class="card bg-base-200">
					<div class="card-body items-center text-center">
						<span class="material-symbols-outlined text-4xl text-primary">eco</span>
						<h3 class="card-title">Eco-Friendly</h3>
						<p>Using environmentally safe cleaning products</p>
					</div>
				</div>
				<div class="card bg-base-200">
					<div class="card-body items-center text-center">
						<span class="material-symbols-outlined text-4xl text-primary">schedule</span>
						<h3 class="card-title">Flexible Scheduling</h3>
						<p>Book services at your convenient time</p>
					</div>
				</div>
			</div>
		</div>
	</section>

	<!-- Featured Categories Section -->
	<section class="py-16 bg-base-200">
		<div class="container mx-auto px-4">
			<div class="text-center mb-12">
				<h2 class="text-3xl font-bold">Our Service Categories</h2>
				<p class="text-base-content/70 mt-2">Explore our comprehensive
					range of cleaning solutions</p>
			</div>

			<div class="grid grid-cols-1 md:grid-cols-3 gap-6 mb-8">
				<%
				ArrayList<Category> categories = (ArrayList<Category>) request.getAttribute("categories");
				if (categories != null && !categories.isEmpty()) {
					for (int i = 0; i < Math.min(3, categories.size()); i++) {
						Category category = categories.get(i);
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
			<div class="text-center">
				<a href="${pageContext.request.contextPath}/categories"
					class="btn btn-primary">View All Categories</a>
			</div>
		</div>
	</section>

	<!-- About Section -->
	<section class="py-16 bg-base-200">
		<div class="container mx-auto px-4 text-center">
			<h2 class="text-3xl font-bold mb-6">Why Choose CleanX</h2>
			<p class="mb-8 max-w-2xl mx-auto">With over a decade of
				experience in professional cleaning services, we pride ourselves on
				delivering exceptional quality and customer satisfaction. Our team
				of experts ensures your space is not just clean, but truly spotless.</p>
			<div class="flex justify-center gap-4">
				<a href="${pageContext.request.contextPath}/about"
					class="btn btn-primary">Learn More</a> <a
					href="${pageContext.request.contextPath}/book"
					class="btn btn-outline">Book Now</a>
			</div>
		</div>
	</section>

	<!-- Footer -->
	<%@ include file="components/footer.jsp"%>
</body>
</html>