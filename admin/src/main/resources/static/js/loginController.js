app.controller('loginController', function($scope, $http)
{

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
            /* alert("Success");*/
            window.location.href = '/home';
        }, function errorCallback(response)
        {
            $scope.errorMessage=response.data.message;
            console.log(response.data);
            //console.log("Failed : "+ response.statusCode +", "+ response.statusText);
            $scope.errorMessage = "Failed";
            // $('#error').visibility.visible();
            //  $('#error').visibility.true;
        });
    };
});