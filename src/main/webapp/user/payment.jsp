<%-- 
    Name: Thiha Swan Htet
    Class: DIT/FT/2B/01
    Admin No: p2336671
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="models.transaction.Transaction"%>
<%@ page import="models.service.Service"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.time.format.DateTimeFormatter"%>

<!DOCTYPE html>
<html lang="en" data-theme="dark">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Payment - CleanX</title>
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
	<div class="container mx-auto p-6 max-w-3xl">
		<div class="card bg-base-100 shadow-xl">
			<div class="card-body">
				<%
				Transaction transaction = (Transaction) request.getAttribute("transaction");
				DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMM dd, yyyy");
				DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a");

				if (transaction != null) {
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
				<!-- Transaction Details -->
				<div class="flex flex-col gap-6">
					<!-- Header -->
					<div class="flex justify-between items-center">
						<h2 class="card-title text-2xl">Transaction Details</h2>
					</div>

					<!-- Service Image and Basic Info -->
					<div class="relative rounded-lg overflow-hidden h-48">
						<img
							src="<%=transaction.getBundle_img() != null ? transaction.getBundle_img()
		: (services != null && !services.isEmpty() ? services.get(0).getImageUrl() : "")%>"
							alt="Service" class="w-full h-full object-cover" />
					</div>

					<!-- Service Details -->
					<div class="bg-base-200 rounded-lg p-6 space-y-4">
						<h3 class="font-bold text-lg text-primary">
							<%=transaction.getBundleName() != null && !transaction.getBundleName().isEmpty() ? transaction.getBundleName()
		: "Custom Services"%>
						</h3>

						<!-- Date and Time -->
						<div class="flex flex-wrap gap-4">
							<div class="flex items-center gap-2">
								<span class="material-symbols-outlined">event</span> <span><%=transaction.getStartDate().format(dateFormatter)%></span>
							</div>
							<div class="flex items-center gap-2">
								<span class="material-symbols-outlined">schedule</span> <span><%=transaction.getTimeSlot().format(timeFormatter)%></span>
							</div>
							<div class="flex items-center gap-2">
								<span class="material-symbols-outlined">location_on</span> <span><%=transaction.getAddress().getPostalCode()%></span>
							</div>
						</div>

						<!-- Services List -->
						<div class="space-y-3">
							<div class="font-medium">Services Included:</div>
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
									%>
									<div class="flex justify-between items-center mt-2">
										<span class="text-sm">Bundle Subtotal</span> <span
											class="text-sm">$<%=String.format("%.2f", bundleSubtotal)%></span>
									</div>
									<%
									if (transaction.getDiscount() > 0) {
									%>
									<div
										class="flex justify-between items-center mt-1 text-success">
										<span class="text-sm">Bundle Discount (<%=transaction.getDiscount()%>%
											OFF)
										</span> <span class="text-sm">-$<%=String.format("%.2f", bundleSubtotal - discountedBundlePrice)%></span>
									</div>
									<div class="flex justify-between items-center mt-1 font-medium">
										<span class="text-sm">Bundle Total After Discount</span> <span
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
									<div class="flex justify-between items-center mt-1 font-medium">
										<span class="text-sm">Additional Services Total</span> <span
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

						<!-- Price Breakdown -->
						<div class="divider"></div>
						<div class="space-y-2">
							<%
							// Calculate and display total
							double total = discountedBundlePrice + servicesSubtotal;
							%>
							<div class="flex justify-between items-center text-lg font-bold">
								<span>Total Amount</span> <span class="text-primary">$<%=String.format("%.2f", total)%></span>
							</div>
						</div>
					</div>

					<!-- Action Buttons -->
					<div class="flex justify-end gap-4 mt-4">
						<a href="${pageContext.request.contextPath}/book"
							class="btn btn-ghost gap-2"> <span
							class="material-symbols-outlined">arrow_back</span> Back to
							Bookings
						</a>
						<form action="${pageContext.request.contextPath}/book/payment"
							method="POST" class="inline">
							<input type="hidden" name="transactionId"
								value="<%=transaction.getId()%>">
							<button type="submit" class="btn btn-primary gap-2">
								<span class="material-symbols-outlined">payments</span> Pay $<%=String.format("%.2f", total)%>
							</button>
						</form>
					</div>
				</div>
				<%
				} else {
				%>
				<div class="text-center py-6">
					<div class="text-error mb-4">Transaction not found</div>
					<a href="${pageContext.request.contextPath}/book"
						class="btn btn-primary"> Return to Bookings </a>
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