<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="models.service.Service"%>
<%@ page import="models.category.Category"%>
<%@ page import="java.util.ArrayList"%>
<!DOCTYPE html>
<html data-theme="dark">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Admin Service Management</title>
<link href="https://cdn.jsdelivr.net/npm/daisyui@3.9.4/dist/full.css"
	rel="stylesheet">
<script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="min-h-screen bg-base-100">
	<div class="container mx-auto px-4 py-8 max-w-4xl">
		<%
		ArrayList<Service> services = null;
		ArrayList<Category> categories = null;

		try {
			services = (ArrayList<Service>) request.getAttribute("services");
			categories = (ArrayList<Category>) request.getAttribute("categories");
			
		
		%>
		<div id="createForm">
			<div
				class="tabs tabs-boxed mb-4 flex flex-wrap justify-center md:justify-start">
				<a
					class="tab <%=request.getParameter("tab") == null || request.getParameter("tab").equals("service") ? "tab-active" : ""%>"
					onclick="switchTab('serviceForm', this)">Create Service</a> <a
					class="tab <%=request.getParameter("tab") != null && request.getParameter("tab").equals("bundle") ? "tab-active" : ""%>"
					onclick="switchTab('bundleForm', this)">Create Bundle</a> <a
					class="tab <%=request.getParameter("tab") != null && request.getParameter("tab").equals("category") ? "tab-active" : ""%>"
					onclick="switchTab('categoryForm', this)">Create Category</a>
			</div>

			<%
			HttpSession sess = request.getSession();
			Map<String, String> errors = (Map<String, String>) sess.getAttribute("createFormErrors");

			if (errors != null && !errors.isEmpty()) {
			%>
			<div role="alert" class="alert alert-error mb-4 shadow-lg">
				<div class="flex flex-col items-start">
					<div class="flex items-center gap-2">
						<svg xmlns="http://www.w3.org/2000/svg"
							class="stroke-current shrink-0 h-6 w-6" fill="none"
							viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round"
								stroke-width="2"
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
			sess.removeAttribute("createFormErrors");
			}
			%>


			<div id="serviceForm"
				class="card bg-base-200 shadow-xl <%=request.getParameter("tab") != null && !request.getParameter("tab").equals("service") ? "hidden" : ""%>">
				<!-- Service form content -->
				<div class="card-body">
					<h2 class="card-title text-xl md:text-2xl mb-4">Create Service</h2>
					<form action="create-new-service" method="POST"
						enctype="multipart/form-data">
						<input type="hidden" name="formType" value="service">

						<div class="grid gap-4">
							<div class="form-control">
								<label class="label">Service Name</label> <input type="text"
									name="serviceName" class="input input-bordered w-full" required>
							</div>

							<div class="form-control">
								<label class="label">Description</label>
								<textarea name="description"
									class="textarea textarea-bordered h-24"></textarea>
							</div>

							<div class="grid gap-4 md:grid-cols-2">
								<div class="form-control">
									<label class="label">Category</label> <select name="categoryId"
										class="select select-bordered w-full" required>
										<option value="" disabled selected>Select a category</option>
										<%
										if (categories != null && !categories.isEmpty()) {
											for (Category category : categories) {
										%>
										<option value="<%=category.getCategoryId()%>"><%=category.getCategoryName()%></option>
										<%
										}
										}
										%>
									</select>
								</div>

								<div class="form-control">
									<label class="label">Service Image (Optional)</label> <input
										type="file" name="serviceImage" accept="image/*"
										class="file-input file-input-bordered w-full">
								</div>

								<div class="form-control">
									<label class="label">Price ($)</label> <input type="number"
										name="price" class="input input-bordered" step="0.01" required>
								</div>
							</div>

							<div class="flex flex-col sm:flex-row gap-2 justify-end mt-4">
								<button type="button"
									onclick="window.location='${pageContext.request.contextPath}/admin'"
									class="btn btn-neutral w-full sm:w-auto">Cancel</button>
								<button type="submit" class="btn btn-primary w-full sm:w-auto">Create
									Service</button>
							</div>
						</div>
					</form>
				</div>
			</div>

			<!-- Bundle Form -->
			<div id="bundleForm"
				class="card bg-base-200 shadow-xl <%=request.getParameter("tab") == null || !request.getParameter("tab").equals("bundle") ? "hidden" : ""%>">
				<div class="card-body">
					<h2 class="card-title text-xl md:text-2xl mb-4">Create Bundle</h2>
					<form action="create-new-bundle" method="POST"
						enctype="multipart/form-data"
						onsubmit="return validateBundleForm()">
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

			<!-- Category Form -->
			<div id="categoryForm"
				class="card bg-base-200 shadow-xl <%=request.getParameter("tab") == null || !request.getParameter("tab").equals("category") ? "hidden" : ""%>">
				<div class="card-body">
					<h2 class="card-title text-xl md:text-2xl mb-4">Create
						Category</h2>
					<form action="create-new-category" method="POST">
						<input type="hidden" name="formType" value="category">

						<div class="grid gap-4">
							<div class="form-control">
								<label class="label">Category Name</label> <input type="text"
									name="categoryName" class="input input-bordered" required>
							</div>

							<div class="flex flex-col sm:flex-row gap-2 justify-end mt-4">
								<button type="button"
									onclick="window.location='${pageContext.request.contextPath}/admin'"
									class="btn btn-neutral w-full sm:w-auto">Cancel</button>
								<button type="submit" class="btn btn-primary w-full sm:w-auto">Create
									Category</button>
							</div>
						</div>
					</form>
				</div>
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
	</div>

	<script>

        function updateSelectedCount() {
            const selectedCount = document.querySelectorAll('input[name="selectedServices"]:checked').length;
            const errorDiv = document.getElementById('serviceSelectionError');
            errorDiv.classList.toggle('hidden', selectedCount >= 2);
        }

        function validateBundleForm() {
            const selectedCount = document.querySelectorAll('input[name="selectedServices"]:checked').length;
            const errorDiv = document.getElementById('serviceSelectionError');
            
            if (selectedCount < 2) {
                errorDiv.classList.remove('hidden');
                return false;
            }
            
            return true;
        }
        
        function switchTab(tab, element) {
            // Update URL without reloading the page
            const url = new URL(window.location);
            url.searchParams.set('tab', tab.replace('Form', '')); // Remove 'Form' suffix for cleaner URL
            window.history.pushState({}, '', url);
            
            // Switch the tab UI
            document.querySelectorAll('.tab').forEach(tab => tab.classList.remove('tab-active'));
            element.classList.add('tab-active');
            
            // Hide/show forms
            document.getElementById('serviceForm').classList.add('hidden');
            document.getElementById('bundleForm').classList.add('hidden');
            document.getElementById('categoryForm').classList.add('hidden');
            document.getElementById(tab).classList.remove('hidden');
        }

        // Function to set initial tab based on URL
        function setInitialTab() {
            const urlParams = new URLSearchParams(window.location.search);
            const tab = urlParams.get('tab');
            
            if (tab) {
                const tabMapping = {
                    'service': 'serviceForm',
                    'bundle': 'bundleForm',
                    'category': 'categoryForm'
                };
                
                const formId = tabMapping[tab.toLowerCase()];
                if (formId) {
                    const tabElement = document.querySelector(`[onclick="switchTab('${formId}', this)"]`);
                    if (tabElement) {
                        switchTab(formId, tabElement);
                    }
                }
            }
        }

        // Call this function when page loads
        document.addEventListener('DOMContentLoaded', setInitialTab);
        
    </script>
</body>
</html>