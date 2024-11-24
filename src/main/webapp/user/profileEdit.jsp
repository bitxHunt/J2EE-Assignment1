<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="models.user.User"%>
<%@ page import="models.address.Address"%>

<!DOCTYPE html>
<html lang="en" data-theme="dark">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>Edit Profile - CleanX</title>
<link
	href="https://cdn.jsdelivr.net/npm/daisyui@4.12.14/dist/full.min.css"
	rel="stylesheet" type="text/css" />
<script src="https://cdn.tailwindcss.com"></script>
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@24,400,0,0" />
</head>
<body class="bg-base-300 min-h-screen">

	<%
	User user = (User) request.getAttribute("user");
	Address homeAddress = (Address) request.getAttribute("homeAddress");
	Address officeAddress = (Address) request.getAttribute("officeAddress");
	
	%>

	<!-- Navbar -->
	<div class="navbar bg-base-100 shadow-xl">
		<div class="flex-1">
			<a href="${pageContext.request.contextPath}/"
				class="btn btn-ghost text-xl">CleanX</a>
		</div>
		<div class="flex-none">
			<div class="dropdown dropdown-end">
				<div tabindex="0" role="button"
					class="btn btn-ghost btn-circle avatar">
					<div
						class="w-10 rounded-full ring ring-primary ring-offset-base-100 ring-offset-2">
						<img alt="Profile"
							src="<%=user.getImageURL()%>" />
					</div>
				</div>
				<ul
					class="mt-3 z-[1] p-2 shadow menu menu-sm dropdown-content bg-base-100 rounded-box w-52">
					<li><a href="${pageContext.request.contextPath}/profile">Profile</a></li>
					<li><a href="${pageContext.request.contextPath}/logout">Logout</a></li>
				</ul>
			</div>
		</div>
	</div>

	<div class="container mx-auto p-6 max-w-4xl">
		<div class="flex items-center gap-4 mb-8">
			<a href="${pageContext.request.contextPath}/profile"
				class="btn btn-circle btn-ghost"> <span
				class="material-symbols-outlined">arrow_back</span>
			</a>
			<h1 class="text-3xl font-bold">Edit Profile</h1>
		</div>

		<form action="${pageContext.request.contextPath}/profile/edit"
			method="POST" enctype="multipart/form-data">
			<input type="hidden" name="homeAddressId" value="<%=homeAddress != null ? homeAddress.getId() : ""%>">
			<input type="hidden" name="officeAddressId" value="<%=officeAddress != null ? officeAddress.getId() : ""%>">
			<div class="grid grid-cols-1 gap-6">
				<!-- Personal Information Card -->
				<div class="card bg-base-100 shadow-xl">
					<div class="card-body">
						<h2 class="card-title mb-4">Personal Information</h2>

						<!-- Profile Image -->
						<div class="flex flex-col items-center gap-4 mb-6">
							<div class="avatar">
								<div
									class="w-32 rounded-full ring ring-primary ring-offset-base-100 ring-offset-2">
									<img src="<%=user.getImageURL()%>"
										alt="Profile" />
								</div>
							</div>
							<div class="form-control w-full max-w-xs">
								<label class="label"> <span class="label-text">Change
										Profile Picture</span>
								</label> <input type="file" name="profileImage" accept="image/*"
									class="file-input file-input-bordered w-full max-w-xs" />
							</div>
						</div>

						<!-- Name Fields -->
						<div class="grid grid-cols-1 md:grid-cols-2 gap-4">
							<div class="form-control">
								<label class="label"> <span class="label-text">First
										Name</span>
								</label> <input type="text" name="firstName"
									value="<%=user.getFirstName()%>" class="input input-bordered"
									required />
							</div>
							<div class="form-control">
								<label class="label"> <span class="label-text">Last
										Name</span>
								</label> <input type="text" name="lastName"
									value="<%=user.getLastName()%>" class="input input-bordered"
									required />
							</div>
						</div>

						<!-- Contact Information -->
						<div class="grid grid-cols-1 md:grid-cols-2 gap-4 mt-4">
							<div class="form-control">
								<label class="label"> <span class="label-text">Phone
										Number</span>
								</label> <input type="tel" name="phoneNo" value="<%=user.getPhoneNo()%>"
									class="input input-bordered" required />
							</div>
							<div class="form-control">
								<label class="label"> <span class="label-text">Email</span>
								</label> <input type="email" value="<%=user.getEmail()%>"
									class="input input-bordered" disabled /> <label class="label">
									<span class="label-text-alt text-base-content/60">Email
										cannot be changed</span>
								</label>
							</div>
						</div>
					</div>
				</div>

				<!-- Addresses Card -->
				<div class="card bg-base-100 shadow-xl">
					<div class="card-body">
						<div class="flex justify-between items-center mb-4">
							<h2 class="card-title">My Addresses</h2>
							<%
							boolean canAddAddress = (homeAddress == null || officeAddress == null);
							if (canAddAddress) {
							%>
							<label for="add-address-modal"
								class="btn btn-primary btn-sm gap-2"> <span
								class="material-symbols-outlined">add</span> Add Address
							</label>
							<%
							}
							%>
						</div>

						<!-- Home Address -->
						<%
						if (homeAddress != null) {
						%>
						<div class="bg-base-200 rounded-xl p-6 mb-4">
							<div class="flex items-center justify-between mb-4">
								<div class="flex items-center gap-4">
									<div class="p-3 bg-primary/10 rounded-full">
										<span class="material-symbols-outlined text-primary text-2xl">home</span>
									</div>
									<h3 class="font-bold text-lg">Home Address</h3>
								</div>
								<form action="${pageContext.request.contextPath}/address/delete"
									method="POST" class="inline">
									<input type="hidden" name="homeAddressId"
										value="<%=homeAddress.getId()%>">
									<button type="submit"
										class="btn btn-ghost btn-sm btn-circle text-error">
										<span class="material-symbols-outlined">delete</span>
									</button>
								</form>
							</div>
							<div class="grid grid-cols-1 md:grid-cols-2 gap-4">
								<div class="form-control">
									<label class="label"> <span class="label-text">Address</span>
									</label> <input type="text" name="homeAddress"
										value="<%=homeAddress.getAddress()%>"
										class="input input-bordered" required />
								</div>
								<div class="form-control">
									<label class="label"> <span class="label-text">Unit	
											Number</span>
									</label> <input type="text" name="homeUnit"
										value="<%=homeAddress.getUnit()%>"
										class="input input-bordered" required />
								</div>
								<div class="form-control">
									<label class="label"> <span class="label-text">Postal
											Code</span>
									</label> <input type="text" name="homePostal"
										value="<%=homeAddress.getPostalCode()%>"
										class="input input-bordered" required pattern="[0-9]{6}" />
								</div>
							</div>
						</div>
						<%
						}
						%>

						<!-- Office Address -->
						<%
						if (officeAddress != null) {
						%>
						<div class="bg-base-200 rounded-xl p-6">
							<div class="flex items-center justify-between mb-4">
								<div class="flex items-center gap-4">
									<div class="p-3 bg-secondary/10 rounded-full">
										<span
											class="material-symbols-outlined text-secondary text-2xl">apartment</span>
									</div>
									<h3 class="font-bold text-lg">Office Address</h3>
								</div>
								<form action="${pageContext.request.contextPath}/address/delete"
									method="POST" class="inline">
									<input type="hidden" name="officeAddressId"
										value="<%=officeAddress.getId()%>">
									<button type="submit"
										class="btn btn-ghost btn-sm btn-circle text-error">
										<span class="material-symbols-outlined">delete</span>
									</button>
								</form>
							</div>
							<div class="grid grid-cols-1 md:grid-cols-2 gap-4">
								<div class="form-control">
									<label class="label"> <span class="label-text">Address</span>
									</label> <input type="text" name="officeAddress"
										value="<%=officeAddress.getAddress()%>"
										class="input input-bordered" required />
								</div>
								<div class="form-control">
									<label class="label"> <span class="label-text">Unit
											Number</span>
									</label> <input type="text" name="officeUnit"
										value="<%=officeAddress.getUnit()%>"
										class="input input-bordered" required />
								</div>
								<div class="form-control">
									<label class="label"> <span class="label-text">Postal
											Code</span>
									</label> <input type="text" name="officePostal"
										value="<%=officeAddress.getPostalCode()%>"
										class="input input-bordered" required pattern="[0-9]{6}" />
								</div>
							</div>
						</div>
						<%
						}
						%>
					</div>
				</div>

				<!-- Save Button -->
				<div class="flex justify-end gap-4">
					<a href="${pageContext.request.contextPath}/profile/edit"
						class="btn btn-ghost">Cancel</a>
					<button type="submit" class="btn btn-primary gap-2">
						<span class="material-symbols-outlined">save</span> Save Changes
					</button>
				</div>
			</div>
		</form>

		<!-- Add Address Modal using DaisyUI -->
		<input type="checkbox" id="add-address-modal" class="modal-toggle" />
		<div class="modal" role="dialog">
			<div class="modal-box">
				<h3 class="font-bold text-lg mb-4">Add New Address</h3>
				<form action="${pageContext.request.contextPath}/address/add"
					method="POST">
					<div class="form-control mb-4">
						<label class="label"> <span class="label-text">Address
								Type</span>
						</label> <select name="addressType" class="select select-bordered"
							required>
							<option value="" disabled selected>Select address type</option>
							<%
							if (homeAddress == null) {
							%>
							<option value="HOME">Home</option>
							<%
							}
							if (officeAddress == null) {
							%>
							<option value="OFFICE">Office</option>
							<%
							}
							%>
						</select>
					</div>
					<div class="form-control">
						<label class="label"> <span class="label-text">Address</span>
						</label> <input type="text" name="address" class="input input-bordered"
							required />
					</div>
					<div class="form-control">
						<label class="label"> <span class="label-text">Unit
								Number</span>
						</label> <input type="text" name="unit" class="input input-bordered"
							required />
					</div>
					<div class="form-control">
						<label class="label"> <span class="label-text">Postal
								Code</span>
						</label> <input type="text" name="postalCode" class="input input-bordered"
							required pattern="[0-9]{6}" />
					</div>
					<div class="modal-action">
						<label for="add-address-modal" class="btn">Cancel</label>
						<button type="submit" class="btn btn-primary">Add Address</button>
					</div>
				</form>
			</div>
			<label class="modal-backdrop" for="add-address-modal">Close</label>
		</div>
	</div>
</body>
</html>