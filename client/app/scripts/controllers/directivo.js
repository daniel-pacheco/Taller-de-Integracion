'use strict';

/**
 * @ngdoc function
 * @name clientAppApp.controller:DirectivoCtrl
 * @description
 * # DirectivoCtrl
 * Controller of the clientAppApp
 */
angular.module('clientAppApp')
  .controller('DirectivoCtrl', function ($scope, $q, $http, $modal, alumnoService) {
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

//modal
/*
var brnSearchModal = $modal({ scope: $scope, template: "views/aside.tpl.html", contentTemplate: false, html: true, show: false });

$scope.showModal = function () {
    brnSearchModal.$promise.then(brnSearchModal.show);
};
*/

//$scope.modal = {title: 'Title', content: 'Hello Modal<br />This is a multiline message!'};

  // Controller usage example C:\Taller-de-Integracion\client\..\bower_components\angular-strap\src\modal
  //
  function MyModalController($scope) {
    /*$scope.title = 'Aviso';
    $scope.content = 'El perfil de alumno DNI: {numDni} fue creado exitosamente';
    $scope.question = '\¿Desea crear otro perfil nuevo?;'*/
  }
  MyModalController.$inject = ['$scope'];
  var myModal = $modal({controller: MyModalController, templateUrl: 'views/templates/messageregistrationdetails.tpl.html', show: false, animation: 'am-fade-and-slide-top'});
  $scope.showModal = function() {
    myModal.$promise.then(myModal.show);
  };
  $scope.hideModal = function() {
    myModal.$promise.then(myModal.hide);
  };

//filters
$scope.dropDownOptions = ['2014', '2015', '2016', 'Todos', 'DNI/MAT'];
$scope.dropDownValue = '';

$scope.dropDownCursoOptions = ['5 Mat', '4 Mat', '3 Mat', '2 Mat'];
$scope.dropDownCursoValue = '';

$scope.dropDownDivisionOptions = ['U', 'A', 'B', 'C'];
$scope.dropDownDivisionValue = '';

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


//---utils
$scope.update = function (variable, value) {
  variable = value;
}

//---test
$scope.friends = [{name:'John', phone:'555-1276'},
                         {name:'Mary', phone:'800-BIG-MARY'},
                         {name:'Mike', phone:'555-4321'},
                         {name:'Adam', phone:'555-5678'},
                         {name:'Julie', phone:'555-8765'},
                         {name:'Juliette', phone:'555-5678'}];

});
