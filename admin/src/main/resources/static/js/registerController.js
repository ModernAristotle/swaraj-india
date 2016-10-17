app.controller('registerController', function($scope, $http) {
    $scope.headingTitle = "User List";

    $scope.user = {};

    $scope.registerUser = function() {
    alert(angular.toJson($scope.user));
        $http({
            method : "POST",
            url : "/service/us/register/web",
            data : angular.toJson($scope.user),
            headers : {
                'Content-Type' : 'application/json'
            }
        }).then(function successCallback(response) {
              alert("Success");
              window.location.href = '/home';
          }, function errorCallback(response) {
              console.log("Failed : "+ response.statusCode +", "+ response.statusText);
              alert(response.statusText);
              $scope.errorMessage = "Failed";
          });
    };
});

