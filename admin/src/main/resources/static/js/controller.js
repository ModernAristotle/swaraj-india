
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

app.controller('domainController', function($scope, $http) {

    console.log("fetching domains");
    $scope.showTable = true;

    $http({
                method : "GET",
                url : "/service/s/domain"
          }).then(function successCallback(response) {
                console.log(angular.toJson(response.data));
                $scope.domains = response.data;
          }, function errorCallback(response) {
                console.log(response.statusText);
          });

    $scope.editDomain = function(domain) {
        $scope.showTable = false;
        $scope.selectedDomain = domain;
    };

     $scope.cancel = function() {
         console.log("Edit Domain cancelled");

         $scope.showTable = true;
     };
     $scope.newDomain = function() {
         $scope.showTable = false;
         $scope.selectedDomain = {};
     };

     $scope.saveDomain = function() {
          $scope.showTable = true;
          console.log("Saving Domain "+ angular.toJson($scope.selectedDomain));
          $http({
              method : "POST",
              url : "/service/s/domain",
              data : angular.toJson($scope.selectedDomain),
              headers : {
                  'Content-Type' : 'application/json'
              }
          }).then(function successCallback(response) {
              console.log("Domain saved succesfully "+ angular.toJson($scope.selectedDomain));

          }, function errorCallback(response) {
              console.log(response.statusText);
          });

      };
});
