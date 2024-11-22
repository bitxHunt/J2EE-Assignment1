<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="models.service.Service"%>
<%@ page import="models.bundle.Bundle"%>
<%@ page import="models.category.Category"%>
<%@ page import="java.util.ArrayList"%>

<!DOCTYPE html>
<html data-theme="dark">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Home Page</title>
<link href="https://cdn.jsdelivr.net/npm/daisyui@3.9.4/dist/full.css"
	rel="stylesheet">
<script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="min-h-screen bg-base-100">
	<div class="container mx-auto px-4 py-8">
		<!-- Top Navigation Bar -->
		<div class="navbar bg-base-200 rounded-box mb-8">
			<div class="flex-1">
				<a class="btn btn-ghost text-xl">Admin View</a>
			</div>
			<div class="flex-none">
				<button
					onclick="window.location='${pageContext.request.contextPath}/admin/createForm'"
					class="btn btn-primary">Create New</button>
			</div>
		</div>
		<!-- Tabs -->
		<div class="tabs tabs-boxed mb-8 flex flex-wrap justify-center">
			<a class="tab tab-active" onclick="switchTab('services', this)">Services</a>
			<a class="tab" onclick="switchTab('bundles', this)">Bundles</a> <a
				class="tab" onclick="switchTab('categories', this)">Categories</a>
		</div>

		<!-- Services Section -->
		<div id="services"
			class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
			<%
			ArrayList<Service> services = (ArrayList<Service>) request.getAttribute("services");
			if (services != null) {
				for (Service service : services) {
			%>
			<div class="card bg-base-200 shadow-xl">
				<figure class="px-6 pt-6">
					<img
						src="<%=service.getImageUrl() != null
		? service.getImageUrl()
		: "https://res.cloudinary.com/dnaulhgz8/image/upload/v1732267743/default_cleaning_image_fz3izs.webp"%>"
						alt="Service Image" class="rounded-xl object-cover h-48 w-full" />
				</figure>
				<div class="card-body">
					<div class="flex justify-between items-start">
						<h2 class="card-title"><%=service.getServiceName()%></h2>
						<div class="badge badge-primary"><%=service.getCategoryName()%></div>
					</div>
					<p class="text-sm opacity-70"><%=(service.getServiceDescription() == null || service.getServiceDescription() == "")
		? "No description available"
		: service.getServiceDescription()%></p>
					<p class="text-xl font-bold mt-2">
						$<%=String.format("%.2f", service.getPrice())%></p>
					<div class="card-actions justify-end mt-4">
						<button class="btn btn-primary"
							onclick="editService(<%=service.getServiceId()%>)">Edit</button>
					</div>
				</div>
			</div>
			<%
			}
			}
			%>
		</div>

		<!-- Bundles Section -->
		<div id="bundles"
			class="hidden grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
			<%
			ArrayList<Bundle> bundles = (ArrayList<Bundle>) request.getAttribute("bundles");
			if (bundles != null) {
				for (Bundle bundle : bundles) {
			%>
			<div class="card bg-base-200 shadow-xl">
				<figure class="px-6 pt-6">
					<img
						src="<%=bundle.getImageUrl() != null
		? bundle.getImageUrl()
		: "https://res.cloudinary.com/dnaulhgz8/image/upload/v1732267743/default_cleaning_image_fz3izs.webp"%>"
						alt="Bundle Image" class="rounded-xl object-cover h-48 w-full" />
				</figure>
				<div class="card-body">
					<div class="flex justify-between items-center">
						<h2 class="card-title"><%=bundle.getBundleName()%></h2>
						<div class="badge badge-secondary"><%=bundle.getDiscountPercent()%>%
							OFF
						</div>
					</div>

					<!-- Price Section -->
					<div class="flex items-end gap-2 mt-2">
						<span class="text-xl font-bold text-primary"> $<%=String.format("%.2f", bundle.getDiscountedPrice())%>
						</span> <span class="text-sm line-through opacity-50"> $<%=String.format("%.2f", bundle.getOriginalPrice())%>
						</span>
					</div>

					<div class="divider">Included Services</div>
					<ul class="list-disc list-inside text-sm">
						<%
						for (Service service : bundle.getServices()) {
						%>
						<li><%=service.getServiceName()%> - $<%=String.format("%.2f", service.getPrice())%></li>
						<%
						}
						%>
					</ul>
					<div class="card-actions justify-end mt-4">
						<button class="btn btn-primary"
							onclick="editBundle(<%=bundle.getBundleId()%>)">Edit</button>
					</div>
				</div>
			</div>
			<%
			}
			}
			%>
		</div>

		<!-- Categories Section -->
		<div id="categories"
			class="hidden grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
			<%
			ArrayList<Category> categories = (ArrayList<Category>) request.getAttribute("categories");
			if (categories != null) {
				for (Category category : categories) {
			%>
			<div class="card bg-base-200 shadow-xl">
				<div class="card-body">
					<h2 class="card-title"><%=category.getCategoryName()%></h2>
					<div class="card-actions justify-end mt-4">
						<button class="btn btn-primary"
							onclick="editCategory(<%=category.getCategoryId()%>)">Edit</button>
					</div>
				</div>
			</div>
			<%
			}
			}
			%>
		</div>
	</div>

	<script>
       function switchTab(tabId, element) {
           // Hide all tabs
           document.getElementById('services').classList.add('hidden');
           document.getElementById('bundles').classList.add('hidden');
           document.getElementById('categories').classList.add('hidden');
           
           // Show selected tab
           document.getElementById(tabId).classList.remove('hidden');
           
           // Update active state
           document.querySelectorAll('.tab').forEach(tab => tab.classList.remove('tab-active'));
           element.classList.add('tab-active');
       }

       function editService(id) {
           // Add edit functionality
           console.log('Edit service:', id);
       }

       function editBundle(id) {
           // Add edit functionality
           console.log('Edit bundle:', id);
       }

       function editCategory(id) {
           // Add edit functionality
           console.log('Edit category:', id);
       }
   </script>
</body>
</html>