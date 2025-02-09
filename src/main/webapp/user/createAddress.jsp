<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en" data-theme="dark">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Add New Address - CleanX</title>
<link
	href="https://cdn.jsdelivr.net/npm/daisyui@4.12.14/dist/full.min.css"
	rel="stylesheet" type="text/css" />
<script src="https://cdn.tailwindcss.com"></script>
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@24,400,0,0" />
</head>
<body class="bg-base-300 min-h-screen">
	<%
	if (session.getAttribute("userId") != null) {
	%>
	<%@ include file="./components/header.jsp"%>
	<%
	} else {
	response.sendRedirect(request.getContextPath() + "/login");
	return;
	}

	String addressType = (String) request.getAttribute("addressType");
	boolean isHome = "HOME".equals(addressType);
	%>

	<div class="container mx-auto p-6 max-w-3xl">
		<div class="card bg-base-200 shadow-xl">
			<div class="card-body">
				<!-- Header -->
				<div class="flex items-center gap-4 mb-6">
					<div
						class="p-3 <%=isHome ? "bg-primary/10" : "bg-secondary/10"%> rounded-full">
						<span
							class="material-symbols-outlined <%=isHome ? "text-primary" : "text-secondary"%> text-2xl">
							<%=isHome ? "home" : "apartment"%>
						</span>
					</div>
					<div>
						<h1 class="text-2xl font-bold">
							Add New
							<%=isHome ? "Home" : "Office"%>
							Address
						</h1>
						<p class="text-base-content/60">Please enter your address
							details below</p>
					</div>
				</div>

				<!-- Address Form -->
				<form action="${pageContext.request.contextPath}/address/add"
					method="POST" class="space-y-6">
					<input type="hidden" name="addressType" value="<%=addressType%>">

					<!-- Street Address -->
					<div class="form-control">
						<label class="label"> <span class="label-text">Street
								Address</span>
						</label> <input type="text" name="address"
							placeholder="Enter your street address"
							class="input input-bordered" required />
					</div>

					<!-- Unit Number -->
					<div class="form-control">
						<label class="label"> <span class="label-text">Unit
								Number</span>
						</label> <input type="text" name="unit" placeholder="e.g., #12-345"
							class="input input-bordered" required />
					</div>

					<!-- Postal Code -->
					<div class="form-control">
						<label class="label"> <span class="label-text">Postal
								Code</span>
						</label> <input type="text" name="postalCode"
							placeholder="6-digit postal code" pattern="[0-9]{6}"
							maxlength="6" class="input input-bordered" required />
					</div>

					<!-- Submit Buttons -->
					<div class="flex gap-4 justify-end mt-6">
						<a href="${pageContext.request.contextPath}/profile"
							class="btn btn-ghost">Cancel</a>
						<button type="submit" class="btn btn-primary">Save
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