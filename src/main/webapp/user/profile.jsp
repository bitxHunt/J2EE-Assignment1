<%-- 
    Name: Thiha Swan Htet
    Class: DIT/FT/2B/01
    Admin No: p2336671
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="models.user.User"%>
<%@ page import="models.address.Address"%>
<%@ page import="models.transaction.Transaction"%>
<%@ page import="models.service.Service"%>
<%@ page import="java.util.ArrayList"%>

<!DOCTYPE html>
<html lang="en" data-theme="dark">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>Profile - CleanX</title>
<link
	href="https://cdn.jsdelivr.net/npm/daisyui@4.12.14/dist/full.min.css"
	rel="stylesheet" type="text/css" />
<script src="https://cdn.tailwindcss.com"></script>
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@24,400,0,0" />
</head>
<body class="bg-base-300 min-h-screen">
	<%
	User user = (User) request.getAttribute("user");	
	/*
	Address officeAddress = (Address) request.getAttribute("officeAddress");
	ArrayList<Transaction> transactions = (ArrayList<Transaction>) request.getAttribute("transactions");
	String totalAmount = (String) request.getAttribute("totalAmount");
	String totalSaving = (String) request.getAttribute("totalSaving");
	*/

	%>

	<!-- Navbar -->
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

	<div class="container mx-auto p-6 max-w-7xl">
		<div
			class="hero bg-base-200 rounded-box mb-8 relative overflow-hidden">
			<!-- Added decorative background pattern -->
			<div class="absolute inset-0 opacity-5">
				<div class="absolute inset-0 bg-primary/10 pattern-grid"></div>
			</div>

			<a href="${pageContext.request.contextPath}/profile/edit"
				class="btn btn-primary btn-sm absolute top-4 right-4 gap-2"> <span
				class="material-symbols-outlined">edit</span> Edit Profile
			</a>

			<div class="hero-content flex-col lg:flex-row gap-8 py-8">
				<div class="avatar">
					<div
						class="w-40 lg:w-48 rounded-full ring ring-primary ring-offset-base-200 ring-offset-4">
						<img src="<%=user.getImageURL()%>" alt="Profile"
							class="mask mask-circle" />
					</div>
				</div>
				<div class="text-center lg:text-left">
					<h1 class="text-4xl font-bold mb-4"><%=user.getFirstName()%>
						<%=user.getLastName()%></h1>
					<div class="flex flex-col gap-3">
						<div class="flex items-center gap-3 p-2 bg-base-100/50 rounded-lg">
							<span class="material-symbols-outlined text-primary">phone_in_talk</span>
							<span><%=user.getPhoneNo()%></span>
						</div>
						<div class="flex items-center gap-3 p-2 bg-base-100/50 rounded-lg">
							<span class="material-symbols-outlined text-primary">mail</span>
							<span><%=user.getEmail()%></span>
						</div>
					</div>
				</div>
			</div>
		</div>

		<!-- Stats Section -->
	</div>
	<!-- Footer -->
	<%@ include file="components/footer.jsp"%>
</body>
</html>
