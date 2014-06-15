<%--
  Created by IntelliJ IDEA.
  User: maciek
  Date: 01.06.14
  Time: 11:54
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html; charset=ISO-8859-1"%>

<html>
<head>
    <title></title>
<%--    <link href="<c:url value = '/resources/assets/css/stylesheet2.css' />"  rel="stylesheet" type="text/css">
    <link href="<c:url value = '/resources/assets/css/fonts/fonts.css' />" rel="stylesheet">--%>
    <script type="text/javascript" src="<c:url value = '/resources/assets/js/lib/require.js' />"></script>
    <script type="text/javascript" src="<c:url value = '/resources/assets/js/lib/jquery.js' />"></script>
    <script type="text/javascript" src="<c:url value = '/resources/assets/bootstrap/js/bootstrap.min.js' />"></script>
    <link href="<c:url value = '/resources/assets/bootstrap/css/bootstrap.min.css' />" rel="stylesheet">

</head>
<body>
<h1> OTO TEKST JSP@</h1>

<form action="/applyForLoan" method="POST">
    First name:
    <input type="text" name="firstName" />
    <br>
    Last name:
    <input type="text" name="lastName" />
    Loan amount:
    <input type="number" name="applyingAmount" />

    <input type="submit" value="submit" />
</form>


</body>
</html>