var app = angular.module('myApp', ["ngResource", "ui.router"]);

app.run(function($state){ $state.go('main'); });

app.config(function($stateProvider) {
    $stateProvider
        .state('main', {
            url: '/main',
            controller:'mainCtrl',
            templateUrl: '../views/main.html'
        })
        .state('task', {
            url: '/task/{imageId}',
            controller:'mainCtrl',
            templateUrl: '../views/result.html',
            params : {
                id:null,
                ipAddress:null,
                host:null,
                nodes:null,
                iterations:null,
                startTime:null,
                endTime:null,
                totalTime:null
            }
        })
        .state('history', {
            url: '/history',
            controller:'mainCtrl',
            templateUrl: '../views/history.html'
        });
});

app.controller('mainCtrl', function($scope, $resource, $state, $stateParams) {
    $scope.history = [];
    $scope.iterations = 1;
    $scope.nodes = 1;
    $scope.params = $stateParams;
    $scope.search = "";

    $scope.appResource = $resource("/app/:operation/:image", {}, {
        getImage: {method: 'GET'},
        getImages: {method: 'GET', isArray: true},
        pwd: {method: 'GET'},
        processImage: {method: 'GET'},
        getHistory: {method: 'GET', isArray: true},
        searchList: {method: 'GET', isArray: true}
    });

    $scope.getImage = function(){
        $scope.appResource.getImage({operation: 'image'}, function(response) { console.log(response); });
    };

    $scope.getImages = function(image){
        $scope.appResource.getImages({operation: 'image', image: image}, function(response) { console.log(response); });
    };

    $scope.processImage = function(image){
        $scope.appResource.processImage({operation: 'process', imageId: image, iterations: $scope.iterations, nodes: $scope.nodes
        }, function(response) {
            $state.go('task', {
                id:response.id,
                ipAddress:response.ipAddress,
                host:response.host,
                nodes:response.nodes,
                iterations:response.iterations,
                startTime:response.startTime,
                endTime:response.endTime,
                totalTime:response.totalTime,
                imageId: image
            });
        });
    };

    $scope.pwd = function(){
        $scope.appResource.pwd({operation: 'pwd'}, function(response) { console.log(response); });
    };

    $scope.getHistory = function(){
        $scope.appResource.getHistory({operation: 'history'}, function(response) {
            $scope.history = response; });
    };

    $scope.searchList = function(){
        $scope.appResource.searchList({operation: 'searchList', userInput: $scope.search}, function(response) {
            $scope.history = response; });
    };
});