<%--
  Created by IntelliJ IDEA.
  User: maciek
  Date: 10.06.14
  Time: 18:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script type="text/javascript" src="<c:url value = '/resources/assets/js/lib/require.js' />"></script>
    <script type="text/javascript" src="<c:url value = '/resources/assets/js/lib/jquery-1.11.1.min.js' />"></script>
    <script type="text/javascript" src="<c:url value = '/resources/assets/bootstrap/js/bootstrap.min.js' />"></script>
    <link href="<c:url value = '/resources/assets/bootstrap/css/bootstrap.min.css' />" rel="stylesheet">

    <title></title>
</head>
<body>

<h1>Wez pożyczkę!</h1>

<form action="/applyForLoan" method="POST">
    First name:
    <input type="text" name="firstName" />
    <br>
    Last name:
    <input type="text" name="lastName" />
    <br>
    Loan amount:
    <input type="number" name="applyingAmount" />
    <br>
    <input type="submit" value="submit" />
</form>

</body>
</html>
