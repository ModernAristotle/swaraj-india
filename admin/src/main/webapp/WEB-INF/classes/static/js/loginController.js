app.controller('loginController', function($scope, $http) {

    $scope.loginUser = function() {
    alert(angular.toJson($scope.user));
        $http({
            method : "POST",
            url : "/service/us/login",
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

