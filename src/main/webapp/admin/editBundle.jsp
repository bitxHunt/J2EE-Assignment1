<%-- 
    Name: Soe Zaw Aung, Scott
    Class: DIT/FT/2B/01
    Admin No: p2340474
--%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="models.service.Service"%>
<%@ page import="models.bundle.Bundle"%>
<%@ page import="java.util.ArrayList"%>
<!DOCTYPE html>
<html data-theme="dark">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Edit Bundle</title>
<link href="https://cdn.jsdelivr.net/npm/daisyui@3.9.4/dist/full.css"
	rel="stylesheet">
<script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="min-h-screen bg-base-100">
	<%
	Bundle bundle = (Bundle) request.getAttribute("bundle");
	ArrayList<Service> allServices = (ArrayList<Service>) request.getAttribute("allServices");

	if (bundle != null && allServices != null) {
	%>
	<div class="container mx-auto px-4 py-8 max-w-4xl">
		<%
		HttpSession sess = request.getSession();
		Map<String, String> errors = (Map<String, String>) sess.getAttribute("editBundleFormErrors");

		if (errors != null && !errors.isEmpty()) {
		%>
		<div role="alert" class="alert alert-error mb-4 shadow-lg">
			<div class="flex flex-col items-start">
				<div class="flex items-center gap-2">
					<svg xmlns="http://www.w3.org/2000/svg"
						class="stroke-current shrink-0 h-6 w-6" fill="none"
						viewBox="0 0 24 24">
                        <path stroke-linecap="round"
							stroke-linejoin="round" stroke-width="2"
							d="M10 14l2-2m0 0l2-2m-2 2l-2-2m2 2l2 2m7-2a9 9 0 11-18 0 9 9 0 0118 0z" />
                    </svg>
					<span class="font-semibold">Please check the following:</span>
				</div>
				<ul class="list-disc list-inside mt-2 ml-8">
					<%
					for (Map.Entry<String, String> error : errors.entrySet()) {
					%>
					<li class="text-sm"><%=error.getValue()%></li>
					<%
					}
					%>
				</ul>
			</div>
		</div>
		<%
		sess.removeAttribute("editBundleFormErrors");
		}
		%>

		<div class="card bg-base-200 shadow-xl">
			<div class="card-body">
				<h2 class="card-title text-xl md:text-2xl mb-4">Edit Bundle</h2>
				<form id="editBundleForm" action="<%=bundle.getBundleId()%>"
					method="POST" enctype="multipart/form-data"
					onsubmit="return validateBundleForm()">

					<div class="grid gap-4">
						<div class="form-control">
							<label class="label cursor-pointer flex justify-start gap-4">
								<span class="label-text text-sm font-medium">Status</span>
								<div class="flex items-center gap-2">
									<input type="checkbox" name="isActive"
										class="toggle toggle-primary"
										<%=bundle.getIsActive() ? "checked" : ""%>> <span
										class="text-sm"> <%=bundle.getIsActive() ? "Active" : "Inactive"%>
									</span>
								</div>
							</label>
						</div>

						<div class="grid gap-4 md:grid-cols-2">
							<div class="form-control">
								<label class="label">Bundle Name</label> <input type="text"
									name="bundleName" value="<%=bundle.getBundleName()%>"
									class="input input-bordered" required>
							</div>

							<div class="form-control">
								<label class="label">Discount (%)</label> <input type="number"
									name="discountPercent" value="<%=bundle.getDiscountPercent()%>"
									class="input input-bordered" min="0" max="100" required>
							</div>
						</div>

						<div class="form-control">
							<label class="label">Bundle Image</label> <img
								src="<%=bundle.getImageUrl() == null || bundle.getImageUrl().trim().isEmpty()
		? "https://cdn-icons-png.flaticon.com/512/1999/1999150.png"
		: bundle.getImageUrl()%>"
								alt="Current bundle image" class="max-w-xs mb-2 rounded-lg">
							<input type="file" name="bundleImage" accept="image/*"
								class="file-input file-input-bordered w-full">
						</div>

						<div class="form-control">
							<label class="label">Select Services</label>
							<div class="grid gap-2 md:grid-cols-2 lg:grid-cols-3">
								<%
								for (Service service : allServices) {
									boolean isSelected = bundle.getServices().stream().anyMatch(s -> s.getServiceId() == service.getServiceId());
								%>
								<div class="form-control border border-base-300 rounded-lg p-3">
									<label class="label cursor-pointer"> <span
										class="label-text"> <%=service.getServiceName()%>
											<div class="text-sm opacity-70">
												$<%=service.getPrice()%>
											</div>
									</span> <input type="checkbox" name="serviceIds"
										value="<%=service.getServiceId()%>"
										class="checkbox checkbox-primary"
										onchange="updateSelectedCount()"
										<%=isSelected ? "checked" : ""%>>
									</label>
								</div>
								<%
								}
								%>
							</div>
							<div class="text-sm mt-2 text-error hidden"
								id="serviceSelectionError">Please select at least 2
								services for the bundle</div>
						</div>

						<div class="flex flex-col sm:flex-row gap-2 justify-end mt-4">
							<button type="button"
								onclick="window.location='${pageContext.request.contextPath}/admin'"
								class="btn btn-neutral w-full sm:w-auto">Cancel</button>


							<button type="submit" form="deleteBundleForm"
								class="btn btn-error w-full sm:w-auto">Delete Bundle</button>

							<button type="submit" form="editBundleForm"
								class="btn btn-primary w-full sm:w-auto">Update Bundle
							</button>
						</div>
					</div>
				</form>
				<form id="deleteBundleForm"
					action="${pageContext.request.contextPath}/admin/delete-bundle/${bundle.bundleId}"
					method="POST"></form>
			</div>
		</div>
	</div>
	<%
	}
	%>

	<script>
		function updateSelectedCount() {
			const selectedCount = document
					.querySelectorAll('input[name="serviceIds"]:checked').length;
			const errorDiv = document.getElementById('serviceSelectionError');
			errorDiv.classList.toggle('hidden', selectedCount >= 2);
		}

		function validateBundleForm() {
			const selectedCount = document
					.querySelectorAll('input[name="serviceIds"]:checked').length;
			const errorDiv = document.getElementById('serviceSelectionError');

			if (selectedCount < 2) {
				errorDiv.classList.remove('hidden');
				return false;
			}

			return true;
		}
	</script>
</body>
</html>