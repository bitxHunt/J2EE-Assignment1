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
<title>Category Form</title>
</head>
<body>
	<!-- Category Form -->
	<div id="categoryForm"
		class="card bg-base-200 shadow-xl <%=request.getParameter("tab") == null || !request.getParameter("tab").equals("category") ? "hidden" : ""%>">
		<div class="card-body">
			<h2 class="card-title text-xl md:text-2xl mb-4">Create Category</h2>
			<form action="create-new-category" method="POST">
				<input type="hidden" name="formType" value="category">

				<div class="grid gap-4">
					<div class="form-control">
						<label class="label">Category Name</label> <input type="text"
							name="categoryName" class="input input-bordered" required>
					</div>

					<div class="flex flex-col sm:flex-row gap-2 justify-end mt-4">
						<button type="button"
							onclick="window.location='${pageContext.request.contextPath}/admin'"
							class="btn btn-neutral w-full sm:w-auto">Cancel</button>
						<button type="submit" class="btn btn-primary w-full sm:w-auto">Create
							Category</button>
					</div>
				</div>
			</form>
		</div>
	</div>
	</div>
</body>
</html>