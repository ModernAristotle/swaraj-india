app.controller('loginController', function($scope, $http)
{
    document.getElementById("error").style.visibility = "hidden";
    $scope.user = {
        userName :"nextinfotechuk@gmail.com"
    };
    $scope.loginUser = function()
    {
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
            document.getElementById("error").style.visibility ="visible";
            $scope.errorMessage=response.data.message;
            console.log(response.data);

        });
    };
});



