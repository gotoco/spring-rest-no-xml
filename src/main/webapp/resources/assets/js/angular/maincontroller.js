/*app.controller("MainController", function($scope){

    $scope.understand = "I now understand how the scope works!";
    $scope.inputLoanValue = "";
    $scope.interestValue = 0;
    $scope.inputDays = "";
    $scope.interest7DaysValue = inputLoanValue*0.5;
    $scope.interest14DaysValue = 2*interest7DaysValue;
    $scope.interest30DaysValue = 2*interest14DaysValue;

});*/

var phonecatApp = angular.module('phonecatApp', []);

phonecatApp.controller('PhoneListCtrl', function ($scope) {
    $scope.phones = [
        {'name': 'Nexus S',
            'snippet': 'Fast just got faster with Nexus S.'},
        {'name': 'Motorola XOOM™ with Wi-Fi',
            'snippet': 'The Next, Next Generation tablet.'},
        {'name': 'MOTOROLA XOOM™',
            'snippet': 'The Next, Next Generation tablet.'}
    ];
});