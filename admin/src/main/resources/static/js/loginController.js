app.controller('loginController', function($scope, $rootScope, $http)
{
    $scope.user = {
        userName :"nextinfotechuk@gmail.com"
    };
    $rootScope.error = {
        show : false
    };
    $scope.loginUser = function()
    {
        $rootScope.error.show = false;
        $http({
            method : "POST",
            url : "/service/us/login",
            data : angular.toJson($scope.user),
            headers : {
                'Content-Type' : 'application/json'
            }
        }).then(function successCallback(response) {
            window.location.href = '/home';
        }, function errorCallback(response)
        {
            $rootScope.error.message = response.data.message;
            $rootScope.error.show = true;
        });
    };
});



