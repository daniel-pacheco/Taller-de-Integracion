'use strict';

/**
 * @ngdoc function
 * @name clientAppApp.controller:DirectivoCtrl
 * @description
 * # DirectivoCtrl
 * Controller of the clientAppApp
 */
angular.module('clientAppApp')
  .controller('DirectivoCtrl', function ($scope, $q, $http, alumnoService) {
    this.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];
$scope.listado1 = true;
$scope.listFilterIsEnabled = false;

//filters
$scope.filterByName = '';

$scope.seleccionar = function (id){

	if (id === 'listado') {
		$scope.listado1 = true;
	}else if (id === 'nuevoPerfil'){
		$scope.listado1 = false;
	};
}

$scope.search = function () {
  if (!$scope.listFilterIsEnabled) {
    $scope.listFilterIsEnabled = true;
  };
}
/*  
var getAll = function(){
    var deferred = $q.defer();
    var url = 'http://192.168.0.110:8080/EscuelaSantaLucia/rest/sAlumno/listAll';
    $http.get(url)
      .success(function(response){
        $scope.answer = response.data;
                
        deferred.resolve(response);
      })
      .error(function(response){
        alert("error");
        deferred.reject("Error al intentar traer datos del usuario");
      });
      return deferred.promise;
  };
getAll();*/

$scope.answer = {};
alumnoService.alumnoGetAll().then(function(response){
  $scope.answer = response.data;  
});

/*
$scope.data = [];

    $http.get('http://192.168.1.11:8080/EscuelaSantaLucia/rest/sAlumno/listAll').success(function(response){

      $scope.data = response;

    });*/

});
