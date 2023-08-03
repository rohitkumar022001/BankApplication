<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<h1 align="center"><% session = request.getSession();

out.println("Transaction Amount of : "+session.getAttribute("amount"));

%> Failed <br>

With Transaction Error :

<%

session = request.getSession();

out.println(session.getAttribute("error"));

%>

</h1>

<br><br>

<a href="HomePage.jsp">Redirect</a>
</body>
</html>