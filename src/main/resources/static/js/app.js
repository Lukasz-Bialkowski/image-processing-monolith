var app = angular.module('myApp', ["ngResource", "ngRoute"]);

app.config(function($routeProvider) {
    var resolveProjects = {
        projects: function (Projects) {
            return Projects.fetch();
        }
    };

    $routeProvider
        .when('/', {
            controller:'mainCtrl',
            templateUrl:'../views/main.html'
            // resolve: resolveProjects
        })
        .when('/edit/:projectId', {
            controller:'EditProjectController as editProject',
            templateUrl:'detail.html'
        })
        .otherwise({
            redirectTo:'/'
        });
});

app.controller('mainCtrl', function($scope, $resource, $http) {
    $scope.images = [];
    $scope.imagesResource = $resource("/process/:operation", {}, {
        listImages: {method: 'GET', params:{ operation: 'getImages'}}
    });

    $scope.getFoo = function(){
        $scope.foo = $scope.imagesResource.listImages({}, function(response) { console.log(response); });
    }
});