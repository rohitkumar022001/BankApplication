<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<h1 align="centre">Welcome to the bank</h1>
<%
session=request.getSession();
String s1=(String)session.getAttribute("cust_name");
out.print(s1+" Welcome to your Account");

%>
<br>
<br>
<a href="CheckBalance">1.check balance</a>
<br>
<br>
<a href="Loan.jsp">2.Apply Loan</a>
<br>
<br>
<a href="Transfer.html">3.Transfer</a>
<br>
<br>
<a href="Logout">4.Log Out</a>
<br>
<br>
<a href="transaction_history.jsp">5.transaction_history</a>
</body>
</html>