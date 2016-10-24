var app = angular.module('app', ['ngRoute','ngResource']);
app.config(function($routeProvider){
    $routeProvider
         .when('/login.html',{
             templateUrl: '/loginView.html',
             controller: 'loginController'
         })
         .when('/',{
              templateUrl: '/loginView.html',
              controller: 'loginController'
          })
       .otherwise(
            { redirectTo: '/'}
        );
});
