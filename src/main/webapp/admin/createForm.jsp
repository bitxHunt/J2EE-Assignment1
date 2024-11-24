<%-- 
    Name: Soe Zaw Aung, Scott
    Class: DIT/FT/2B/01
    Admin No: p2340474
--%>
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


			<!-- Service Form -->
			<%@ include file="./components/serviceForm.jsp"%>


			<!-- Bundle Form -->
			<%@ include file="./components/bundleForm.jsp"%>

			<!-- Category Form -->
			<%@ include file="./components/categoryForm.jsp"%>
	</div>

	<script>

       
        
        function switchTab(tab, element) {
            // Update URL without reloading the page
            const url = new URL(window.location);
            url.searchParams.set('tab', tab.replace('Form', '')); 
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

        // Call the initTab function when page loads
        document.addEventListener('DOMContentLoaded', setInitialTab);
        
    </script>
</body>
</html>