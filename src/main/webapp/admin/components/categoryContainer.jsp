<%-- 
    Name: Soe Zaw Aung, Scott
    Class: DIT/FT/2B/01
    Admin No: p2340474
--%>
<%@page import="models.category.Category"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Category Container</title>
</head>
<body>
	<!-- Categories Section -->
	<div id="categories"
		class="hidden grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
		<%
		ArrayList<Category> categories = (ArrayList<Category>) request.getAttribute("categories");
		if (categories != null && !categories.isEmpty()) {
			for (Category category : categories) {
		%>
		<div class="card bg-base-200 shadow-xl">
			<div class="card-body">
				<h2 class="card-title"><%=category.getCategoryName()%></h2>
				<div class="card-actions justify-end mt-4">
					<button class="btn btn-primary"
						onclick="window.location.href='${pageContext.request.contextPath}/admin/edit-category/<%=category.getCategoryId()%>'">
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
				<h3 class="text-lg font-semibold mb-2">No Categories Available</h3>
				<p class="text-sm opacity-70 mb-4">There are currently no
					categories. Click 'Create New' to add a category.</p>
				<button
					onclick="window.location='${pageContext.request.contextPath}/admin/createForm?tab=category'"
					class="btn btn-primary">Create Category</button>
			</div>
		</div>
		<%
		}
		%>
	</div>
</body>
</html>