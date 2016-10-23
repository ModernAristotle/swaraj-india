
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

    loadDomains($scope, $http)

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
              console.log("Domain saved succesfully "+ angular.toJson(response));
              loadDomains($scope, $http)
              $scope.cancel();
          }, function errorCallback(response) {
              console.log(response.statusText);
          });

      };
});


app.controller('domainTemplateController', function($scope, $http) {

    console.log("fetching domain templates");
    $scope.showTable = true;

    loadDomains($scope, $http)
    loadDomainTemplates($scope, $http)

    $scope.editDomainTemplate = function(domainTemplate) {
        $scope.showTable = false;
        $scope.selectedDomainTemplate = domainTemplate;
    };

     $scope.cancel = function() {
         console.log("Edit Domain Template cancelled");
         $scope.showTable = true;
     };
     $scope.newDomainTemplate = function() {
         $scope.showTable = false;
         $scope.selectedDomain = {};
     };

     $scope.saveDomainTemplate = function() {
          $scope.showTable = true;
          console.log("Saving Domain Template : "+ angular.toJson($scope.selectedDomainTemplate));
          $http({
              method : "POST",
              url : "/service/s/domaintemplate",
              data : angular.toJson($scope.selectedDomainTemplate),
              headers : {
                  'Content-Type' : 'application/json'
              }
          }).then(function successCallback(response) {
              console.log("Domain saved succesfully "+ angular.toJson(response));
              loadDomainTemplates($scope, $http)
              $scope.cancel();

          }, function errorCallback(response) {
              console.log(response.statusText);
          });

      };
});

app.controller('urlMappingController', function($scope, $http) {

    console.log("fetching Url mappings");
    $scope.showTable = true;
    $scope.domainSelected = false;

    loadDomains($scope, $http)

    $scope.onDomainSelection = function() {
        $scope.domainSelected = true;
        //Load all Url Mapping for this domain
        loadUrlMappingOfDomain($scope, $http, $scope.selectedDomainId)
    };
    $scope.editUrlMapping = function(urlMapping) {
        $scope.showTable = false;
        $scope.selectedUrlMapping = urlMapping;
    };

     $scope.cancel = function() {
         console.log("Edit UrlMapping cancelled");
         $scope.showTable = true;
     };
     $scope.newUrlMapping = function() {
         $scope.showTable = false;
         $scope.selectedUrlMapping = {domainId: $scope.selectedDomainId, httpCacheTimeSeconds:600};
     };

     $scope.saveUrlMapping = function() {
          $scope.showTable = true;
          console.log("Saving Url Mapping : "+ angular.toJson($scope.selectedUrlMapping));
          $http({
              method : "POST",
              url : "/service/s/urlmapping",
              data : angular.toJson($scope.selectedUrlMapping),
              headers : {
                  'Content-Type' : 'application/json'
              }
          }).then(function successCallback(response) {
              console.log("Url Mapping saved succesfully "+ angular.toJson(response));
              loadUrlMappingOfDomain($scope, $http, $scope.selectedUrlMapping.domainId)
              $scope.cancel();

          }, function errorCallback(response) {
              console.log(response.statusText);
          });

      };
});

function loadDomains(scope, http){
http({
        method : "GET",
        url : "/service/s/domain"
     }).then(function successCallback(response) {
        console.log(angular.toJson(response.data));
        scope.domains = response.data;
     }, function errorCallback(response) {
        console.log(response.statusText);
     });
}

function loadDomainTemplates(scope, http){

http({
        method : "GET",
        url : "/service/s/domaintemplate"
      }).then(function successCallback(response) {
        console.log(angular.toJson(response.data));
        scope.domainTemplates = response.data;
      }, function errorCallback(response) {
        console.log(response.statusText);
      });

}

function loadUrlMappingOfDomain(scope, http, domainId){

http({
        method : "GET",
        url : "/service/s/urlmapping/domain/"+domainId
      }).then(function successCallback(response) {
        console.log(angular.toJson(response.data));
        scope.urlMappings = response.data;
      }, function errorCallback(response) {
        console.log(response.statusText);
      });

}
