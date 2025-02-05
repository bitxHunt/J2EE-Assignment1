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
	<%-- <%@ include file="./components/displayBundles.jsp"%> --%>
	<%@ include file="./components/displayServicesByCategory.jsp"%>
</body>
</html>