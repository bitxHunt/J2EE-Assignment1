<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="models.service.Service"%>
<%@ page import="models.bundle.Bundle"%>
<%@ page import="models.category.Category"%>
<%@ page import="java.util.ArrayList"%>

<!DOCTYPE html>
<html data-theme="dark">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Home Page</title>
<link href="https://cdn.jsdelivr.net/npm/daisyui@3.9.4/dist/full.css"
	rel="stylesheet">

<script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="min-h-screen bg-base-100">
	<div class="container mx-auto px-4 py-8">
		<%@ include file="./components/adminNavbar.jsp"%>
		<!-- Tabs -->
		<div class="tabs tabs-boxed mb-8 flex flex-wrap justify-center">
			<a class="tab tab-active" onclick="switchTab('services', this)">Services</a>
			<a class="tab" onclick="switchTab('bundles', this)">Bundles</a> <a
				class="tab" onclick="switchTab('categories', this)">Categories</a>
		</div>

		<!-- Services Section -->
		<%@ include file="./components/serviceContainer.jsp"%>
		<!-- Bundles Section -->
		<%@ include file="./components/bundleContainer.jsp"%>
		<!-- Categories Section -->
		<%@ include file="./components/categoryContainer.jsp"%>
	</div>
	<script>
       function switchTab(tabId, element) {
           // Hide all tabs
           document.getElementById('services').classList.add('hidden');
           document.getElementById('bundles').classList.add('hidden');
           document.getElementById('categories').classList.add('hidden');
           
           // Show selected tab
           document.getElementById(tabId).classList.remove('hidden');
           
           // Update active state
           document.querySelectorAll('.tab').forEach(tab => tab.classList.remove('tab-active'));
           element.classList.add('tab-active');
       }

   </script>
</body>
</html>