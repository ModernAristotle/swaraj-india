var app = angular.module('app', ['ngRoute','ngResource']);
app.config(function($routeProvider){
    $routeProvider
        .when('/me/basic.html',{
            templateUrl: '/me/basic.html',
            controller: 'rolesController'
        })
        .when('/me/security.html',{
            templateUrl: '/me/security.html',
            controller: 'rolesController'
        })
        .when('/me/contact.html',{
            templateUrl: '/me/contact.html',
            controller: 'rolesController'
        })
        .when('/me/location.html',{
            templateUrl: '/me/location.html',
            controller: 'rolesController'
        })
        .when('/me/donations.html',{
            templateUrl: '/me/donations.html',
            controller: 'usersController'
        })
        .when('/me/awaz.html',{
             templateUrl: '/me/awaz.html',
             controller: 'usersController'
         })
        .when('/admin/content/news.html',{
             templateUrl: '/admin/content/news.html',
             controller: 'usersController'
         })
        .when('/admin/content/blogs.html',{
             templateUrl: '/admin/content/blogs.html',
             controller: 'usersController'
         })
        .when('/admin/content/poll.html',{
             templateUrl: '/admin/content/poll.html',
             controller: 'usersController'
         })
        .when('/admin/content/events.html',{
             templateUrl: '/admin/content/events.html',
             controller: 'usersController'
         })
        .when('/admin/campaign/facebook.html',{
             templateUrl: '/admin/campaign/facebook.html',
             controller: 'usersController'
         })
        .when('/admin/campaign/twitter.html',{
             templateUrl: '/admin/campaign/twitter.html',
             controller: 'usersController'
         })
        .when('/admin/campaign/sms.html',{
             templateUrl: '/admin/campaign/sms.html',
             controller: 'usersController'
         })
        .when('/admin/campaign/email.html',{
             templateUrl: '/admin/campaign/email.html',
             controller: 'usersController'
         })
        .when('/admin/campaign/call.html',{
             templateUrl: '/admin/campaign/call.html',
             controller: 'usersController'
         })
        .when('/admin/campaign/mobilegroups.html',{
             templateUrl: '/admin/campaign/mobilegroups.html',
             controller: 'usersController'
         })
        .when('/admin/campaign/donations.html',{
             templateUrl: '/admin/campaign/donations.html',
             controller: 'usersController'
         })
        .when('/admin/campaign/candidates.html',{
             templateUrl: '/admin/campaign/candidates.html',
             controller: 'usersController'
         })
        .when('/admin/developer/domain.html',{
             templateUrl: '/admin/developer/domain.html',
             controller: 'domainController'
         })
        .when('/admin/developer/urlmapping.html',{
             templateUrl: '/admin/developer/urlmapping.html',
             controller: 'usersController'
         })
        .when('/admin/developer/templates.html',{
             templateUrl: '/admin/developer/templates.html',
             controller: 'usersController'
         })
        .when('/admin/developer/static.html',{
             templateUrl: '/admin/developer/static.html',
             controller: 'usersController'
         })
        .when('/admin/user/roles.html',{
             templateUrl: '/admin/user/roles.html',
             controller: 'usersController'
         })
        .when('/admin/user/search.html',{
             templateUrl: '/admin/user/search.html',
             controller: 'usersController'
         })
        .when('/admin/user/edit.html',{
             templateUrl: '/admin/user/edit.html',
             controller: 'usersController'
         })
        .when('/admin/location/update.html',{
             templateUrl: '/admin/location/update.html',
             controller: 'usersController'
         })
         .when('/register.html',{
             templateUrl: '/registerView.html',
             controller: 'registerController'
         })
       .otherwise(
            { redirectTo: '/'}
        );
});
