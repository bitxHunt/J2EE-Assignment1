<%-- 
    Name: Thiha Swan Htet
    Class: DIT/FT/2B/01
    Admin No: p2336671
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="java.util.ArrayList, models.service.Service, models.bundle.Bundle, models.address.Address, java.time.*"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Cart Preview</title>
<link href="https://cdn.jsdelivr.net/npm/daisyui@3.5.0/dist/full.css"
	rel="stylesheet">
<script src="https://cdn.tailwindcss.com"></script>
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@24,400,0,0" />
</head>
<body class="bg-base-200 min-h-screen">
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
	<div class="container mx-auto p-4">
		<div class="card bg-base-100 shadow-xl">
			<div class="card-body">
				<h2 class="card-title text-2xl mb-6">Review Your Selection</h2>

				<!-- Progress Steps -->
				<ul class="steps steps-horizontal w-full mb-8">
					<li class="step step-primary">Select Time</li>
					<li class="step step-primary">Choose Address</li>
					<li class="step step-primary">Service Info</li>
					<li class="step step-primary">Review</li>
				</ul>

				<!-- Booking Details -->
				<%
				LocalDate selectedDate = (LocalDate) request.getAttribute("selectedDate");
				LocalTime selectedTime = (LocalTime) request.getAttribute("selectedTime");
				Address address = (Address) request.getAttribute("selectedAddress");
				String formattedTotal = "";
				%>

				<div class="bg-base-200 p-4 rounded-lg mb-6">
					<h3 class="font-bold text-lg mb-2">Booking Details</h3>
					<div class="grid grid-cols-1 md:grid-cols-2 gap-4">
						<div>
							<p class="text-sm font-semibold">Date & Time:</p>
							<p class="text-base-content"><%=selectedDate%>
								at
								<%=selectedTime%></p>
						</div>
						<div>
							<p class="text-sm font-semibold">Service Location:</p>
							<p class="text-base-content"><%=address.getAddress()%></p>
							<p class="text-base-content"><%=address.getUnit()%></p>
							<p class="text-base-content">
								Singapore
								<%=address.getPostalCode()%></p>
						</div>
					</div>
				</div>

				<!-- If Bundle is selected -->
				<%
				Bundle selectedBundle = (Bundle) request.getAttribute("selectedBundle");
				if (selectedBundle != null) {
				%>
				<div class="bg-base-200 p-4 rounded-lg mb-6">
					<div class="flex items-center mb-4">
						<img src="<%=selectedBundle.getImageUrl()%>"
							alt="<%=selectedBundle.getBundleName()%>"
							class="w-20 h-20 mr-4 object-cover rounded">
						<div>
							<h3 class="font-bold text-lg"><%=selectedBundle.getBundleName()%></h3>
							<div class="badge badge-secondary"><%=selectedBundle.getDiscountPercent()%>%
								OFF
							</div>
						</div>
					</div>

					<div class="divider"></div>

					<!-- Bundle Services -->
					<div class="space-y-2">
						<p class="font-semibold">Included Services:</p>
						<%
						for (Service service : selectedBundle.getServices()) {
						%>
						<div class="flex justify-between items-center">
							<span>• <%=service.getServiceName()%></span> <span
								class="text-base-content">$<%=service.getPrice()%></span>
						</div>
						<%
						}
						%>
					</div>

					<div class="divider"></div>

					<!-- Price Summary -->
					<div class="flex justify-between items-center text-lg">
						<div>
							<span class="line-through text-sm text-gray-500">$<%=selectedBundle.getOriginalPrice()%></span>
							<span class="font-bold text-primary ml-2">$<%=selectedBundle.getDiscountedPrice()%></span>
						</div>
					</div>
				</div>
				<%
				}
				%>

				<!-- If Individual Services are selected -->
				<%
				ArrayList<Service> selectedServices = (ArrayList<Service>) request.getAttribute("selectedServices");
				if (selectedServices != null && !selectedServices.isEmpty()) {
				%>
				<div class="bg-base-200 p-4 rounded-lg mb-6">
					<h3 class="font-bold text-lg mb-4">Selected Services</h3>

					<!-- List of Services -->
					<div class="space-y-4">
						<%
						double total = 0;
						if (selectedBundle != null) {
							total += selectedBundle.getDiscountedPrice();
						}
						for (Service service : selectedServices) {
							total += service.getPrice();
						%>
						<div
							class="flex items-center justify-between p-2 bg-base-100 rounded">
							<div class="flex items-center">
								<img src="<%=service.getImageUrl()%>"
									alt="<%=service.getServiceName()%>"
									class="w-16 h-16 mr-4 object-cover rounded">
								<div>
									<p class="font-semibold"><%=service.getServiceName()%></p>
									<p class="text-sm text-gray-600"><%=service.getServiceDescription()%></p>
								</div>
							</div>
							<span class="text-primary font-bold">$<%=service.getPrice()%></span>
						</div>
						<%
						}

						formattedTotal = String.format("%.2f", total);
						%>
					</div>

					<div class="divider"></div>

					<!-- Total Price -->
					<div class="flex justify-between items-center text-lg">
						<span class="font-bold">Total:</span> <span
							class="font-bold text-primary">$<%=formattedTotal%></span>
					</div>
				</div>
				<%
				}
				%>

				<!-- Action Buttons -->
				<div class="flex flex-col md:flex-row gap-4 justify-end mt-6">
					<form action="${pageContext.request.contextPath}/book/confirm"
						method="post" class="flex-1 md:flex-none">
						<input type="hidden" name="bookingDate"
							value="<%=request.getAttribute("bookingDate")%>"> <input
							type="hidden" name="timeSlot"
							value="<%=request.getAttribute("timeSlot")%>"> <input
							type="hidden" name="address"
							value="<%=request.getAttribute("address")%>"> <input
							type="hidden" name="subTotal" value="<%=formattedTotal%>">
						<%
						if (selectedBundle != null) {
						%>
						<input type="hidden" name="bundleId"
							value="<%=selectedBundle.getBundleId()%>">
						<%
						}
						%>
						<%
						if (selectedServices != null && !selectedServices.isEmpty()) {
							for (Service service : selectedServices) {
						%>
						<input type="hidden" name="selectedServices"
							value="<%=service.getServiceId()%>">
						<%
						}
						}
						%>
						<button type="submit" class="btn btn-outline w-full md:w-auto"
							name="btnSubmit" value="1">Add to Cart</button>
					</form>

					<form action="${pageContext.request.contextPath}/book/confirm"
						method="post" class="flex-1 md:flex-none">
						<input type="hidden" name="bookingDate"
							value="<%=request.getAttribute("bookingDate")%>"> <input
							type="hidden" name="timeSlot"
							value="<%=request.getAttribute("timeSlot")%>"> <input
							type="hidden" name="address"
							value="<%=request.getAttribute("address")%>"> <input
							type="hidden" name="subTotal" value="<%=formattedTotal%>">
						<%
						if (selectedBundle != null) {
						%>
						<input type="hidden" name="bundleId"
							value="<%=selectedBundle.getBundleId()%>">
						<%
						}
						%>
						<%
						if (selectedServices != null && !selectedServices.isEmpty()) {
							for (Service service : selectedServices) {
						%>
						<input type="hidden" name="selectedServices"
							value="<%=service.getServiceId()%>">
						<%
						}
						}
						%>
						<button type="submit" class="btn btn-primary w-full md:w-auto"
							name="btnSubmit" value="2">Proceed to Payment</button>
					</form>
				</div>
			</div>
		</div>
	</div>
	<!-- Footer -->
	<%@ include file="components/footer.jsp"%>
</body>
</html>