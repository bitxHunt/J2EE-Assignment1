<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html data-theme="dark">
<head>
    <meta charset="UTF-8">
    <title>Create Service/Bundle</title>
    <link href="https://cdn.jsdelivr.net/npm/daisyui@3.9.4/dist/full.css" rel="stylesheet">
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="min-h-screen bg-base-100 p-8">
    <div id="createForm">
        <div class="tabs tabs-boxed mb-4">
            <a class="tab tab-active" onclick="switchTab('serviceForm', this)">Create Service</a>
            <a class="tab" onclick="switchTab('bundleForm', this)">Create Bundle</a>
        </div>

        <!-- Service Form -->
        <div id="serviceForm" class="card bg-base-200 shadow-xl p-6">
            <h2 class="text-2xl font-bold mb-4">Create Service</h2>
            <form action="createService" method="POST" class="space-y-4">
                <div class="form-control">
                    <label class="label">Service Name</label>
                    <input type="text" name="serviceName" class="input input-bordered" required>
                </div>

                <div class="form-control">
                    <label class="label">Description</label>
                    <textarea name="description" class="textarea textarea-bordered h-24" required></textarea>
                </div>

                <div class="form-control">
                    <label class="label">Category</label>
                    <div class="relative">
                        <input type="hidden" name="categoryId" id="categoryId">
                        <button type="button" 
                                id="categoryButton"
                                class="input input-bordered w-full text-left flex justify-between items-center"
                                onclick="toggleCategoryPopup()">
                            <span id="selectedCategory">Select Category</span>
                            <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 9l-7 7-7-7" />
                            </svg>
                        </button>
                        
                        <div id="categoryPopup" class="hidden absolute top-full left-0 w-full mt-1 bg-base-100 border border-base-300 rounded-lg shadow-lg z-50">
                            <div class="p-2">
                                <input type="text" 
                                       id="categorySearch" 
                                       class="input input-bordered w-full mb-2" 
                                       placeholder="Search or create category..."
                                       oninput="filterCategories(this.value)">
                            </div>
                            <div id="categoryList" class="max-h-48 overflow-y-auto">
                                <!-- Categories will be populated here -->
                            </div>
                            <div id="createNewCategory" class="hidden p-2 border-t border-base-300">
                                <div class="flex gap-2">
                                    <input type="text" 
                                           id="newCategoryInput" 
                                           class="input input-bordered input-sm flex-1" 
                                           placeholder="New category name">
                                    <button type="button" 
                                            class="btn btn-primary btn-sm"
                                            onclick="createNewCategory()">
                                        Create
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="form-control">
                    <label class="label">Price</label>
                    <input type="number" name="price" class="input input-bordered" required>
                </div>

                <div class="flex gap-2">
                    <button type="submit" class="btn btn-primary">Create Service</button>
                    <button type="button" onclick="window.location='${pageContext.request.contextPath}/admin/home.jsp'" class="btn btn-neutral">Cancel</button>
                </div>
            </form>
        </div>

        <!-- Bundle Form -->
        <div id="bundleForm" class="card bg-base-200 shadow-xl p-6 hidden">
            <h2 class="text-2xl font-bold mb-4">Create Bundle</h2>
            <form action="createBundle" method="POST" class="space-y-4">
                <div class="form-control">
                    <label class="label">Bundle Name</label>
                    <input type="text" name="bundleName" class="input input-bordered" required>
                </div>

                <div class="form-control">
                    <label class="label">Discount Percentage</label>
                    <input type="number" name="discount" class="input input-bordered" min="0" max="100" required>
                </div>

                <div class="form-control">
                    <label class="label">Add Services</label>
                    <div class="flex gap-2">
                        <select id="bundleService" class="select select-bordered flex-1">
                            <!-- Services will be populated here -->
                        </select>
                        <button type="button" onclick="addService()" class="btn btn-secondary">Add</button>
                    </div>
                </div>

                <div id="selectedServices" class="space-y-2 mt-4">
                    <!-- Selected services will be displayed here -->
                </div>

                <div class="flex gap-2">
                    <button type="submit" class="btn btn-primary">Create Bundle</button>
                    <button type="button" onclick="window.location='${pageContext.request.contextPath}/admin/home.jsp'" class="btn btn-neutral">Cancel</button>
                </div>
            </form>
        </div>
    </div>

    <script>
        // Tab switching functionality
        function switchTab(tab, element) {
            document.querySelectorAll('.tab').forEach(tab => tab.classList.remove('tab-active'));
            element.classList.add('tab-active');
            
            document.getElementById('serviceForm').classList.add('hidden');
            document.getElementById('bundleForm').classList.add('hidden');
            document.getElementById(tab).classList.remove('hidden');
        }

        // Category selector functionality
        let categories = [
            { id: 1, name: 'Category 1' },
            { id: 2, name: 'Category 2' }
        ];

        function toggleCategoryPopup() {
            const popup = document.getElementById('categoryPopup');
            popup.classList.toggle('hidden');
            if (!popup.classList.contains('hidden')) {
                document.getElementById('categorySearch').focus();
                renderCategories(categories);
            }
        }

        function renderCategories(categoriesToRender) {
            const categoryList = document.getElementById('categoryList');
            categoryList.innerHTML = categoriesToRender.map(category => `
                <div class="p-2 cursor-pointer hover:bg-base-300" 
                     onclick="selectCategory(${category.id}, '${category.name}')">
                    ${category.name}
                </div>
            `).join('');
        }

        function filterCategories(searchTerm) {
            const filteredCategories = categories.filter(cat => 
                cat.name.toLowerCase().includes(searchTerm.toLowerCase())
            );
            renderCategories(filteredCategories);
            
            const createNew = document.getElementById('createNewCategory');
            if (searchTerm && !filteredCategories.length) {
                createNew.classList.remove('hidden');
                document.getElementById('newCategoryInput').value = searchTerm;
            } else {
                createNew.classList.add('hidden');
            }
        }

        function selectCategory(id, name) {
            document.getElementById('categoryId').value = id;
            document.getElementById('selectedCategory').textContent = name;
            toggleCategoryPopup();
        }

        function createNewCategory() {
            const input = document.getElementById('newCategoryInput');
            const newCategoryName = input.value.trim();
            
            if (newCategoryName) {
                // In production, this should be an API call to your backend
                const newId = Math.max(...categories.map(c => c.id)) + 1;
                const newCategory = { id: newId, name: newCategoryName };
                categories.push(newCategory);
                
                selectCategory(newId, newCategoryName);
                input.value = '';
                document.getElementById('categorySearch').value = '';
            }
        }

        // Bundle service selection functionality
        let selectedServices = new Set();

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
            serviceItem.className = 'flex justify-between items-center p-2 bg-base-300 rounded-lg';
            serviceItem.innerHTML = `
                <span>${serviceName}</span>
                <button type="button" onclick="removeService('${serviceId}', this)" class="btn btn-error btn-sm">Remove</button>
            `;
            servicesList.appendChild(serviceItem);
        }

        function removeService(serviceId, button) {
            selectedServices.delete(serviceId);
            button.closest('div').remove();
        }

        // Close category popup when clicking outside
        document.addEventListener('click', function(event) {
            const dropdown = document.querySelector('.relative');
            const popup = document.getElementById('categoryPopup');
            
            if (!dropdown.contains(event.target) && !popup.classList.contains('hidden')) {
                popup.classList.add('hidden');
            }
        });

        // Initialize services in bundle form (replace with actual data)
        window.onload = function() {
            const bundleService = document.getElementById('bundleService');
            // Replace with actual service data from backend
            const services = [
                { id: 1, name: 'Service 1' },
                { id: 2, name: 'Service 2' },
                { id: 3, name: 'Service 3' }
            ];
            
            bundleService.innerHTML = services.map(service => 
                `<option value="${service.id}">${service.name}</option>`
            ).join('');
        };
    </script>
</body>
</html>