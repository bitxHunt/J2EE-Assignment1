<%-- 
    Name: Thiha Swan Htet
    Class: DIT/FT/2B/01
    Admin No: p2336671
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="java.util.ArrayList, models.service.Service, models.bundle.Bundle"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Services & Bundles</title>
<link href="https://cdn.jsdelivr.net/npm/daisyui@3.5.0/dist/full.css"
	rel="stylesheet">
<script src="https://cdn.tailwindcss.com"></script>
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@24,400,0,0" />
</head>
<body class="bg-base-200 min-h-screen">
	<!-- Header -->
	<%
	Integer userId = (Integer) session.getAttribute("userId");
	if (userId != null) {
	%>
	<%@ include file="./components/header.jsp"%>
	<%
	} else {
	%>
	<%@ include file="../../public/components/header.jsp"%>
	<%
	}
	%>
	<div class="container mx-auto p-4">
		<div class="card bg-base-100 shadow-xl">
			<div class="card-body">
				<h2 class="card-title text-2xl mb-6">Our Services</h2>

				<!-- Progress Steps -->
				<ul class="steps steps-horizontal w-full mb-8">
					<li class="step step-primary">Select Time</li>
					<li class="step step-primary">Choose Address</li>
					<li class="step step-primary">Service Info</li>
					<li class="step">Review</li>
				</ul>

				<form action="${pageContext.request.contextPath}/book/review"
					method="post">
					<!-- Bundles Section -->
					<div class="mb-8">
						<h3 class="text-xl font-bold mb-4">Service Bundles</h3>
						<div class="grid grid-cols-1 md:grid-cols-2 gap-6">
							<%
							ArrayList<Bundle> bundles = (ArrayList<Bundle>) request.getAttribute("bundles");
							if (bundles != null) {
								for (Bundle bundle : bundles) {
							%>
							<div class="card bg-base-200 shadow-sm">
								<div class="card-body">
									<div class="flex items-center mb-4">
										<img src="<%=bundle.getImageUrl()%>"
											alt="<%=bundle.getBundleName()%>"
											class="w-20 h-20 mr-4 object-cover rounded">
										<div>
											<span class="text-lg font-bold"><%=bundle.getBundleName()%></span>
											<div class="badge badge-secondary ml-2"><%=bundle.getDiscountPercent()%>%
												OFF
											</div>
										</div>
									</div>

									<!-- Services included -->
									<div class="ml-4 mb-4">
										<p class="text-sm font-semibold mb-2">Included Services:</p>
										<ul class="text-sm text-gray-600">
											<%
											for (Service service : bundle.getServices()) {
											%>
											<li class="flex justify-between mb-1"><span>• <%=service.getServiceName()%></span>
												<span>$<%=service.getPrice()%></span></li>
											<%
											}
											%>
										</ul>
									</div>

									<div class="flex justify-between items-center mt-4">
										<div>
											<span class="text-sm line-through text-gray-500">$<%=bundle.getOriginalPrice()%></span>
											<span class="text-xl font-bold text-primary ml-2">$<%=bundle.getDiscountedPrice()%></span>
										</div>
										<input type="radio" name="selected_bundle"
											value="<%=bundle.getBundleId()%>" class="radio radio-primary">
									</div>
								</div>
							</div>
							<%
							}
							}
							%>
						</div>
					</div>

					<!-- Divider -->
					<div class="divider text-base-content">OR/AND</div>

					<!-- Individual Services Section -->
					<div class="mt-8">
						<h3 class="text-xl font-bold mb-4">Individual Services</h3>
						<div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
							<%
							ArrayList<Service> services = (ArrayList<Service>) request.getAttribute("services");
							if (services != null) {
								for (Service service : services) {
							%>
							<div class="card bg-base-200 shadow-sm">
								<div class="card-body">
									<div class="form-control">
										<label class="label cursor-pointer justify-between">
											<div class="flex items-center">
												<img src="<%=service.getImageUrl()%>"
													alt="<%=service.getServiceName()%>"
													class="w-16 h-16 mr-4 object-cover rounded">
												<div>
													<span class="label-text font-bold"><%=service.getServiceName()%></span>
													<p class="text-sm text-gray-600"><%=service.getServiceDescription()%></p>
												</div>
											</div> <input type="checkbox" name="selected_services"
											value="<%=service.getServiceId()%>"
											class="checkbox checkbox-primary">
										</label>
									</div>
									<div class="card-actions justify-end">
										<span class="text-primary font-bold">$<%=service.getPrice()%></span>
									</div>
								</div>
							</div>
							<%
							}
							}
							%>
						</div>
					</div>

					<div class="card-actions justify-between mt-6">
						<a href="${pageContext.request.contextPath}/book/address"
							class="btn">Back</a>
						<button type="submit" class="btn btn-primary">Review</button>
					</div>
				</form>
			</div>
		</div>
	</div>
	<!-- Footer -->
	<%@ include file="components/footer.jsp"%>
</body>
</html>