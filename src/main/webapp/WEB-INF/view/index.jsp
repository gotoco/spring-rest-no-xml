
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
    Address:
    <input type="text" name="address" />
    <br>
    Loan amount:
    <input type="number" name="applyingAmount" />
    <br>
    Na ile dni?:
    <input type="number" name="maturityInDays" />
    <br>
    <input type="submit" value="Apply!" />
</form>

<h1>Przedłuż porzyczkę!</h1>

<form action="/postForExtendLoan" method="POST">
    ClientId:
    <input type="text" name="userId" />
    <br>
    LoanId:
    <input type="text" name="loanId" />
    <br>
    New Expiration date
    <input type="date" name="newExpirationDate">
    <br>
    <input type="submit" value="submit" />
</form>

</body>
</html>
