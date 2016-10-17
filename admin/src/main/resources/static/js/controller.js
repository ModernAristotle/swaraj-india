app.controller('registereController', function($scope) {
    $scope.headingTitle = "User List";

    $scope.registerUser = function() {
    alert($scope.form);
        $http({
            method : "POST",
            url : "/service/us/register/web",
            data : angular.toJson($scope.form),
            headers : {
                'Content-Type' : 'application/json'
            }
        }).then(function successCallback(response) {
              $window.location.href = '/index.html';
          }, function errorCallback(response) {
              console.log(response.statusText);
          });
    };
});

app.controller('rolesController', function($scope) {
    $scope.headingTitle = "Roles List";
});
