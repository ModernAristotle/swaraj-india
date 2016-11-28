
app.controller('registereController', function($scope) {
    $scope.headingTitle = "Registration";

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

app.controller('domainController', function($scope, $rootScope, $http) {
    $rootScope.headingTitle = "Domains";
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


app.controller('domainTemplateController', function($scope, $rootScope, $http) {

    $rootScope.headingTitle = "Domain Templates";

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
     $scope.refreshDomainTemplate = function() {
          console.log("Refreshing DOmain Template");
          refreshDOmainTemplateFromGithub($scope, $http, $scope.selectedDomainTemplate.id)
      };

     $scope.newDomainTemplate = function() {
         $scope.showTable = false;
         $scope.selectedDomain = {};
     };

     $scope.saveDomainTemplate = function() {
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

app.controller('urlMappingController', function($scope, $rootScope, $http) {

    $rootScope.headingTitle = "Url Mapping";

    console.log("fetching Url mappings");
    $scope.showTable = true;
    $scope.domainSelected = false;
    $scope.selectedUrlMapping = {};
    loadDomains($scope, $http)

    $scope.onDomainSelection = function() {
        $scope.domainSelected = true;
        //Load all Url Mapping for this domain
        loadUrlMappingOfDomain($scope, $http, $scope.selectedDomainId)
    };
    $scope.editUrlMapping = function(urlMapping) {
        $scope.showTable = false;
        $scope.selectedUrlMapping = urlMapping;
        loadDataPluginsOfUrlMapping($scope, $http, $scope.selectedUrlMapping.id);
    };

     $scope.cancel = function() {
         console.log("Edit UrlMapping cancelled");
         $scope.showTable = true;
     };
     $scope.newUrlMapping = function() {
         $scope.showTable = false;
         $scope.selectedUrlMapping = {domainId: $scope.selectedDomainId, httpCacheTimeSeconds:600};
         loadDataPluginsOfUrlMapping($scope, $http, 0);
     };

     $scope.pluginSelection = [];
     $scope.$watch('pluginSelection', function () {
       console.log('change', $scope.pluginSelection);
       $scope.selectedUrlMapping.dataPlugins = [];
       angular.forEach($scope.pluginSelection, function (dataPluginSelection, index) {
           console.log('dataPluginSelection : ', dataPluginSelection);
           if (dataPluginSelection) {
           $scope.selectedUrlMapping.dataPlugins.push($scope.urlMappingDataPlugins[index].id);
           console.log('$scope.selectedUrlMapping : ', $scope.selectedUrlMapping);
           }
       });
     }, true);

     $scope.saveUrlMapping = function() {
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


app.controller('htmlPartController', function($scope, $rootScope, $http) {

    $rootScope.headingTitle = "Html Part";
    console.log("fetching Domain Templates");
    $scope.showTable = true;
    $scope.domainTemplateSelected = false;

    loadDomainTemplates($scope, $http)


    $scope.onDomainTemplateSelection = function() {
        $scope.domainTemplateSelected = true;
        //Load all Url Mapping for this domain
        loadHtmlPartOfDomainTemplate($scope, $http, $scope.selectedDomainTemplateId);
        loadGitFilesOfDomainTemplate($scope, $http, $scope.selectedDomainTemplateId);
    };
    $scope.editHtmlPart = function(htmlPart) {
        $scope.showTable = false;
        $scope.selectedHtmlPart = htmlPart;
    };

     $scope.cancel = function() {
         console.log("Edit Html Part cancelled");
         $scope.showTable = true;
     };
     $scope.newHtmlPart = function() {
         $scope.showTable = false;
         $scope.selectedHtmlPart = {domainTemplateId: $scope.selectedDomainTemplateId};
     };

     $scope.saveHtmlPart = function() {
          console.log("Saving Html part : "+ angular.toJson($scope.selectedHtmlPart));
          $http({
              method : "POST",
              url : "/service/s/htmlpart",
              data : angular.toJson($scope.selectedHtmlPart),
              headers : {
                  'Content-Type' : 'application/json'
              }
          }).then(function successCallback(response) {
              console.log("Url Mapping saved succesfully "+ angular.toJson(response));
              loadHtmlPartOfDomainTemplate($scope, $http, $scope.selectedDomainTemplateId)
              $scope.cancel();

          }, function errorCallback(response) {
              console.log(response.statusText);
          });

      };
});

app.controller('urlTemplateController', function($scope, $rootScope, $http) {

    $rootScope.headingTitle = "Url Templates";
    console.log("fetching Domain Templates");
    $scope.showTable = true;
    $scope.domainTemplateSelected = false;

    loadDomainTemplates($scope, $http);

    $scope.onDomainTemplateSelection = function() {
        $scope.domainTemplateSelected = true;
        loadUrlTemplateOfDomainTemplate($scope, $http, $scope.selectedDomainTemplateId);
        loadMainHtmlPartOfDomainTemplate($scope, $http, $scope.selectedDomainTemplateId);
        loadGitFilesOfDomainTemplate($scope, $http, $scope.selectedDomainTemplateId);

        for(i = 0; i < $scope.domainTemplates.length; i++){
            if($scope.domainTemplates[i].id == $scope.selectedDomainTemplateId){
                loadUrlMappingOfDomain($scope, $http, $scope.domainTemplates[i].domainId);
            }
        }

    };
    $scope.editUrlTemplate = function(urlTemplate) {
        $scope.showTable = false;
        $scope.selectedUrlTemplate = urlTemplate;
    };

     $scope.cancel = function() {
         console.log("Edit Url Template cancelled");
         $scope.showTable = true;
     };
     $scope.newUrlTemplate = function() {
         $scope.showTable = false;
         $scope.selectedUrlTemplate = {domainTemplateId: $scope.selectedDomainTemplateId};
     };

     $scope.saveUrlTemplate = function() {
          console.log("Saving Url Template : "+ angular.toJson($scope.selectedUrlTemplate));
          $http({
              method : "POST",
              url : "/service/s/urltemplate",
              data : angular.toJson($scope.selectedUrlTemplate),
              headers : {
                  'Content-Type' : 'application/json'
              }
          }).then(function successCallback(response) {
              console.log("Url Mapping saved succesfully "+ angular.toJson(response));
              loadUrlTemplateOfDomainTemplate($scope, $http, $scope.selectedDomainTemplateId);
              $scope.cancel();

          }, function errorCallback(response) {
              console.log(response.statusText);
          });

      };
});

app.controller('pressReleaseController', function($scope, $rootScope, $http) {

    $rootScope.headingTitle = "Press Releases";
    console.log("fetching Press Releases");
    $scope.showTable = true;

    loadPressReleases($scope, $http);
    $scope.selectedPressRelease = {};

    $scope.editPressRelease = function(pressRelease) {
        $scope.showTable = false;
        $scope.selectedPressRelease = pressRelease;
        CKEDITOR.instances.content.setData(pressRelease.content);
    };

     $scope.cancel = function() {
         console.log("Edit Press release cancelled");
         $scope.showTable = true;
     };
     $scope.newPressRelease = function() {
         $scope.showTable = false;
         $scope.selectedPressRelease = {};
     };

     $scope.savePressRelease     = function() {
     $scope.selectedPressRelease.content = CKEDITOR.instances.content.getData();

          console.log("Saving Press release : "+ angular.toJson($scope.selectedPressRelease));
          $http({
              method : "POST",
              url : "/service/s/pressrelease",
              data : angular.toJson($scope.selectedPressRelease),
              headers : {
                  'Content-Type' : 'application/json'
              }
          }).then(function successCallback(response) {
              console.log("Press release saved succesfully "+ angular.toJson(response));
              loadPressReleases($scope, $http, $scope.selectedDomainTemplateId);
              $scope.cancel();

          }, function errorCallback(response) {
              console.log(response.statusText);
          });

      };
});


function loadDomains(scope, http){
console.log("Loading Domains ");

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
console.log("Loading Domain templates");

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
console.log("Loading Url Mapping of Domain : "+ domainId);
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

function loadDataPluginsOfUrlMapping(scope, http, urlMappingId){
console.log("Loading Data plugins of Url Mapping : "+ urlMappingId);
http({
        method : "GET",
        url : "/service/s/custom/plugin/"+urlMappingId
      }).then(function successCallback(response) {
        console.log(angular.toJson(response.data));
        scope.urlMappingDataPlugins = response.data;
      }, function errorCallback(response) {
        console.log(response.statusText);
      });

}


function loadHtmlPartOfDomainTemplate(scope, http, domainTemplateId){
console.log("Loading Html part of Domain template : "+ domainTemplateId);

http({
        method : "GET",
        url : "/service/s/htmlpart/domaintemplate/"+domainTemplateId
      }).then(function successCallback(response) {
        console.log(angular.toJson(response.data));
        scope.htmlParts = response.data;
      }, function errorCallback(response) {
        console.log(response.statusText);
      });

}

function loadGitFilesOfDomainTemplate(scope, http, domainTemplateId){
console.log("Loading Git Files of Domain template : "+ domainTemplateId);

http({
        method : "GET",
        url : "/service/s/gitfiles/domaintemplate/"+domainTemplateId
      }).then(function successCallback(response) {
        console.log(angular.toJson(response.data));
        scope.domainTemplateGitFiles = response.data;
      }, function errorCallback(response) {
        console.log(response.statusText);
      });

}



function loadMainHtmlPartOfDomainTemplate(scope, http, domainTemplateId){
console.log("Loading Main Html part of Domain template : "+ domainTemplateId);

http({
        method : "GET",
        url : "/service/s/htmlpart/main/domaintemplate/"+domainTemplateId
      }).then(function successCallback(response) {
        console.log("Success: "+ angular.toJson(response.data));
        scope.mainHtmlParts = response.data;
      }, function errorCallback(response) {
        console.log("failed : "+ response.statusText);
      });

}

function loadUrlTemplateOfDomainTemplate(scope, http, domainTemplateId){
console.log("Loading Url Template of Domain template : "+ domainTemplateId);

http({
        method : "GET",
        url : "/service/s/urltemplate/domaintemplate/"+domainTemplateId
      }).then(function successCallback(response) {
        console.log(angular.toJson(response.data));
        scope.urlTemplates = response.data;
      }, function errorCallback(response) {
        console.log(response.statusText);
      });

}

function refreshDOmainTemplateFromGithub(scope, http, domainTemplateId){
console.log("Loading Url Template of Domain template : "+ domainTemplateId);

http({
        method : "GET",
        url : "/service/s/refresh/domaintemplate/"+domainTemplateId
      }).then(function successCallback(response) {
        console.log(angular.toJson(response.data));
      }, function errorCallback(response) {
        console.log(response.statusText);
      });

}

function loadPressReleases(scope, http){
console.log("Loading Press Releases");

http({
        method : "GET",
        url : "/service/s/pressrelease"
      }).then(function successCallback(response) {
        console.log(angular.toJson(response.data));
        scope.pressReleases = response.data;
      }, function errorCallback(response) {
        console.log(response.statusText);
      });

}
