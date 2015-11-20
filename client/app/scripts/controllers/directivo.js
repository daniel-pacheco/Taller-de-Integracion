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

//tooltips
$scope.tooltip = {
  tooltipProfile : {
    'title' : 'Perfil'
  }, tooltipAttendance : {
    'title' : 'Inasistencias'
  }, tooltipReportCard : {
    'title' : 'Libreta de Calificaciones'
  }, tooltipAcademicPerformance : {
    'title' : 'Desempeño académico'
  }


};

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

//---Llamadas al servicio ALUMNO---
$scope.answer = [];
alumnoService.alumnoGetAll().then(function(response){
  $scope.answer = response.data;  
});

//---test
$scope.friends = [{name:'John', phone:'555-1276'},
                         {name:'Mary', phone:'800-BIG-MARY'},
                         {name:'Mike', phone:'555-4321'},
                         {name:'Adam', phone:'555-5678'},
                         {name:'Julie', phone:'555-8765'},
                         {name:'Juliette', phone:'555-5678'}];

});
