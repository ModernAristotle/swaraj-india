app.controller('registerController', function($scope, $http)
{
    $scope.headingTitle = "User List";
    $scope.user = {};
    $scope.registerUser = function()
    {
        $http({
            method : "POST",
            url : "/service/us/register/web",
            data : angular.toJson($scope.user),
            headers : {
                'Content-Type' : 'application/json'
            }
        }).then(function successCallback(response)
        {
            window.location.href = '/home';
        }, function errorCallback(response)
        {
            $scope.Error=response.data;
        });
    };
    $scope.countryIsVisible = false;
    $scope.showhidecountry = function ()
    {
        $scope.countryIsVisible = $scope.showcountry;
        $http({
            method : "GET",
            url : "/service/us/location/countries",
            data : angular.toJson($scope.user),
            headers : {
                'Content-Type' : 'application/json'
            }
        }).then(function successCallback(response)
        {
            $scope.Country=response.data;
            $scope.Countryname=response.data.name;
            $scope.CountryisdCode=response.data.isdCode;
        }, function errorCallback(response)
        {
            $scope.Error=response.data;
        });
    }
});

