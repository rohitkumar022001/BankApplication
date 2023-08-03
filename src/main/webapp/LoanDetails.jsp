<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<h1>
Hii <%
session = request.getSession();
out.println(session.getAttribute("cust_name"));
%>
<br>
Here Are the Details : 
</h1>

<br><br>

<h3>
<%
session = request.getSession();
out.println("Id : "+session.getAttribute("Lid"));
%>
<br>
<%
session = request.getSession();
out.println("Loan type : "+session.getAttribute("Ltype"));
%>
<br>
<%
session = request.getSession();
out.println("Tenure : "+session.getAttribute("Tenure"));
%>
<br>
<%
session = request.getSession();
out.println("Interest : "+session.getAttribute("Interest"));
%>
<br>
<%
session = request.getSession();
out.println("Description : "+session.getAttribute("Description"));
%></h3>
<br><br>
<a href="HomePage.jsp">Click to Redirect</a>
<br>
<h1 align="center">Thank You.</h1>

</body>
</html>