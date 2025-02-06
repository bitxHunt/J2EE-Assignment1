<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en" data-theme="dark">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Create Organization - CleanX</title>
<link
	href="https://cdn.jsdelivr.net/npm/daisyui@4.12.14/dist/full.min.css"
	rel="stylesheet" type="text/css" />
<script src="https://cdn.tailwindcss.com"></script>
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@24,400,0,0" />
</head>
<body class="bg-base-100">
	<%@ include file="components/header.jsp"%>

	<div class="min-h-screen flex items-center justify-center px-4 py-8">
		<div class="card w-full max-w-md bg-base-200 shadow-xl">
			<div class="card-body">
				<h2 class="card-title text-2xl font-bold text-center mb-6">Create
					New Organization</h2>

				<%-- Error Message Display --%>
				<%
				String error = (String) request.getAttribute("error");
				if (error != null && !error.isEmpty()) {
				%>
				<div class="alert alert-error mb-4">
					<span class="material-symbols-outlined">error</span> <span><%=error%></span>
				</div>
				<%
				request.removeAttribute("error");
				}
				%>


				<form
					action="${pageContext.request.contextPath}/register-organization"
					method="POST" class="space-y-6">
					<div class="form-control">
						<label class="label"> <span class="label-text">Organization
								Name</span> <span class="label-text-alt text-error">*Required</span>
						</label> <input type="text" name="organizationName"
							placeholder="Enter organization name"
							class="input input-bordered w-full" required minlength="2"
							maxlength="100" value="${param.organizationName}" /> <label
							class="label"> <span
							class="label-text-alt text-base-content/70">2-100
								characters</span>
						</label>
					</div>

					<div class="card-actions justify-end mt-6">
						<a href="${pageContext.request.contextPath}/organizations"
							class="btn btn-ghost"> <span
							class="material-symbols-outlined">arrow_back</span> Cancel
						</a>
						<button type="submit" class="btn btn-primary">
							<span class="material-symbols-outlined">add_business</span>
							Create Organization
						</button>
					</div>
				</form>
			</div>
		</div>
	</div>

	<%@ include file="components/footer.jsp"%>
</body>
</html>