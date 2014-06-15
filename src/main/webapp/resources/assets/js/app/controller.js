require(["app"], function(app) {
    app.controller(
        "HelloController",
        function($scope) {
            $scope.sayHello = function() {
                return "Hello";
            }
        }
    );
});