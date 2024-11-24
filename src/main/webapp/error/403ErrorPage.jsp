<%-- 
    Name: Soe Zaw Aung, Scott
    Class: DIT/FT/2B/01
    Admin No: p2340474
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html data-theme="dark">
<head>
<meta charset="UTF-8">
<title>Access Denied Error</title>
<link href="https://cdn.jsdelivr.net/npm/daisyui@3.9.4/dist/full.css"
	rel="stylesheet">
<script src="https://cdn.tailwindcss.com"></script>
</head>
<body>
	<div class="min-h-screen bg-base-200 flex items-center justify-center">
		<div class="text-center max-w-md p-8 rounded-lg">
			<svg viewBox="0 0 400 300" class="w-full max-w-sm mx-auto mb-8">
                <!-- Background Security Shield -->
                <path
					d="M200 50 L300 100 L300 200 L200 250 L100 200 L100 100 Z"
					fill="#2A303C" stroke="#3b3b3b" stroke-width="4" />
                
                <!-- Lock Body -->
                <rect x="160" y="130" width="80" height="70" rx="10"
					fill="#ef4444" stroke="#b91c1c" stroke-width="4" />
                
                <!-- Lock Shackle -->
                <path d="M180 130 L180 100 Q200 80 220 100 L220 130"
					fill="none" stroke="#b91c1c" stroke-width="12"
					stroke-linecap="round" />
                
                <!-- 403 Text -->
                <text x="200" y="170" text-anchor="middle"
					font-family="monospace" font-size="24" font-weight="bold"
					fill="white">403</text>
            </svg>

			<h1 class="text-4xl font-bold mb-4 text-error">Access Forbidden</h1>
			<p class="text-base-content opacity-75 mb-8">Sorry, you don't
				have permission to access this page.</p>
			<a href="${pageContext.request.contextPath}/" class="btn btn-primary">
				Back to Home </a>
		</div>
	</div>
</body>
</html>