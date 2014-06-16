<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html ng-app="phonecatApp">

<head>
    <title></title>

    <script src="/resources/assets/js/lib/angular.js" type="text/javascript"></script>
    <script src="/resources/assets/js/lib/angular-resource.js" type="text/javascript"></script>
<%--    <script src="/resources/assets/js/angular/app.js" type="text/javascript"></script>--%>
    <script src="/resources/assets/js/angular/maincontroller.js" type="text/javascript"></script>

</head>

<%--<body ng-controller="PhoneListCtrl">

<ul>
    <li ng-repeat="phone in phones">
        {{phone.name}}
        <p>{{phone.snippet}}</p>
    </li>
</ul>

</body>--%>

<body ng-controller="MainCtrl">

User name: {{user.firstName}}


</body>
</html>
