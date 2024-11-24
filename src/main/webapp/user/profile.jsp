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
	Address homeAddress = (Address) request.getAttribute("homeAddress");
	Address officeAddress = (Address) request.getAttribute("officeAddress");
	ArrayList<Transaction> transactions = (ArrayList<Transaction>) request.getAttribute("transactions");
	String totalAmount = (String) request.getAttribute("totalAmount");
	String totalSaving = (String) request.getAttribute("totalSaving");
	%>

	<!-- Navbar -->
	<%@ include file="./components/header.jsp"%>

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
		<div class="stats shadow bg-base-200 w-full mb-8">
			<div class="stat">
				<div class="stat-figure text-primary">
					<span class="material-symbols-outlined text-4xl">calendar_month</span>
				</div>
				<div class="stat-title">Total Bookings</div>
				<div class="stat-value text-primary"><%=transactions.size()%></div>
				<div class="stat-desc">↗︎ All time record</div>
			</div>

			<div class="stat">
				<div class="stat-figure text-secondary">
					<span class="material-symbols-outlined text-4xl">payments</span>
				</div>
				<div class="stat-title">Amount Spent</div>
				<div class="stat-value text-secondary">
					$<%=totalAmount%>
				</div>
				<div class="stat-desc">Lifetime spending</div>
			</div>

			<div class="stat">
				<div class="stat-figure text-accent">
					<span class="material-symbols-outlined text-4xl">redeem</span>
				</div>
				<div class="stat-title">Discount Earned</div>
				<div class="stat-value text-accent">
					$<%=totalSaving%></div>
				<div class="stat-desc">21 rewards claimed</div>
			</div>
		</div>

		<!-- Address and Booking Grid - Enhanced layout -->
		<div class="grid grid-cols-1 lg:grid-cols-2 gap-8">
			<!-- Address Section - Enhanced cards -->
			<div class="card bg-base-200 shadow-xl">
				<div class="card-body">
					<div class="flex justify-between items-center mb-6">
						<h2 class="card-title text-2xl">My Addresses</h2>
					</div>

					<!-- Home Address Card -->
					<div
						class="bg-base-100 rounded-xl p-6 mb-4 hover:bg-base-100/80 transition-colors">
						<div class="flex items-center gap-4 mb-4">
							<div class="p-3 bg-primary/10 rounded-full">
								<span class="material-symbols-outlined text-primary text-2xl">home</span>
							</div>
							<div>
								<h3 class="font-bold text-lg">Home Address</h3>
								<div class="badge badge-primary badge-sm">Primary</div>
							</div>
						</div>
						<div class="space-y-2 ml-2 border-l-2 border-primary/20 pl-4">
							<p class="text-base-content/90"><%=homeAddress.getAddress()%></p>
							<p class="text-base-content/75">
								Unit #<%=homeAddress.getUnit()%></p>
							<p class="text-base-content/75">
								Singapore
								<%=homeAddress.getPostalCode()%></p>
						</div>
					</div>

					<!-- Office Address Card -->
					<div
						class="bg-base-100 rounded-xl p-6 hover:bg-base-100/80 transition-colors">
						<div class="flex items-center gap-4 mb-4">
							<div class="p-3 bg-secondary/10 rounded-full">
								<span class="material-symbols-outlined text-secondary text-2xl">apartment</span>
							</div>
							<div>
								<h3 class="font-bold text-lg">Office Address</h3>
								<div class="badge badge-secondary badge-sm">Secondary</div>
							</div>
						</div>
						<div class="space-y-2 ml-2 border-l-2 border-secondary/20 pl-4">
							<p class="text-base-content/90"><%=officeAddress.getAddress()%></p>
							<p class="text-base-content/75">
								Unit #<%=officeAddress.getUnit()%></p>
							<p class="text-base-content/75">
								Singapore
								<%=officeAddress.getPostalCode()%></p>
						</div>
					</div>
				</div>
			</div>

			<!-- Booking History -->
			<div class="space-y-6">
				<div class="flex justify-between items-center">
					<h2 class="text-2xl font-bold">Recent Bookings</h2>
					<%
					if (transactions != null && !transactions.isEmpty()) {
					%>
					<a href="${pageContext.request.contextPath}/book/view"
						class="btn btn-ghost btn-sm"> View All <span
						class="material-symbols-outlined">chevron_right</span>
					</a>
					<%
					}
					%>
				</div>

				<%
				if (transactions != null && !transactions.isEmpty()) {
				%>
				<div
					class="card bg-base-200 shadow-xl transition-all hover:shadow-2xl">
					<div class="flex flex-col md:flex-row">
						<div class="card-body">
							<div class="flex flex-col gap-4">
								<!-- Service Info -->
								<div>
									<h2 class="card-title text-primary">
										<%=transactions.get(0).getBundleName() == null ? "Services" : transactions.get(0).getBundleName()%>
									</h2>
									<div class="flex items-center gap-2 text-sm opacity-75 mt-1">
										<span class="material-symbols-outlined">schedule</span>
										<%=transactions.get(0).getStartDate()%>
									</div>
								</div>

								<!-- Services List -->
								<div class="bg-base-100 rounded-box p-4">
									<div class="text-sm font-medium mb-2">Ordered Services:</div>
									<div class="divide-y divide-base-content/10">
										<%
										for (Service service : transactions.get(0).getServices()) {
										%>
										<div class="flex items-center gap-3 py-2">
											<span class="material-symbols-outlined text-primary">cleaning_services</span>
											<span><%=service.getServiceName()%></span>
										</div>
										<%
										}
										%>
									</div>
								</div>

								<!-- Price and Location -->
								<div class="flex flex-wrap gap-4">
									<div
										class="flex items-center gap-2 bg-warning/10 text-warning rounded-lg px-3 py-2">
										<span class="material-symbols-outlined">paid</span> <span
											class="font-medium">$<%=String.format("%.2f", transactions.get(0).getSubTotal())%></span>
									</div>
									<div
										class="flex items-center gap-2 bg-error/10 text-error rounded-lg px-3 py-2">
										<span class="material-symbols-outlined">location_on</span> <span>Singapore
											<%=transactions.get(0).getAddress().getPostalCode()%></span>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>

				<%
				} else {
				%>
				<!-- Empty State Card -->
				<div class="card bg-base-200 shadow-xl">
					<div class="card-body items-center text-center py-12">
						<span
							class="material-symbols-outlined text-6xl text-base-content/30 mb-4">history</span>
						<h3 class="text-xl font-bold mb-2">No Bookings Yet</h3>
						<p class="text-base-content/60 mb-6">Start your cleaning
							journey today with our professional services!</p>
						<a href="${pageContext.request.contextPath}/book"
							class="btn btn-primary gap-2"> <span
							class="material-symbols-outlined">add</span> Book Your First
							Service
						</a>
					</div>
				</div>
				<%
				}
				%>
			</div>

		</div>
	</div>
	<!-- Footer -->
	<%@ include file="components/footer.jsp"%>
</body>
</html>
