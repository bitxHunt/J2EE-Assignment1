<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="models.user.User"%>
<%@ page import="models.address.Address"%>
<%@ page import="models.booking.Booking"%>
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
	ArrayList<Booking> bookings = (ArrayList<Booking>) request.getAttribute("bookings");
	Booking latestBooking = bookings != null && !bookings.isEmpty() ? bookings.get(0) : null;
	%>

	<!-- Navbar -->
	<%
	if (session.getAttribute("userId") != null) {
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
		<!-- Hero Section -->
		<div
			class="hero bg-base-200 rounded-box mb-8 relative overflow-hidden">
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
				<div class="stat-value text-primary"><%=bookings != null ? bookings.size() : 0%></div>
				<div class="stat-desc">↗︎ All time record</div>
			</div>

			<div class="stat">
				<div class="stat-figure text-secondary">
					<span class="material-symbols-outlined text-4xl">schedule</span>
				</div>
				<div class="stat-title">Latest Booking Status</div>
				<div class="stat-value text-secondary">
					<%=latestBooking != null ? latestBooking.getStatus() : "No Bookings"%>
				</div>
				<div class="stat-desc">Current status</div>
			</div>
		</div>

		<!-- Address and Booking Grid -->
		<div class="grid grid-cols-1 lg:grid-cols-2 gap-8">
			<!-- Address Section -->
			<div class="card bg-base-200 shadow-xl">
				<div class="card-body">
					<div class="flex justify-between items-center mb-6">
						<h2 class="card-title text-2xl">My Address</h2>
					</div>

					<%
					if (homeAddress != null && homeAddress.getAddress() != null) {
					%>
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
					<%
					} else {
					%>
					<div class="text-center py-8">
						<span
							class="material-symbols-outlined text-4xl text-base-content/30 mb-4">location_off</span>
						<p class="text-base-content/60">No address added yet</p>
					</div>
					<%
					}
					%>
				</div>
			</div>

			<!-- Latest Booking -->
			<div class="space-y-6">
				<div class="flex justify-between items-center">
					<h2 class="text-2xl font-bold">Latest Booking</h2>
					<%
					if (latestBooking != null) {
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
				if (latestBooking != null) {
				%>
				<!-- Latest Booking Card -->
				<div
					class="card bg-base-200 shadow-xl transition-all hover:shadow-2xl">
					<div class="card-body">
						<div class="flex flex-col gap-4">
							<!-- Header with ID and Status -->
							<div class="flex justify-between items-start">
								<div>
									<h2 class="card-title text-primary">
										Booking #<%=latestBooking.getId()%></h2>
									<div class="flex items-center gap-2 text-sm opacity-75 mt-1">
										<span class="material-symbols-outlined">schedule</span>
										<%=latestBooking.getDate()%>
										-
										<%=latestBooking.getTimeSlot().getStartTime()%>
									</div>
								</div>
								<div class="flex flex-col gap-2 items-end">
									<!-- Booking Status -->
									<div
										class="badge badge-lg 
                        <%=latestBooking.getStatus().equals("PENDING") ? "badge-warning"
		: latestBooking.getStatus().equals("COMPLETED") ? "badge-success"
				: latestBooking.getStatus().equals("CANCELLED") ? "badge-error" : "badge-info"%>">
										<%=latestBooking.getStatus()%>
									</div>
									<!-- Payment Status -->
									<div
										class="badge badge-outline 
                        <%=latestBooking.getPayment().getStatus().equals("PAID") ? "badge-success"
		: latestBooking.getPayment().getStatus().equals("REFUNDED") ? "badge-warning"
				: latestBooking.getPayment().getStatus().equals("CANCELLED") ? "badge-error" : "badge-info"%>">
										Payment
										<%=latestBooking.getPayment().getStatus()%>
									</div>
								</div>
							</div>

							<!-- Services Summary -->
							<div class="bg-base-100 rounded-box p-4">
								<div class="text-sm font-medium mb-2">Booked Services:</div>
								<div class="divide-y divide-base-content/10">
									<%
									if (latestBooking.getBundle() != null) {
									%>
									<!-- Bundle Header -->
									<div class="flex items-center justify-between py-2">
										<div class="flex items-center gap-2">
											<span class="material-symbols-outlined text-secondary">package</span>
											<span class="font-medium"><%=latestBooking.getBundle().getBundleName()%></span>
										</div>
										<div class="text-sm badge badge-secondary">
											<%=latestBooking.getBundle().getDiscountPercent()%>% OFF
										</div>
									</div>
									<%
									}
									%>

									<!-- Individual Services -->
									<%
									if (latestBooking.getServices() != null) {
										for (Service service : latestBooking.getServices()) {
									%>
									<div class="flex items-center justify-between py-2">
										<div class="flex items-center gap-2">
											<span class="material-symbols-outlined text-primary">cleaning_services</span>
											<span><%=service.getServiceName()%></span>
										</div>
										<div class="text-sm font-semibold">
											$<%=String.format("%.2f", service.getPrice())%>
										</div>
									</div>
									<%
									}
									}
									%>
								</div>

								<!-- Total Price -->
								<div
									class="flex justify-between items-center mt-4 pt-4 border-t border-base-content/10">
									<div class="flex items-center gap-2">
										<span class="font-medium">Total Amount</span>
										<%
										if (latestBooking.getBundle() != null) {
										%>
										<div class="badge badge-sm badge-outline badge-secondary">Bundle
											Discount Applied</div>
										<%
										}
										%>
									</div>
									<div class="flex items-center gap-2 text-2xl font-bold">
										<span class="material-symbols-outlined text-warning">payments</span>
										<span class="text-warning">$<%=String.format("%.2f", latestBooking.getPayment().getAmount())%></span>
									</div>
								</div>

								<!-- Location -->
								<div class="bg-base-100 rounded-box p-4">
									<div class="text-sm font-medium mb-2">Location:</div>
									<div class="flex items-center gap-2 text-base-content/80">
										<span class="material-symbols-outlined">location_on</span> <span><%=latestBooking.getAddress().getAddress()%>,
											Unit #<%=latestBooking.getAddress().getUnit()%>, S(<%=latestBooking.getAddress().getPostalCode()%>)</span>
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

		<%!// Helper method to calculate total amount
	private double getTotalAmount(Booking booking) {
		double total = 0.0;

		// Add up service prices
		if (booking.getServices() != null) {
			for (Service service : booking.getServices()) {
				total += service.getPrice();
			}
		}

		// Apply bundle discount if exists
		if (booking.getBundle() != null) {
			double discount = booking.getBundle().getDiscountPercent();
			total = total * (1 - (discount / 100.0));
		}

		return total;
	}%>
</body>
</html>