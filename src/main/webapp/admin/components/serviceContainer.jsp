<%-- 
    Name: Soe Zaw Aung, Scott
    Class: DIT/FT/2B/01
    Admin No: p2340474
--%>
<%@page import="models.service.Service"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Service Container</title>
</head>
<body>
	<!-- Services Section -->
	<div id="services"
		class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
		<%
		ArrayList<Service> services = (ArrayList<Service>) request.getAttribute("services");
		if (services != null && !services.isEmpty()) {
			for (Service service : services) {
		%>
		<div
			class="card bg-base-200 shadow-xl <%=!service.getIsActive() ? "opacity-40" : ""%>">
			<figure class="px-6 pt-6">
				<img
					src="<%=service.getImageUrl() != null ? service.getImageUrl()
		: "https://res.cloudinary.com/dnaulhgz8/image/upload/v1732267743/default_cleaning_image_fz3izs.webp"%>"
					alt="Service Image" class="rounded-xl object-cover h-48 w-full" />
			</figure>
			<div class="card-body">
				<div class="flex justify-between items-start">
					<h2 class="card-title"><%=service.getServiceName()%></h2>
					<div class="badge badge-primary badge-lg whitespace-nowrap px-4"><%=service.getCategoryName()%></div>
				</div>
				<p class="text-sm opacity-70"><%=(service.getServiceDescription() == null || service.getServiceDescription() == "") ? "No description available"
				: service.getServiceDescription()%></p>
				<p class="text-xl font-bold mt-2">
					$<%=String.format("%.2f", service.getPrice())%></p>
				<div class="card-actions justify-between items-center mt-4">
					<div class="flex items-center gap-2">
						<span class="text-sm font-medium">Status:</span> <span
							class="px-3 py-1 rounded-full text-sm font-semibold <%=service.getIsActive() ? "bg-green-100 text-green-800" : "bg-red-100 text-red-800"%>">
							<%=service.getIsActive() ? "Active" : "Inactive"%>
						</span>
					</div>
					<button class="btn btn-primary"
						onclick="window.location.href='${pageContext.request.contextPath}/admin/edit-service/<%=service.getServiceId()%>'">
						Edit</button>
				</div>
			</div>
		</div>
		<%
		}
		} else {
		%>
		<div class="col-span-full text-center py-8">
			<div class="max-w-md mx-auto bg-base-200 rounded-xl shadow-lg p-6">
				<h3 class="text-lg font-semibold mb-2">No Services Available</h3>
				<p class="text-sm opacity-70 mb-4">There are currently no
					services. Click 'Create New' to add a service.</p>
				<button
					onclick="window.location='${pageContext.request.contextPath}/admin/createForm?tab=service'"
					class="btn btn-primary">Create Service</button>
			</div>
		</div>
		<%
		}
		%>
	</div>
</body>
</html>