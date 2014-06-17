<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html ng-app="phonecatApp">

<head>
    <title></title>

    <!-- LOAD BOOTSTRAP CSS -->
    <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.2/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="/resources/assets/css/style.css" />

    <!-- LOAD JQUERY -->
    <script src="//ajax.googleapis.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>
    <!-- LOAD ANGULAR -->
    <script src="/resources/assets/js/lib/angular.js" type="text/javascript"></script>
    <script src="/resources/assets/js/lib/angular-resource.js" type="text/javascript"></script>

    <script src="/resources/assets/js/angular/controllers.js" type="text/javascript"></script>

</head>
<body>
<div id="header">
    <img src="/resources/assets/img/logo.png" class="logo-img" />
    <div id="login">
        <input type="text" name="email" placeholder="adres email" required class="login-field" />
        <input type="text" name="password" placeholder="hasło" required class="login-field" />
        <a href="#">
            <div class="login-button">
                wejdź
            </div>
        </a>
    </div>
    <nav>
        <ul id="top-menu">
            <a href="homepage.html">
                <li class="nav-current-page">
                    <img src="/resources/assets/img/home.png" />
                </li>
            </a>
            <a href="applyforloan.html">
                <li class="apply-for-loan">
                    Weź Pożyczkę
                </li>
            </a>
            <a href="accountservices.html">
                <li class="account-services">
                    Operacje na koncie
                </li>
            </a>
        </ul>
    </nav>
</div>

<div class="panel panel-default bs-example">
    <!-- Default panel contents -->
    <p style="margin: 0 0 200px;">
        <div class="panel-heading"><h3>W celu przeprowadzenia operacji na koncie podaj swoje imię i nazwisko.</h3></div>
        <div class="panel-body">
        <div>
            <p><input type="text" name="firstName" placeholder="imię" required class="login-field" /></p>
            <p><input type="text" name="lastName" placeholder="nazwisko" required class="login-field" /></p>
            <p><a class="btn btn-primary btn-lg" role="button" ng-click="getClientData()" > Wyświetl informacje.</a></p>
        </div>
        </div>
    </p>
    <!-- Table -->
    <table class="table">
        ...
    </table>
</div>


</body>
<%--<body ng-controller="MainCtrl">

User name: {{user.firstName}}


</body>--%>
</html>
