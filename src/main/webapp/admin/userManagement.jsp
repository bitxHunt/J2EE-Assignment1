<%@page import="models.role.Role"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="models.user.User"%>
<%@ page import="java.util.ArrayList"%>
<!DOCTYPE html>
<html data-theme="dark">
<head>
<meta charset="UTF-8">
<title>HR Management</title>
<link href="https://cdn.jsdelivr.net/npm/daisyui@3.9.4/dist/full.css"
	rel="stylesheet">
<script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="min-h-screen bg-base-100">
	<div class="container mx-auto px-4 py-8">
		<%@ include file="./components/adminNavbar.jsp"%>
		<div class="flex justify-between items-center mb-6">
			<h1 class="text-2xl font-bold">User Management</h1>
			<!-- Add User Button -->
			<button class="btn btn-primary"
				onclick="document.getElementById('addUserModal').showModal()">
				<svg xmlns="http://www.w3.org/2000/svg" class="h-6 w-6" fill="none"
					viewBox="0 0 24 24" stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round"
						stroke-width="2" d="M12 4v16m8-8H4" />
                </svg>
			</button>
		</div>

		<!-- User Table -->
		<div class="overflow-x-auto bg-base-200 rounded-lg">
			<table class="table">
				<thead>
					<tr>
						<th>ID</th>
						<th>Name</th>
						<th>Email</th>
						<th>Phone</th>
						<th>Role</th>
						<th>Actions</th>
					</tr>
				</thead>
				<tbody>
					<%
					ArrayList<User> users = (ArrayList<User>) request.getAttribute("users");
					ArrayList<Role> roles = (ArrayList<Role>) request.getAttribute("roles");
					if (users != null) {
						for (User user : users) {
							String roleName = "Unknown";
							for (Role role : roles) {
						if (role.getRoleId() == user.getRole()) {
							roleName = role.getRoleName();
							break;
						}
							}
							boolean isAdmin = "ADMIN".equals(roleName);
					%>
					<tr>
						<td><%=user.getId()%></td>
						<td><%=user.getFirstName() + " " + user.getLastName()%></td>
						<td><%=user.getEmail()%></td>
						<td><%=user.getPhoneNo()%></td>
						<td><span class="badge badge-outline badge-md"> <%=roleName%>
						</span></td>
						<td class="flex gap-2">
							<div class="tooltip"
								data-tip="<%=isAdmin ? "You cannot perform actions against other admins" : ""%>">
								<button type="button" class="btn btn-sm btn-info"
									onclick="openEditModal(<%=user.getId()%>, <%=user.getRole()%>)"
									<%=isAdmin ? "disabled" : ""%>>Edit</button>
							</div>
							<div class="tooltip"
								data-tip="<%=isAdmin ? "You cannot perform actions against other admins" : ""%>">
								<button type="button" class="btn btn-sm btn-error"
									onclick="openDeleteModal(<%=user.getId()%>, <%=user.getRole()%>)"
									<%=isAdmin ? "disabled" : ""%>>Delete</button>
							</div>
						</td>
					</tr>
					<%
					}
					}
					%>
				</tbody>
			</table>
		</div>

		<!-- Pagination -->
		<div class="flex justify-center mt-4">
			<div class="join">
				<%
				int currentPage = (int) request.getAttribute("currentPage");
				int totalPages = (int) request.getAttribute("totalPages");
				for (int i = 1; i <= totalPages; i++) {
				%>
				<a href="?page=<%=i%>"
					class="join-item btn <%=currentPage == i ? "btn-active" : ""%>"><%=i%></a>
				<%
				}
				%>
			</div>
		</div>

		<%
		String userManagementMsg = (String) session.getAttribute("userManagementMsg");
		boolean showToast = userManagementMsg != null && !userManagementMsg.isEmpty();
		boolean isSuccess = userManagementMsg != null && userManagementMsg.toLowerCase().contains("success");
		if (showToast) {
			session.removeAttribute("userManagementMsg");
		}
		%>


		<!-- Show Message Toast -->
		<%
		if (showToast) {
		%>
		<div class="toast toast-top toast-end">
			<div class="alert <%=isSuccess ? "alert-success" : "alert-info"%>">
				<span><%=userManagementMsg%></span>
				<button class="btn btn-sm btn-ghost"
					onclick="this.parentElement.remove()">✕</button>
			</div>
		</div>
		<%
		}
		%>

		<!-- Add User Modal -->
		<dialog id="addUserModal" class="modal">
		<div class="modal-box">
			<h3 class="font-bold text-lg mb-4">Add New User</h3>
			<form action="manage-users" method="POST">
				<input type="hidden" name="action" value="add">
				<div class="form-control gap-4">
					<div class="grid grid-cols-2 gap-4">
						<input type="text" name="firstName" placeholder="First Name"
							class="input input-bordered" required> <input type="text"
							name="lastName" placeholder="Last Name"
							class="input input-bordered" required>
					</div>
					<input type="email" name="email" placeholder="Email"
						class="input input-bordered" required> <input type="tel"
						name="phoneNo" placeholder="Phone Number"
						class="input input-bordered" required> <input
						type="password" name="password" placeholder="Password"
						class="input input-bordered" required> <select name="role"
						class="select select-bordered" required>
						<option value="">Select Role</option>
						<%
						if (roles != null) {
							for (Role role : roles) {
						%>
						<option value="<%=role.getRoleId()%>"><%=role.getRoleName()%></option>
						<%
						}
						}
						%>
					</select>
				</div>
				<div class="modal-action">
					<button type="button" class="btn"
						onclick="document.getElementById('addUserModal').close()">Cancel</button>
					<button type="submit" class="btn btn-primary">Add User</button>
				</div>
			</form>
		</div>
		</dialog>

		<!-- Edit User Modal -->
		<dialog id="editUserModal" class="modal">
		<div class="modal-box">
			<h3 class="font-bold text-lg mb-4">Edit User Role</h3>
			<form action="manage-users" method="POST">
				<input type="hidden" name="action" value="edit"> <input
					type="hidden" name="userId" id="editUserId"> <input
					type="hidden" name="currentRoleId" id="currentRoleId">
				<div class="form-control gap-4">
					<!-- Remove other input fields, keep only role select -->
					<select name="editRoleId" id="editRole"
						class="select select-bordered" required>
						<option value="">Select Role</option>
						<%
						if (roles != null) {
							for (Role role : roles) {
						%>
						<option value="<%=role.getRoleId()%>"><%=role.getRoleName()%></option>
						<%
						}
						}
						%>
					</select>
				</div>
				<div class="modal-action">
					<button type="submit" class="btn btn-primary">Update Role</button>
					<button type="button" class="btn"
						onclick="document.getElementById('editUserModal').close()">Cancel</button>
				</div>
			</form>
		</div>
		</dialog>
		<!-- Delete User Modal -->
		<dialog id="deleteUserModal" class="modal">
		<div class="modal-box">
			<h3 class="font-bold text-lg">Confirm Delete</h3>
			<p class="py-4">Are you sure you want to delete this user? This
				action cannot be undone.</p>
			<form action="manage-users" method="POST">
				<input type="hidden" name="action" value="delete"> <input
					type="hidden" name="userId" id="deleteUserId"> <input
					type="hidden" name="currentRoleId" id="deleteCurrentRoleId">
				<div class="modal-action">
					<button type="submit" class="btn btn-error">Delete</button>
					<button type="button" class="btn"
						onclick="document.getElementById('deleteUserModal').close()">Cancel</button>
				</div>
			</form>
		</div>
		</dialog>
	</div>
	<script>
    function openEditModal(userId, currentRole) {
        document.getElementById('editUserId').value = userId;
        document.getElementById('editRole').value = currentRole;
        document.getElementById('currentRoleId').value = currentRole;
        document.getElementById('editUserModal').showModal();
    }
    
    function openDeleteModal(userId,currentRole) {
        document.getElementById('deleteUserId').value = userId;
        document.getElementById('deleteCurrentRoleId').value = currentRole;
        document.getElementById('deleteUserModal').showModal();
    }
</script>
</body>
</html>