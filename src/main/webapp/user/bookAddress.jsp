<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="models.address.Address"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Select Address</title>
<link href="https://cdn.jsdelivr.net/npm/daisyui@3.5.0/dist/full.css"
	rel="stylesheet">
<script src="https://cdn.tailwindcss.com"></script>
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@24,400,0,0" />
</head>
<body class="bg-base-200 min-h-screen">
	<!-- Header -->

	<%@ include file="./components/header.jsp"%>
	<div class="container mx-auto p-4">
		<div class="card bg-base-100 shadow-xl">
			<div class="card-body">
				<h2 class="card-title text-2xl mb-6">Select Address</h2>

				<ul class="steps steps-horizontal w-full mb-8">
					<li class="step step-primary">Select Time</li>
					<li class="step step-primary">Choose Address</li>
					<li class="step">Service Info</li>
					<li class="step">Review</li>
				</ul>

				<form action="${pageContext.request.contextPath}/book/services"
					method="post">
					<div class="grid grid-cols-1 md:grid-cols-2 gap-6">
						<div>
							<%
							Address homeAddress = (Address) request.getAttribute("homeAddress");
							Address officeAddress = (Address) request.getAttribute("officeAddress");

							if (homeAddress == null && officeAddress == null) {
							%>
							<div class="alert alert-info">
								<span>No saved addresses found. Please use the external
									address form.</span>
							</div>
							<%
							} else {
							if (homeAddress != null) {
							%>
							<div class="card bg-base-200 mb-4">
								<div class="card-body">
									<h3 class="card-title">Home Address</h3>
									<div class="form-control">
										<label class="label cursor-pointer"> <span
											class="label-text"> <%=homeAddress.getAddress()%><br>
												Unit: <%=homeAddress.getUnit()%><br> <%=homeAddress.getPostalCode()%>
										</span> <input type="radio" name="address_type" value="1"
											class="radio radio-primary">
										</label>
									</div>
								</div>
							</div>
							<%
							}
							if (officeAddress != null) {
							%>
							<div class="card bg-base-200">
								<div class="card-body">
									<h3 class="card-title">Office Address</h3>
									<div class="form-control">
										<label class="label cursor-pointer"> <span
											class="label-text"> <%=officeAddress.getAddress()%><br>
												Unit: <%=officeAddress.getUnit()%><br> <%=officeAddress.getPostalCode()%>
										</span> <input type="radio" name="address_type" value="2"
											class="radio radio-primary">
										</label>
									</div>
								</div>
							</div>
							<%
							}
							}
							%>
						</div>

						<!-- Right Column - External Address -->
						<div class="card bg-base-200">
							<div class="card-body">
								<h3 class="card-title">External Address</h3>
								<div class="form-control">
									<label class="label"> <span class="label-text">Use
											external address</span> <input type="radio" name="address_type"
										value="3" class="radio radio-primary"
										<%=(homeAddress == null && officeAddress == null) ? "checked" : ""%>>
									</label>
								</div>

								<div class="form-control mt-4">
									<label class="label"> <span class="label-text">Address</span>
									</label>
									<textarea name="external_address"
										class="textarea textarea-bordered" placeholder="Enter address"></textarea>
								</div>

								<div class="form-control mt-4">
									<label class="label"> <span class="label-text">Unit
											Number</span>
									</label> <input type="text" name="external_unit"
										class="input input-bordered">
								</div>

								<div class="form-control mt-4">
									<label class="label"> <span class="label-text">Postal
											Code</span>
									</label> <input type="text" name="external_postal"
										class="input input-bordered">
								</div>
							</div>
						</div>
					</div>

					<div class="card-actions justify-between mt-6">
						<a href="${pageContext.request.contextPath}/book/slots"
							class="btn">Back</a>
						<button type="submit" class="btn btn-primary">Continue to
							Services</button>
					</div>
				</form>
			</div>
		</div>
	</div>
</body>
<!-- Footer -->
<%@ include file="components/footer.jsp"%>
</html>
