<%-- 
    Name: Soe Zaw Aung, Scott
    Class: DIT/FT/2B/01
    Admin No: p2340474
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Service Page</title>
</head>
<body>
	<!-- Header -->
		<%
	Integer user = (Integer) session.getAttribute("userId");
	if (user != null) {
	%>
	<%@ include file="../user/components/header.jsp"%>
	<%
	} else {
	%>
	<%@ include file="components/header.jsp"%>
	<%
	}
	%>
<<<<<<< HEAD
	<%-- <%@ include file="./components/displayBundles.jsp"%> --%>
=======
	<%@ include file="./components/displayBundles.jsp"%>
	
>>>>>>> a6963b03eb2831ce3bebb0136e6af89cc8fc740b
	<%@ include file="./components/displayServicesByCategory.jsp"%>
</body>
</html>