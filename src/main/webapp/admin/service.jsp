<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html data-theme="dark">
<head>
    <meta charset="UTF-8">
    <title>Service Details</title>
    <link href="https://cdn.jsdelivr.net/npm/daisyui@3.9.4/dist/full.css" rel="stylesheet">
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="min-h-screen bg-base-100 p-8">

    <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
        <!-- Service Card -->
        <div class="card w-96 bg-base-100 shadow-xl hover:shadow-2xl transition-shadow duration-200">
            <!-- Image at the top of card -->
            <figure class="px-4 pt-4">
                <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcShbHFEP8xuKF78r944lNgAeCojF6cdh5EEjCZ4BSqvEP9L3kHmnaRsH58iMPZqW0CfBwE&usqp=CAU" 
                     alt="Service Image" 
                     class="rounded-xl h-48 w-full object-cover" />
            </figure>
            
            <div class="card-body">
                <!-- Service Status Toggle & Edit Button -->
                <div class="flex justify-end gap-2 mb-2 items-center">
                    <span class="text-sm opacity-75">Status</span>
                    <input type="checkbox" class="toggle toggle-success" checked />
                    <button onclick="window.location='${pageContext.request.contextPath}/admin/editService.jsp'" class="btn btn-square btn-sm btn-ghost">
                        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-5 h-5">
                            <path stroke-linecap="round" stroke-linejoin="round" d="M16.862 4.487l1.687-1.688a1.875 1.875 0 112.652 2.652L10.582 16.07a4.5 4.5 0 01-1.897 1.13L6 18l.8-2.685a4.5 4.5 0 011.13-1.897l8.932-8.931zm0 0L19.5 7.125M18 14v4.75A2.25 2.25 0 0115.75 21H5.25A2.25 2.25 0 013 18.75V8.25A2.25 2.25 0 015.25 6H10" />
                        </svg>
                    </button>
                </div>

                <!-- Service Name with Category Badge -->
                <div class="flex justify-between items-start">
                    <h2 class="card-title text-xl font-bold text-primary">Hair Cut</h2>
                    <div class="badge badge-secondary">Hair Care</div>
                </div>
                
                <!-- Price -->
                <div class="text-2xl font-bold mt-2 text-accent">$29.99</div>
                
                <!-- Description -->
                <p class="mt-4 text-base-content/80">Professional haircut service including washing, cutting, and styling.</p>
            </div>
        </div>

    <script>
        // Add event listener to all toggles
        document.querySelectorAll('.toggle').forEach(toggle => {
            toggle.addEventListener('change', function() {
                const isActive = this.checked;
                // You can add AJAX call here to update service status
                console.log(`Service status changed to: ${isActive ? 'Active' : 'Inactive'}`);
            });
        });
    </script>
</body>
</html>