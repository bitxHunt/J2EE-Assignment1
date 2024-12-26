<%-- 
    Name: Thiha Swan Htet
    Class: DIT/FT/2B/01
    Admin No: p2336671
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="models.transaction.Transaction"%>
<%@ page import="models.service.Service"%>
<%@ page import="models.address.Address"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.time.format.DateTimeFormatter"%>
<%@ page import="java.time.LocalDateTime"%>

<!DOCTYPE html>
<html lang="en" data-theme="dark">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>My Bookings - CleanX</title>
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

	<div class="container mx-auto p-6 max-w-5xl">
		<!-- Header Section -->
		<div class="flex justify-between items-center mb-6">
			<h1 class="text-3xl font-bold">My Bookings</h1>
			<a href="${pageContext.request.contextPath}/book/slots"
				class="btn btn-primary gap-2"> <span
				class="material-symbols-outlined">add</span> Book New Service
			</a>
		</div>

		<!-- Booking Cards -->
		<div class="space-y-6">
			<%
			ArrayList<Transaction> transactions = (ArrayList<Transaction>) request.getAttribute("transactions");
			DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMM dd, yyyy");
			DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a");

			if (transactions != null && !transactions.isEmpty()) {
				for (Transaction transaction : transactions) {
					// Calculate bundle subtotal if bundle services exist
					double bundleSubtotal = 0.0;
					ArrayList<Service> bundleServices = transaction.getBundleServices();
					if (bundleServices != null) {
				for (Service service : bundleServices) {
					bundleSubtotal += service.getPrice();
				}
					}

					// Calculate services subtotal
					double servicesSubtotal = 0.0;
					ArrayList<Service> services = transaction.getServices();
					if (services != null) {
				for (Service service : services) {
					servicesSubtotal += service.getPrice();
				}
					}

					// Calculate discounted bundle price
					double discountedBundlePrice = bundleSubtotal * (1 - (transaction.getDiscount() / 100.0));
			%>
			<div
				class="card bg-base-200 shadow-xl transition-all hover:shadow-2xl">
				<div class="flex flex-col md:flex-row">
					<!-- Image Section -->
					<!-- Image Section -->
					<figure class="w-full md:w-48 relative">
						<img
							src="<%=transaction.getBundle_img() != null ? transaction.getBundle_img()
		: (services != null && !services.isEmpty() ? services.get(0).getImageUrl() : "")%>"
							alt="Service Image" class="h-48 md:h-full w-full object-cover" />
						<!-- Add this status badge -->
						<div class="absolute top-2 right-2">
							<%
							String status = transaction.getStatus();
							String badgeClass = "";
							String badgeText = status;

							switch (status) {
							case "PENDING":
								badgeClass = "badge badge-warning";
								break;
							case "PAID":
								badgeClass = "badge badge-info";
								break;
							case "IN_PROGRESS":
								badgeClass = "badge badge-primary";
								badgeText = "In Progress";
								break;
							case "COMPLETED":
								badgeClass = "badge badge-success";
								break;
							case "CANCELLED":
								badgeClass = "badge badge-error";
								break;
							default:
								badgeClass = "badge";
							}
							%>
							<span class="<%=badgeClass%>"><%=badgeText%></span>
						</div>
					</figure>

					<!-- Content Section -->
					<div class="card-body">
						<div class="flex flex-col gap-4">
							<!-- Service Info -->
							<div>
								<h2 class="card-title text-primary">
									<%=transaction.getBundleName() != null ? transaction.getBundleName() : "Custom Services"%>
								</h2>
								<!-- Add date and time section -->
								<div class="flex flex-wrap gap-4 mt-2">
									<div class="flex items-center gap-2 text-sm opacity-75">
										<span class="material-symbols-outlined">calendar_today</span>
										<span><%=transaction.getStartDate().format(dateFormatter)%></span>
									</div>
									<div class="flex items-center gap-2 text-sm opacity-75">
										<span class="material-symbols-outlined">schedule</span> <span><%=transaction.getTimeSlot().format(timeFormatter)%></span>
									</div>
									<div class="flex items-center gap-2">
										<span
											class="badge <%=transaction.getStatus().equals("CANCELLED") ? "badge-error"
		: transaction.getStatus().equals("COMPLETED") ? "badge-success"
				: transaction.getStatus().equals("IN_PROGRESS") ? "badge-primary"
						: transaction.getStatus().equals("PENDING") ? "badge-warning" : "badge-info"%>">
											<%=transaction.getStatus()%>
										</span>
									</div>
								</div>
							</div>

							<!-- Services List -->
							<div class="bg-base-100 rounded-box p-4">
								<div class="text-sm font-medium mb-2">Ordered Services:</div>
								<div class="divide-y divide-base-content/10">
									<%
									// Display bundle services if they exist
									if (bundleServices != null && !bundleServices.isEmpty()) {
									%>
									<div class="py-2">
										<div class="font-medium text-sm text-primary mb-2">Bundle
											Services:</div>
										<%
										for (Service service : bundleServices) {
										%>
										<div class="flex items-center justify-between py-1">
											<div class="flex items-center gap-3">
												<span class="material-symbols-outlined text-primary">cleaning_services</span>
												<span><%=service.getServiceName()%></span>
											</div>
											<span class="text-sm opacity-75">$<%=String.format("%.2f", service.getPrice())%></span>
										</div>
										<%
										}
										if (transaction.getDiscount() > 0) {
										%>
										<div
											class="flex justify-between items-center mt-2 text-success">
											<span class="text-sm">Bundle Discount (<%=transaction.getDiscount()%>%
												OFF)
											</span> <span class="text-sm">-$<%=String.format("%.2f", bundleSubtotal - discountedBundlePrice)%></span>
										</div>
										<div
											class="flex justify-between items-center mt-1 font-medium">
											<span class="text-sm">Bundle Total</span> <span
												class="text-sm">$<%=String.format("%.2f", discountedBundlePrice)%></span>
										</div>
										<%
										}
										%>
									</div>
									<%
									}

									// Display additional services if they exist
									if (services != null && !services.isEmpty()) {
									%>
									<div class="py-2">
										<div class="font-medium text-sm text-primary mb-2">
											<%=bundleServices != null && !bundleServices.isEmpty() ? "Additional Services:" : "Services:"%>
										</div>
										<%
										for (Service service : services) {
										%>
										<div class="flex items-center justify-between py-1">
											<div class="flex items-center gap-3">
												<span class="material-symbols-outlined text-primary">cleaning_services</span>
												<span><%=service.getServiceName()%></span>
											</div>
											<span class="text-sm opacity-75">$<%=String.format("%.2f", service.getPrice())%></span>
										</div>
										<%
										}
										if (services.size() > 1) {
										%>
										<div
											class="flex justify-between items-center mt-1 font-medium">
											<span class="text-sm">Services Total</span> <span
												class="text-sm">$<%=String.format("%.2f", servicesSubtotal)%></span>
										</div>
										<%
										}
										%>
									</div>
									<%
									}
									%>
								</div>
							</div>

							<!-- Price Summary -->
							<div class="flex flex-wrap gap-4">
								<%
								// Calculate and display total
								double total = discountedBundlePrice + servicesSubtotal;
								%>
								<div
									class="flex items-center gap-2 bg-warning/10 text-warning rounded-lg px-3 py-2">
									<span class="material-symbols-outlined">paid</span> <span
										class="font-medium">Total: $<%=String.format("%.2f", total)%></span>
								</div>
								<div
									class="flex items-center gap-2 bg-error/10 text-error rounded-lg px-3 py-2">
									<span class="material-symbols-outlined">location_on</span> <span><%=transaction.getAddress().getPostalCode()%></span>
								</div>
							</div>

							<!-- Action Buttons -->
							<div class="card-actions justify-end gap-2">
								<%
								if (transaction.getStatus().equals("IN_PROGRESS")) {
								%>
								<form action="${pageContext.request.contextPath}/book/cancel"
									method="POST" class="inline">
									<input type="hidden" name="id" value="<%=transaction.getId()%>">
									<input type="hidden" name="action" value="cancel">
									<button type="submit" class="btn btn-error btn-sm gap-2">
										<span class="material-symbols-outlined">cancel</span> Cancel
										Service
									</button>
								</form>
								<%
								} else if (transaction.getStatus().equals("PENDING")) {
								%>
								<form action="${pageContext.request.contextPath}/book/payment"
									method="GET" class="inline">
									<input type="hidden" name="id" value="<%=transaction.getId()%>">
									<button type="submit" class="btn btn-primary btn-sm gap-2">
										<span class="material-symbols-outlined">payments</span> Make
										Payment
									</button>
								</form>
								<%
								}
								%>
							</div>
						</div>
					</div>
				</div>
			</div>
			<%
			}
			} else {
			%>
			<!-- Empty State -->
			<div class="card bg-base-200 shadow-xl text-center p-12">
				<div class="flex flex-col items-center gap-4">
					<div class="p-4 bg-base-100 rounded-full">
						<span class="material-symbols-outlined text-6xl opacity-50">calendar_month</span>
					</div>
					<div>
						<h3 class="font-bold text-xl mb-2">No Bookings Yet</h3>
						<p class="text-base-content/70 mb-6">Start your cleaning
							journey with us today!</p>
						<a href="${pageContext.request.contextPath}/book"
							class="btn btn-primary gap-2"> <span
							class="material-symbols-outlined">add</span> Book Your First
							Service
						</a>
					</div>
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