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
	<div class="navbar bg-base-100 shadow-xl">
		<div class="flex-1">
			<a class="btn btn-ghost text-xl">CleanX</a>
		</div>
		<div class="flex-none">
			<div class="dropdown dropdown-end">
				<div tabindex="0" role="button"
					class="btn btn-ghost btn-circle avatar">
					<div
						class="w-10 rounded-full ring ring-primary ring-offset-base-100 ring-offset-2">
						<img alt="Profile"
							src="${pageContext.request.contextPath}/assets/avatar.jpg" />
					</div>
				</div>
				<ul
					class="mt-3 z-[1] p-2 shadow menu menu-sm dropdown-content bg-base-100 rounded-box w-52">
					<li><a href="${pageContext.request.contextPath}/profile">Profile</a></li>
					<li><a href="${pageContext.request.contextPath}/logout">Logout</a></li>
				</ul>
			</div>
		</div>
	</div>

	<div class="container mx-auto p-6 max-w-7xl">
		<div
			class="hero bg-base-200 rounded-box mb-8 relative overflow-hidden">
			<!-- Added decorative background pattern -->
			<div class="absolute inset-0 opacity-5">
				<div class="absolute inset-0 bg-primary/10 pattern-grid"></div>
			</div>

			<a href="${pageContext.request.contextPath}/editProfile"
				class="btn btn-primary btn-sm absolute top-4 right-4 gap-2"> <span
				class="material-symbols-outlined">edit</span> Edit Profile
			</a>

			<div class="hero-content flex-col lg:flex-row gap-8 py-8">
				<div class="avatar">
					<div
						class="w-40 lg:w-48 rounded-full ring ring-primary ring-offset-base-200 ring-offset-4">
						<img src="${pageContext.request.contextPath}/assets/avatar.jpg"
							alt="Profile" class="mask mask-circle" />
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
				<div class="stat-value text-primary"><%=transactions.size() %></div>
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
						<button class="btn btn-ghost btn-sm btn-circle">
							<span class="material-symbols-outlined">add</span>
						</button>
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
					<a href="${pageContext.request.contextPath}/transactions"
						class="btn btn-ghost btn-sm"> View All <span
						class="material-symbols-outlined">chevron_right</span>
					</a>
				</div>

				<%
				if (transactions != null) {
				%>
				<div
					class="card bg-base-200 shadow-xl transition-all hover:shadow-2xl">
					<div class="flex flex-col md:flex-row">
						<!-- Image with status overlay -->
						<figure class="w-full md:w-48 relative">
							<img src="<%=transactions.get(0).getBundle_img() == null ? transactions.get(0).getServices().get(0).getImageUrl() : transactions.get(0).getBundleName()%>"
								alt="Service Image" class="h-48 md:h-full w-full object-cover" />
							<div class="absolute top-2 right-2">
								<div class="badge badge-success gap-2">
									<span class="material-symbols-outlined">check_circle</span>
									<%=transactions.get(0).getStatus()%>
								</div>
							</div>
						</figure>

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

								<!-- Action Button -->
								<div class="card-actions justify-end">
									<a
										href="${pageContext.request.contextPath}/transaction/details?id=<%=transactions.get(0).getId()%>"
										class="btn btn-primary gap-2"> <span
										class="material-symbols-outlined">visibility</span> View
										Details
									</a>
								</div>
							</div>
						</div>
					</div>
				</div>
				<%
				} else {
				%>
				<!-- Empty State Card -->
				<div class="card bg-base-200 shadow-xl text-center p-8">
					<div class="flex flex-col items-center gap-4">
						<div class="p-4 bg-base-100 rounded-full">
							<span class="material-symbols-outlined text-4xl opacity-50">calendar_month</span>
						</div>
						<div>
							<h3 class="font-bold text-lg">No Recent Bookings</h3>
							<p class="text-base-content/70 mb-4">Start your cleaning
								journey today!</p>
							<a href="${pageContext.request.contextPath}/book"
								class="btn btn-primary gap-2"> <span
								class="material-symbols-outlined">add</span> Book a Service
							</a>
						</div>
					</div>
				</div>
				<%
				}
				%>
			</div>
		</div>
	</div>
	</div>
	</div>
</body>
</html>
