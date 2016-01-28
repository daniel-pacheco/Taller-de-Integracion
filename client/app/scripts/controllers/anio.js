'use strict';

/**
 * @ngdoc function
 * @name clientAppApp.controller:AnioCtrl
 * @description
 * # AnioCtrl
 * Controller of the clientAppApp
 */
 angular.module('clientAppApp')
 .config(function($stateProvider, USER_ROLES) {
    $stateProvider
    .state('anio', {
        url: '/anio',
        templateUrl: 'views/anio.html',
        controller: 'AnioCtrl',
        data: {
        	pageTitle: 'Anio',
          authorizedRoles: [USER_ROLES.admin]           
        }
    });
})
 .controller('AnioCtrl', function ($scope) {
 	
 	$scope.tooltip = {
 		tooltipEdit : {
 			'title' : 'Editar'
 		}
 	};

 	$scope.listadoAnio = true;
 	$scope.administrarAnio = false;
 	$scope.nuevoAnio = false;

 	$scope.seleccionar = function(id) {//Hacer una funcion que ponga en true el nombre de variable que le llegue y el resto en false (toogle de tres variables)
 		if (id === 'listadoAnio') {
 			$scope.administrarAnio = false;
 			$scope.nuevoAnio = false;
 			$scope.listadoAnio = true;
 			
 		}else if (id === 'nuevoAnio'){
 			$scope.listadoAnio = false;
 			$scope.administrarAnio = false;
 			$scope.nuevoAnio = true;
 		} else if (id === "administrarAnio"){
 			$scope.listadoAnio = false;
 			$scope.nuevoAnio = false;
 			$scope.administrarAnio = true;
 		};
 	};


  $scope.dropDownOptions = ['7º','4º', '5º', '3º'];
  $scope.dropDownValue = '';

   $scope.dropDownDiviosnOptions = ['U','A', 'B', 'C'];
  $scope.dropDownDivisionValue = '';

  $scope.acept = function () {
  if (!$scope.listAnioIsEnabled) {
    $scope.listAnioIsEnabled = true;
  };
}

 	//Test
 	$scope.anios = [{anio:'7º', division:'U', turno: 'Tarde', cantidadDeAlumnos: '21'},
 	{anio:'1º', division:'U', turno: 'Tarde', cantidadDeAlumnos: '32'},
 	{anio:'2º', division:'U', turno: 'Tarde', cantidadDeAlumnos: '28'},
 	{anio:'3º', division:'U', turno: 'Tarde', cantidadDeAlumnos: '22'},
 	{anio:'4º', division:'U', turno: 'Tarde', cantidadDeAlumnos: '45'},
 	{anio:'5º', division:'U', turno: 'Tarde', cantidadDeAlumnos: '32'}];

 	//---test list alumnos
$scope.test = [{nro:'1', name:'John', firstName:'Lennon', DNI:'555555555'},
					{nro:'1', name:'John', firstName:'Lennon', DNI:'555555555'},
					{nro:'2', name:'John', firstName:'Lennon', DNI:'555555555'},
					{nro:'3', name:'John', firstName:'Lennon', DNI:'555555555'},
					{nro:'4', name:'John', firstName:'Lennon', DNI:'555555555'},
					{nro:'5', name:'John', firstName:'Lennon', DNI:'555555555'},
					{nro:'6', name:'John', firstName:'Lennon', DNI:'555555555'},
					{nro:'7', name:'John', firstName:'Lennon', DNI:'555555555'}];

 });
