<%-- 
    Name: Thiha Swan Htet
    Class: DIT/FT/2B/01
    Admin No: p2336671
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="models.user.User"%>
<%@ page import="models.address.Address"%>
<%@ page import="java.util.ArrayList"%>

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
	ArrayList<Address> addresses = (ArrayList<Address>) request.getAttribute("addresses");
	%>

	<!-- Navbar -->
	<%
	Integer userId = (Integer) session.getAttribute("userId");
	if (userId != null) {
	%>
	<%@ include file="./components/header.jsp"%>
	<%
	} else {
	%>
	<%@ include file="../../public/components/header.jsp"%>
	<%
	}
	%>

	<div class="container mx-auto p-6 max-w-4xl space-y-6">
		<!-- Back Navigation -->
		<div class="flex items-center gap-4">
			<a href="${pageContext.request.contextPath}/profile"
				class="btn btn-circle btn-ghost"> <span
				class="material-symbols-outlined">arrow_back</span>
			</a>
			<h1 class="text-3xl font-bold">Edit Profile</h1>
		</div>

		<!-- Personal Information Form -->
		<div class="card bg-base-100 shadow-xl">
			<div class="card-body">
				<h2 class="card-title text-2xl mb-6">Personal Information</h2>
				<form action="${pageContext.request.contextPath}/profile/edit"
					method="POST" enctype="multipart/form-data">
					<!-- Profile Picture -->
					<div class="flex flex-col items-center gap-4 mb-8">
						<div class="avatar">
							<div
								class="w-32 rounded-full ring ring-primary ring-offset-base-100 ring-offset-2">
								<img src="<%=user.getImageURL()%>" alt="Profile" />
							</div>
						</div>
						<div class="form-control w-full max-w-xs">
							<label class="label"> <span class="label-text">Update
									Profile Picture</span>
							</label> <input type="file" name="profileImage" accept="image/*"
								class="file-input file-input-bordered w-full" />
						</div>
					</div>

					<!-- User Details -->
					<div class="grid grid-cols-1 md:grid-cols-2 gap-6">
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
								<span class="label-text-alt text-error">Email cannot be
									changed</span>
							</label>
						</div>
					</div>

					<div class="flex justify-end mt-6">
						<button type="submit" class="btn btn-primary gap-2">
							<span class="material-symbols-outlined">save</span> Update
							Profile
						</button>
					</div>
				</form>
			</div>
		</div>

		<!-- Addresses Section -->
		<h2 class="text-2xl font-bold mt-8 mb-4">My Addresses</h2>

		<%
		if (addresses != null) {
			for (Address address : addresses) {
				boolean isHome = address.getAddType().getId() == 1;
		%>
		<div class="card bg-base-100 shadow-xl mb-6">
			<div class="card-body">
				<div class="flex items-center justify-between mb-6">
					<div class="flex items-center gap-4">
						<div
							class="p-3 <%=isHome ? "bg-primary/10" : "bg-secondary/10"%> rounded-full">
							<span
								class="material-symbols-outlined <%=isHome ? "text-primary" : "text-secondary"%> text-2xl">
								<%=isHome ? "home" : "apartment"%>
							</span>
						</div>
						<h3 class="text-xl font-bold"><%=isHome ? "Home" : "Office"%>
							Address
						</h3>
					</div>
					<div class="flex gap-2">
						<form action="${pageContext.request.contextPath}/address/delete"
							method="POST" class="inline"
							onsubmit="return confirm('Are you sure you want to delete this address?')">
							<input type="hidden" name="addressId"
								value="<%=address.getId()%>">
							<button type="submit" class="btn btn-error btn-sm gap-2">
								<span class="material-symbols-outlined">delete</span> Delete
							</button>
						</form>
					</div>
				</div>

				<form action="${pageContext.request.contextPath}/address/edit"
					method="POST">
					<input type="hidden" name="addressId" value="<%=address.getId()%>">
					<input type="hidden" name="addressType"
						value="<%=isHome ? "HOME" : "OFFICE"%>">

					<div class="grid grid-cols-1 md:grid-cols-2 gap-6">
						<div class="form-control">
							<label class="label"><span class="label-text">Address</span></label>
							<input type="text" name="address"
								value="<%=address.getAddress()%>" class="input input-bordered"
								required />
						</div>

						<div class="form-control">
							<label class="label"><span class="label-text">Unit
									Number</span></label> <input type="text" name="unit"
								value="<%=address.getUnit()%>" class="input input-bordered"
								required />
						</div>

						<div class="form-control">
							<label class="label"><span class="label-text">Postal
									Code</span></label> <input type="text" name="postalCode"
								value="<%=address.getPostalCode()%>"
								class="input input-bordered" required pattern="[0-9]{6}" />
						</div>
					</div>

					<div class="flex justify-end mt-6">
						<button type="submit" class="btn btn-primary gap-2">
							<span class="material-symbols-outlined">save</span> Save Changes
						</button>
					</div>
				</form>
			</div>
		</div>
		<%
		}
		}
		%>

		<!-- Add New Address Section -->
		<%
		if (addresses == null || addresses.size() < 2) {
			// Determine which type of address to add
			String addressTypeToAdd = "HOME";
			if (addresses != null && addresses.size() == 1) {
				addressTypeToAdd = addresses.get(0).getAddType().getId() == 1 ? "OFFICE" : "HOME";
			}
		%>
		<div class="card bg-base-100 shadow-xl text-center">
			<div class="card-body">
				<h3 class="text-xl font-bold mb-4">Add New Address</h3>
				<a
					href="${pageContext.request.contextPath}/address/create?type=<%=addressTypeToAdd%>"
					class="btn btn-primary gap-2 inline-flex items-center justify-center">
					<span class="material-symbols-outlined">add</span> Add <%=addressTypeToAdd.equals("HOME") ? "Home" : "Office"%>
					Address
				</a>
			</div>
		</div>
		<%
		}
		%>
	</div>

	<%@ include file="components/footer.jsp"%>
</body>
</html>