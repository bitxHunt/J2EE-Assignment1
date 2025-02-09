<%-- 
    Name: Soe Zaw Aung, Scott
    Class: DIT/FT/2B/01
    Admin No: p2340474
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="models.organization.Organization"%>

<!DOCTYPE html>
<html lang="en" data-theme="dark">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>My Organizations - CleanX</title>
<link
	href="https://cdn.jsdelivr.net/npm/daisyui@4.12.14/dist/full.min.css"
	rel="stylesheet" type="text/css" />
<script src="https://cdn.tailwindcss.com"></script>
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@24,400,0,0" />
</head>
<body class="bg-base-100">
	<%@ include file="components/header.jsp"%>

	<div class="container mx-auto px-4 py-8">
		<div class="flex justify-between items-center mb-8">
			<h1 class="text-3xl font-bold">My Organizations</h1>
			<a href="${pageContext.request.contextPath}/register-organization"
				class="btn btn-primary"> <span class="material-symbols-outlined">add_business</span>
				Create Organization
			</a>
		</div>

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

		<%-- Success Message Display --%>
		<%
		String success = (String) session.getAttribute("successMessage");
		if (success != null && !success.isEmpty()) {
		%>
		<div class="alert alert-success mb-4">
			<span class="material-symbols-outlined">check_circle</span> <span><%=success%></span>
		</div>
		<%
		session.removeAttribute("successMessage");
		}
		%>

		<%
		ArrayList<Organization> organizations = (ArrayList<Organization>) request.getAttribute("organizations");
		if (organizations != null && !organizations.isEmpty()) {
		%>
		<div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
			<%
			for (Organization org : organizations) {
			%>
			<div class="card bg-base-200 shadow-xl">
				<div class="card-body">
					<h2 class="card-title flex justify-between items-center">
						<%=org.getName()%>
						<div class="badge badge-primary">Active</div>
					</h2>

					<div class="space-y-4 mt-4">
						<div>
							<label class="text-sm font-semibold mb-1 block">Access
								Key</label>
							<div class="flex items-center gap-2">
								<input type="text" value="<%=org.getAccessKey()%>"
									class="input input-bordered w-full" readonly />
								<button
									onclick="copyToClipboard(this, '<%=org.getAccessKey()%>')"
									class="btn btn-square btn-ghost">
									<span class="material-symbols-outlined">content_copy</span>
								</button>
							</div>
						</div>

						<div>
							<label class="text-sm font-semibold mb-1 block">Secret
								Key</label>
							<div class="flex items-center gap-2">
								<div class="relative flex-1">
									<input type="password" value="<%=org.getSecretKey()%>"
										class="input input-bordered w-full" readonly
										id="secretKey<%=org.getId()%>" />
									<button onclick="toggleSecretKey('<%=org.getId()%>')"
										class="absolute right-2 top-1/2 -translate-y-1/2 btn btn-ghost btn-sm">
										<span class="material-symbols-outlined">visibility</span>
									</button>
								</div>
								<button
									onclick="copyToClipboard(this, '<%=org.getSecretKey()%>')"
									class="btn btn-square btn-ghost">
									<span class="material-symbols-outlined">content_copy</span>
								</button>
							</div>
						</div>
					</div>
				</div>
			</div>
			<%
			}
			%>
		</div>
		<%
		} else {
		%>
		<div class="text-center py-16">
			<span
				class="material-symbols-outlined text-6xl text-base-content/50 mb-4">business</span>
			<h3 class="text-xl font-semibold mb-2">No Organizations Yet</h3>
			<p class="text-base-content/70 mb-6">Create your first
				organization to get started.</p>
			<a href="${pageContext.request.contextPath}/register-organization"
				class="btn btn-primary"> <span class="material-symbols-outlined">add_business</span>
				Create Organization
			</a>
		</div>
		<%
		}
		%>
	</div>

	<%@ include file="components/footer.jsp"%>

	<script>
        function toggleSecretKey(orgId) {
            const input = document.getElementById('secretKey' + orgId);
            const button = input.nextElementSibling.querySelector('.material-symbols-outlined');
            
            if (input.type === 'password') {
                input.type = 'text';
                button.textContent = 'visibility_off';
            } else {
                input.type = 'password';
                button.textContent = 'visibility';
            }
        }

        function copyToClipboard(button, text) {
            navigator.clipboard.writeText(text).then(() => {
                // Visual feedback
                const originalIcon = button.innerHTML;
                button.innerHTML = '<span class="material-symbols-outlined">check</span>';
                setTimeout(() => {
                    button.innerHTML = originalIcon;
                }, 2000);
            });
        }
    </script>
</body>
</html>