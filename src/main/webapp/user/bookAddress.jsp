<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="models.address.Address"%>
<%@ page import="java.util.ArrayList"%>

<!DOCTYPE html>
<html data-theme="dark">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Select Service Address</title>
<link href="https://cdn.jsdelivr.net/npm/daisyui@3.9.4/dist/full.css"
	rel="stylesheet">
<script src="https://cdn.tailwindcss.com"></script>
<style>
.address-radio:checked+.address-label {
	background-color: rgba(147, 51, 234, 0.1);
	border-color: rgb(147, 51, 234);
}
</style>
</head>
<body class="min-h-screen bg-gradient-to-b from-base-300 to-base-200">
	<%@ include file="./components/header.jsp"%>

	<main class="container mx-auto px-4 py-8">
		<div class="max-w-4xl mx-auto">
			<!-- Title Section -->
			<div class="text-center mb-8">
				<h1
					class="text-4xl font-bold bg-gradient-to-r from-purple-500 to-pink-500 bg-clip-text text-transparent">
					Choose Your Service Address</h1>
				<p class="text-base-content/70 mt-2">Select where you'd like us
					to provide the service</p>
			</div>

			<!-- Progress Steps -->
			<div class="mb-12">
				<ul class="steps steps-horizontal w-full">
					<li class="step step-primary font-medium">Select Time</li>
					<li class="step step-primary font-medium">Choose Address</li>
					<li class="step font-medium">Service Info</li>
					<li class="step font-medium">Review</li>
				</ul>
			</div>

			<form action="${pageContext.request.contextPath}/book/address"
				method="post" class="space-y-6">
				<%
				ArrayList<Address> addresses = (ArrayList<Address>) request.getAttribute("addresses");
				Address homeAddress = addresses != null && !addresses.isEmpty() ? addresses.get(0) : null;
				Address officeAddress = addresses != null && addresses.size() > 1 ? addresses.get(1) : null;
				
				System.out.println("Home Address: " + homeAddress.getId());
				%>

				<!-- Saved Addresses Section -->
				<div class="grid grid-cols-1 md:grid-cols-2 gap-6">
					<!-- Home Address Card -->
					<%
					if (homeAddress != null) {
					%>
					<div
						class="card bg-base-100 shadow-xl hover:shadow-2xl transition-all duration-300">
						<div class="card-body relative">
							<!-- Radio button positioned absolutely in top right -->
							<div class="absolute top-4 right-4">
								<input type="radio" name="selected_address"
									value="<%=homeAddress.getId()%>" class="radio radio-primary"
									required>
							</div>

							<!-- Card content -->
							<div class="flex items-center gap-3 mb-4">
								<div class="p-2 rounded-lg bg-primary/10">
									<svg xmlns="http://www.w3.org/2000/svg"
										class="w-6 h-6 text-primary" fill="none" viewBox="0 0 24 24"
										stroke="currentColor">
                        <path stroke-linecap="round"
											stroke-linejoin="round" stroke-width="2"
											d="M3 12l2-2m0 0l7-7 7 7M5 10v10a1 1 0 001 1h3m10-11l2 2m-2-2v10a1 1 0 01-1 1h-3m-6 0a1 1 0 001-1v-4a1 1 0 011-1h2a1 1 0 011 1v4a1 1 0 001 1m-6 0h6" />
                    </svg>
								</div>
								<h3 class="card-title text-xl text-primary">Home Address</h3>
							</div>
							<div class="space-y-1 text-base-content/80">
								<p><%=homeAddress.getAddress()%></p>
								<p>
									Unit:
									<%=homeAddress.getUnit()%></p>
								<p><%=homeAddress.getPostalCode()%></p>
							</div>
						</div>
					</div>
					<%
					}
					%>

					<!-- Office Address Card -->
					<%
					if (officeAddress != null) {
					%>
					<div
						class="card bg-base-100 shadow-xl hover:shadow-2xl transition-all duration-300">
						<div class="card-body relative">
							<!-- Radio button positioned absolutely in top right -->
							<div class="absolute top-4 right-4">
								<input type="radio" name="selected_address"
									value="<%=officeAddress.getId()%>" class="radio radio-primary">
							</div>

							<!-- Card content -->
							<div class="flex items-center gap-3 mb-4">
								<div class="p-2 rounded-lg bg-primary/10">
									<svg xmlns="http://www.w3.org/2000/svg"
										class="w-6 h-6 text-primary" fill="none" viewBox="0 0 24 24"
										stroke="currentColor">
                        <path stroke-linecap="round"
											stroke-linejoin="round" stroke-width="2"
											d="M19 21V5a2 2 0 00-2-2H7a2 2 0 00-2 2v16m14 0h2m-2 0h-5m-9 0H3m2 0h5M9 7h1m-1 4h1m4-4h1m-1 4h1m-5 10v-5a1 1 0 011-1h2a1 1 0 011 1v5m-4 0h4" />
                    </svg>
								</div>
								<h3 class="card-title text-xl text-primary">Office Address</h3>
							</div>
							<div class="space-y-1 text-base-content/80">
								<p><%=officeAddress.getAddress()%></p>
								<p>
									Unit:
									<%=officeAddress.getUnit()%></p>
								<p><%=officeAddress.getPostalCode()%></p>
							</div>
						</div>
					</div>
					<%
					}
					%>
				</div>

				<!-- External Address Section -->
				<div
					class="card bg-base-100 shadow-xl hover:shadow-2xl transition-all duration-300">
					<div class="card-body relative">
						<!-- Radio button positioned absolutely in top right -->
						<div class="absolute top-4 right-4">
							<input type="radio" name="selected_address" value="new"
								class="radio radio-primary"
								<%=(homeAddress == null) ? "checked" : ""%>>
						</div>

						<!-- Card content -->
						<div class="flex items-center gap-3 mb-6">
							<div class="p-2 rounded-lg bg-primary/10">
								<svg xmlns="http://www.w3.org/2000/svg"
									class="w-6 h-6 text-primary" fill="none" viewBox="0 0 24 24"
									stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round"
										stroke-width="2"
										d="M17.657 16.657L13.414 20.9a1.998 1.998 0 01-2.827 0l-4.244-4.243a8 8 0 1111.314 0z" />
                    <path stroke-linecap="round" stroke-linejoin="round"
										stroke-width="2" d="M15 11a3 3 0 11-6 0 3 3 0 016 0z" />
                </svg>
							</div>
							<h3 class="card-title text-xl text-primary">Use Different
								Address</h3>
						</div>

						<div class="grid grid-cols-1 md:grid-cols-2 gap-6">
							<div class="form-control">
								<label class="label"> <span class="label-text">Address</span>
								</label>
								<textarea name="street"
									class="textarea textarea-bordered h-24"
									placeholder="Enter your street address"></textarea>
							</div>

							<div class="space-y-4">
								<div class="form-control">
									<label class="label"> <span class="label-text">Unit
											Number</span>
									</label> <input type="text" name="unit"
										class="input input-bordered" placeholder="e.g. #12-34">
								</div>

								<div class="form-control">
									<label class="label"> <span class="label-text">Postal
											Code</span>
									</label> <input type="text" name="postal"
										class="input input-bordered" placeholder="e.g. 123456">
								</div>
							</div>
						</div>
					</div>
				</div>

				<!-- Navigation Buttons -->
				<div class="flex justify-between items-center mt-8">
					<a href="${pageContext.request.contextPath}/book"
						class="btn btn-ghost gap-2"> <svg
							xmlns="http://www.w3.org/2000/svg" class="h-5 w-5"
							viewBox="0 0 20 20" fill="currentColor">
                            <path fill-rule="evenodd"
								d="M9.707 16.707a1 1 0 01-1.414 0l-6-6a1 1 0 010-1.414l6-6a1 1 0 011.414 1.414L5.414 9H17a1 1 0 110 2H5.414l4.293 4.293a1 1 0 010 1.414z"
								clip-rule="evenodd" />
                        </svg> Back to Time Slots
					</a>

					<button type="submit" class="btn btn-primary btn-lg gap-2">
						Continue to Services
						<svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5"
							viewBox="0 0 20 20" fill="currentColor">
                            <path fill-rule="evenodd"
								d="M10.293 3.293a1 1 0 011.414 0l6 6a1 1 0 010 1.414l-6 6a1 1 0 01-1.414-1.414L14.586 11H3a1 1 0 110-2h11.586l-4.293-4.293a1 1 0 010-1.414z"
								clip-rule="evenodd" />
                        </svg>
					</button>
				</div>
			</form>
		</div>
	</main>

	<%@ include file="components/footer.jsp"%>
</body>
</html>