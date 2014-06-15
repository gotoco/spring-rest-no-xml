define("app", ["angular", "angular-resource"], function(angular) {
    var app = angular.module("app", ["ngResource"] );

    var services = angular.module('ngdemo.services', ['ngResource']);

    services.factory('UserFactory', function ($resource) {
        return $resource('/ngdemo/rest/users', {}, {
            query: {
                method: 'GET',
                params: {},
                isArray: false
            }
        })
    });


    return app;
});