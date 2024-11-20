<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html data-theme="dark">
<head>
<meta charset="UTF-8">
<title>Create Service/Bundle</title>
<link href="https://cdn.jsdelivr.net/npm/daisyui@3.9.4/dist/full.css"
	rel="stylesheet">
<script src="https://cdn.tailwindcss.com"></script>
<script>
        let selectedServices = new Set();


        function switchTab(tab, element) {
            // Remove active class from all tabs
            document.querySelectorAll('.tab').forEach(tab => tab.classList.remove('tab-active'));
            // Add active class to clicked tab
            element.classList.add('tab-active');
            
            document.getElementById('serviceForm').classList.add('hidden');
            document.getElementById('bundleForm').classList.add('hidden');
            document.getElementById(tab).classList.remove('hidden');
        }

        function addService() {
            const select = document.getElementById('bundleService');
            const serviceId = select.value;
            const serviceName = select.options[select.selectedIndex].text;
            
            if (selectedServices.has(serviceId)) {
                alert('This service is already added to the bundle!');
                return;
            }

            selectedServices.add(serviceId);
            
            const servicesList = document.getElementById('selectedServices');
            const serviceItem = document.createElement('div');
            serviceItem.className = 'flex justify-between items-center p-2 bg-base-200 rounded-lg mb-2';
            serviceItem.innerHTML = `
                <span class="text-white">${serviceName}</span>
                <button onclick="removeService('${serviceId}', this)" class="btn btn-error btn-sm">Remove</button>
            `;
            servicesList.appendChild(serviceItem);
        }

        function removeService(serviceId, button) {
            selectedServices.clear(); // Clear the entire Set
            const currentServices = document.querySelectorAll('#selectedServices > div');
            currentServices.forEach(service => {
                // Re-add services that are still visible in UI, except the one being removed
                if (service !== button.parentElement) {
                    const serviceName = service.querySelector('span').textContent;
                    const id = service.querySelector('button').getAttribute('onclick').split("'")[1];
                    selectedServices.add(id);
                }
            });
            button.parentElement.remove();
        }
    </script>
</head>
<body class="min-h-screen bg-base-100 p-8">


	<div id="createForm">
		<div class="tabs tabs-boxed mb-4">
			<a class="tab tab-active" onclick="switchTab('serviceForm', this)">Create
				Service</a> <a class="tab" onclick="switchTab('bundleForm', this)">Create
				Bundle</a>
		</div>

		<!-- Service Form -->
		<div id="serviceForm" class="card bg-base-200 shadow-xl p-6">
			<h2 class="text-2xl font-bold mb-4">Create Service</h2>
			<form action="createService" method="POST" class="space-y-4">
				<div class="form-control">
					<label class="label">Service Name</label> <input type="text"
						name="serviceName" class="input input-bordered" required>
				</div>

				<div class="form-control">
					<label class="label">Description</label>
					<textarea name="description"
						class="textarea textarea-bordered h-24" required></textarea>
				</div>

				<div class="form-control">
					<label class="label">Category</label> <select name="category"
						class="select select-bordered">
						<option value="">Select Category</option>
						<option value="1">Category 1</option>
						<option value="2">Category 2</option>
					</select>
				</div>

				<div class="form-control">
					<label class="label">Price</label> <input type="number"
						name="price" class="input input-bordered" required>
				</div>



				<div class="flex gap-2">
					<button type="submit" class="btn btn-primary">Create
						Service</button>
					<button type="button"
						onclick="window.location='${pageContext.request.contextPath}/admin/dashboard.jsp'"
						class="btn btn-neutral">Cancel</button>
				</div>
			</form>
		</div>

		<!-- Bundle Form -->
		<div id="bundleForm" class="card bg-base-200 shadow-xl p-6 hidden">
			<h2 class="text-2xl font-bold mb-4">Create Bundle</h2>
			<form action="createBundle" method="POST" class="space-y-4">
				<div class="form-control">
					<label class="label">Bundle Name</label> <input type="text"
						name="bundleName" class="input input-bordered" required>
				</div>

				<div class="form-control">
					<label class="label">Discount Percentage</label> <input
						type="number" name="discount" class="input input-bordered" min="0"
						max="100" required>
				</div>

				<div class="form-control">
					<label class="label">Add Services</label>
					<div class="flex gap-2">
						<select id="bundleService" class="select select-bordered flex-1">
							<option value="1">Service 1</option>
							<option value="2">Service 2</option>
							<option value="3">Service 3</option>
						</select>
						<button type="button" onclick="addService()"
							class="btn btn-secondary">Add</button>
					</div>
				</div>

				<div id="selectedServices" class="space-y-2 mt-4"></div>

				<div class="flex gap-2">
					<button type="submit" class="btn btn-primary">Create
						Bundle</button>
					<button type="button"
						onclick="window.location='${pageContext.request.contextPath}/admin/dashboard.jsp'"
						class="btn btn-neutral">Cancel</button>
				</div>
			</form>
		</div>
	</div>
</body>
</html>