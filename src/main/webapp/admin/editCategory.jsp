<%-- 
    Name: Soe Zaw Aung, Scott
    Class: DIT/FT/2B/01
    Admin No: p2340474
--%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="models.category.Category"%>
<!DOCTYPE html>
<html data-theme="dark">
<head>
<meta charset="UTF-8">
<title>Edit Category</title>
<link href="https://cdn.jsdelivr.net/npm/daisyui@3.9.4/dist/full.css"
	rel="stylesheet">
<script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="min-h-screen bg-base-100">
	<div class="container mx-auto px-4 py-8 max-w-4xl">
		<%
		Category category = (Category) request.getAttribute("category");
		boolean isDeleteable = (boolean) request.getAttribute("deleteable");
		if (category != null) {
		%>
		<div class="card bg-base-200 shadow-xl">
			<div class="card-body">
				<h2 class="card-title text-xl md:text-2xl mb-4">Edit Category</h2>
				<form
					action="<%=request.getContextPath()%>/admin/edit-category/<%=category.getCategoryId()%>"
					method="POST">
					<input type="hidden" name="_method" value="POST">

					<div class="grid gap-4">
						<div class="form-control">
							<label class="label">Category Name</label> <input type="text"
								name="categoryName" value="<%=category.getCategoryName()%>"
								class="input input-bordered w-full" required>
						</div>

						<div class="flex flex-col sm:flex-row gap-2 justify-end mt-4">
							<button type="button"
								onclick="window.location='${pageContext.request.contextPath}/admin'"
								class="btn btn-neutral w-full sm:w-auto">Cancel</button>
							<button type="submit" class="btn btn-primary w-full sm:w-auto">Update
								Category</button>
						</div>
					</div>
				</form>
				<%
				if (isDeleteable) {
				%>
				<form
					action="<%=request.getContextPath()%>/admin/delete-category/<%=category.getCategoryId()%>"
					method="POST">
					<input type="hidden" name="_method" value="DELETE">
					<button type="submit" class="btn btn-error w-full sm:w-auto">Delete
						Category</button>
				</form>
				<%
				} else {
				%>
				<div class="btn btn-error w-full sm:w-auto disabled mt-18px">You
					cannot delete this category because it is currently being used by
					services. Please update those services to use a different category
					before attempting to delete this one.</div>
				<%
				}
				%>
			</div>
		</div>
		<%
		}
		%>
	</div>
</body>
</html>