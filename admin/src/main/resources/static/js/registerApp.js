var app = angular.module('app', ['ngRoute','ngResource']);
app.config(function($routeProvider){
    $routeProvider
         .when('/register.html',{
             templateUrl: '/registerView.html',
             controller: 'registerController'
         })
         .when('/',{
              templateUrl: '/registerView.html',
              controller: 'registerController'
          })
       .otherwise(
            { redirectTo: '/'}
        );
});
