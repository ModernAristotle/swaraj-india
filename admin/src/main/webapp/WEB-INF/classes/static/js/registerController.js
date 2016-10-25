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
            alert("Success");
            window.location.href = '/home';
        }, function errorCallback(response)
        {
            $scope.Error=response.data;
            console.log("Failed : "+ response.statusCode +", "+ response.statusText);
            $scope.errorMessage = "Failed";
        });
    };

    $scope.IsVisible = false;
    $scope.ShowHide = function ()
    {

        $scope.IsVisible = $scope.ShowPassport;

        $scope.selectedCountryName = null;
        $scope.CountryName = [];
        $http({
            method : "GET",
            url : "/service/us/location/countries",
            data : angular.toJson($scope.user),
            headers : {
                'Content-Type' : 'application/json'
            }
        }).then(function successCallback(response)
        {
            console.log(response.data);
            $scope.CountryName = response.data;
        }, function errorCallback(response)
        {
            $scope.Error=response.data;
        });
    }
});



