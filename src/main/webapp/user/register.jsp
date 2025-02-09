<%-- 
    Name: Thiha Swan Htet
    Class: DIT/FT/2B/01
    Admin No: p2336671
--%>

<!DOCTYPE html>
<html lang="en" class="dark">
<head>
<meta charset="UTF-8" />
<title>Registration | Clean X</title>
<link
	href="https://cdn.jsdelivr.net/npm/daisyui@4.12.14/dist/full.min.css"
	rel="stylesheet" type="text/css" />
<script src="https://cdn.tailwindcss.com"></script>
</head>

<body
	class="bg-gray-900 min-h-screen flex items-center justify-center p-4 md:p-0">
	<div class="card w-full max-w-3xl bg-gray-800 text-gray-100 shadow-xl">
		<div class="card-body">
			<!-- Logo and Header -->
			<div class="text-center mb-8">
				<div class="flex justify-center mb-4">
					<svg class="w-16 h-16 text-blue-500" viewBox="0 0 24 24"
						fill="none" xmlns="http://www.w3.org/2000/svg">
                        <path
							d="M21 12C21 16.9706 16.9706 21 12 21C7.02944 21 3 16.9706 3 12C3 7.02944 7.02944 3 12 3C16.9706 3 21 7.02944 21 12Z"
							stroke="currentColor" stroke-width="2" />
                        <path d="M12 7V12L15 15" stroke="currentColor"
							stroke-width="2" stroke-linecap="round" />
                    </svg>
				</div>
				<h1 class="text-3xl font-bold">
					Join Clean <span class="text-blue-500">X</span>
				</h1>
				<p class="text-gray-400 mt-2">Create your account in just a few
					steps</p>
			</div>

			<form action="${pageContext.request.contextPath}/register"
				method="post" class="space-y-8">
				<!-- Personal Information Section -->
				<div class="space-y-4">
					<h2
						class="text-xl font-semibold text-gray-300 flex items-center gap-2">
						<svg class="w-5 h-5 text-blue-500" fill="none"
							stroke="currentColor" viewBox="0 0 24 24">
                            <path stroke-linecap="round"
								stroke-linejoin="round" stroke-width="2"
								d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z" />
                        </svg>
						Personal Information
					</h2>

					<div class="grid grid-cols-1 md:grid-cols-2 gap-4">
						<div class="form-control">
							<input type="text" name="firstName" placeholder="First Name"
								class="input input-bordered bg-gray-700 border-gray-600 text-gray-100 placeholder-gray-400 focus:border-blue-500 focus:ring-blue-500"
								required />
						</div>
						<div class="form-control">
							<input type="text" name="lastName" placeholder="Last Name"
								class="input input-bordered bg-gray-700 border-gray-600 text-gray-100 placeholder-gray-400 focus:border-blue-500 focus:ring-blue-500"
								required />
						</div>
					</div>

					<div class="form-control">
						<input type="email" name="email" placeholder="Email"
							class="input input-bordered bg-gray-700 border-gray-600 text-gray-100 placeholder-gray-400 focus:border-blue-500 focus:ring-blue-500"
							required />
					</div>

					<div class="form-control">
						<input type="password" name="password" placeholder="Password"
							class="input input-bordered bg-gray-700 border-gray-600 text-gray-100 placeholder-gray-400 focus:border-blue-500 focus:ring-blue-500"
							required />
					</div>

					<div class="form-control">
						<input type="tel" name="phoneNo" placeholder="Phone Number"
							class="input input-bordered bg-gray-700 border-gray-600 text-gray-100 placeholder-gray-400 focus:border-blue-500 focus:ring-blue-500"
							required />
					</div>
				</div>

				<!-- Address Section -->
				<div class="space-y-4">
					<h2
						class="text-xl font-semibold text-gray-300 flex items-center gap-2">
						<svg class="w-5 h-5 text-blue-500" fill="none"
							stroke="currentColor" viewBox="0 0 24 24">
                            <path stroke-linecap="round"
								stroke-linejoin="round" stroke-width="2"
								d="M3 12l2-2m0 0l7-7 7 7M5 10v10a1 1 0 001 1h3m10-11l2 2m-2-2v10a1 1 0 01-1 1h-3m-6 0a1 1 0 001-1v-4a1 1 0 011-1h2a1 1 0 011 1v4a1 1 0 001 1m-6 0h6" />
                        </svg>
						Home Address
					</h2>

					<input type="hidden" name="addressTypeId" value="1" />

					<div class="form-control">
						<input type="text" name="street" placeholder="Street Address"
							class="input input-bordered bg-gray-700 border-gray-600 text-gray-100 placeholder-gray-400 focus:border-blue-500 focus:ring-blue-500"
							required />
					</div>

					<div class="grid grid-cols-1 md:grid-cols-2 gap-4">
						<div class="form-control">
							<input type="text" name="unit" placeholder="Unit Number"
								class="input input-bordered bg-gray-700 border-gray-600 text-gray-100 placeholder-gray-400 focus:border-blue-500 focus:ring-blue-500"
								required />
						</div>
						<div class="form-control">
							<input type="number" name="postalCode" placeholder="Postal Code"
								class="input input-bordered bg-gray-700 border-gray-600 text-gray-100 placeholder-gray-400 focus:border-blue-500 focus:ring-blue-500"
								required />
						</div>
					</div>
				</div>

				<!-- Action Buttons -->
				<div class="space-y-4 pt-4">
					<button type="submit" class="btn btn-primary w-full text-white">Create
						Account</button>

					<div class="text-center space-y-2">
						<a href="${pageContext.request.contextPath}/"
							class="text-sm text-gray-400 hover:text-blue-500 transition-colors">
							Back to Home </a>
						<p class="text-gray-400">
							Already have an account? <a
								href="${pageContext.request.contextPath}/login"
								class="text-blue-500 hover:text-blue-400 font-medium"> Sign
								in </a>
						</p>
					</div>
				</div>
			</form>

			<!-- Error Toast -->
			<%
			String errMsg = (String) session.getAttribute("errMsg");
			if (errMsg != null) {
			%>
			<div class="toast toast-top toast-end">
				<div class="alert alert-error">
					<span><%=errMsg%></span>
				</div>
			</div>
			<%
			session.removeAttribute("errMsg");
			%>
			<%
			}
			%>
		</div>
	</div>
</body>
</html>