<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="models.user.User"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Header</title>
</head>
<body>
	<%
	String profileImage = (String) session.getAttribute("profileImg");
	%>
	<div
		class="navbar bg-base-100 sticky top-0 z-50 border-b border-base-200">
		<div class="navbar-start">
			<!-- Mobile menu -->
			<div class="dropdown">
				<label tabindex="0" class="btn btn-ghost lg:hidden"> <span
					class="material-symbols-outlined">menu</span>
				</label>
				<ul tabindex="0"
					class="menu menu-sm dropdown-content mt-3 z-[1] p-2 shadow bg-base-200 rounded-box w-52">
					<li><a href="${pageContext.request.contextPath}/"
						class="text-base">Home</a></li>
					<li><a href="${pageContext.request.contextPath}/categories"
						class="text-base">Categories</a></li>
					<li><a href="${pageContext.request.contextPath}/bundles"
						class="text-base">Bundles</a></li>
					<li><a href="${pageContext.request.contextPath}/book"
						class="text-base">Book Service</a></li>
				</ul>
			</div>
			<!-- Logo -->
			<a href="${pageContext.request.contextPath}/"
				class="btn btn-ghost normal-case text-xl gap-2"> <span
				class="material-symbols-outlined">cleaning_services</span> CleanX
			</a>
		</div>

		<!-- Desktop menu -->
		<div class="navbar-center hidden lg:flex">
			<ul class="menu menu-horizontal px-1 gap-2">
				<li><a href="${pageContext.request.contextPath}/categories"
					class="text-base hover:text-primary transition-colors"> <span
						class="material-symbols-outlined">category</span> Categories
				</a></li>
				<li><a href="${pageContext.request.contextPath}/bundles"
					class="text-base hover:text-primary transition-colors"> <span
						class="material-symbols-outlined">inventory_2</span> Bundles
				</a></li>
				<li><a href="${pageContext.request.contextPath}/book"
					class="text-base hover:text-primary transition-colors"> <span
						class="material-symbols-outlined">calendar_month</span> Book
						Service
				</a></li>
			</ul>
		</div>

		<div class="navbar-end gap-2">
			<!-- Profile Dropdown -->
			<div class="dropdown dropdown-end">
				<label tabindex="0" class="btn btn-ghost btn-circle avatar">
					<div
						class="w-10 rounded-full ring ring-primary ring-offset-base-100 ring-offset-2">
						<img alt="Profile" src="<%=profileImage %>"
							onerror="this.src='https://placehold.co/100x100'" />
					</div>
				</label>
				<ul tabindex="0"
					class="menu menu-sm dropdown-content mt-3 z-[1] p-2 shadow bg-base-200 rounded-box w-52">
					<li><a href="${pageContext.request.contextPath}/profile"
						class="flex items-center gap-2"> <span
							class="material-symbols-outlined">person</span> Profile
					</a></li>
					<li><a href="${pageContext.request.contextPath}/book/view"
						class="flex items-center gap-2"> <span
							class="material-symbols-outlined">history</span> Booking History
					</a></li>
					<li><a href="${pageContext.request.contextPath}/logout"
						class="flex items-center gap-2 text-error"> <span
							class="material-symbols-outlined">logout</span> Logout
					</a></li>
				</ul>
			</div>
		</div>
	</div>

</body>
</html>