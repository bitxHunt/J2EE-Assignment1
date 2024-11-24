<%-- 
    Name: Thiha Swan Htet
    Class: DIT/FT/2B/01
    Admin No: p2336671
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Header</title>
</head>
<body>

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
					<li><a href="${pageContext.request.contextPath}/about"
						class="text-base">About</a></li>
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
				<li><a href="${pageContext.request.contextPath}/"
					class="text-base">Home</a></li>
				<li><a href="${pageContext.request.contextPath}/categories"
					class="text-base">Services</a></li>
				<li><a href="${pageContext.request.contextPath}/bundles"
					class="text-base">Bundles</a></li>
				<li><a href="${pageContext.request.contextPath}/about"
					class="text-base">About</a></li>
			</ul>
		</div>

		<div class="navbar-end gap-2">
			<a href="${pageContext.request.contextPath}/login"
				class="btn btn-ghost">Login</a> <a
				href="${pageContext.request.contextPath}/register"
				class="btn btn-primary">Sign Up</a>
		</div>
	</div>
</body>
</html>