<%-- 
    Name: Soe Zaw Aung, Scott
    Class: DIT/FT/2B/01
    Admin No: p2340474
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isErrorPage="true"%>
<!DOCTYPE html>
<html data-theme="dark">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Internal Server Error</title>
<link href="https://cdn.jsdelivr.net/npm/daisyui@3.9.4/dist/full.css"
	rel="stylesheet">
<script src="https://cdn.tailwindcss.com"></script>
</head>
<body
	class="min-h-screen bg-base-100 flex items-center justify-center p-4">
	<div class="max-w-md w-full">
		<div class="card bg-base-200 shadow-xl">
			<div class="card-body text-center">
				<div class="mb-6">
					<!-- Error Icon -->
					<svg class="mx-auto h-16 w-16 text-error" fill="none"
						stroke="currentColor" viewBox="0 0 24 24">
                        <path stroke-linecap="round"
							stroke-linejoin="round" stroke-width="2"
							d="M12 9v2m0 4h.01m-6.938 4h13.856c1.54 0 2.502-1.667 1.732-3L13.732 4c-.77-1.333-2.694-1.333-3.464 0L3.34 16c-.77 1.333.192 3 1.732 3z">
                        </path>
                    </svg>
				</div>

				<h2 class="text-2xl font-bold mb-2">Oops! Something went wrong</h2>
				<p class="text-base-content/70 mb-6">We're experiencing some
					technical difficulties. Our team has been notified and is working
					on the issue.</p>

				<div class="divider"></div>

				<div class="space-y-4">
					<p class="text-sm text-base-content/60">Error Code: 500 -
						Internal Server Error</p>
					<div class="flex flex-col gap-2">
						<a href="${pageContext.request.contextPath}/"
							class="btn btn-primary"> Return to Homepage </a>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>