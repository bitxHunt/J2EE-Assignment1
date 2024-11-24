<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="java.util.List, models.booking.Booking, java.time.*, java.time.format.*"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Select Booking Slot</title>
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
				<h2 class="card-title text-2xl mb-6">Select Booking Time</h2>

				<!-- Progress Steps -->
				<ul class="steps steps-horizontal w-full mb-8">
					<li class="step step-primary">Select Time</li>
					<li class="step">Choose Address</li>
					<li class="step">Service Info</li>
					<li class="step">Review</li>
				</ul>

				<form action="${pageContext.request.contextPath}/book/address"
					method="post">
					<div class="grid grid-cols-1 md:grid-cols-4 gap-4">
						<%
						// Get the list of available slots from the servlet backend
						List<Booking> slots = (List<Booking>) request.getAttribute("timeslots");
						DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("EEEE, MMM dd");
						DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

						for (Booking slot : slots) {
						%>
						<div class="card bg-base-200 shadow-sm">
							<div class="card-body p-4">
								<h3 class="card-title text-sm">
									<%=LocalDate.parse(slot.getDate().toString()).format(dateFormatter)%>
								</h3>
								<div class="space-y-2">
									<%
									for (LocalTime time : slot.getSlots()) {
									%>
									<label class="label cursor-pointer justify-start gap-2">
										<input type="radio" name="selected_slot"
										value="<%=slot.getDate() + "_" + time%>"
										class="radio radio-primary"> <span class="label-text"><%=time.format(timeFormatter)%></span>
									</label>
									<%
									}
									%>
								</div>
							</div>
						</div>
						<%
						}
						%>
					</div>

					<div class="card-actions justify-end mt-6">
						<button type="submit" class="btn btn-primary">Continue to
							Address</button>
					</div>
				</form>
			</div>
		</div>
	</div>
	<!-- Footer -->
	<%@ include file="components/footer.jsp"%>
</body>
</html>