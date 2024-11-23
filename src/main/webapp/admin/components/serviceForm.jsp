<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.Map"%>
<%@ page import="models.service.Service"%>
<%@ page import="models.category.Category"%>
<%@ page import="java.util.ArrayList"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Service Form</title>
</head>
<body>
	<%
	ArrayList<Category> categories = null;

	try {
		categories = (ArrayList<Category>) request.getAttribute("categories");
	%>
	<!-- Service form -->
	<div id="serviceForm"
		class="card bg-base-200 shadow-xl <%=request.getParameter("tab") != null && !request.getParameter("tab").equals("service") ? "hidden" : ""%>">
		<!-- Service form content -->
		<div class="card-body">
			<h2 class="card-title text-xl md:text-2xl mb-4">Create Service</h2>
			<form action="create-new-service" method="POST"
				enctype="multipart/form-data">
				<input type="hidden" name="formType" value="service">

				<div class="grid gap-4">
					<div class="form-control">
						<label class="label">Service Name</label> <input type="text"
							name="serviceName" class="input input-bordered w-full" required>
					</div>

					<div class="form-control">
						<label class="label">Description</label>
						<textarea name="description"
							class="textarea textarea-bordered h-24"></textarea>
					</div>

					<div class="grid gap-4 md:grid-cols-2">
						<div class="form-control">
							<label class="label">Category</label> <select name="categoryId"
								class="select select-bordered w-full" required>
								<option value="" disabled selected>Select a category</option>
								<%
								if (categories != null && !categories.isEmpty()) {
									for (Category category : categories) {
								%>
								<option value="<%=category.getCategoryId()%>"><%=category.getCategoryName()%></option>
								<%
								}
								}
								%>
							</select>
						</div>

						<div class="form-control">
							<label class="label">Service Image (Optional)</label> <input
								type="file" name="serviceImage" accept="image/*"
								class="file-input file-input-bordered w-full">
						</div>

						<div class="form-control">
							<label class="label">Price ($)</label> <input type="number"
								name="price" class="input input-bordered" step="0.01" required>
						</div>
					</div>

					<div class="flex flex-col sm:flex-row gap-2 justify-end mt-4">
						<button type="button"
							onclick="window.location='${pageContext.request.contextPath}/admin'"
							class="btn btn-neutral w-full sm:w-auto">Cancel</button>
						<button type="submit" class="btn btn-primary w-full sm:w-auto">Create
							Service</button>
					</div>
				</div>
			</form>
		</div>
	</div>
	<%
	} catch (Exception e) {
	String err = (String) request.getAttribute("err");
	if (err != null) {
	%>
	<div class="alert alert-error">
		<%=err%>
	</div>
	<%
	}
	}
	%>
</body>
</html>