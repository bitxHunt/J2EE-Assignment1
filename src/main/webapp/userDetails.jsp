<!-- 
	Author: Thiha Swan Htet (p2336671)
	Date: 8/11/24
	Description: ST0510/JAD Week4 Submission
 -->

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="models.user.*"%>
<%@ page import="java.util.ArrayList"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Users</title>
<script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-100">
	<%
	// Users List
	try {
		ArrayList<User> users = (ArrayList<User>) request.getAttribute("users");
	%>

	<div class="h-screen flex items-center justify-center">
		<div class="container mt-5 bg-white rounded-lg shadow-lg p-6">
			<h2 class="text-2xl font-bold text-center mb-4">Users List</h2>
			<table
				class="min-w-full bg-white border border-gray-300 rounded-lg overflow-hidden">
				<thead>
					<tr class="bg-violet-500 text-white">
						<th class="py-3 px-4 text-left">First Name</th>
						<th class="py-3 px-4 text-left">Last Name</th>
					</tr>
				</thead>
				<tbody>
					<%
					if (users != null && !users.isEmpty()) {
						for (User user : users) {
					%>
					<tr class="hover:bg-gray-100 transition duration-200">
						<td class="border-b border-gray-300 py-2 px-4"><%=user.getFirstName()%></td>
						<td class="border-b border-gray-300 py-2 px-4"><%=user.getLastName()%></td>
					</tr>
					<%
					}
					} else {
					%>
					<tr>
						<td colspan="2" class="text-center py-2">No users found</td>
					</tr>
					<%
					}
					%>
				</tbody>
			</table>
		</div>
	</div>

	<%
	} catch (Exception e) {
	String err = (String) request.getParameter("err");
	%>
	<div class="alert alert-danger" role="alert"><%=err%></div>
	<%
	}
	%>
</body>
</html>