<%-- 
    Name: Soe Zaw Aung, Scott
    Class: DIT/FT/2B/01
    Admin No: p2340474
--%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="models.service.Service"%>
<%@ page import="models.category.Category"%>
<%@ page import="java.util.ArrayList"%>
<!DOCTYPE html>
<html data-theme="dark">
<head>
<meta charset="UTF-8">
<title>Edit Service</title>
<link href="https://cdn.jsdelivr.net/npm/daisyui@3.9.4/dist/full.css"
	rel="stylesheet">
<script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="min-h-screen bg-base-100">
	<%
	Service service = (Service) request.getAttribute("service");
	ArrayList<Category> categories = (ArrayList<Category>) request.getAttribute("categories");

	if (service != null && categories != null) {
	%>
	<div class="container mx-auto px-4 py-8 max-w-4xl">
		<%
		HttpSession sess = request.getSession();
		Map<String, String> errors = (Map<String, String>) sess.getAttribute("editServiceFormErrors");

		if (errors != null && !errors.isEmpty()) {
		%>
		<div role="alert" class="alert alert-error mb-4 shadow-lg">
			<div class="flex flex-col items-start">
				<div class="flex items-center gap-2">
					<svg xmlns="http://www.w3.org/2000/svg"
						class="stroke-current shrink-0 h-6 w-6" fill="none"
						viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round"
							stroke-width="2"
							d="M10 14l2-2m0 0l2-2m-2 2l-2-2m2 2l2 2m7-2a9 9 0 11-18 0 9 9 0 0118 0z" />
                </svg>
					<span class="font-semibold">Please check the following:</span>
				</div>
				<ul class="list-disc list-inside mt-2 ml-8">
					<%
					for (Map.Entry<String, String> error : errors.entrySet()) {
					%>
					<li class="text-sm"><%=error.getValue()%></li>
					<%
					}
					%>
				</ul>
			</div>
		</div>
		<%
		sess.removeAttribute("editServiceFormErrors");
		}
		%>

		<div class="card bg-base-200 shadow-xl">
			<div class="card-body">
				<h2 class="card-title text-xl md:text-2xl mb-4">Edit Service</h2>
				<form
					action="${pageContext.request.contextPath}/admin/edit-service/<%=service.getServiceId()%>"
					method="POST" enctype="multipart/form-data">
					<div class="grid gap-4">
						<div class="form-control">
							<label class="label cursor-pointer flex justify-start gap-4">
								<span class="label-text text-sm font-medium">Status</span>
								<div class="flex items-center gap-2">
									<input type="checkbox" name="isActive"
										class="toggle toggle-primary"
										<%=service.getIsActive() ? "checked" : ""%>> <span
										class="text-sm"><%=service.getIsActive() ? "Active" : "Inactive"%></span>
								</div>
							</label>
						</div>

						<div class="form-control">
							<label class="label">Service Name</label> <input type="text"
								name="serviceName" value="<%=service.getServiceName()%>"
								class="input input-bordered w-full" required>
						</div>

						<div class="form-control">
							<label class="label">Description</label>
							<textarea name="description"
								class="textarea textarea-bordered h-24"><%=service.getServiceDescription()%></textarea>
						</div>

						<div class="grid gap-4 md:grid-cols-2">
							<div class="form-control">
								<label class="label">Category</label> <select name="categoryId"
									class="select select-bordered w-full" required>
									<%
									for (Category category : categories) {
									%>
									<option value="<%=category.getCategoryId()%>"
										<%=category.getCategoryId() == service.getCategoryId() ? "selected" : ""%>>
										<%=category.getCategoryName()%>
									</option>
									<%
									}
									%>
								</select>
							</div>

							<div class="form-control">
								<label class="label">Service Image</label> <img
									src="<%=service.getImageUrl() == null || service.getImageUrl().trim().isEmpty()
		? "https://cdn-icons-png.flaticon.com/512/1999/1999150.png"
		: service.getImageUrl()%>"
									alt="Current service image" class="max-w-xs mb-2"> <input
									type="file" name="serviceImage" accept="image/*"
									class="file-input file-input-bordered w-full">
							</div>

							<div class="form-control">
								<label class="label">Price ($)</label> <input type="number"
									name="price" value="<%=service.getPrice()%>"
									class="input input-bordered" step="0.01" required>
							</div>
						</div>
						<div class="flex flex-col sm:flex-row gap-2 justify-end mt-4">
							<button type="button"
								onclick="window.location='${pageContext.request.contextPath}/admin'"
								class="btn btn-neutral w-full sm:w-auto">Cancel</button>
							<button type="submit" class="btn btn-primary w-full sm:w-auto">
								Update Service</button>
						</div>
					</div>
				</form>
				<form
					action="${pageContext.request.contextPath}/admin/delete-service/${service.serviceId}"
					method="POST" style="display: inline;">
					<button type="submit" class="btn btn-error w-full sm:w-auto">
						Delete Service</button>
				</form>

			</div>
		</div>
	</div>
	<%
	}
	%>
</body>
</html>