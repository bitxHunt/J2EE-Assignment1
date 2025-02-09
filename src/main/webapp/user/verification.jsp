<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en" data-theme="dark">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Email Verification - CleanX</title>
<link
	href="https://cdn.jsdelivr.net/npm/daisyui@4.12.14/dist/full.min.css"
	rel="stylesheet" type="text/css" />
<script src="https://cdn.tailwindcss.com"></script>
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@24,400,0,0" />
</head>
<body class="bg-base-300 min-h-screen">
	<%@ include file="../../public/components/header.jsp"%>

	<div class="min-h-screen flex flex-col items-center justify-center p-4">
		<div class="card w-full max-w-md bg-base-100 shadow-xl">
			<div class="card-body">
				<h2 class="card-title text-2xl mb-2">Email Verification</h2>

				<%-- Display error message if any --%>
				<%
				String errorMessage = (String) request.getAttribute("errorMessage");
				if (errorMessage != null && !errorMessage.isEmpty()) {
				%>
				<div class="alert alert-error mb-4">
					<span class="material-symbols-outlined">error</span> <span><%=errorMessage%></span>
				</div>
				<%
				}
				%>

				<%-- Display success message if any --%>
				<%
				String successMessage = (String) request.getAttribute("successMessage");
				if (successMessage != null && !successMessage.isEmpty()) {
				%>
				<div class="alert alert-success mb-4">
					<span class="material-symbols-outlined">check_circle</span> <span><%=successMessage%></span>
				</div>
				<%
				}
				%>

				<p class="text-base-content/70 mb-6">Haven't received your
					verification email? Enter your email address below and we'll send
					you a new verification link.</p>

				<form action="${pageContext.request.contextPath}/resend"
					method="POST" class="space-y-4">
					<div class="form-control">
						<label class="label"> <span class="label-text">Email
								Address</span>
						</label> <input type="email" name="email" placeholder="Enter your email"
							class="input input-bordered" required />
					</div>

					<div class="form-control mt-6">
						<button type="submit" class="btn btn-primary gap-2">
							<span class="material-symbols-outlined">send</span> Resend
							Verification Email
						</button>
					</div>
				</form>

				<div class="divider">or</div>

				<div class="text-center">
					<p class="text-base-content/70 mb-2">Already have a
						verification link?</p>
					<a href="${pageContext.request.contextPath}/login"
						class="link link-primary"> Return to Login </a>
				</div>
			</div>
		</div>
	</div>

	<%@ include file="../../public/components/footer.jsp"%>
</body>
</html>