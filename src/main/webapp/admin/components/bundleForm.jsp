<%@ page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="models.service.Service"%>
<%@ page import="models.category.Category"%>
<%@ page import="java.util.ArrayList"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Bundle Form</title>
</head>
<body>
	<%
	ArrayList<Service> services = null;
	try {
		services = (ArrayList<Service>) request.getAttribute("services");
	%>
	<!-- Bundle Form -->
	<div id="bundleForm"
		class="card bg-base-200 shadow-xl <%=request.getParameter("tab") == null || !request.getParameter("tab").equals("bundle") ? "hidden" : ""%>">
		<div class="card-body">
			<h2 class="card-title text-xl md:text-2xl mb-4">Create Bundle</h2>
			<form action="create-new-bundle" method="POST"
				enctype="multipart/form-data" onsubmit="return validateBundleForm()">
				<input type="hidden" name="formType" value="bundle">

				<div class="grid gap-4">
					<div class="grid gap-4 md:grid-cols-2">
						<div class="form-control">
							<label class="label">Bundle Name</label> <input type="text"
								name="bundleName" class="input input-bordered" required>
						</div>

						<div class="form-control">
							<label class="label">Discount (%)</label> <input type="number"
								name="discount" class="input input-bordered" min="0" max="100"
								required>
						</div>
					</div>

					<div class="form-control">
						<label class="label">Bundle Image (Optional)</label> <input
							type="file" name="bundleImage" accept="image/*"
							class="file-input file-input-bordered w-full">
					</div>

					<div class="form-control">
						<label class="label">Select Services</label>
						<div class="grid gap-2 md:grid-cols-2 lg:grid-cols-3">
							<%
							if (services != null && !services.isEmpty()) {
								for (Service service : services) {
							%>
							<div class="form-control border border-base-300 rounded-lg p-3">
								<label class="label cursor-pointer"> <span
									class="label-text"> <%=service.getServiceName()%>
										<div class="text-sm opacity-70">
											$<%=service.getPrice()%></div>
								</span> <input type="checkbox" name="selectedServices"
									value="<%=service.getServiceId()%>"
									class="checkbox checkbox-primary"
									onchange="updateSelectedCount()">
								</label>
							</div>
							<%
							}
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
						<button type="submit" class="btn btn-primary w-full sm:w-auto">Create
							Bundle</button>
					</div>
				</div>
			</form>
		</div>
	</div>
	<%
	} catch (Exception e) {
	String err = (String) request.getAttribute("err");
	if (err != null) {
	%>
	<div class="alert alert-error">
		<%=err%>
	</div>
	<%
	}
	}
	%>

	<script>
		function updateSelectedCount() {
			const selectedCount = document
					.querySelectorAll('input[name="selectedServices"]:checked').length;
			const errorDiv = document.getElementById('serviceSelectionError');
			errorDiv.classList.toggle('hidden', selectedCount >= 2);
		}

		function validateBundleForm() {
			const selectedCount = document
					.querySelectorAll('input[name="selectedServices"]:checked').length;
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