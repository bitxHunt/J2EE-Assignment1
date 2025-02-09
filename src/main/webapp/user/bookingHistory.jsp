<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="models.booking.Booking"%>
<%@ page import="models.service.Service"%>
<%@ page import="models.payment.Payment"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.time.format.DateTimeFormatter"%>
<%@ page import="java.time.LocalDateTime"%>

<!DOCTYPE html>
<html lang="en" data-theme="dark">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Booking History - CleanX</title>
<link
	href="https://cdn.jsdelivr.net/npm/daisyui@4.12.14/dist/full.min.css"
	rel="stylesheet" type="text/css" />
<script src="https://cdn.tailwindcss.com"></script>
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@24,400,0,0" />
</head>
<body class="bg-base-300 min-h-screen">
	<!-- Header -->
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

	<div class="container mx-auto p-6 max-w-5xl">
		<!-- Header Section -->
		<div
			class="hero bg-base-200 rounded-box mb-8 relative overflow-hidden">
			<div class="absolute inset-0 opacity-5">
				<div class="absolute inset-0 bg-primary/10 pattern-grid"></div>
			</div>
			<div class="hero-content text-center py-8">
				<div>
					<h1 class="text-4xl font-bold mb-4">My Bookings</h1>
					<p class="text-base-content/70">View and manage all your
						cleaning service bookings</p>
					<a href="${pageContext.request.contextPath}/book"
						class="btn btn-primary mt-4 gap-2"> <span
						class="material-symbols-outlined">add</span> Book New Service
					</a>
				</div>
			</div>
		</div>

		<!-- Messages -->
		<%
		String successMsg = (String) session.getAttribute("successMsg");
		if (successMsg != null && !successMsg.isEmpty()) {
		%>
		<div class="alert alert-success mb-4">
			<span class="material-symbols-outlined">check_circle</span> <span><%=successMsg%></span>
		</div>
		<%
		session.removeAttribute("successMsg");
		%>
		<%
		}

		String errorMsg = (String) session.getAttribute("errorMsg");
		if (errorMsg != null && !errorMsg.isEmpty()) {
		%>
		<div class="alert alert-error mb-4">
			<span class="material-symbols-outlined">error</span> <span><%=errorMsg%></span>
		</div>
		<%
		session.removeAttribute("errorMsg");
		%>
		<%
		}
		%>

		<!-- Booking Cards -->
		<div class="space-y-6">
			<%
			ArrayList<Booking> bookings = (ArrayList<Booking>) request.getAttribute("bookings");
			DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMM dd, yyyy");
			DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a");

			if (bookings != null && !bookings.isEmpty()) {
				for (Booking booking : bookings) {
			%>
			<div
				class="card bg-base-200 shadow-xl transition-all hover:shadow-2xl">
				<div class="card-body">
					<!-- Booking Header -->
					<div class="flex justify-between items-start">
						<div>
							<h2 class="card-title text-primary">
								Booking #<%=booking.getId()%></h2>
							<div class="flex flex-wrap gap-4 mt-2">
								<div class="flex items-center gap-2 text-sm opacity-75">
									<span class="material-symbols-outlined">calendar_today</span> <span><%=booking.getDate().format(dateFormatter)%></span>
								</div>
								<div class="flex items-center gap-2 text-sm opacity-75">
									<span class="material-symbols-outlined">schedule</span> <span><%=booking.getTimeSlot().getStartTime().format(timeFormatter)%></span>
								</div>
							</div>
						</div>
						<div class="flex flex-col gap-2 items-end">
							<div
								class="badge badge-lg <%=booking.getStatus().equals("PENDING") ? "badge-warning"
		: booking.getStatus().equals("COMPLETED") ? "badge-success"
				: booking.getStatus().equals("CANCELLED") ? "badge-error" : "badge-info"%>">
								<%=booking.getStatus()%>
							</div>
							<div
								class="badge badge-outline <%=booking.getPayment().getStatus().equals("PAID") ? "badge-success"
		: booking.getPayment().getStatus().equals("REFUNDED") ? "badge-warning"
				: booking.getPayment().getStatus().equals("CANCELLED") ? "badge-error" : "badge-info"%>">
								Payment
								<%=booking.getPayment().getStatus()%>
							</div>
						</div>
					</div>

					<!-- Services Details -->
					<div class="bg-base-100 rounded-box p-4 mt-4">
						<div class="text-sm font-medium mb-2">Booked Services:</div>
						<div class="divide-y divide-base-content/10">
							<%
							if (booking.getBundle() != null) {
							%>
							<!-- Bundle Section -->
							<div class="py-2">
								<div class="flex items-center justify-between">
									<div class="flex items-center gap-2">
										<span class="material-symbols-outlined text-secondary">package</span>
										<span class="font-medium"><%=booking.getBundle().getBundleName()%></span>
									</div>
									<div class="text-sm badge badge-secondary">
										<%=booking.getBundle().getDiscountPercent()%>% OFF
									</div>
								</div>
							</div>
							<%
							}
							%>

							<!-- Individual Services -->
							<%
							if (booking.getServices() != null) {
								for (Service service : booking.getServices()) {
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
					</div>

					<!-- Location and Total -->
					<div class="flex flex-wrap justify-between items-center mt-4">
						<div class="flex items-center gap-2 text-base-content/80">
							<span class="material-symbols-outlined">location_on</span> <span>
								<%=booking.getAddress().getAddress()%>, Unit #<%=booking.getAddress().getUnit()%>,
								S(<%=booking.getAddress().getPostalCode()%>)
							</span>
						</div>
						<div class="flex items-center gap-4">
							<div
								class="flex items-center gap-2 text-xl font-bold text-warning">
								<span class="material-symbols-outlined">payments</span> $<%=String.format("%.2f", booking.getPayment().getAmount())%>
							</div>
						</div>
					</div>

					<!-- Action Buttons -->
					<div class="card-actions justify-end mt-4">
						<%
						if (booking.getStatus().equals("CONFIRMED")) {
						LocalDateTime now = LocalDateTime.now();
						LocalDateTime serviceEndTime = LocalDateTime.of(booking.getDate(), booking.getTimeSlot().getEndTime());

						if (now.isAfter(serviceEndTime)) {
						%>
						<form action="${pageContext.request.contextPath}/book/complete"
							method="POST">
							<input type="hidden" name="bookingId"
								value="<%=booking.getId()%>">
							<button class="btn btn-success btn-sm gap-2">
								<span class="material-symbols-outlined">task_alt</span> Mark as
								Complete
							</button>
						</form>
						<%
						} else {
						%>
						<div class="tooltip" data-tip="Service not yet completed">
							<button class="btn btn-success btn-sm gap-2" disabled>
								<span class="material-symbols-outlined">schedule</span>
								Available after
								<%=serviceEndTime.format(timeFormatter)%>
							</button>
						</div>
						<%
						}
						} else if (booking.getStatus().equals("IN_PROGRESS")) {
						%>
						<form action="${pageContext.request.contextPath}/book/cancel"
							method="POST">
							<input type="hidden" name="id" value="<%=booking.getId()%>">
							<button class="btn btn-error btn-sm gap-2">
								<span class="material-symbols-outlined">cancel</span> Cancel
								Booking
							</button>
						</form>
						<%
						}
						%>
					</div>
				</div>
			</div>
			<%
			}
			} else {
			%>
			<!-- Empty State -->
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

	<!-- Footer -->
	<%@ include file="components/footer.jsp"%>
</body>
</html>